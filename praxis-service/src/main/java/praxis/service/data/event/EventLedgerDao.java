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
package praxis.service.data.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Component
public class EventLedgerDao {
    private static final Logger LOG = LoggerFactory.getLogger(EventLedgerDao.class);

    @Autowired
    private DataSource dataSource;

    /**
     * Save the data to the consumeEvent ledger.
     *
     * @param data binary data to save
     * @return
     */
    public Mono<String> save(byte[] data) {
        return Mono.fromSupplier(() -> {
           try (Connection conn = dataSource.getConnection()) {
               final String sql = "INSERT INTO event_ledger (led_id, led_ts, evt_data) VALUES (?, ?, ?)";
               final UUID newId = UUID.randomUUID();

               try (PreparedStatement ps = conn.prepareStatement(sql)) {
                   ps.setObject(1, newId);
                   ps.setTimestamp(2, Timestamp.from(Instant.now()));
                   ps.setBytes(3, data);

                   ps.executeUpdate();

                   return newId.toString();
               }
           } catch (SQLException e) {
               throw new RuntimeException("Error occurred saving event to the event ledger", e);
           }
        });
    }
}
