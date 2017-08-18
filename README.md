# Delorean Alert Condition Plugin for Graylog

[![Build Status](https://travis-ci.org/alcampos/graylog-plugin-alert-condition-delorean.svg?branch=master)](https://travis-ci.org/alcampos/graylog-plugin-alert-condition-delorean)

Graylog plugin to go back in the past check the alert for a specified time and alert in the future

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
