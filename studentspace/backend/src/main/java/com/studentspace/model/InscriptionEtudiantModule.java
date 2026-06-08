package com.studentspace.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "student_module_enrollment",
       uniqueConstraints = @UniqueConstraint(columnNames = {"etudiant_id", "module_id"}))
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InscriptionEtudiantModule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "date_inscription") @Builder.Default private LocalDate dateInscription = LocalDate.now();
    @Column(name = "date_creation") @Builder.Default private OffsetDateTime dateCreation = OffsetDateTime.now();
    @Column(name = "date_mise_a_jour") @Builder.Default private OffsetDateTime dateMiseAJour = OffsetDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "etudiant_id", nullable = false) private Etudiant etudiant;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "module_id", nullable = false) private Module module;
    @PreUpdate public void preUpdate() { this.dateMiseAJour = OffsetDateTime.now(); }
}
