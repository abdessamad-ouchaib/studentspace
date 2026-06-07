package com.studentspace.service;

import com.studentspace.dto.ExamenRequest;
import com.studentspace.dto.ExamenResponse;
import com.studentspace.model.Examen;
import com.studentspace.repository.ExamenRepository;
import com.studentspace.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamenService {

    private final ExamenRepository examenRepo;
    private final ModuleRepository moduleRepo;

    public List<ExamenResponse> listerTous() {
        return examenRepo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<ExamenResponse> parModule(Long moduleId) {
        return examenRepo.findByModuleId(moduleId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ExamenResponse creer(ExamenRequest req) {
        Examen e = Examen.builder()
                .titre(req.getTitre())
                .anneeExamen(req.getAnneeExamen())
                .fichierUrl(req.getFichierUrl())
                .module(req.getModuleId() != null ? moduleRepo.findById(req.getModuleId()).orElseThrow() : null)
                .build();
        return toResponse(examenRepo.save(e));
    }

    public ExamenResponse modifier(Long id, ExamenRequest req) {
        Examen e = examenRepo.findById(id).orElseThrow(() -> new RuntimeException("Examen non trouvé"));
        e.setTitre(req.getTitre());
        e.setAnneeExamen(req.getAnneeExamen());
        e.setFichierUrl(req.getFichierUrl());
        if (req.getModuleId() != null) moduleRepo.findById(req.getModuleId()).ifPresent(e::setModule);
        return toResponse(examenRepo.save(e));
    }

    public void supprimer(Long id) {
        examenRepo.deleteById(id);
    }

    private ExamenResponse toResponse(Examen e) {
        return ExamenResponse.builder()
                .id(e.getId())
                .titre(e.getTitre())
                .anneeExamen(e.getAnneeExamen())
                .fichierUrl(e.getFichierUrl())
                .module(e.getModule() != null ? e.getModule().getNom() : null)
                .moduleId(e.getModule() != null ? e.getModule().getId() : null)
                .dateTeleversement(e.getDateTeleversement() != null ? e.getDateTeleversement().toString() : null)
                .build();
    }
}
