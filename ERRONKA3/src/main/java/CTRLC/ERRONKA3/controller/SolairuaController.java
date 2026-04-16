package CTRLC.ERRONKA3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CTRLC.ERRONKA3.model.solairua;
import CTRLC.ERRONKA3.service.HistorikoaService;
import jakarta.servlet.http.HttpSession;

@Controller
public class SolairuaController {

    private final CTRLC.ERRONKA3.repository.SolairuaRepository solairuaRepository;
    private final HistorikoaService historikoaService;

    public SolairuaController(CTRLC.ERRONKA3.repository.SolairuaRepository solairuaRepository,
                              HistorikoaService historikoaService) {
        this.solairuaRepository = solairuaRepository;
        this.historikoaService = historikoaService;
    }

    @PostMapping("/admin/solairua/save")
    public String saveSolairua(@RequestParam String id_solairua,
                               @RequestParam Integer solairu_zenbakia,
                               @RequestParam String id_eraikina,
                               HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }

        solairua solairua = new solairua();
        solairua.setId_solairua(id_solairua);
        solairua.setSolairu_zenbakia(solairu_zenbakia);
        solairua.setId_eraikina(id_eraikina);
        solairuaRepository.save(solairua);

        historikoaService.logAction("solairua", "INSERT", "Solairua sortu: " + id_solairua);
        return "redirect:/admin";
    }

    @PostMapping("/admin/solairua/delete")
    public String deleteSolairua(@RequestParam String id_solairua, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }
        solairuaRepository.deleteById(id_solairua);
        historikoaService.logAction("solairua", "DELETE", "Solairua ezabatu: " + id_solairua);
        return "redirect:/admin";
    }

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return role != null && role.contains("admin");
    }
}