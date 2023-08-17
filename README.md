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
2023-08-17T20:27:57.137Z	INFO	dispatcher-query-2	io.trino.plugin.eventlistener.log.LogEventListener	QueryCompletedEvent: queryId=20230817_202756_00000_p8h22,principal=trino,executionStartTimeUnix=1692304076180,endTimeUnix=1692304077047,executionStartTimeIso=2023-08-17T20:27:56.180Z,endTimeIso=2023-08-17T20:27:57.047Z,failureMessage=Query was canceled,query=select  *  from tpcds.sf1.customer 
```
