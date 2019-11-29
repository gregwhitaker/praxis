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
package praxis.demo.core.reporting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import praxis.client.Praxis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ReportingListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingListener.class);

    @Autowired
    private Praxis praxis;

    private License license;

    public ReportingListener() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerFor(License.class);

        try {
            license = reader.readValue(getClass().getClassLoader().getResourceAsStream("license.json"));
        } catch (IOException e) {
            LOG.error("Unable to read license.json");
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        SystemInfo si = new SystemInfo();

        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();

        Map<String, Object> eventData = new HashMap<>();
        eventData.put("os", os.toString());
        eventData.put("uptime", FormatUtil.formatElapsedSecs(os.getSystemUptime()));

    }
}
