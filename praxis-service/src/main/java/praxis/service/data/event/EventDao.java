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
import praxis.service.data.event.model.Event;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class EventDao {
    private static final Logger LOG = LoggerFactory.getLogger(EventDao.class);

    @Autowired
    private DataSource dataSource;

    public Mono<Event> findOne(String id) {
        return findOne(UUID.fromString(id));
    }

    public Mono<Event> findOne(UUID id) {
        return Mono.fromSupplier(() -> {
            try (Connection conn = dataSource.getConnection()) {
                final String sql = "SELECT * FROM events WHERE evt_id = ?";

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setObject(1, id);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return Event.from(rs);
                        }
                    }
                }

                return null;
            } catch (Exception e) {
                throw new RuntimeException("", e);
            }
        });
    }
}
