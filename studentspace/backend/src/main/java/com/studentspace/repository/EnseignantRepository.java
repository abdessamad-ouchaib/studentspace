package com.studentspace.repository;
import com.studentspace.model.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    Optional<Enseignant> findByEmail(String email);
    List<Enseignant> findBySpecialite(String specialite);

    @Query("SELECT e FROM Enseignant e WHERE LOWER(e.nom) LIKE LOWER(CONCAT('%', :terme, '%')) " +
           "OR LOWER(e.prenom) LIKE LOWER(CONCAT('%', :terme, '%'))")
    List<Enseignant> rechercherParNom(@Param("terme") String terme);
}
