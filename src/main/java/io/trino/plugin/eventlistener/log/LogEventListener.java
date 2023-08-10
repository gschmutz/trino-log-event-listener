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

import com.google.inject.Inject;
import io.airlift.log.Logger;
import io.trino.spi.eventlistener.EventListener;
import io.trino.spi.eventlistener.QueryCompletedEvent;
import io.trino.spi.eventlistener.QueryCreatedEvent;
import io.trino.spi.eventlistener.SplitCompletedEvent;

import static java.util.Objects.requireNonNull;

/**
 * Implement an EventListener that stores information in the Trino log
 */
public class LogEventListener
        implements EventListener
{
    private static final Logger log = Logger.get(LogEventListener.class);

    private final boolean logCreated;
    private final boolean logCompleted;
    private final boolean logSplit;
    private final boolean replaceNewLines;

    @Inject
    public LogEventListener(
            LogEventListenerConfig config)
    {
        requireNonNull(config, "log event listener config is null");

        this.logCreated = config.getLogCreated();
        this.logCompleted = config.getLogCompleted();
        this.logSplit = config.getLogSplit();
        this.replaceNewLines = config.getReplaceNewLines();
    }

    @Override
    public void queryCreated(QueryCreatedEvent event)
    {
        if (logCreated) {
            log.info(formatQueryCreatedEvent(event));
        }
    }

    @Override
    public void queryCompleted(QueryCompletedEvent event)
    {
        if (logCompleted) {
            log.info(formatQueryCompletedEvent(event));
        }
    }

    @Override
    public void splitCompleted(SplitCompletedEvent event)
    {
        if (logSplit) {
            log.info(formatSplitCompletedEvent(event));
        }
    }

    private String formatQueryCreatedEvent(QueryCreatedEvent event)
    {
        StringBuilder message = new StringBuilder();
        message.append(event.getClass().getSimpleName()).append(": ");
        message.append("queryId=").append(event.getMetadata().getQueryId()).append(",");
        event.getContext().getPrincipal().ifPresent(principal ->
                message.append("principal=").append(principal).append(","));
        message.append("createTime=").append(event.getCreateTime().toEpochMilli()).append(",");
        message.append("query=").append(rpelaceSpecialCharaters(event.getMetadata().getQuery(), replaceNewLines));

        return message.toString();
    }

    private String formatQueryCompletedEvent(QueryCompletedEvent event)
    {
        StringBuilder message = new StringBuilder();
        message.append(event.getClass().getSimpleName()).append(": ");
        message.append("queryId=").append(event.getMetadata().getQueryId()).append(",");
        event.getContext().getPrincipal().ifPresent(principal ->
                message.append("principal=").append(principal).append(","));
        message.append("executionStartTime=").append(event.getExecutionStartTime().toEpochMilli()).append(",");
        message.append("query=").append(rpelaceSpecialCharaters(event.getMetadata().getQuery(), replaceNewLines)).append(",");
        event.getFailureInfo().ifPresent(failureInfo ->
                failureInfo.getFailureMessage().ifPresent(failureMessage ->
                        message.append("failureMessage=").append(failureMessage)));

        return message.toString();
    }

    private String formatSplitCompletedEvent(SplitCompletedEvent event)
    {
        StringBuilder message = new StringBuilder();
        message.append(event.getClass().getSimpleName()).append(": ");
        message.append("queryId=").append(event.getQueryId()).append(",");
        message.append("createTime=").append(event.getCreateTime().toEpochMilli()).append(",");
        message.append("payload=").append(event.getPayload()).append(",");
        event.getFailureInfo().ifPresent(failureInfo ->
                message.append("failureMessage=").append(failureInfo.getFailureMessage()));

        return message.toString();
    }

    private String rpelaceSpecialCharaters(String query, boolean replaceNewLines)
    {
        String value = query;
        if (replaceNewLines) {
            value = value.replaceAll("[\\r\\n]", " ");
        }
        return value;
    }
}
