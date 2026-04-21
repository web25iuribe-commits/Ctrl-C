package CTRLC.ERRONKA3.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }

        String validationError = validateGailuaInput(id_gailua, marka, modeloa, serie_zenb, gela_zenb);
        if (validationError != null) {
            redirectAttributes.addFlashAttribute("error", validationError);
            return "redirect:/admin";
        }

        try {
            gailua gailua = new gailua();
            gailua.setId_gailua(id_gailua.trim());
            gailua.setMarka(marka.trim());
            gailua.setModeloa(modeloa.trim());
            gailua.setSerie_zenb(serie_zenb.trim());
            gailua.setNeurriak(neurriak == null ? null : neurriak.trim());
            gailua.setGela_zenb(gela_zenb.trim());
            gailuaRepository.save(gailua);
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da gailua gorde: datuak ez dira baliodunak edo lotura okerrak daude.");
            return "redirect:/admin";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da gailua gorde. Saiatu berriro datuak egiaztatuta.");
            return "redirect:/admin";
        }

        historikoaService.logAction("gailua", "INSERT", "Gailua sortu: " + id_gailua);
        redirectAttributes.addFlashAttribute("success", "Gailua ondo sortu da.");
        return "redirect:/admin";
    }

    @PostMapping("/admin/gailua/update")
    public String updateGailua(@RequestParam String id_gailua,
                               @RequestParam String marka,
                               @RequestParam String modeloa,
                               @RequestParam String serie_zenb,
                               @RequestParam String neurriak,
                               @RequestParam String gela_zenb,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }

        String validationError = validateGailuaInput(id_gailua, marka, modeloa, serie_zenb, gela_zenb);
        if (validationError != null) {
            redirectAttributes.addFlashAttribute("error", validationError);
            return "redirect:/admin";
        }

        try {
            gailua gailua = new gailua();
            gailua.setId_gailua(id_gailua.trim());
            gailua.setMarka(marka.trim());
            gailua.setModeloa(modeloa.trim());
            gailua.setSerie_zenb(serie_zenb.trim());
            gailua.setNeurriak(neurriak == null ? null : neurriak.trim());
            gailua.setGela_zenb(gela_zenb.trim());
            gailuaRepository.save(gailua);
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da gailua eguneratu: datuak ez dira baliodunak edo lotura okerrak daude.");
            return "redirect:/admin";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da gailua eguneratu. Saiatu berriro datuak egiaztatuta.");
            return "redirect:/admin";
        }

        historikoaService.logAction("gailua", "UPDATE", "Gailua aldatu: " + id_gailua);
        redirectAttributes.addFlashAttribute("success", "Gailua ondo eguneratu da.");
        return "redirect:/admin";
    }

    @PostMapping("/admin/gailua/delete")
    public String deleteGailua(@RequestParam String id_gailua,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }

        if (isBlank(id_gailua)) {
            redirectAttributes.addFlashAttribute("error", "Gailuaren IDa ezin da hutsik egon.");
            return "redirect:/admin";
        }

        try {
            gailuaRepository.deleteById(id_gailua.trim());
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da gailua ezabatu: beste erregistro batzuekin lotuta dago.");
            return "redirect:/admin";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da gailua ezabatu. Saiatu berriro.");
            return "redirect:/admin";
        }

        historikoaService.logAction("gailua", "DELETE", "Gailua ezabatu: " + id_gailua);
        redirectAttributes.addFlashAttribute("success", "Gailua ondo ezabatu da.");
        return "redirect:/admin";
    }

    private String validateGailuaInput(String idGailua,
                                       String marka,
                                       String modeloa,
                                       String serieZenb,
                                       String gelaZenb) {
        if (isBlank(idGailua) || isBlank(marka) || isBlank(modeloa) || isBlank(serieZenb) || isBlank(gelaZenb)) {
            return "Eremu guztiak bete behar dira (neurriak salbu).";
        }
        return null;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return role != null && role.contains("admin");
    }
}