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
                final String sql = "SELECT * FROM event WHERE evt_id = ?";

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setObject(1, id);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return Event.from(rs);
                        }
                    }
                }

                return null;
            } catch (SQLException e) {
                throw new RuntimeException("", e);
            }
        });
    }
}
