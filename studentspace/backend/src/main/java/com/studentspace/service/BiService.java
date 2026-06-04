package com.studentspace.service;

import com.studentspace.model.*;
import com.studentspace.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  BI SERVICE — Business Intelligence & Analytics              ║
 * ║  Auteur : Abdessamad Ouchaib                                 ║
 * ║  Fournit toutes les données analytiques pour :               ║
 * ║    - Le dashboard Angular intégré                            ║
 * ║    - Power BI Desktop (via API REST)                         ║
 * ╚══════════════════════════════════════════════════════════════╝
 */
@Service
@RequiredArgsConstructor
public class BiService {

    private final EtudiantRepository      etudiantRepo;
    private final EnseignantRepository    enseignantRepo;
    private final FiliereRepository       filiereRepo;
    private final ModuleRepository        moduleRepo;
    private final InscriptionRepository   inscriptionRepo;
    private final ExamenRepository        examenRepo;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // ── 1. KPIs GLOBAUX ─────────────────────────────────────────
    public Map<String, Object> getKpisGlobaux() {
        long totalEtudiants    = etudiantRepo.count();
        long totalEnseignants  = enseignantRepo.count();
        long totalFilieres     = filiereRepo.count();
        long totalModules      = moduleRepo.count();
        long totalInscriptions = inscriptionRepo.count();
        long totalExamens      = examenRepo.count();
        double tauxInscription = totalEtudiants > 0
            ? Math.round((double) totalInscriptions / totalEtudiants * 100.0) / 100.0 : 0;

        Map<String, Object> kpis = new LinkedHashMap<>();
        kpis.put("totalEtudiants",       totalEtudiants);
        kpis.put("totalEnseignants",      totalEnseignants);
        kpis.put("totalFilieres",         totalFilieres);
        kpis.put("totalModules",          totalModules);
        kpis.put("totalInscriptions",     totalInscriptions);
        kpis.put("totalExamens",          totalExamens);
        kpis.put("tauxInscriptionMoyen",  tauxInscription);
        return kpis;
    }

    // ── 2. ÉTUDIANTS PAR FILIÈRE ─────────────────────────────────
    public List<Map<String, Object>> getEtudiantsParFiliere() {
        List<Filiere> filieres = filiereRepo.findAll();
        long total = etudiantRepo.count();

        return filieres.stream().map(f -> {
            long count = f.getEtudiantsFiliere() != null ? f.getEtudiantsFiliere().size() : 0;
            double pct = total > 0 ? Math.round((double) count / total * 1000.0) / 10.0 : 0;
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("filiereId",          f.getId());
            row.put("filiereNom",         f.getNom());
            row.put("anneeUniversitaire", f.getAnneeUniversitaire());
            row.put("semestre",           f.getSemestre());
            row.put("nombreEtudiants",    count);
            row.put("pourcentage",        pct);
            return row;
        }).collect(Collectors.toList());
    }

    // ── 3. INSCRIPTIONS PAR MODULE ───────────────────────────────
    public List<Map<String, Object>> getInscriptionsParModule() {
        List<com.studentspace.model.Module> modules = moduleRepo.findAll();

        return modules.stream().map(m -> {
            long inscrits = inscriptionRepo.countByModuleId(m.getId());
            long examens  = examenRepo.findByModuleId(m.getId()).size();
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("moduleId",      m.getId());
            row.put("moduleNom",     m.getNom());
            row.put("filiereNom",    m.getFiliere() != null ? m.getFiliere().getNom() : "—");
            row.put("enseignantNom", m.getEnseignant() != null
                ? m.getEnseignant().getNom() + " " + m.getEnseignant().getPrenom() : "—");
            row.put("nombreInscrits", inscrits);
            row.put("nombreExamens",  examens);
            return row;
        }).sorted(Comparator.comparingLong(r -> -((Long)((Map<?,?>)r).get("nombreInscrits"))))
          .collect(Collectors.toList());
    }

    // ── 4. PERFORMANCE PAR ENSEIGNANT ────────────────────────────
    public List<Map<String, Object>> getPerformanceEnseignants() {
        List<Enseignant> enseignants = enseignantRepo.findAll();

        return enseignants.stream().map(e -> {
            List<com.studentspace.model.Module> modules = moduleRepo.findAll()
                .stream().filter(m -> m.getEnseignant() != null
                    && m.getEnseignant().getId().equals(e.getId()))
                .collect(Collectors.toList());

            long totalEtudiants = modules.stream()
                .mapToLong(m -> inscriptionRepo.countByModuleId(m.getId())).sum();
            long totalExamens = modules.stream()
                .mapToLong(m -> examenRepo.findByModuleId(m.getId()).size()).sum();
            double moyenne = modules.isEmpty() ? 0 :
                Math.round((double) totalEtudiants / modules.size() * 100.0) / 100.0;

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("enseignantId",              e.getId());
            row.put("enseignantNom",             e.getNom() + " " + e.getPrenom());
            row.put("enseignantEmail",           e.getEmail());
            row.put("nombreModules",             (long) modules.size());
            row.put("totalEtudiantsSuivis",      totalEtudiants);
            row.put("totalExamensCrees",         totalExamens);
            row.put("moyenneEtudiantsParModule", moyenne);
            return row;
        }).collect(Collectors.toList());
    }

