package CTRLC.ERRONKA3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CTRLC.ERRONKA3.model.gela;
import CTRLC.ERRONKA3.service.HistorikoaService;
import jakarta.servlet.http.HttpSession;

@Controller
public class GelaController {

    private final CTRLC.ERRONKA3.repository.GelaRepository gelaRepository;
    private final HistorikoaService historikoaService;

    public GelaController(CTRLC.ERRONKA3.repository.GelaRepository gelaRepository,
                          HistorikoaService historikoaService) {
        this.gelaRepository = gelaRepository;
        this.historikoaService = historikoaService;
    }

    @PostMapping("/admin/gela/save")
    public String saveGela(@RequestParam String gela_zenb,
                           @RequestParam String id_solairua,
                           HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }

        gela gela = new gela();
        gela.setGela_zenb(gela_zenb);
        gela.setId_solairua(id_solairua);
        gelaRepository.save(gela);

        historikoaService.logAction("gela", "INSERT", "Gela sortu: " + gela_zenb);
        return "redirect:/admin";
    }

    @PostMapping("/admin/gela/delete")
    public String deleteGela(@RequestParam String gela_zenb, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }
        gelaRepository.deleteById(gela_zenb);
        historikoaService.logAction("gela", "DELETE", "Gela ezabatu: " + gela_zenb);
        return "redirect:/admin";
    }

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return role != null && role.contains("admin");
    }
}