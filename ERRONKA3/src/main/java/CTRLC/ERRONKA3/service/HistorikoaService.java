package CTRLC.ERRONKA3.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import CTRLC.ERRONKA3.model.historikoa;
import CTRLC.ERRONKA3.repository.HistorikoaRepository;

@Service
public class HistorikoaService {

    private final HistorikoaRepository historikoaRepository;

    public HistorikoaService(HistorikoaRepository historikoaRepository) {
        this.historikoaRepository = historikoaRepository;
    }

    public void logAction(String taula, String ekintza, String oharra) {
        historikoa historikoa = new historikoa();
        historikoa.setTaula(taula);
        historikoa.setEkintza(ekintza);
        historikoa.setData_aldaketa(new Date());
        historikoa.setOharra(oharra);
        historikoaRepository.save(historikoa);
    }
}