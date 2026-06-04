package com.studentspace.repository;
import com.studentspace.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    Optional<Etudiant> findByNumeroApogee(String numeroApogee);
    boolean existsByNumeroApogee(String numeroApogee);
    List<Etudiant> findByFiliereId(Long filiereId);

    @Query("SELECT e FROM Etudiant e WHERE LOWER(e.nom) LIKE LOWER(CONCAT('%', :terme, '%')) " +
           "OR LOWER(e.prenom) LIKE LOWER(CONCAT('%', :terme, '%'))")
    List<Etudiant> rechercherParNom(@Param("terme") String terme);
}
