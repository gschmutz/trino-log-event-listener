/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.plugin.eventlistener.log;

import io.airlift.configuration.Config;
import io.airlift.configuration.ConfigDescription;

public class LogEventListenerConfig
{
    private boolean logCreated;
    private boolean logCompleted;
    private boolean logSplit;

    private boolean replaceNewLines;

    @ConfigDescription("Will log io.trino.spi.eventlistener.QueryCreatedEvent")
    @Config("log-event-listener.log-created")
    public LogEventListenerConfig setLogCreated(boolean logCreated)
    {
        this.logCreated = logCreated;
        return this;
    }

    public boolean getLogCreated()
    {
        return this.logCreated;
    }

    @ConfigDescription("Will log io.trino.spi.eventlistener.QueryCompletedEvent")
    @Config("log-event-listener.log-completed")
    public LogEventListenerConfig setLogCompleted(boolean logCompleted)
    {
        this.logCompleted = logCompleted;
        return this;
    }

    public boolean getLogCompleted()
    {
        return this.logCompleted;
    }

    @ConfigDescription("Will log io.trino.spi.eventlistener.SplitCompletedEvent")
    @Config("log-event-listener.log-split")
    public LogEventListenerConfig setLogSplit(boolean logSplit)
    {
        this.logSplit = logSplit;
        return this;
    }

    public boolean getLogSplit()
    {
        return this.logSplit;
    }

    @ConfigDescription("Replace newlines in query string before writing to log output")
    @Config("log-event-listener.replace-new-lines")
    public LogEventListenerConfig setReplaceNewLines(boolean replaceNewLines)
    {
        this.replaceNewLines = replaceNewLines;
        return this;
    }

    public boolean getReplaceNewLines()
    {
        return this.replaceNewLines;
    }
}
