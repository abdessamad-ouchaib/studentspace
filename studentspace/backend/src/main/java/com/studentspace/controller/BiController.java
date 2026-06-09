package com.studentspace.controller;

import com.studentspace.service.BiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  BI CONTROLLER — Endpoints REST pour Power BI & Dashboard    ║
 * ║  Auteur : Abdessamad Ouchaib                                 ║
 * ║                                                              ║
 * ║  BASE URL : /api/bi                                          ║
 * ║                                                              ║
 * ║  ENDPOINTS ANALYTICS :                                       ║
 * ║    GET /api/bi/kpis              → KPIs globaux              ║
 * ║    GET /api/bi/etudiants-filiere → Étudiants par filière     ║
 * ║    GET /api/bi/inscriptions-module → Inscriptions/module     ║
 * ║    GET /api/bi/performance-enseignants → Perf enseignants    ║
 * ║    GET /api/bi/distribution-annee → Distribution annuelle    ║
 * ║                                                              ║
 * ║  ENDPOINTS EXPORT POWER BI :                                 ║
 * ║    GET /api/bi/export/etudiants   → Table étudiants          ║
 * ║    GET /api/bi/export/modules     → Table modules            ║
 * ║    GET /api/bi/export/inscriptions → Table inscriptions      ║
 * ╚══════════════════════════════════════════════════════════════╝
 */
@RestController
@RequestMapping("/api/bi")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://studentspace01.vercel.app", "https://app.powerbi.com"})
public class BiController {

    private final BiService biService;

    // ── ANALYTICS ───────────────────────────────────────────────

    /**
     * KPIs globaux de la plateforme
     * Power BI : Connecteur Web → GET http://localhost:8080/api/bi/kpis
     */
    @GetMapping("/kpis")
    public ResponseEntity<Map<String, Object>> getKpisGlobaux() {
        return ResponseEntity.ok(biService.getKpisGlobaux());
    }

    /**
     * Répartition des étudiants par filière
     * Utilisé pour : Graphique camembert / barres dans Power BI
     */
    @GetMapping("/etudiants-filiere")
    public ResponseEntity<List<Map<String, Object>>> getEtudiantsParFiliere() {
        return ResponseEntity.ok(biService.getEtudiantsParFiliere());
    }

    /**
     * Nombre d'inscriptions par module
     * Utilisé pour : Histogramme des modules les plus suivis
     */
    @GetMapping("/inscriptions-module")
    public ResponseEntity<List<Map<String, Object>>> getInscriptionsParModule() {
        return ResponseEntity.ok(biService.getInscriptionsParModule());
    }

    /**
     * Performance des enseignants
     * Utilisé pour : Tableau comparatif enseignants dans Power BI
     */
    @GetMapping("/performance-enseignants")
    public ResponseEntity<List<Map<String, Object>>> getPerformanceEnseignants() {
        return ResponseEntity.ok(biService.getPerformanceEnseignants());
    }

    /**
     * Distribution par année universitaire
     * Utilisé pour : Timeline / évolution annuelle dans Power BI
     */
    @GetMapping("/distribution-annee")
    public ResponseEntity<List<Map<String, Object>>> getDistributionParAnnee() {
        return ResponseEntity.ok(biService.getDistributionParAnnee());
    }

    // ── EXPORTS POWER BI ────────────────────────────────────────

    /**
     * Export table étudiants pour Power BI
     * Dans Power BI Desktop : Obtenir données → Web → http://localhost:8080/api/bi/export/etudiants
     */
    @GetMapping("/export/etudiants")
    public ResponseEntity<List<Map<String, Object>>> exportEtudiants() {
        return ResponseEntity.ok(biService.exportEtudiantsPowerBi());
    }

    /**
     * Export table modules pour Power BI
     */
    @GetMapping("/export/modules")
    public ResponseEntity<List<Map<String, Object>>> exportModules() {
        return ResponseEntity.ok(biService.exportModulesPowerBi());
    }

    /**
     * Export table inscriptions pour Power BI
     */
    @GetMapping("/export/inscriptions")
    public ResponseEntity<List<Map<String, Object>>> exportInscriptions() {
        return ResponseEntity.ok(biService.exportInscriptionsPowerBi());
    }
}
