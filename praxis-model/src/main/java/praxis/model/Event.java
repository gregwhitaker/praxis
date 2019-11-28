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
package praxis.model;

/**
 * Interface that all Praxis events must implement.
 */
public interface Event {

    /**
     * Gets this event's identifier.
     *
     * @return event identifier
     */
    String getId();

    /**
     * Gets the event identifier of the correlated event.
     *
     * @return event identifier of correlated event or <code>null</code> if there is
     * no correlated event
     */
    String getCorrelationId();

    /**
     * Gets the type of the event.
     *
     * @return event type id
     */
    long getType();

    /**
     * Gets the timestamp of when the event occurred.
     *
     * @return event timestamp
     */
    long getTimestamp();
}
