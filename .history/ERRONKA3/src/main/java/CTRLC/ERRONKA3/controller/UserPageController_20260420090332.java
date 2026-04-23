package CTRLC.ERRONKA3.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CTRLC.ERRONKA3.model.erabiltzailea;
import CTRLC.ERRONKA3.model.eraikina;
import CTRLC.ERRONKA3.model.gailua;
import CTRLC.ERRONKA3.model.gela;
import CTRLC.ERRONKA3.model.solairua;
import CTRLC.ERRONKA3.repository.ErabiltzaileRepository;
import CTRLC.ERRONKA3.repository.EraikinaRepository;
import CTRLC.ERRONKA3.repository.GailuaRepository;
import CTRLC.ERRONKA3.repository.GelaRepository;
import CTRLC.ERRONKA3.repository.SolairuaRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserPageController {

    private final ErabiltzaileRepository erabiltzaileRepository;
    private final EraikinaRepository eraikinaRepository;
    private final GelaRepository gelaRepository;
    private final GailuaRepository gailuaRepository;
    private final SolairuaRepository solairuaRepository;

    public UserPageController(ErabiltzaileRepository erabiltzaileRepository,
                              EraikinaRepository eraikinaRepository,
                              GelaRepository gelaRepository,
                              GailuaRepository gailuaRepository,
                              SolairuaRepository solairuaRepository) {
        this.erabiltzaileRepository = erabiltzaileRepository;
        this.eraikinaRepository = eraikinaRepository;
        this.gelaRepository = gelaRepository;
        this.gailuaRepository = gailuaRepository;
        this.solairuaRepository = solairuaRepository;
    }

    @GetMapping("/user")
    public String userPage(Model model,
                           HttpSession session,
                           @RequestParam(value = "searchUserId", required = false) String searchUserId,
                           @RequestParam(value = "searchUserName", required = false) String searchUserName,
                           @RequestParam(value = "searchBuildId", required = false) String searchBuildId,
                           @RequestParam(value = "searchBuildTerm", required = false) String searchBuildTerm,
                           @RequestParam(value = "searchGelaTerm", required = false) String searchGelaTerm,
                           @RequestParam(value = "searchGailuTerm", required = false) String searchGailuTerm,
                           @RequestParam(value = "searchSolairuTerm", required = false) String searchSolairuTerm) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        if (isAdmin(session)) {
            return "redirect:/admin";
        }

        List<erabiltzailea> erabiltzaileak = getUserSearchResults(searchUserId, searchUserName);
        List<eraikina> eraikinak = getBuildingSearchResults(searchBuildId, searchBuildTerm);
        List<gela> gelak = getGelaSearchResults(searchGelaTerm);
        List<gailua> gailuak = getGailuaSearchResults(searchGailuTerm);
        List<solairua> solairuak = getSolairuaSearchResults(searchSolairuTerm);

        model.addAttribute("currentUser", session.getAttribute("loggedUser"));
        model.addAttribute("role", session.getAttribute("role"));
        model.addAttribute("erabiltzaileak", erabiltzaileak);
        model.addAttribute("eraikinak", eraikinak);
        model.addAttribute("gelak", gelak);
        model.addAttribute("gailuak", gailuak);
        model.addAttribute("solairuak", solairuak);
        model.addAttribute("searchUserId", searchUserId);
        model.addAttribute("searchUserName", searchUserName);
        model.addAttribute("searchBuildId", searchBuildId);
        model.addAttribute("searchBuildTerm", searchBuildTerm);
        model.addAttribute("searchGelaTerm", searchGelaTerm);
        model.addAttribute("searchGailuTerm", searchGailuTerm);
        model.addAttribute("searchSolairuTerm", searchSolairuTerm);
        return "user";
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("loggedUser") != null;
    }

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return role != null && role.contains("admin");
    }

    private List<erabiltzailea> getUserSearchResults(String searchUserId, String searchUserName) {
        if (searchUserId != null && !searchUserId.isBlank()) {
            return erabiltzaileRepository.findById(searchUserId).map(List::of).orElse(Collections.emptyList());
        }
        if (searchUserName != null && !searchUserName.isBlank()) {
            return erabiltzaileRepository.findByNameContaining(searchUserName);
        }
        return erabiltzaileRepository.findAll();
    }

    private List<eraikina> getBuildingSearchResults(String searchBuildId, String searchBuildTerm) {
        if (searchBuildId != null && !searchBuildId.isBlank()) {
            return eraikinaRepository.findById(searchBuildId).map(List::of).orElse(Collections.emptyList());
        }
        if (searchBuildTerm != null && !searchBuildTerm.isBlank()) {
            return eraikinaRepository.findByTermContaining(searchBuildTerm);
        }
        return eraikinaRepository.findAll();
    }

    private List<gela> getGelaSearchResults(String searchGelaTerm) {
        if (searchGelaTerm != null && !searchGelaTerm.isBlank()) {
            return gelaRepository.findByTermContaining(searchGelaTerm);
        }
        return gelaRepository.findAll();
    }

    private List<gailua> getGailuaSearchResults(String searchGailuTerm) {
        if (searchGailuTerm != null && !searchGailuTerm.isBlank()) {
            return gailuaRepository.findByTermContaining(searchGailuTerm);
        }
        return gailuaRepository.findAll();
    }

    private List<solairua> getSolairuaSearchResults(String searchSolairuTerm) {
        if (searchSolairuTerm != null && !searchSolairuTerm.isBlank()) {
            return solairuaRepository.findByTermContaining(searchSolairuTerm);
        }
        return solairuaRepository.findAll();
    }
}