package com.studentspace.repository;
import com.studentspace.model.Module; // explicit import — évite ambiguïté avec java.lang.Module
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findByFiliereId(Long filiereId);
    List<Module> findByEnseignantId(Long enseignantId);

    @Query("SELECT m FROM Module m WHERE LOWER(m.nom) LIKE LOWER(CONCAT('%', :terme, '%'))")
    List<Module> rechercherParNom(@Param("terme") String terme);
}
