package com.studentspace.repository;
import com.studentspace.model.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {
    List<Examen> findByModuleId(Long moduleId);
    List<Examen> findByAnneeExamen(Integer annee);
}
