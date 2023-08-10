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

import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;
import io.airlift.bootstrap.Bootstrap;
import io.airlift.json.JsonModule;
import io.trino.spi.eventlistener.EventListener;
import io.trino.spi.eventlistener.EventListenerFactory;

import java.util.Map;

import static io.airlift.configuration.ConfigBinder.configBinder;

public class LogEventListenerFactory
        implements EventListenerFactory
{
    @Override
    public String getName()
    {
        return "log";
    }

    @Override
    public EventListener create(Map<String, String> config)
    {
        Bootstrap app = new Bootstrap(
                new JsonModule(),
                new MysqlDataSourceModule(),
                binder -> {
                    binder.bind(LogEventListener.class).in(Scopes.SINGLETON);
                });

        Injector injector = app
                .doNotInitializeLogging()
                .setRequiredConfigurationProperties(config)
                .initialize();

        return injector.getInstance(LogEventListener.class);
    }

    private static class MysqlDataSourceModule
            implements Module
    {
        @Override
        public void configure(Binder binder)
        {
            configBinder(binder).bindConfig(LogEventListenerConfig.class);
        }
    }
}
