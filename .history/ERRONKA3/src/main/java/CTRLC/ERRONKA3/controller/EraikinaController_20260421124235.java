package CTRLC.ERRONKA3.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }

        String validationError = validateEraikinaInput(id_eraikina, izena, helbidea, hiria, posta_kodea);
        if (validationError != null) {
            redirectAttributes.addFlashAttribute("error", validationError);
            return "redirect:/admin";
        }

        try {
            eraikina eraikina = new eraikina();
            eraikina.setId_eraikina(id_eraikina.trim());
            eraikina.setIzena(izena.trim());
            eraikina.setHelbidea(helbidea.trim());
            eraikina.setHiria(hiria.trim());
            eraikina.setPosta_kodea(posta_kodea.trim());
            eraikinaRepository.save(eraikina);
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da eraikina gorde: datuak ez dira baliodunak edo errepikatuak dira.");
            return "redirect:/admin";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da eraikina gorde. Saiatu berriro datuak egiaztatuta.");
            return "redirect:/admin";
        }

        historikoaService.logAction("eraikina", "INSERT", "Eraikina sortu: " + id_eraikina);
        redirectAttributes.addFlashAttribute("success", "Eraikina ondo sortu da.");
        return "redirect:/admin";
    }

    @PostMapping("/admin/eraikina/update")
    public String updateEraikina(@RequestParam String id_eraikina,
                                 @RequestParam String izena,
                                 @RequestParam String helbidea,
                                 @RequestParam String hiria,
                                 @RequestParam String posta_kodea,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }

        String validationError = validateEraikinaInput(id_eraikina, izena, helbidea, hiria, posta_kodea);
        if (validationError != null) {
            redirectAttributes.addFlashAttribute("error", validationError);
            return "redirect:/admin";
        }

        try {
            eraikina eraikina = new eraikina();
            eraikina.setId_eraikina(id_eraikina.trim());
            eraikina.setIzena(izena.trim());
            eraikina.setHelbidea(helbidea.trim());
            eraikina.setHiria(hiria.trim());
            eraikina.setPosta_kodea(posta_kodea.trim());
            eraikinaRepository.save(eraikina);
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da eraikina eguneratu: datuak ez dira baliodunak edo errepikatuak dira.");
            return "redirect:/admin";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da eraikina eguneratu. Saiatu berriro datuak egiaztatuta.");
            return "redirect:/admin";
        }

        historikoaService.logAction("eraikina", "UPDATE", "Eraikina aldatu: " + id_eraikina);
        redirectAttributes.addFlashAttribute("success", "Eraikina ondo eguneratu da.");
        return "redirect:/admin";
    }

    @PostMapping("/admin/eraikina/delete")
    public String deleteEraikina(@RequestParam String id_eraikina,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }

        if (isBlank(id_eraikina)) {
            redirectAttributes.addFlashAttribute("error", "Eraikinaren IDa ezin da hutsik egon.");
            return "redirect:/admin";
        }

        try {
            eraikinaRepository.deleteById(id_eraikina.trim());
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da eraikina ezabatu: beste erregistro batzuekin lotuta dago.");
            return "redirect:/admin";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da eraikina ezabatu. Saiatu berriro.");
            return "redirect:/admin";
        }

        historikoaService.logAction("eraikina", "DELETE", "Eraikina ezabatu: " + id_eraikina);
        redirectAttributes.addFlashAttribute("success", "Eraikina ondo ezabatu da.");
        return "redirect:/admin";
    }

    private String validateEraikinaInput(String idEraikina,
                                         String izena,
                                         String helbidea,
                                         String hiria,
                                         String postaKodea) {
        if (isBlank(idEraikina) || isBlank(izena) || isBlank(helbidea) || isBlank(hiria) || isBlank(postaKodea)) {
            return "Eremu guztiak bete behar dira.";
        }

        if (!postaKodea.trim().matches("^[0-9]{5}$")) {
            return "Posta kodeak 5 zenbaki izan behar ditu.";
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