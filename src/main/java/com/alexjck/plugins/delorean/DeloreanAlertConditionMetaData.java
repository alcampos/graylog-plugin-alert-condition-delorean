package com.alexjck.plugins.delorean;

import org.graylog2.plugin.PluginMetaData;
import org.graylog2.plugin.ServerStatus;
import org.graylog2.plugin.Version;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

public class DeloreanAlertConditionMetaData implements PluginMetaData {
    private static final String PLUGIN_PROPERTIES = "com.alexjck.graylog.plugins.graylog-plugin-alert-condition-delorean/graylog-plugin.properties";

    @Override
    public String getUniqueId() {
        return "com.alexjck.plugins.delorean.alerts.DeloreanAlertCondition";
    }

    @Override
    public String getName() {
        return "Delorean Alert Condition";
    }

    @Override
    public String getAuthor() {
        return "Alexander Campos <alexander.campos@mercadolibre.com>";
    }

    @Override
    public URI getURL() {
        return URI.create("https://github.com/alcampos/graylog-plugin-alert-condition-delorean");
    }

    @Override
    public Version getVersion() {
        return Version.fromPluginProperties(getClass(), PLUGIN_PROPERTIES, "version", Version.from(0, 0, 2, "unknown"));
    }

    @Override
    public String getDescription() {
        return "Alert condition that check an alert in the past and go back to the future";
    }

    @Override
    public Version getRequiredVersion() {
        return Version.fromPluginProperties(getClass(), PLUGIN_PROPERTIES, "graylog.version", Version.from(2, 3, 0));
    }

    @Override
    public Set<ServerStatus.Capability> getRequiredCapabilities() {
        return Collections.emptySet();
    }
}
