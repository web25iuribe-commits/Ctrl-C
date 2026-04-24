package CTRLC.ERRONKA3.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import CTRLC.ERRONKA3.model.historikoa;
import CTRLC.ERRONKA3.repository.HistorikoaRepository;

@Service
public class HistorikoaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistorikoaService.class);

    private final HistorikoaRepository historikoaRepository;
    private final boolean appLoggingEnabled;

    public HistorikoaService(HistorikoaRepository historikoaRepository,
                             @Value("${app.historikoa.application-logging:false}") boolean appLoggingEnabled) {
        this.historikoaRepository = historikoaRepository;
        this.appLoggingEnabled = appLoggingEnabled;
    }

    public void logAction(String taula, String ekintza, String oharra) {
        if (!appLoggingEnabled) {
            return;
        }

        historikoa historikoa = new historikoa();
        historikoa.setTaula(taula);
        historikoa.setEkintza(ekintza);
        historikoa.setData_aldaketa(new Date());
        historikoa.setOharra(oharra);
        try {
            historikoaRepository.save(historikoa);
        } catch (RuntimeException ex) {
            LOGGER.warn("Ezin izan da historikoa gorde: taula={}, ekintza={}, oharra={}", taula, ekintza, oharra, ex);
        }
    }
}