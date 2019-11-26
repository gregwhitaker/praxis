package praxis.service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import praxis.service.data.ping.IngestLedgerDao;

@Component
public class IngestService {
    private static final Logger LOG = LoggerFactory.getLogger(IngestService.class);

    @Autowired
    private IngestLedgerDao ingestLedgerDao;
}
