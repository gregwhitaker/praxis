package praxis.service.core.event;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import praxis.service.core.logging.LogMessage;

import javax.sql.DataSource;

@Component
public class EventProcessorHandler implements EventHandler<EventProcessor.ProcessEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(EventProcessorHandler.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public void onEvent(EventProcessor.ProcessEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOG.debug(LogMessage.builder()
                .withMessage("Processing Event")
                .withData("eventId", event.getEventId())
                .build());
    }
}
