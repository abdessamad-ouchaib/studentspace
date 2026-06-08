package com.studentspace.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "student_module_enrollment",
       uniqueConstraints = @UniqueConstraint(columnNames = {"etudiant_id", "module_id"}))
public class InscriptionEtudiantModule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "date_inscription") private LocalDate dateInscription = LocalDate.now();
    @Column(name = "date_creation") private OffsetDateTime dateCreation = OffsetDateTime.now();
    @Column(name = "date_mise_a_jour") private OffsetDateTime dateMiseAJour = OffsetDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "etudiant_id", nullable = false) private Etudiant etudiant;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "module_id", nullable = false) private Module module;
    @PreUpdate public void preUpdate() { this.dateMiseAJour = OffsetDateTime.now(); }
    public InscriptionEtudiantModule() {}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public LocalDate getDateInscription(){return dateInscription;} public void setDateInscription(LocalDate v){dateInscription=v;}
    public OffsetDateTime getDateCreation(){return dateCreation;} public void setDateCreation(OffsetDateTime v){dateCreation=v;}
    public OffsetDateTime getDateMiseAJour(){return dateMiseAJour;} public void setDateMiseAJour(OffsetDateTime v){dateMiseAJour=v;}
    public Etudiant getEtudiant(){return etudiant;} public void setEtudiant(Etudiant v){etudiant=v;}
    public Module getModule(){return module;} public void setModule(Module v){module=v;}
}
