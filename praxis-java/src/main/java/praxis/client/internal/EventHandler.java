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
package praxis.client.internal;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import praxis.client.PraxisConfiguration;

public class EventHandler implements com.lmax.disruptor.EventHandler<Event> {
    private static final Logger LOG = LoggerFactory.getLogger(EventHandler.class);

    private final String url;
    private final OkHttpClient httpClient;

    public EventHandler(PraxisConfiguration config) {
        this.url = config.getBaseUrl() + "/events";

        this.httpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
    }

    @Override
    public void onEvent(Event event, long sequence, boolean endOfBatch) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(event.getWrappedEvent().toBytes()))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                LOG.debug("Published event to Praxis");
            } else {
                LOG.error("Event publishing to Praxis failed [code: '{}]", response.code());
            }
        }
    }
}
