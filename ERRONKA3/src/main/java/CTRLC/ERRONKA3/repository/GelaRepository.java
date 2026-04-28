package CTRLC.ERRONKA3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CTRLC.ERRONKA3.model.gela;


@Repository // Spring-i esaten dio klase honek datu-basearekin komunikazioa kudeatzen duela
public interface GelaRepository extends JpaRepository<gela, String> {
    /* Hemen ez dugu metodorik idatzi behar momentuz.
       JpaRepository luzatzean, Spring-ek automatikoki ematen dizkigu:
       - findAll(): Gela guztiak zerrendan lortzeko.
       - save(gela): Gela berri bat gordetzeko edo eguneratzeko.
       - findById(id): Id baten bidez gailua bilatzeko.
       - deleteById(id): Gailua ezabatzeko.
    */
}
