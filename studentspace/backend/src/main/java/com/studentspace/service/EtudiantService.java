package com.studentspace.service;

import com.studentspace.dto.EtudiantRequest;
import com.studentspace.dto.EtudiantResponse;
import com.studentspace.model.Etudiant;
import com.studentspace.model.Filiere;
import com.studentspace.repository.EtudiantRepository;
import com.studentspace.repository.FiliereRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EtudiantService {

    private final EtudiantRepository etudiantRepo;
    private final FiliereRepository filiereRepo;
    private final PasswordEncoder passwordEncoder;

    public List<EtudiantResponse> listerTous() {
        return etudiantRepo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public EtudiantResponse trouverParId(Long id) {
        return toResponse(etudiantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé")));
    }

    public EtudiantResponse trouverParApogee(String apogee) {
        return toResponse(etudiantRepo.findByNumeroApogee(apogee)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé")));
    }

    public EtudiantResponse creer(EtudiantRequest req) {
        Filiere filiere = req.getFiliereId() != null
                ? filiereRepo.findById(req.getFiliereId()).orElse(null) : null;

        Etudiant e = new Etudiant(
                req.getEmail(),
                passwordEncoder.encode(req.getMotDePasse()),
                req.getNumeroApogee(),
                req.getNom(),
                req.getPrenom(),
                filiere
        );
        return toResponse(etudiantRepo.save(e));
    }

    public EtudiantResponse modifier(Long id, EtudiantRequest req) {
        Etudiant e = etudiantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
        e.setNom(req.getNom());
        e.setPrenom(req.getPrenom());
        e.setEmail(req.getEmail());
        if (req.getMotDePasse() != null && !req.getMotDePasse().isBlank()) {
            e.setMotDePasseHash(passwordEncoder.encode(req.getMotDePasse()));
        }
        if (req.getFiliereId() != null) {
            filiereRepo.findById(req.getFiliereId()).ifPresent(e::setFiliere);
        }
        return toResponse(etudiantRepo.save(e));
    }

    public void supprimer(Long id) {
        etudiantRepo.deleteById(id);
    }

    private EtudiantResponse toResponse(Etudiant e) {
        return EtudiantResponse.builder()
                .id(e.getId())
                .numeroApogee(e.getNumeroApogee())
                .nom(e.getNom())
                .prenom(e.getPrenom())
                .email(e.getEmail())
                .filiere(e.getFiliere() != null ? e.getFiliere().getNom() : null)
                .filiereId(e.getFiliere() != null ? e.getFiliere().getId() : null)
                .dateCreation(e.getDateCreation() != null ? e.getDateCreation().toString() : null)
                .build();
    }
}
