package com.studentspace.controller;

import com.studentspace.dto.*;
import com.studentspace.model.Enseignant;
import com.studentspace.model.Etudiant;
import com.studentspace.model.Utilisateur;
import com.studentspace.repository.EnseignantRepository;
import com.studentspace.repository.EtudiantRepository;
import com.studentspace.repository.UtilisateurRepository;
import com.studentspace.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://studentspace01.vercel.app"})
public class AuthController {

    private final EtudiantRepository etudiantRepo;
    private final EnseignantRepository enseignantRepo;
    private final UtilisateurRepository utilisateurRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /** Login étudiant par numéro APOGEE uniquement */
    @PostMapping("/etudiant")
    public ResponseEntity<AuthResponse> loginEtudiant(@RequestBody LoginEtudiantRequest req) {
        Etudiant e = etudiantRepo.findByNumeroApogee(req.getNumeroApogee())
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));

        String token = jwtService.genererToken(e.getNumeroApogee(), "ETUDIANT");

        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .role("ETUDIANT")
                .userId(e.getId())
                .nom(e.getNom())
                .prenom(e.getPrenom())
                .email(e.getEmail())
                .numeroApogee(e.getNumeroApogee())
                .build());
    }

    /** Login enseignant par email + mot de passe */
    @PostMapping("/enseignant")
    public ResponseEntity<AuthResponse> loginEnseignant(@RequestBody LoginEnseignantRequest req) {
        Enseignant ens = enseignantRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Enseignant non trouvé"));

        if (!passwordEncoder.matches(req.getMotDePasse(), ens.getMotDePasseHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtService.genererToken(ens.getEmail(), "ENSEIGNANT");

        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .role("ENSEIGNANT")
                .userId(ens.getId())
                .nom(ens.getNom())
                .prenom(ens.getPrenom())
                .email(ens.getEmail())
                .build());
    }

    /** Login admin par email + mot de passe */
    @PostMapping("/admin")
    public ResponseEntity<AuthResponse> loginAdmin(@RequestBody LoginEnseignantRequest req) {
        Utilisateur u = utilisateurRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!u.getRole().name().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (!passwordEncoder.matches(req.getMotDePasse(), u.getMotDePasseHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtService.genererToken(u.getEmail(), "ADMIN");

        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .role("ADMIN")
                .userId(u.getId())
                .email(u.getEmail())
                .build());
    }
}
