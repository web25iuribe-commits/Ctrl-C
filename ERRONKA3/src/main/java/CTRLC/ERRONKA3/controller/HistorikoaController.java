package CTRLC.ERRONKA3.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import CTRLC.ERRONKA3.model.historikoa;
import CTRLC.ERRONKA3.repository.HistorikoaRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class HistorikoaController {

    private final HistorikoaRepository historikoaRepository;

    public HistorikoaController(HistorikoaRepository historikoaRepository) {
        this.historikoaRepository = historikoaRepository;
    }

    @GetMapping("/admin/historikoa")
    public String historikoaPage(Model model, HttpSession session) {
        if (!isLoggedIn(session) || !isAdmin(session)) {
            return "redirect:/login";
        }

        List<historikoa> historikoak = historikoaRepository.findAll();
        model.addAttribute("historikoak", historikoak);
        model.addAttribute("currentUser", session.getAttribute("loggedUser"));
        model.addAttribute("role", session.getAttribute("role"));
        return "historikoa";
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("loggedUser") != null;
    }

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return role != null && role.contains("admin");
    }
}