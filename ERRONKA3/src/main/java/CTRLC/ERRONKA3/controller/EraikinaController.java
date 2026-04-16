package CTRLC.ERRONKA3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CTRLC.ERRONKA3.model.eraikina;
import CTRLC.ERRONKA3.service.HistorikoaService;
import jakarta.servlet.http.HttpSession;

@Controller
public class EraikinaController {

    private final CTRLC.ERRONKA3.repository.EraikinaRepository eraikinaRepository;
    private final HistorikoaService historikoaService;

    public EraikinaController(CTRLC.ERRONKA3.repository.EraikinaRepository eraikinaRepository,
                              HistorikoaService historikoaService) {
        this.eraikinaRepository = eraikinaRepository;
        this.historikoaService = historikoaService;
    }

    @PostMapping("/admin/eraikina/save")
    public String saveEraikina(@RequestParam String id_eraikina,
                               @RequestParam String izena,
                               @RequestParam String helbidea,
                               @RequestParam String hiria,
                               @RequestParam String posta_kodea,
                               HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }

        eraikina eraikina = new eraikina();
        eraikina.setId_eraikina(id_eraikina);
        eraikina.setIzena(izena);
        eraikina.setHelbidea(helbidea);
        eraikina.setHiria(hiria);
        eraikina.setPosta_kodea(posta_kodea);
        eraikinaRepository.save(eraikina);

        historikoaService.logAction("eraikina", "INSERT", "Eraikina sortu: " + id_eraikina);
        return "redirect:/admin";
    }

    @PostMapping("/admin/eraikina/delete")
    public String deleteEraikina(@RequestParam String id_eraikina, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }
        eraikinaRepository.deleteById(id_eraikina);
        historikoaService.logAction("eraikina", "DELETE", "Eraikina ezabatu: " + id_eraikina);
        return "redirect:/admin";
    }

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return role != null && role.contains("admin");
    }
}