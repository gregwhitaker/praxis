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

import java.util.HashMap;
import java.util.Map;

/**
 * Attribute container for events that support the attachment of user-defined attributes.
 */
public abstract class AttributeEvent {

    protected final Map<String, Object> attrs = new HashMap<>();

    public long getAttributeCount() {
        return attrs.size();
    }

    public void clearAttributes() {
        this.attrs.clear();
    }

    public void addAttribute(String key, String value) {
        this.attrs.put(key, value);
    }

    public void addAttribute(String key, Integer value) {
        this.attrs.put(key, value);
    }

    public void addAttribute(String key, Long value) {
        this.attrs.put(key, value);
    }

    public void addAttribtue(String key, Double value) {
        this.attrs.put(key, value);
    }

    public void addAttribute(String key, Float value) {
        this.attrs.put(key, value);
    }

    public void addAttribute(String key, Boolean value) {
        this.attrs.put(key, value);
    }
}
