package CTRLC.ERRONKA3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import CTRLC.ERRONKA3.model.erabiltzailea;


@Repository // Spring-i esaten dio klase honek datu-basearekin komunikazioa kudeatzen duela
public interface ErabiltzaileRepository extends JpaRepository<erabiltzailea, String> {

    @Query("SELECT u FROM erabiltzailea u WHERE u.erabiltzaile_mota = :mota")
    Optional<erabiltzailea> findFirstByMota(@Param("mota") String mota);

    @Query("SELECT u FROM erabiltzailea u WHERE LOWER(u.helbide_elektronikoa) = LOWER(:email) AND u.pasahitza = :pass")
    Optional<erabiltzailea> findByEmailAndPassword(@Param("email") String email,
                                                   @Param("pass") String pasahitza);

    Optional<erabiltzailea> findById(String id_erab);

    @Query("SELECT u FROM erabiltzailea u WHERE LOWER(u.izena) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(u.abizena) LIKE LOWER(CONCAT('%', :name, '%'))")
    java.util.List<erabiltzailea> findByNameContaining(@Param("name") String name);

}

