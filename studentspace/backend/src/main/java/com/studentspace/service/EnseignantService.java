package com.studentspace.service;

import com.studentspace.dto.EnseignantRequest;
import com.studentspace.dto.EnseignantResponse;
import com.studentspace.model.Enseignant;
import com.studentspace.repository.EnseignantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnseignantService {

    private final EnseignantRepository enseignantRepo;
    private final PasswordEncoder passwordEncoder;

    public List<EnseignantResponse> listerTous() {
        return enseignantRepo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public EnseignantResponse trouverParId(Long id) {
        return toResponse(enseignantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Enseignant non trouvé")));
    }

    public EnseignantResponse creer(EnseignantRequest req) {
        Enseignant e = new Enseignant(
                req.getEmail(),
                passwordEncoder.encode(req.getMotDePasse()),
                req.getNom(),
                req.getPrenom(),
                req.getSpecialite(),
                req.getTelephone()
        );
        e.setDepartement(req.getDepartement());
        e.setBureau(req.getBureau());
        return toResponse(enseignantRepo.save(e));
    }

    public EnseignantResponse modifier(Long id, EnseignantRequest req) {
        Enseignant e = enseignantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Enseignant non trouvé"));
        e.setNom(req.getNom());
        e.setPrenom(req.getPrenom());
        e.setEmail(req.getEmail());
        e.setSpecialite(req.getSpecialite());
        e.setTelephone(req.getTelephone());
        e.setDepartement(req.getDepartement());
        e.setBureau(req.getBureau());
        if (req.getMotDePasse() != null && !req.getMotDePasse().isBlank()) {
            e.setMotDePasseHash(passwordEncoder.encode(req.getMotDePasse()));
        }
        return toResponse(enseignantRepo.save(e));
    }

    public void supprimer(Long id) {
        enseignantRepo.deleteById(id);
    }

    private EnseignantResponse toResponse(Enseignant e) {
        return EnseignantResponse.builder()
                .id(e.getId())
                .nom(e.getNom())
                .prenom(e.getPrenom())
                .email(e.getEmail())
                .telephone(e.getTelephone())
                .specialite(e.getSpecialite())
                .departement(e.getDepartement())
                .bureau(e.getBureau())
                .nombreModules(e.getModulesEnseignant() != null ? e.getModulesEnseignant().size() : 0)
                .build();
    }
}
