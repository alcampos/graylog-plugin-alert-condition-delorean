package com.alexjck.plugins.delorean;

import org.graylog2.plugin.Plugin;
import org.graylog2.plugin.PluginMetaData;
import org.graylog2.plugin.PluginModule;

import java.util.Collection;
import java.util.Collections;

public class DeloreanAlertConditionPlugin implements Plugin {
    @Override
    public PluginMetaData metadata() {
        return new DeloreanAlertConditionMetaData();
    }

    @Override
    public Collection<PluginModule> modules () {
        return Collections.<PluginModule>singletonList(new DeloreanAlertConditionModule());
    }
}
