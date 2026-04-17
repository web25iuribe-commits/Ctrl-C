package CTRLC.ERRONKA3.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import CTRLC.ERRONKA3.model.solairua;


@Repository // Spring-i esaten dio klase honek datu-basearekin komunikazioa kudeatzen duela
public interface SolairuaRepository extends JpaRepository<solairua, String> {

    @Query("SELECT s FROM solairua s WHERE LOWER(s.id_solairua) LIKE LOWER(CONCAT('%', :term, '%')) OR LOWER(s.id_eraikina) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<solairua> findByTermContaining(@Param("term") String term);
   
    /* Hemen ez dugu metodorik idatzi behar momentuz.
       JpaRepository luzatzean, Spring-ek automatikoki ematen dizkigu:
       - findAll(): Gailua guztiak zerrendan lortzeko.
       - save(gailua): Gailua berri bat gordetzeko edo eguneratzeko.
       - findById(id): Id baten bidez gailua bilatzeko.
       - deleteById(id): Gailua ezabatzeko.
    */
}