package CTRLC.ERRONKA3.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CTRLC.ERRONKA3.model.erabiltzailea;
import CTRLC.ERRONKA3.repository.ErabiltzaileRepository;
import CTRLC.ERRONKA3.service.HistorikoaService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ErabiltzaileaController {

    private final ErabiltzaileRepository erabiltzaileRepository;
    private final HistorikoaService historikoaService;

    public ErabiltzaileaController(ErabiltzaileRepository erabiltzaileRepository,
                                   HistorikoaService historikoaService) {
        this.erabiltzaileRepository = erabiltzaileRepository;
        this.historikoaService = historikoaService;
    }

    @PostMapping("/admin/erabiltzailea/save")
    public String saveErabiltzailea(@RequestParam String id_erab,
                                    @RequestParam String NAN,
                                    @RequestParam String izena,
                                    @RequestParam String abizena,
                                    @RequestParam String helbide_elektronikoa,
                                    @RequestParam String pasahitza,
                                    @RequestParam String erabiltzaile_mota,
                                    HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }

        erabiltzailea erab = new erabiltzailea();
        erab.setId_erab(id_erab);
        erab.setNAN(NAN);
        erab.setIzena(izena);
        erab.setAbizena(abizena);
        erab.setHelbide_elektronikoa(helbide_elektronikoa);
        erab.setPasahitza(pasahitza);
        erab.setErabiltzaile_mota(erabiltzaile_mota);
        erab.setAlta_data(new Date());
        erabiltzaileRepository.save(erab);

        historikoaService.logAction("erabiltzailea", "INSERT", "Erabiltzailea sortu: " + id_erab);
        return "redirect:/admin";
    }

    @PostMapping("/admin/erabiltzailea/update")
    public String updateErabiltzailea(@RequestParam String id_erab,
                                      @RequestParam String NAN,
                                      @RequestParam String izena,
                                      @RequestParam String abizena,
                                      @RequestParam String helbide_elektronikoa,
                                      @RequestParam String pasahitza,
                                      @RequestParam String erabiltzaile_mota,
                                      HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }

        erabiltzailea erab = new erabiltzailea();
        erab.setId_erab(id_erab);
        erab.setNAN(NAN);
        erab.setIzena(izena);
        erab.setAbizena(abizena);
        erab.setHelbide_elektronikoa(helbide_elektronikoa);
        erab.setPasahitza(pasahitza);
        erab.setErabiltzaile_mota(erabiltzaile_mota);
        Date altaData = erabiltzaileRepository.findById(id_erab)
            .map(erabiltzailea::getAlta_data)
            .orElse(new Date());
        erab.setAlta_data(altaData);
        erabiltzaileRepository.save(erab);

        historikoaService.logAction("erabiltzailea", "UPDATE", "Erabiltzailea aldatu: " + id_erab);
        return "redirect:/admin";
    }

    @PostMapping("/admin/erabiltzailea/delete")
    public String deleteErabiltzailea(@RequestParam String id_erab, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/admin?accessDenied=true";
        }
        erabiltzaileRepository.deleteById(id_erab);
        historikoaService.logAction("erabiltzailea", "DELETE", "Erabiltzailea ezabatu: " + id_erab);
        return "redirect:/admin";
    }

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return role != null && role.contains("admin");
    }
}