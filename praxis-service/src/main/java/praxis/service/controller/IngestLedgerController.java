package praxis.service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import praxis.service.service.IngestService;

@RestController
public class IngestLedgerController {
    private static final Logger LOG = LoggerFactory.getLogger(IngestLedgerController.class);

    @Autowired
    private IngestService ingestService;
}
