package com.studentspace.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.*;

/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  ENTITÉ Utilisateur — Classe de base (héritage)              ║
 * ║  Auteur : Abdessamad Ouchaib                                 ║
 * ║  Source : Diagramme de classes du rapport PFE                ║
 * ╚══════════════════════════════════════════════════════════════╝
 *
 * Classe parente pour Etudiant, Enseignant et Administrateur.
 * Stratégie d'héritage : JOINED (table par sous-classe — PostgreSQL).
 * Concepts Spring Boot Coursera : @Entity, @Inheritance, @Column.
 */
@Entity
@Table(name = "utilisateur")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
@NoArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "mot_de_passe_hash", nullable = false)
    private String motDePasseHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "date_creation")
    private OffsetDateTime dateCreation = OffsetDateTime.now();

    @Column(name = "date_mise_a_jour")
    private OffsetDateTime dateMiseAJour = OffsetDateTime.now();

    // Relation : un utilisateur peut envoyer plusieurs messages
    @OneToMany(mappedBy = "expediteur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messagesEnvoyes = new HashSet<>();

    @PreUpdate
    public void preUpdate() {
        this.dateMiseAJour = OffsetDateTime.now();
    }

    // Enum des rôles (Coursera : enums Java)
    public enum Role {
        ETUDIANT, ENSEIGNANT, ADMIN
    }
}
