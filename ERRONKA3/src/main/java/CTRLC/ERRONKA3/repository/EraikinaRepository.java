package CTRLC.ERRONKA3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import CTRLC.ERRONKA3.model.eraikina;


@Repository // Spring-i esaten dio klase honek datu-basearekin komunikazioa kudeatzen duela
public interface EraikinaRepository extends JpaRepository<eraikina, String> {

    @Query("SELECT e FROM eraikina e WHERE LOWER(e.id_eraikina) LIKE LOWER(CONCAT('%', :term, '%')) OR LOWER(e.izena) LIKE LOWER(CONCAT('%', :term, '%')) OR LOWER(e.helbidea) LIKE LOWER(CONCAT('%', :term, '%')) OR LOWER(e.hiria) LIKE LOWER(CONCAT('%', :term, '%')) OR LOWER(e.posta_kodea) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<eraikina> findByTermContaining(@Param("term") String term);
   
    /* Hemen ez dugu metodorik idatzi behar momentuz.
       JpaRepository luzatzean, Spring-ek automatikoki ematen dizkigu:
       - findAll(): Eraikin guztiak zerrendan lortzeko.
       - save(eraikina): Eraikin berri bat gordetzeko edo eguneratzeko.
       - findById(id): Id baten bidez eraikina bilatzeko.
       - deleteById(id): Eraikina ezabatzeko.
    */
}

