package com.alexjck.plugins.delorean;

import java.util.Collections;
import java.util.Set;

import org.graylog2.plugin.PluginConfigBean;
import org.graylog2.plugin.PluginModule;

import com.alexjck.plugins.delorean.alerts.DeloreanAlertCondition;
import com.alexjck.plugins.delorean.decorator.DeloreanDecorator;

public class DeloreanAlertConditionModule extends PluginModule {
    /**
     * Returns all configuration beans required by this plugin.
     *
     * Implementing this method is optional. The default method returns an empty {@link Set}.
     */
    @Override
    public Set<? extends PluginConfigBean> getConfigBeans() {
        return Collections.emptySet();
    }

    @Override
    protected void configure() {
        /*
         * Register your plugin types here.
         *
         * Examples:
         *
         * addMessageInput(Class<? extends MessageInput>);
         * addMessageFilter(Class<? extends MessageFilter>);
         * addMessageOutput(Class<? extends MessageOutput>);
         * addPeriodical(Class<? extends Periodical>);
         * addAlarmCallback(Class<? extends AlarmCallback>);
         * addInitializer(Class<? extends Service>);
         * addRestResource(Class<? extends PluginRestResource>);
         *
         *
         * Add all configuration beans returned by getConfigBeans():
         *
         * addConfigBeans();
         */

        addAlertCondition(DeloreanAlertCondition.class.getCanonicalName(),
        		DeloreanAlertCondition.class,
        		DeloreanAlertCondition.Factory.class);

        installSearchResponseDecorator(searchResponseDecoratorBinder(),
                DeloreanDecorator.class,
                DeloreanDecorator.Factory.class);
    }
}
