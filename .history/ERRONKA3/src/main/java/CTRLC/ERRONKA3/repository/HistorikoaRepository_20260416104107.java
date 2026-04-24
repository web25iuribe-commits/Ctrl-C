package CTRLC.ERRONKA3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CTRLC.ERRONKA3.model.historikoa;

@Repository
public interface HistorikoaRepository extends JpaRepository<historikoa, Integer> {
    // JpaRepository provides basic CRUD operations
}
