package CTRLC.ERRONKA3.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import CTRLC.ERRONKA3.model.erabiltzailea;
import CTRLC.ERRONKA3.model.eraikina;
import CTRLC.ERRONKA3.model.gailua;
import CTRLC.ERRONKA3.model.gela;
import CTRLC.ERRONKA3.model.historikoa;
import CTRLC.ERRONKA3.model.solairua;
import CTRLC.ERRONKA3.repository.ErabiltzaileRepository;
import CTRLC.ERRONKA3.repository.EraikinaRepository;
import CTRLC.ERRONKA3.repository.GailuaRepository;
import CTRLC.ERRONKA3.repository.GelaRepository;
import CTRLC.ERRONKA3.repository.HistorikoaRepository;
import CTRLC.ERRONKA3.repository.SolairuaRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminPageController {

    private final ErabiltzaileRepository erabiltzaileRepository;
    private final EraikinaRepository eraikinaRepository;
    private final GelaRepository gelaRepository;
    private final GailuaRepository gailuaRepository;
    private final SolairuaRepository solairuaRepository;
    private final HistorikoaRepository historikoaRepository;

    public AdminPageController(ErabiltzaileRepository erabiltzaileRepository,
                               EraikinaRepository eraikinaRepository,
                               GelaRepository gelaRepository,
                               GailuaRepository gailuaRepository,
                               SolairuaRepository solairuaRepository,
                               HistorikoaRepository historikoaRepository) {
        this.erabiltzaileRepository = erabiltzaileRepository;
        this.eraikinaRepository = eraikinaRepository;
        this.gelaRepository = gelaRepository;
        this.gailuaRepository = gailuaRepository;
        this.solairuaRepository = solairuaRepository;
        this.historikoaRepository = historikoaRepository;
    }

    @GetMapping("/admin")
    public String adminPage(Model model, HttpSession session) {
        if (!isLoggedIn(session) || !isAdmin(session)) {
            return "redirect:/login";
        }

        List<erabiltzailea> erabiltzaileak = erabiltzaileRepository.findAll();
        List<eraikina> eraikinak = eraikinaRepository.findAll();
        List<gela> gelak = gelaRepository.findAll();
        List<gailua> gailuak = gailuaRepository.findAll();
        List<solairua> solairuak = solairuaRepository.findAll();
        List<historikoa> historikoak = historikoaRepository.findAll();

        model.addAttribute("currentUser", session.getAttribute("loggedUser"));
        model.addAttribute("role", session.getAttribute("role"));
        model.addAttribute("erabiltzaileak", erabiltzaileak);
        model.addAttribute("eraikinak", eraikinak);
        model.addAttribute("gelak", gelak);
        model.addAttribute("gailuak", gailuak);
        model.addAttribute("solairuak", solairuak);
        model.addAttribute("historikoak", historikoak);
        return "admin";
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("loggedUser") != null;
    }

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return role != null && role.contains("admin");
    }
}