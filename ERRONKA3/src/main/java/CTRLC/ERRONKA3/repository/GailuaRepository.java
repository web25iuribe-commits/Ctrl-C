package CTRLC.ERRONKA3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CTRLC.ERRONKA3.model.gailua;


@Repository // Spring-i esaten dio klase honek datu-basearekin komunikazioa kudeatzen duela
public interface GailuaRepository extends JpaRepository<gailua, String> {
   
    /* Hemen ez dugu metodorik idatzi behar momentuz.
       JpaRepository luzatzean, Spring-ek automatikoki ematen dizkigu:
       - findAll(): Gailua guztiak zerrendan lortzeko.
       - save(gailua): Gailua berri bat gordetzeko edo eguneratzeko.
       - findById(id): Id baten bidez gailua bilatzeko.
       - deleteById(id): Gailua ezabatzeko.
    */
}
