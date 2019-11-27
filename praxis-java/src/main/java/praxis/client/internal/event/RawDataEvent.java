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
package praxis.client.internal.event;

public class RawDataEvent implements Event {
    public static final int EVENT_TYPE = 1;

    private long timestamp;
    private Object data;
    private Class<?> datatype;

    public RawDataEvent() {
        // Noop
    }

    public RawDataEvent(final Object data, final Class<?> datatype) {
        this.timestamp = System.currentTimeMillis();
        this.data = data;
        this.datatype = datatype;
    }

    @Override
    public int getType() {
        return EVENT_TYPE;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Class<?> getDatatype() {
        return datatype;
    }

    public void setDatatype(Class<?> datatype) {
        this.datatype = datatype;
    }
}
