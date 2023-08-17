# trino-log-event-listener

## Overview

Trino Event Listener which writes various properties from the `QueryCreatedEvent`, `QueryCompletedEvent` and `SplitCompletedEvent` to the Trino log. 

## Usage

To use this plugin you need to copy the distribution package to the Trino plugin (`<path_to_trino>/plugin/log-event-listener/`) directory and configure the plugin.

### Configuration

Create `<path_to_trino>/etc/event-listener.properties` with the following required parameters, e.g.:

```bash
event-listener.name=log

log-event-listener.log-created=false
log-event-listener.log-completed=true
log-event-listener.log-split=false
log-event-listener.replace-new-lines=true
log-event-listener.log-date-in-liux-format=true
log-event-listener.log-date-in-iso-format=false
```

### Sample Output

Here an example of a log line with the configuration from above (instance of a `QueryCompletedEvent`. 

```bash
2023-08-10T13:54:11.841Z	INFO	dispatcher-query-2	io.trino.plugin.eventlistener.log.LogEventListener	QueryCompletedEvent: queryId=20230810_135410_00001_qget4,principal=trino,executionStartTime=1691675650171,query=select * from tpch.sf1.customer ,failureMessage=Query was canceled
```
