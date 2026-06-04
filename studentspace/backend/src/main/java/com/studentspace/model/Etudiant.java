package com.studentspace.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  ENTITÉ Etudiant — Hérite de Utilisateur                     ║
 * ║  Auteur : Abdessamad Ouchaib                                 ║
 * ╚══════════════════════════════════════════════════════════════╝
 *
 * Table PostgreSQL : etudiant (+ table utilisateur via JOIN).
 * Identifié de façon unique par le numéro APOGEE.
 */
@Entity
@Table(name = "etudiant")
@PrimaryKeyJoinColumn(name = "utilisateur_id")
@Getter @Setter @NoArgsConstructor
public class Etudiant extends Utilisateur {

    @Column(name = "numero_apogee", nullable = false, unique = true)
    private String numeroApogee;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    // Un étudiant appartient à une filière
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;

    // Un étudiant a plusieurs inscriptions aux modules
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<InscriptionEtudiantModule> inscriptions = new HashSet<>();

    // Constructeur pratique
    public Etudiant(String email, String motDePasseHash,
                    String numeroApogee, String nom, String prenom, Filiere filiere) {
        setEmail(email);
        setMotDePasseHash(motDePasseHash);
        setRole(Role.ETUDIANT);
        this.numeroApogee = numeroApogee;
        this.nom          = nom;
        this.prenom       = prenom;
        this.filiere      = filiere;
    }
}
