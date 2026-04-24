package CTRLC.ERRONKA3.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import CTRLC.ERRONKA3.model.gailua;


@Repository // Spring-i esaten dio klase honek datu-basearekin komunikazioa kudeatzen duela
public interface GailuaRepository extends JpaRepository<gailua, String> {

    @Query("SELECT g FROM gailua g WHERE LOWER(g.id_gailua) LIKE LOWER(CONCAT('%', :term, '%')) OR LOWER(g.marka) LIKE LOWER(CONCAT('%', :term, '%')) OR LOWER(g.modeloa) LIKE LOWER(CONCAT('%', :term, '%')) OR LOWER(g.serie_zenb) LIKE LOWER(CONCAT('%', :term, '%')) OR LOWER(g.gela_zenb) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<gailua> findByTermContaining(@Param("term") String term);
   
    /* Hemen ez dugu metodorik idatzi behar momentuz.
       JpaRepository luzatzean, Spring-ek automatikoki ematen dizkigu:
       - findAll(): Gailua guztiak zerrendan lortzeko.
       - save(gailua): Gailua berri bat gordetzeko edo eguneratzeko.
       - findById(id): Id baten bidez gailua bilatzeko.
       - deleteById(id): Gailua ezabatzeko.
    */
}
