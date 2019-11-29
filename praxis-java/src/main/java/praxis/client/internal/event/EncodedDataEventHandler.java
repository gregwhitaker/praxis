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

import com.lmax.disruptor.EventHandler;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import praxis.client.PraxisConfiguration;

import javax.crypto.Cipher;
import java.security.PublicKey;

/**
 * Handles {@link EncodedDataEvent} messages.
 */
public class EncodedDataEventHandler implements EventHandler<EventWrapper> {
    private static final Logger LOG = LoggerFactory.getLogger(EncodedDataEventHandler.class);

    private final EventBuffer buffer;
    private final OkHttpClient httpClient;
    private final String eventsUrl;
    private final Cipher cipher;

    public EncodedDataEventHandler(PraxisConfiguration config, final EventBuffer buffer) {
        this.buffer = buffer;
        this.httpClient = new OkHttpClient();
        this.eventsUrl = getBasePraxisUrl(config) + "/events";
        this.cipher = getCipher(config);
    }

    @Override
    public void onEvent(EventWrapper event, long sequence, boolean endOfBatch) throws Exception {
        if (event.getType() == EncodedDataEvent.EVENT_TYPE) {
            EncodedDataEvent encodedDataEvent = (EncodedDataEvent) event.getEvent();

            Request request = new Request.Builder()
                    .url(eventsUrl)
                    .post(RequestBody.create(cipher.doFinal(encodedDataEvent.getData())))
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

    private String getBasePraxisUrl(PraxisConfiguration config) {
        StringBuilder urlBuilder = new StringBuilder();

        if (config.isSslEnabled()) {
            urlBuilder.append("https://");
            urlBuilder.append(config.getHostname());

            if (config.getPort() != 443) {
                urlBuilder.append(":").append(config.getPort());
            }
        } else {
            urlBuilder.append("http://");
            urlBuilder.append(config.getHostname());

            if (config.getPort() != 80) {
                urlBuilder.append(":").append(config.getPort());
            }
        }

        return urlBuilder.toString();
    }

    private Cipher getCipher(PraxisConfiguration config) {
        Request request = new Request.Builder()
                .url(getBasePraxisUrl(config) + "/events/public_key")
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                JWKSet jwkSet = JWKSet.parse(response.body().string());
                PublicKey publicKey = RSAKey.parse(jwkSet.getKeys().get(0).toJSONString()).toPublicKey();

                Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);

                return cipher;
            } else {
                // TODO: fix this error handling
                throw new RuntimeException("");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
