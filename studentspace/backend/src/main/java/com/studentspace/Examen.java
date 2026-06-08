package com.studentspace.model;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "examen")
public class Examen {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String titre;
    @Column(name = "annee_examen") private Integer anneeExamen;
    @Column(name = "fichier_url") private String fichierUrl;
    @Column(name = "date_televersement") private OffsetDateTime dateTeleversement;
    @Column(name = "date_creation") private OffsetDateTime dateCreation = OffsetDateTime.now();
    @Column(name = "date_mise_a_jour") private OffsetDateTime dateMiseAJour = OffsetDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "module_id", nullable = false) private Module module;
    @PreUpdate public void preUpdate() { this.dateMiseAJour = OffsetDateTime.now(); }
    public Examen() {}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getTitre(){return titre;} public void setTitre(String v){titre=v;}
    public Integer getAnneeExamen(){return anneeExamen;} public void setAnneeExamen(Integer v){anneeExamen=v;}
    public String getFichierUrl(){return fichierUrl;} public void setFichierUrl(String v){fichierUrl=v;}
    public OffsetDateTime getDateTeleversement(){return dateTeleversement;} public void setDateTeleversement(OffsetDateTime v){dateTeleversement=v;}
    public OffsetDateTime getDateCreation(){return dateCreation;} public void setDateCreation(OffsetDateTime v){dateCreation=v;}
    public OffsetDateTime getDateMiseAJour(){return dateMiseAJour;} public void setDateMiseAJour(OffsetDateTime v){dateMiseAJour=v;}
    public Module getModule(){return module;} public void setModule(Module v){module=v;}
}
