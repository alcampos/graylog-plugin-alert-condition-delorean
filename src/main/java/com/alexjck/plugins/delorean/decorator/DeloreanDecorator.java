package com.alexjck.plugins.delorean.decorator;

import com.google.inject.assistedinject.Assisted;

import org.graylog2.decorators.Decorator;
import org.graylog2.plugin.configuration.ConfigurationRequest;
import org.graylog2.plugin.decorators.SearchResponseDecorator;
import org.graylog2.rest.resources.search.responses.SearchResponse;

import javax.inject.Inject;

public class DeloreanDecorator implements SearchResponseDecorator {

	@SuppressWarnings("unused")
	private Decorator decorator;

	@Inject
	public DeloreanDecorator(@Assisted Decorator decorator) {
		this.decorator = decorator;
	}

	@Override
	public SearchResponse apply(SearchResponse searchResponse) {
		return searchResponse;
	}

	public interface Factory extends SearchResponseDecorator.Factory {
		@Override
		DeloreanDecorator create(Decorator decorator);

		@Override
		DeloreanDecorator.Config getConfig();

		@Override
		DeloreanDecorator.Descriptor getDescriptor();
	}

	public static class Config implements SearchResponseDecorator.Config {

		@Override
		public ConfigurationRequest getRequestedConfiguration() {
			return new ConfigurationRequest();
		}
	}

	public static class Descriptor extends SearchResponseDecorator.Descriptor {
		public Descriptor() {
			super("Delorean Field Content Alert Condition",
					"https://github.com/alcampos/graylog-plugin-alert-condition-delorean",
					"This condition is triggered when in the past the content of messages is equal to a defined value.");
		}
	}
}