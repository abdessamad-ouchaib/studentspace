package com.studentspace.repository;
import com.studentspace.model.InscriptionEtudiantModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<InscriptionEtudiantModule, Long> {
    List<InscriptionEtudiantModule> findByEtudiantId(Long etudiantId);
    List<InscriptionEtudiantModule> findByModuleId(Long moduleId);
    boolean existsByEtudiantIdAndModuleId(Long etudiantId, Long moduleId);
    void deleteByEtudiantIdAndModuleId(Long etudiantId, Long moduleId);
    long countByModuleId(Long moduleId);
}
