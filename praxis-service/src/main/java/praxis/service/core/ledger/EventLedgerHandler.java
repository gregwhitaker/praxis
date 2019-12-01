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
package praxis.service.core.ledger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import praxis.service.core.ledger.model.Event;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

/**
 * Processes new events that are entered into the ledger.
 */
@Component
public class EventLedgerHandler implements EventHandler<EventLedgerProcessor.ProcessLedgerEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(EventLedgerHandler.class);

    @Autowired
    private DataSource dataSource;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onEvent(EventLedgerProcessor.ProcessLedgerEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOG.debug("Processing event: '{}'", event.getLedgerId());

        try (Connection conn = dataSource.getConnection()) {
            final String selectSql = "SELECT e.* FROM event_ledger e WHERE led_id = ? FOR UPDATE";
            final String insertSql = "INSERT INTO events (evt_id, evt_corr_id, evt_type, evt_ts, evt_process_ts, evt_app, evt_ins, evt_env, evt_attrs) VALUES (?,?,?,?,?,?,?,?,?::JSON)";

            // Disable auto commit as we are starting a transaction
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(selectSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                ps.setObject(1, event.getLedgerId());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        byte[] eventBytes = rs.getBytes("evt_data");

                        Event newEvent = MAPPER.readerFor(Event.class).readValue(eventBytes);

                        // Decomposing new event in ledger and inserting into event table
                        try (PreparedStatement ips = conn.prepareStatement(insertSql)) {
                            ips.setObject(1, newEvent.getId());
                            ips.setObject(2, newEvent.getCorrelatedId());
                            ips.setInt(3, newEvent.getType());
                            ips.setTimestamp(4, new Timestamp(newEvent.getTimestamp()));
                            ips.setTimestamp(5, Timestamp.from(Instant.now()));
                            ips.setString(6, newEvent.getApplication());
                            ips.setString(7, newEvent.getInstance());
                            ips.setString(8, newEvent.getEnvironment());
                            ips.setObject(9, MAPPER.writerFor(Map.class).writeValueAsString(newEvent.getAttributes()));

                            ips.executeUpdate();
                        }

                        // Updating the processed timestamp on the ledger
                        rs.updateTimestamp("led_process_ts", Timestamp.from(Instant.now()));
                        rs.updateRow();

                        LOG.debug("Processed Event: '{}'", newEvent.getId().toString());
                    }
                }
            }

            // Commit the transaction
            conn.commit();
            conn.setAutoCommit(true);
        }
    }
}
