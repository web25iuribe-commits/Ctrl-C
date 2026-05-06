package CTRLC.ERRONKA3.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import CTRLC.ERRONKA3.model.gela;
import jakarta.servlet.http.HttpSession;

@Controller
public class GelaController {

    private final CTRLC.ERRONKA3.repository.GelaRepository gelaRepository;

    public GelaController(CTRLC.ERRONKA3.repository.GelaRepository gelaRepository) {
        this.gelaRepository = gelaRepository;
    }

    @PostMapping("/admin/gela/save")
    public String saveGela(@RequestParam String gela_zenb,
                           @RequestParam String id_solairua,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/admin/editatu?accessDenied=true";
        }

        String validationError = validateGelaInput(gela_zenb, id_solairua);
        if (validationError != null) {
            redirectAttributes.addFlashAttribute("error", validationError);
            return "redirect:/admin/editatu";
        }

        String normalizedId = gela_zenb.trim();
        if (gelaRepository.existsById(normalizedId)) {
            redirectAttributes.addFlashAttribute("error", "ID hori dagoeneko existitzen da. Mesedez, jarri existitzen ez den ID bat.");
            return "redirect:/admin/editatu";
        }

        try {
            gela gela = new gela();
            gela.setGela_zenb(normalizedId);
            gela.setId_solairua(id_solairua.trim());
            gelaRepository.save(gela);
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da gela gorde: datuak ez dira baliodunak edo lotura okerrak daude.");
            return "redirect:/admin/editatu";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da gela gorde. Saiatu berriro datuak egiaztatuta.");
            return "redirect:/admin/editatu";
        }

        redirectAttributes.addFlashAttribute("success", "Gela ondo sortu da.");
        return "redirect:/admin/editatu";
    }

    @PostMapping("/admin/gela/update")
    public String updateGela(@RequestParam String gela_zenb,
                             @RequestParam String id_solairua,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/admin/editatu?accessDenied=true";
        }

        String validationError = validateGelaInput(gela_zenb, id_solairua);
        if (validationError != null) {
            redirectAttributes.addFlashAttribute("error", validationError);
            return "redirect:/admin/editatu";
        }

        try {
            gela gela = new gela();
            gela.setGela_zenb(gela_zenb.trim());
            gela.setId_solairua(id_solairua.trim());
            gelaRepository.save(gela);
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da gela eguneratu: datuak ez dira baliodunak edo lotura okerrak daude.");
            return "redirect:/admin/editatu";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da gela eguneratu. Saiatu berriro datuak egiaztatuta.");
            return "redirect:/admin/editatu";
        }

        redirectAttributes.addFlashAttribute("success", "Gela ondo eguneratu da.");
        return "redirect:/admin/editatu";
    }

    @PostMapping("/admin/gela/delete")
    public String deleteGela(@RequestParam String gela_zenb,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/admin/editatu?accessDenied=true";
        }

        if (isBlank(gela_zenb)) {
            redirectAttributes.addFlashAttribute("error", "Gelaren IDa ezin da hutsik egon.");
            return "redirect:/admin/editatu";
        }

        try {
            gelaRepository.deleteById(gela_zenb.trim());
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da gela ezabatu: beste erregistro batzuekin lotuta dago.");
            return "redirect:/admin/editatu";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da gela ezabatu. Saiatu berriro.");
            return "redirect:/admin/editatu";
        }

        redirectAttributes.addFlashAttribute("success", "Gela ondo ezabatu da.");
        return "redirect:/admin/editatu";
    }

    private String validateGelaInput(String gelaZenb, String idSolairua) {
        if (isBlank(gelaZenb) || isBlank(idSolairua)) {
            return "Eremu guztiak bete behar dira.";
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
