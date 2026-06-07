package com.studentspace.service;

import com.studentspace.dto.FiliereRequest;
import com.studentspace.dto.FiliereResponse;
import com.studentspace.model.Filiere;
import com.studentspace.repository.FiliereRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FiliereService {

    private final FiliereRepository filiereRepo;

    public List<FiliereResponse> listerToutes() {
        return filiereRepo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public FiliereResponse trouverParId(Long id) {
        return toResponse(filiereRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Filière non trouvée")));
    }

    public FiliereResponse creer(FiliereRequest req) {
        Filiere f = Filiere.builder()
                .nom(req.getNom())
                .description(req.getDescription())
                .anneeUniversitaire(req.getAnneeUniversitaire())
                .semestre(req.getSemestre())
                .build();
        return toResponse(filiereRepo.save(f));
    }

    public FiliereResponse modifier(Long id, FiliereRequest req) {
        Filiere f = filiereRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Filière non trouvée"));
        f.setNom(req.getNom());
        f.setDescription(req.getDescription());
        f.setAnneeUniversitaire(req.getAnneeUniversitaire());
        f.setSemestre(req.getSemestre());
        return toResponse(filiereRepo.save(f));
    }

    public void supprimer(Long id) {
        filiereRepo.deleteById(id);
    }

    private FiliereResponse toResponse(Filiere f) {
        return FiliereResponse.builder()
                .id(f.getId())
                .nom(f.getNom())
                .description(f.getDescription())
                .anneeUniversitaire(f.getAnneeUniversitaire())
                .semestre(f.getSemestre())
                .nombreEtudiants(f.getEtudiantsFiliere() != null ? f.getEtudiantsFiliere().size() : 0)
                .nombreModules(f.getModulesFiliere() != null ? f.getModulesFiliere().size() : 0)
                .build();
    }
}
