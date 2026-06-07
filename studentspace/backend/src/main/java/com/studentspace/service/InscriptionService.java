package com.studentspace.service;

import com.studentspace.dto.InscriptionRequest;
import com.studentspace.dto.InscriptionResponse;
import com.studentspace.model.InscriptionEtudiantModule;
import com.studentspace.repository.EtudiantRepository;
import com.studentspace.repository.InscriptionRepository;
import com.studentspace.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InscriptionService {

    private final InscriptionRepository inscriptionRepo;
    private final EtudiantRepository etudiantRepo;
    private final ModuleRepository moduleRepo;

    public List<InscriptionResponse> listerToutes() {
        return inscriptionRepo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<InscriptionResponse> parEtudiant(Long etudiantId) {
        return inscriptionRepo.findByEtudiantId(etudiantId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public InscriptionResponse inscrire(InscriptionRequest req) {
        InscriptionEtudiantModule i = InscriptionEtudiantModule.builder()
                .etudiant(etudiantRepo.findById(req.getEtudiantId()).orElseThrow())
                .module(moduleRepo.findById(req.getModuleId()).orElseThrow())
                .build();
        return toResponse(inscriptionRepo.save(i));
    }

    public void desinscrire(Long id) {
        inscriptionRepo.deleteById(id);
    }

    private InscriptionResponse toResponse(InscriptionEtudiantModule i) {
        return InscriptionResponse.builder()
                .id(i.getId())
                .etudiantNom(i.getEtudiant() != null ? i.getEtudiant().getPrenom() + " " + i.getEtudiant().getNom() : null)
                .etudiantId(i.getEtudiant() != null ? i.getEtudiant().getId() : null)
                .moduleNom(i.getModule() != null ? i.getModule().getNom() : null)
                .moduleId(i.getModule() != null ? i.getModule().getId() : null)
                .dateInscription(i.getDateInscription() != null ? i.getDateInscription().toString() : null)
                .build();
    }
}
