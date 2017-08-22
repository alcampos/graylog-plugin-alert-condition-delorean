package com.alexjck.plugins.delorean.alerts;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.graylog2.Configuration;
import org.graylog2.alerts.AbstractAlertCondition;
import org.graylog2.indexer.results.ResultMessage;
import org.graylog2.indexer.results.SearchResult;
import org.graylog2.indexer.searches.Searches;
import org.graylog2.indexer.searches.Sorting;
import org.graylog2.plugin.Message;
import org.graylog2.plugin.MessageSummary;
import org.graylog2.plugin.Tools;
import org.graylog2.plugin.alarms.AlertCondition;
import org.graylog2.plugin.configuration.ConfigurationRequest;
import org.graylog2.plugin.configuration.fields.ConfigurationField;
import org.graylog2.plugin.configuration.fields.NumberField;
import org.graylog2.plugin.configuration.fields.TextField;
import org.graylog2.plugin.indexer.searches.timeranges.AbsoluteRange;
import org.graylog2.plugin.streams.Stream;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class DeloreanAlertCondition extends AbstractAlertCondition {
    private static final Logger LOG = LoggerFactory.getLogger(DeloreanAlertCondition.class);

    private final Searches searches;
    @SuppressWarnings("unused")
	private final Configuration configuration;
    private final String query;
    private final Integer backtime;
    private final Integer staytime;

    public interface Factory extends AlertCondition.Factory {
        @Override
        DeloreanAlertCondition create(Stream stream,
                                               @Assisted("id") String id,
                                               DateTime createdAt,
                                               @Assisted("userid") String creatorUserId,
                                               Map<String, Object> parameters,
                                               @Assisted("title") @Nullable String title);

        @Override
        Config config();

        @Override
        Descriptor descriptor();
    }

    public static class Config implements AlertCondition.Config {
        public Config() {
        }

        @Override
        public ConfigurationRequest getRequestedConfiguration() {
            final ConfigurationRequest configurationRequest = ConfigurationRequest.createWithFields(
                    new TextField("query", "Query", "", "Query that should be checked", ConfigurationField.Optional.NOT_OPTIONAL),
                    new NumberField("backtime", "Back Time", 0, "Number of minutes to go back to the past to check the value", ConfigurationField.Optional.NOT_OPTIONAL),
                    new NumberField("staytime", "Stay Time", 1, "Number of minutes of the time to stay in the past", ConfigurationField.Optional.NOT_OPTIONAL)
            );
            configurationRequest.addFields(AbstractAlertCondition.getDefaultConfigurationFields());
            return configurationRequest;
        }
    }

    public static class Descriptor extends AlertCondition.Descriptor {
        public Descriptor() {
            super(
                "Delorean Query Alert Condition",
                "https://github.com/alcampos/graylog-plugin-alert-condition-delorean",
                "This condition is triggered when in the past the content of messages match a defined query."
            );
        }
    }

	@AssistedInject
    public DeloreanAlertCondition(Searches searches,
                                           Configuration configuration,
                                           @Assisted Stream stream,
                                           @Nullable @Assisted("id") String id,
                                           @Assisted DateTime createdAt,
                                           @Assisted("userid") String creatorUserId,
                                           @Assisted Map<String, Object> parameters,
                                           @Assisted("title") @Nullable String title) {
        super(stream, id, DeloreanAlertCondition.class.getCanonicalName(), createdAt, creatorUserId, parameters, title);
        this.searches = searches;
        this.configuration = configuration;
        this.query = (String) parameters.get("query");
        this.backtime = (Integer) parameters.get("backtime");
        this.staytime = (Integer) parameters.get("staytime");
    }

    @Override
    public CheckResult runCheck() {        
        String filter = "streams:" + stream.getId();
        Integer backlogSize = getBacklog();
        boolean backlogEnabled = false;
        int searchLimit = 1;

        if(backlogSize != null && backlogSize > 0) {
            backlogEnabled = true;
            searchLimit = backlogSize;
        }

        SearchResult result = searches.search(
		    query,
		    filter,
		    AbsoluteRange.create(Tools.nowUTC().minus(Minutes.minutes(backtime)).minus(Minutes.minutes(staytime)), Tools.nowUTC().minus(Minutes.minutes(backtime))),
		    searchLimit,
		    0,
		    new Sorting(Message.FIELD_TIMESTAMP, Sorting.Direction.DESC)
		);

		final List<MessageSummary> summaries;
		if (backlogEnabled) {
		    summaries = Lists.newArrayListWithCapacity(result.getResults().size());
		    for (ResultMessage resultMessage : result.getResults()) {
		        final Message msg = resultMessage.getMessage();
		        summaries.add(new MessageSummary(resultMessage.getIndex(), msg));
		    }
		} else {
		    summaries = Collections.emptyList();
		}

		final long count = result.getTotalResults();

		final String resultDescription = "Stream received messages matching <" + query + "> "
		    + "(Current grace time: " + grace + " minutes)";

		if (count > 0) {
		    LOG.debug("Alert check <{}> found [{}] messages.", id, count);
		    return new CheckResult(true, this, resultDescription, Tools.nowUTC(), summaries);
		} else {
		    LOG.debug("Alert check <{}> returned no results.", id);
		    return new NegativeCheckResult();
		}
    }

    @Override
    public String getDescription() {
        return "query: " + query
                + ", grace: " + grace
                + ", backtime: " + backtime
                + ", repeat notifications: " + repeatNotifications;
    }
}