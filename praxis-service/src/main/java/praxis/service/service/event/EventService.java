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
package praxis.service.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import praxis.service.data.event.EventDao;
import praxis.service.data.event.model.Event;
import reactor.core.publisher.Mono;

@Component
public class EventService {

    @Autowired
    private EventDao eventDao;

    public Mono<Event> getEvent(String id) {
        return eventDao.findOne(id);
    }
}
