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
package praxis.client;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
class PraxisEventHandler implements EventHandler<PraxisEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(PraxisEventHandler.class);

    public PraxisEventHandler(PraxisConfiguration config) {

    }

    @Override
    public void onEvent(PraxisEvent event, long sequence, boolean endOfBatch) throws Exception {

    }
}
