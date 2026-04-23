package CTRLC.ERRONKA3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import CTRLC.ERRONKA3.model.erabiltzailea;
import CTRLC.ERRONKA3.repository.ErabiltzaileRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class HasieraController {

    private final ErabiltzaileRepository erabiltzaileRepository;

    public HasieraController(ErabiltzaileRepository erabiltzaileRepository) {
        this.erabiltzaileRepository = erabiltzaileRepository;
    }

    @GetMapping("/login")
    public String loginForm(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "login";
    }

    @PostMapping("/login")
    public String autenticar(@RequestParam String email,
                             @RequestParam String pasahitza,
                             @RequestParam(value = "selectedRole", required = false, defaultValue = "erabiltzaile") String selectedRole,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        String emaila = email == null ? "" : email.trim();
        String password = pasahitza == null ? "" : pasahitza.trim();

        if (emaila.isEmpty() || password.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Emaila edo Pasahitza ez da zuzena.");
            return "redirect:/login";
        }

        erabiltzailea user = erabiltzaileRepository.findByEmailAndPassword(emaila, password).orElse(null);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Emaila edo Pasahitza ez da zuzena.");
            return "redirect:/login";
        }

        boolean userIsAdmin = isAdminRole(user.getErabiltzaile_mota());
        boolean selectedAdmin = "admin".equals(selectedRole);
        if (userIsAdmin != selectedAdmin) {
            redirectAttributes.addFlashAttribute("error", "Hautatutako sarbide mota ez dator bat. Egiaztatu aukera eta saiatu berriro.");
            return "redirect:/login";
        }

        session.setAttribute("loggedUser", user);
        session.setAttribute("role", user.getErabiltzaile_mota());
        if (userIsAdmin) {
            return "redirect:/admin";
        }
        return "redirect:/user";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping({"/", "/eraikina", "/usuarios"})
    public String kaixo(HttpSession session) {
        erabiltzailea loggedUser = (erabiltzailea) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }

        String role = (String) session.getAttribute("role");
        if (isAdminRole(role)) {
            return "redirect:/admin";
        }
        return "redirect:/user";
    }

    private boolean isAdminRole(String role) {
        return role != null && role.toLowerCase().contains("admin");
    }
}
