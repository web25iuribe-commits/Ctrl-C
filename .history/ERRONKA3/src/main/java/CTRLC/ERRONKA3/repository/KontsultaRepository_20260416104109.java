package CTRLC.ERRONKA3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CTRLC.ERRONKA3.model.kontsulta;

@Repository
public interface KontsultaRepository extends JpaRepository<kontsulta, String> {
    // JpaRepository provides basic CRUD operations
}
