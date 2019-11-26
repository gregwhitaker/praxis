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
package praxis.service.data.ingest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class IngestLedgerDao {
    private static final Logger LOG = LoggerFactory.getLogger(IngestLedgerDao.class);

    @Autowired
    private DataSource dataSource;

    /**
     * Save the data to the ingest ledger.
     *
     * @param data binary data to save
     * @return
     */
    public Mono<Void> save(byte[] data) {
        return Mono.defer(() -> {
            try (Connection conn = dataSource.getConnection()) {
                final String sql = "INSERT INTO ingest_ledger (data) VALUES (?)";

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setBytes(1, data);
                    ps.execute();

                    return Mono.empty();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error occurred saving data to ingest ledger", e);
            }
        });
    }
}
