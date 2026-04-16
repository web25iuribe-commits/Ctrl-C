package CTRLC.ERRONKA3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CTRLC.ERRONKA3.model.gailua;
import CTRLC.ERRONKA3.service.HistorikoaService;
import jakarta.servlet.http.HttpSession;

@Controller
public class GailuaController {

    private final CTRLC.ERRONKA3.repository.GailuaRepository gailuaRepository;
    private final HistorikoaService historikoaService;

    public GailuaController(CTRLC.ERRONKA3.repository.GailuaRepository gailuaRepository,
                            HistorikoaService historikoaService) {
        this.gailuaRepository = gailuaRepository;
        this.historikoaService = historikoaService;
    }

    @PostMapping("/admin/gailua/save")
    public String saveGailua(@RequestParam String id_gailua,
                             @RequestParam String marka,
                             @RequestParam String modeloa,
                             @RequestParam String serie_zenb,
                             @RequestParam String neurriak,
                             @RequestParam String gela_zenb,
                             HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }

        gailua gailua = new gailua();
        gailua.setId_gailua(id_gailua);
        gailua.setMarka(marka);
        gailua.setModeloa(modeloa);
        gailua.setSerie_zenb(serie_zenb);
        gailua.setNeurriak(neurriak);
        gailua.setGela_zenb(gela_zenb);
        gailuaRepository.save(gailua);

        historikoaService.logAction("gailua", "INSERT", "Gailua sortu: " + id_gailua);
        return "redirect:/admin";
    }

    @PostMapping("/admin/gailua/delete")
    public String deleteGailua(@RequestParam String id_gailua, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }
        gailuaRepository.deleteById(id_gailua);
        historikoaService.logAction("gailua", "DELETE", "Gailua ezabatu: " + id_gailua);
        return "redirect:/admin";
    }

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return role != null && role.contains("admin");
    }
}