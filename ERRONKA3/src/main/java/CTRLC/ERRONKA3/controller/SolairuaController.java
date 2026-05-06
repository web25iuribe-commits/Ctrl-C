package CTRLC.ERRONKA3.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import CTRLC.ERRONKA3.model.solairua;
import jakarta.servlet.http.HttpSession;

@Controller
public class SolairuaController {

    private final CTRLC.ERRONKA3.repository.SolairuaRepository solairuaRepository;

    public SolairuaController(CTRLC.ERRONKA3.repository.SolairuaRepository solairuaRepository) {
        this.solairuaRepository = solairuaRepository;
    }

    @PostMapping("/admin/solairua/save")
    public String saveSolairua(@RequestParam String id_solairua,
                               @RequestParam String solairu_zenbakia,
                               @RequestParam String id_eraikina,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/admin/editatu?accessDenied=true";
        }

        Integer zenbakia = parseSolairuZenbakia(solairu_zenbakia);
        String validationError = validateSolairuaInput(id_solairua, zenbakia, id_eraikina);
        if (validationError != null) {
            redirectAttributes.addFlashAttribute("error", validationError);
            return "redirect:/admin/editatu";
        }

        String normalizedId = id_solairua.trim();
        if (solairuaRepository.existsById(normalizedId)) {
            redirectAttributes.addFlashAttribute("error", "ID hori dagoeneko existitzen da. Mesedez, jarri existitzen ez den ID bat.");
            return "redirect:/admin/editatu";
        }

        try {
            solairua solairua = new solairua();
            solairua.setId_solairua(normalizedId);
            solairua.setSolairu_zenbakia(zenbakia);
            solairua.setId_eraikina(id_eraikina.trim());
            solairuaRepository.save(solairua);
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da solairua gorde: datuak ez dira baliodunak edo lotura okerrak daude.");
            return "redirect:/admin/editatu";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da solairua gorde. Saiatu berriro datuak egiaztatuta.");
            return "redirect:/admin/editatu";
        }

        redirectAttributes.addFlashAttribute("success", "Solairua ondo sortu da.");
        return "redirect:/admin/editatu";
    }

    @PostMapping("/admin/solairua/update")
    public String updateSolairua(@RequestParam String id_solairua,
                                 @RequestParam String solairu_zenbakia,
                                 @RequestParam String id_eraikina,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/admin/editatu?accessDenied=true";
        }

        Integer zenbakia = parseSolairuZenbakia(solairu_zenbakia);
        String validationError = validateSolairuaInput(id_solairua, zenbakia, id_eraikina);
        if (validationError != null) {
            redirectAttributes.addFlashAttribute("error", validationError);
            return "redirect:/admin/editatu";
        }

        try {
            solairua solairua = new solairua();
            solairua.setId_solairua(id_solairua.trim());
            solairua.setSolairu_zenbakia(zenbakia);
            solairua.setId_eraikina(id_eraikina.trim());
            solairuaRepository.save(solairua);
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da solairua eguneratu: datuak ez dira baliodunak edo lotura okerrak daude.");
            return "redirect:/admin/editatu";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da solairua eguneratu. Saiatu berriro datuak egiaztatuta.");
            return "redirect:/admin/editatu";
        }

        redirectAttributes.addFlashAttribute("success", "Solairua ondo eguneratu da.");
        return "redirect:/admin/editatu";
    }

    @PostMapping("/admin/solairua/delete")
    public String deleteSolairua(@RequestParam String id_solairua,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/admin/editatu?accessDenied=true";
        }

        if (isBlank(id_solairua)) {
            redirectAttributes.addFlashAttribute("error", "Solairuaren IDa ezin da hutsik egon.");
            return "redirect:/admin/editatu";
        }

        try {
            solairuaRepository.deleteById(id_solairua.trim());
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da solairua ezabatu: beste erregistro batzuekin lotuta dago.");
            return "redirect:/admin/editatu";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Ezin izan da solairua ezabatu. Saiatu berriro.");
            return "redirect:/admin/editatu";
        }

        redirectAttributes.addFlashAttribute("success", "Solairua ondo ezabatu da.");
        return "redirect:/admin/editatu";
    }

    private String validateSolairuaInput(String idSolairua, Integer solairuZenbakia, String idEraikina) {
        if (isBlank(idSolairua) || isBlank(idEraikina)) {
            return "Eremu guztiak bete behar dira.";
        }

        if (solairuZenbakia == null) {
            return "Solairu zenbakia zenbaki osoa izan behar da.";
        }

        if (solairuZenbakia < 0 || solairuZenbakia > 200) {
            return "Solairu zenbakiak 0 eta 200 artean egon behar du.";
        }

        return null;
    }

    private Integer parseSolairuZenbakia(String value) {
        if (isBlank(value)) {
            return null;
        }

        try {
            return Integer.valueOf(value.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return role != null && role.contains("admin");
    }
}
