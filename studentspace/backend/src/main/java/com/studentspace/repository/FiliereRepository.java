package com.studentspace.repository;
import com.studentspace.model.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere, Long> {
    Optional<Filiere> findByNom(String nom);
    List<Filiere> findByAnneeUniversitaire(Integer annee);
    boolean existsByNom(String nom);
}
