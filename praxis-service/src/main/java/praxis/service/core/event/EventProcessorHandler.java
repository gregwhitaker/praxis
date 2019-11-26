/**
 * Copyright 2019 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package praxis.service.core.event;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import praxis.service.core.logging.LogMessage;

import javax.sql.DataSource;

@Component
public class EventProcessorHandler implements EventHandler<EventProcessor.ProcessEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(EventProcessorHandler.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public void onEvent(EventProcessor.ProcessEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOG.debug(LogMessage.builder()
                .withMessage("Processing Event")
                .withData("eventId", event.getEventId())
                .build());
    }
}
