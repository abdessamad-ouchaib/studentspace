package com.studentspace.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name = "message")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenu;

    @Column(name = "date_envoi", nullable = false)
    private OffsetDateTime dateEnvoi = OffsetDateTime.now();

    @Column(name = "date_creation")
    private OffsetDateTime dateCreation = OffsetDateTime.now();

    @Column(name = "date_mise_a_jour")
    private OffsetDateTime dateMiseAJour = OffsetDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expediteur_id", nullable = false)
    private Utilisateur expediteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @PreUpdate
    public void preUpdate() { this.dateMiseAJour = OffsetDateTime.now(); }
}
