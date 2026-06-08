package com.studentspace.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "examen")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Examen {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String titre;
    @Column(name = "annee_examen") private Integer anneeExamen;
    @Column(name = "fichier_url") private String fichierUrl;
    @Column(name = "date_televersement") private OffsetDateTime dateTeleversement;
    @Column(name = "date_creation") @Builder.Default private OffsetDateTime dateCreation = OffsetDateTime.now();
    @Column(name = "date_mise_a_jour") @Builder.Default private OffsetDateTime dateMiseAJour = OffsetDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "module_id", nullable = false) private Module module;
    @PreUpdate public void preUpdate() { this.dateMiseAJour = OffsetDateTime.now(); }
}
