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
```
