package com.studentspace.service;

import com.studentspace.dto.ModuleRequest;
import com.studentspace.dto.ModuleResponse;
import com.studentspace.model.Module;
import com.studentspace.repository.EnseignantRepository;
import com.studentspace.repository.FiliereRepository;
import com.studentspace.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepo;
    private final FiliereRepository filiereRepo;
    private final EnseignantRepository enseignantRepo;

    public List<ModuleResponse> listerTous() {
        return moduleRepo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<ModuleResponse> parEnseignant(Long id) {
        return moduleRepo.findByEnseignantId(id).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<ModuleResponse> parFiliere(Long id) {
        return moduleRepo.findByFiliereId(id).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ModuleResponse creer(ModuleRequest req) {
    Module m = new Module();
    m.setNom(req.getNom());
    m.setDescription(req.getDescription());
    if (req.getFiliereId() != null) filiereRepo.findById(req.getFiliereId()).ifPresent(m::setFiliere);
    if (req.getEnseignantId() != null) enseignantRepo.findById(req.getEnseignantId()).ifPresent(m::setEnseignant);
    return toResponse(moduleRepo.save(m));
}
    public ModuleResponse modifier(Long id, ModuleRequest req) {
        Module m = moduleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Module non trouvé"));
        m.setNom(req.getNom());
        m.setDescription(req.getDescription());
        if (req.getFiliereId() != null) filiereRepo.findById(req.getFiliereId()).ifPresent(m::setFiliere);
        if (req.getEnseignantId() != null) enseignantRepo.findById(req.getEnseignantId()).ifPresent(m::setEnseignant);
        return toResponse(moduleRepo.save(m));
    }

    public void supprimer(Long id) {
        moduleRepo.deleteById(id);
    }

    private ModuleResponse toResponse(Module m) {
        return ModuleResponse.builder()
                .id(m.getId())
                .nom(m.getNom())
                .description(m.getDescription())
                .filiere(m.getFiliere() != null ? m.getFiliere().getNom() : null)
                .filiereId(m.getFiliere() != null ? m.getFiliere().getId() : null)
                .enseignant(m.getEnseignant() != null ? m.getEnseignant().getNom() + " " + m.getEnseignant().getPrenom() : null)
                .enseignantId(m.getEnseignant() != null ? m.getEnseignant().getId() : null)
                .nombreEtudiants(m.getInscriptions() != null ? m.getInscriptions().size() : 0)
                .build();
    }
}
