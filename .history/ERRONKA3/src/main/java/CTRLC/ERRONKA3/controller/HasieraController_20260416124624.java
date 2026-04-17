package CTRLC.ERRONKA3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import CTRLC.ERRONKA3.model.erabiltzailea;
import CTRLC.ERRONKA3.model.eraikina;
import CTRLC.ERRONKA3.repository.ErabiltzaileRepository;
import CTRLC.ERRONKA3.repository.EraikinaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // Spring-i esaten dio klase honek HTTP eskariak (URLak) jasoko dituela
public class HasieraController {

    private final ErabiltzaileRepository erabiltzaileRepository;
    private final EraikinaRepository eraikinaRepository;

    @Autowired // Lotura automatikoa.  Spring-ek automatikoki bilatuko du Repository-aren inplementazioa
    public HasieraController(ErabiltzaileRepository erabiltzaileRepository,
                             EraikinaRepository eraikinaRepository) {
        this.erabiltzaileRepository = erabiltzaileRepository;
        this.eraikinaRepository = eraikinaRepository;
    }

    @PostMapping("/login")
    public String loginForm(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "login";
    }

    @PostMapping("/login")
    public String autenticar(@RequestParam String username,
                             @RequestParam String pasahitza,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        erabiltzailea user = erabiltzaileRepository.findByUsernameAndPassword(username, pasahitza).orElse(null);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Erabiltzaile izena edo pasahitza ez daude zuzenak.");
            return "redirect:/login";
        }
        session.setAttribute("loggedUser", user);
        session.setAttribute("role", user.getErabiltzaile_mota());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping({"/", "/eraikina", "/usuarios"})
    public String kaixo(Model model,
                        HttpSession session,
                        HttpServletRequest request,
                        @RequestParam(value = "accessDenied", required = false) String accessDenied) {
        erabiltzailea loggedUser = (erabiltzailea) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }

        String path = request.getServletPath();
        String mezua = switch (path) {
            case "/eraikina" -> "Eraikinen zerrenda";
            case "/usuarios" -> "Erabiltzaile bereziak";
            default -> "Ongi etorri Miguel Altunako Gailuen Kudeatzailera!";
        };

        String role = (String) session.getAttribute("role");
        boolean isAdmin = "administrador".equals(role);

        model.addAttribute("mezua", mezua);
        model.addAttribute("erabiltzaileak", erabiltzaileRepository.findAll());
        model.addAttribute("eraikinak", eraikinaRepository.findAll());
        model.addAttribute("currentUser", loggedUser);
        model.addAttribute("role", role);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isUsuario", "usuario".equals(role));
        model.addAttribute("accessDenied", accessDenied != null);
        return "index";
    }

    @PostMapping("/eraikina/save")
    public String saveEraikina(@RequestParam String id_eraikina,
                               @RequestParam String izena,
                               @RequestParam String helbidea,
                               @RequestParam String hiria,
                               @RequestParam String posta_kodea,
                               HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/?accessDenied=true";
        }

        eraikina eraikina = new eraikina();
        eraikina.setId_eraikina(id_eraikina);
        eraikina.setIzena(izena);
        eraikina.setHelbidea(helbidea);
        eraikina.setHiria(hiria);
        eraikina.setPosta_kodea(posta_kodea);
        eraikinaRepository.save(eraikina);
        return "redirect:/";
    }

    @PostMapping("/eraikina/delete")
    public String deleteEraikina(@RequestParam String id_eraikina,
                                 HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/?accessDenied=true";
        }
        eraikinaRepository.deleteById(id_eraikina);
        return "redirect:/";
    }

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return role != null && role.equals("administrador");
    }
}
