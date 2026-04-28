package CTRLC.ERRONKA3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CTRLC.ERRONKA3.model.eraikina;


@Repository // Spring-i esaten dio klase honek datu-basearekin komunikazioa kudeatzen duela
public interface EraikinaRepository extends JpaRepository<eraikina, String> {
   
    /* Hemen ez dugu metodorik idatzi behar momentuz.
       JpaRepository luzatzean, Spring-ek automatikoki ematen dizkigu:
       - findAll(): Eraikin guztiak zerrendan lortzeko.
       - save(eraikina): Eraikin berri bat gordetzeko edo eguneratzeko.
       - findById(id): Id baten bidez eraikina bilatzeko.
       - deleteById(id): Eraikina ezabatzeko.
    */
}