    // ── 5. DISTRIBUTION PAR ANNÉE UNIVERSITAIRE ──────────────────
    public List<Map<String, Object>> getDistributionParAnnee() {
        List<Filiere> filieres = filiereRepo.findAll();

        Map<Integer, List<Filiere>> parAnnee = filieres.stream()
            .filter(f -> f.getAnneeUniversitaire() != null)
            .collect(Collectors.groupingBy(Filiere::getAnneeUniversitaire));

        return parAnnee.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(entry -> {
                List<Filiere> fs = entry.getValue();
                long nbModules = fs.stream()
                    .mapToLong(f -> f.getModulesFiliere() != null ? f.getModulesFiliere().size() : 0).sum();
                long nbEtudiants = fs.stream()
                    .mapToLong(f -> f.getEtudiantsFiliere() != null ? f.getEtudiantsFiliere().size() : 0).sum();
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("anneeUniversitaire", entry.getKey());
                row.put("nombreFilieres",     (long) fs.size());
                row.put("nombreModules",      nbModules);
                row.put("nombreEtudiants",    nbEtudiants);
                return row;
            }).collect(Collectors.toList());
    }

    // ── 6. EXPORT POWER BI — TABLE ÉTUDIANTS ────────────────────
    public List<Map<String, Object>> exportEtudiantsPowerBi() {
        List<Etudiant> etudiants = etudiantRepo.findAll();
        return etudiants.stream().map(e -> {
            long nbModules = inscriptionRepo.findByEtudiantId(e.getId()).size();
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("etudiantId",          e.getId());
            row.put("numeroApogee",        e.getNumeroApogee());
            row.put("nom",                 e.getNom());
            row.put("prenom",              e.getPrenom());
            row.put("email",               e.getEmail());
            row.put("filiereNom",          e.getFiliere() != null ? e.getFiliere().getNom() : "—");
            row.put("anneeUniversitaire",  e.getFiliere() != null ? e.getFiliere().getAnneeUniversitaire() : null);
            row.put("semestre",            e.getFiliere() != null ? e.getFiliere().getSemestre() : null);
            row.put("nombreModulesSuivis", nbModules);
            return row;
        }).collect(Collectors.toList());
    }

    // ── 7. EXPORT POWER BI — TABLE INSCRIPTIONS ──────────────────
    public List<Map<String, Object>> exportInscriptionsPowerBi() {
        List<InscriptionEtudiantModule> inscriptions = inscriptionRepo.findAll();
        return inscriptions.stream().map(i -> {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("inscriptionId",  i.getId());
            row.put("etudiantNom",    i.getEtudiant().getNom());
            row.put("etudiantPrenom", i.getEtudiant().getPrenom());
            row.put("numeroApogee",   i.getEtudiant().getNumeroApogee());
            row.put("moduleNom",      i.getModule().getNom());
            row.put("filiereNom",     i.getModule().getFiliere() != null
                ? i.getModule().getFiliere().getNom() : "—");
            row.put("enseignantNom",  i.getModule().getEnseignant() != null
                ? i.getModule().getEnseignant().getNom() + " " + i.getModule().getEnseignant().getPrenom() : "—");
            row.put("dateInscription", i.getDateInscription() != null
                ? i.getDateInscription().toString() : "—");
            return row;
        }).collect(Collectors.toList());
    }

    // ── 8. EXPORT POWER BI — TABLE MODULES ──────────────────────
    public List<Map<String, Object>> exportModulesPowerBi() {
        List<com.studentspace.model.Module> modules = moduleRepo.findAll();
        return modules.stream().map(m -> {
            long inscrits = inscriptionRepo.countByModuleId(m.getId());
            long examens  = examenRepo.findByModuleId(m.getId()).size();
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("moduleId",      m.getId());
            row.put("moduleNom",     m.getNom());
            row.put("description",   m.getDescription());
            row.put("filiereNom",    m.getFiliere() != null ? m.getFiliere().getNom() : "—");
            row.put("enseignantNom", m.getEnseignant() != null
                ? m.getEnseignant().getNom() + " " + m.getEnseignant().getPrenom() : "—");
            row.put("nombreEtudiants", inscrits);
            row.put("nombreExamens",   examens);
            row.put("dateCreation",  m.getDateCreation() != null
                ? m.getDateCreation().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "—");
            return row;
        }).collect(Collectors.toList());
    }
}
