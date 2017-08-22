# Delorean Alert Condition Plugin for Graylog

[![Build Status](https://travis-ci.org/alcampos/graylog-plugin-alert-condition-delorean.svg?branch=master)](https://travis-ci.org/alcampos/graylog-plugin-alert-condition-delorean)

Graylog plugin to go back in the past check the alert for a specified time and alert in the present

**Required Graylog version:** 2.3 and later

Installation
------------

[Download the plugin](https://github.com/alcampos/graylog-plugin-alert-condition-delorean/releases/latest)
and place the `.jar` file in your Graylog plugin directory. The plugin directory
is the `plugins/` folder relative from your `graylog-server` directory by default
and can be configured in your `graylog.conf` file.

Restart `graylog-server` and you are done.

Usage
-----

*Function Prototype:*

First we have to select the alert type **Delorean**


![Alert Condition Selection](https://github.com/alcampos/graylog-plugin-alert-condition-delorean/blob/master/media/delorean_selection.png)


Then we have to set all the common fields plus the **backtime** and the **staytime**


![Alert Condition Fields](https://github.com/alcampos/graylog-plugin-alert-condition-delorean/blob/master/media/delorean_alert.png)

All set! We now can go back search an event for sometime in the past and get the alert! This is usefull when the log have some delay and it's timestamp isn't the same as the EventReceivedTime when the log arrives.

Also you can use this feature to not just search for a 1 minute time event, all you have to do is set the **backtime** in 0.

Getting started
---------------

This project is using Maven 3 and requires Java 7 or higher.

* Clone this repository.
* Run `mvn package` to build a JAR file.
* Optional: Run `mvn jdeb:jdeb` and `mvn rpm:rpm` to create a DEB and RPM package respectively.
* Copy generated JAR file in target directory to your Graylog plugin directory.
* Restart the Graylog.

Plugin Release
--------------

We are using the maven release plugin:

```
$ mvn release:prepare
[...]
$ mvn release:perform
```

This sets the version numbers, creates a tag and pushes to GitHub. Travis CI will build the release artifacts and upload to GitHub automatically.
