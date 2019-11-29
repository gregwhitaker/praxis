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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import praxis.service.core.event.model.Event;
import praxis.service.core.logging.LogMessage;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;

@Component
public class EventLedgerHandler implements EventHandler<EventProcessor.ProcessLedgerEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(EventLedgerHandler.class);

    @Autowired
    private DataSource dataSource;
//
//    CREATE TABLE events (
//            evt_id          UUID            PRIMARY KEY,
//            evt_corr_id     UUID,
//            evt_type        BIGINT          NOT NULL,
//            evt_ts          TIMESTAMP       NOT NULL,
//            evt_proc_ts     TIMESTAMP       NOT NULL,
//            evt_app         VARCHAR(250),
//    evt_ins         VARCHAR(250),
//    evt_env         VARCHAR(250),
//    evt_attrs       JSONB
//);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onEvent(EventProcessor.ProcessLedgerEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOG.debug(LogMessage.builder()
                .withMessage("Processing Event")
                .withData("eventId", event.getLedgerId())
                .build());

        try (Connection conn = dataSource.getConnection()) {
            final String selectSql = "SELECT e.* FROM event_ledger e WHERE led_id = ? FOR UPDATE";
            final String insertSql = "INSERT INTO events (evt_id, evt_corr_id, evt_type, evt_ts, evt_proc_ts, evt_app, evt_ins, evt_env, evt_attrs) VALUES (?,?,?,?,?,?,?,?,?)";

            // Disable auto commit as we are starting a transaction
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(selectSql, ResultSet.CONCUR_UPDATABLE)) {
                ps.setObject(1, event.getLedgerId());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        byte[] eventBytes = rs.getBytes("evt_data");

                        System.out.println(new String(eventBytes));

                        Event baseEvent = MAPPER.readerFor(Event.class).readValue(eventBytes);

                        // Updating the processed timestamp
                        rs.updateTimestamp("led_proc_ts", Timestamp.from(Instant.now()));
                        rs.updateRow();
                    }
                }
            }

            // Commit the transaction
            conn.commit();
            conn.setAutoCommit(true);
        }
    }
}
