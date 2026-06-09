/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  BI DASHBOARD COMPONENT — Business Intelligence              ║
 * ║  Auteur : Abdessamad Ouchaib                                 ║
 * ║  Dashboard analytique intégré dans l'interface Admin         ║
 * ║  Consomme les endpoints /api/bi/*                            ║
 * ╚══════════════════════════════════════════════════════════════╝
 */

import { Component, OnInit } from '@angular/core';
import { CommonModule }       from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector:   'app-bi-dashboard',
  standalone: true,
  imports:    [CommonModule],
  template: `
<div class="bi-wrapper">

  <!-- ── HEADER ───────────────────────────────────────────────── -->
  <div class="bi-header">
    <div>
      <h1 class="bi-title">📊 Tableau de Bord Business Intelligence</h1>
      <p class="bi-subtitle">Analyse des données académiques — StudentSpace</p>
    </div>
    <div class="bi-actions">
      <button (click)="exporterPowerBi()" class="btn-export">
        ⬇ Exporter pour Power BI
      </button>
      <button (click)="chargerDonnees()" class="btn-refresh">
        🔄 Actualiser
      </button>
    </div>
  </div>

  <!-- ── KPI CARDS ────────────────────────────────────────────── -->
  <div class="kpi-grid" *ngIf="kpis">
    <div class="kpi-card blue">
      <div class="kpi-icon">🎓</div>
      <div class="kpi-val">{{ kpis.totalEtudiants }}</div>
      <div class="kpi-lbl">Étudiants</div>
    </div>
    <div class="kpi-card green">
      <div class="kpi-icon">👨‍🏫</div>
      <div class="kpi-val">{{ kpis.totalEnseignants }}</div>
      <div class="kpi-lbl">Enseignants</div>
    </div>
    <div class="kpi-card purple">
      <div class="kpi-icon">📚</div>
      <div class="kpi-val">{{ kpis.totalModules }}</div>
      <div class="kpi-lbl">Modules</div>
    </div>
    <div class="kpi-card orange">
      <div class="kpi-icon">🏫</div>
      <div class="kpi-val">{{ kpis.totalFilieres }}</div>
      <div class="kpi-lbl">Filières</div>
    </div>
    <div class="kpi-card teal">
      <div class="kpi-icon">📝</div>
      <div class="kpi-val">{{ kpis.totalInscriptions }}</div>
      <div class="kpi-lbl">Inscriptions</div>
    </div>
    <div class="kpi-card red">
      <div class="kpi-icon">📄</div>
      <div class="kpi-val">{{ kpis.totalExamens }}</div>
      <div class="kpi-lbl">Examens</div>
    </div>
  </div>

  <!-- ── CHARTS ROW 1 ─────────────────────────────────────────── -->
  <div class="charts-row">

    <!-- Étudiants par Filière -->
    <div class="chart-card large">
      <h2 class="chart-title">👩‍🎓 Étudiants par Filière</h2>
      <div class="chart-container" *ngIf="etudiantsFiliere.length > 0; else noData">
        <div *ngFor="let f of etudiantsFiliere" class="bar-item">
          <div class="bar-label" [title]="f.filiereNom">{{ f.filiereNom }}</div>
          <div class="bar-track">
            <div class="bar-fill blue"
                 [style.width]="getBarWidth(f.nombreEtudiants, maxEtudiants)">
            </div>
          </div>
          <div class="bar-val">{{ f.nombreEtudiants }} <span class="pct">({{ f.pourcentage }}%)</span></div>
        </div>
      </div>
      <ng-template #noData><p class="no-data">Aucune donnée disponible</p></ng-template>
    </div>

    <!-- Taux d'inscription -->
    <div class="chart-card small">
      <h2 class="chart-title">📈 Taux d'Inscription</h2>
      <div class="donut-wrapper" *ngIf="kpis">
        <div class="donut-circle">
          <span class="donut-val">{{ kpis.tauxInscriptionMoyen }}</span>
          <span class="donut-unit">moy.</span>
        </div>
        <p class="donut-desc">modules par étudiant en moyenne</p>
      </div>
      <div class="insight-box" *ngIf="kpis">
        <div class="insight-item">
          <span class="dot blue"></span>
          Total inscriptions : <strong>{{ kpis.totalInscriptions }}</strong>
        </div>
        <div class="insight-item">
          <span class="dot green"></span>
          Total étudiants : <strong>{{ kpis.totalEtudiants }}</strong>
        </div>
      </div>
    </div>
  </div>

  <!-- ── CHARTS ROW 2 ─────────────────────────────────────────── -->
  <div class="charts-row">

    <!-- Top Modules par Inscriptions -->
    <div class="chart-card medium">
      <h2 class="chart-title">🏆 Top Modules par Inscriptions</h2>
      <table class="bi-table" *ngIf="inscriptionsModule.length > 0">
        <thead>
          <tr>
            <th>#</th>
            <th>Module</th>
            <th>Filière</th>
            <th>Enseignant</th>
            <th>Inscrits</th>
            <th>Examens</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let m of inscriptionsModule.slice(0,8); let i = index">
            <td><span class="rank" [class.gold]="i===0" [class.silver]="i===1" [class.bronze]="i===2">{{ i+1 }}</span></td>
            <td class="fw-medium">{{ m.moduleNom }}</td>
            <td class="text-gray">{{ m.filiereNom }}</td>
            <td class="text-gray">{{ m.enseignantNom }}</td>
            <td><span class="badge blue">{{ m.nombreInscrits }}</span></td>
            <td><span class="badge purple">{{ m.nombreExamens }}</span></td>
          </tr>
        </tbody>
      </table>
      <p class="no-data" *ngIf="inscriptionsModule.length === 0">Aucun module disponible</p>
    </div>

    <!-- Performance Enseignants -->
    <div class="chart-card medium">
      <h2 class="chart-title">👨‍🏫 Performance Enseignants</h2>
      <div *ngFor="let e of performanceEnseignants" class="enseignant-row">
        <div class="ens-avatar">{{ getInitials(e.enseignantNom) }}</div>
        <div class="ens-info">
          <div class="ens-nom">{{ e.enseignantNom }}</div>
          <div class="ens-stats">
            <span>{{ e.nombreModules }} modules</span>
            <span>·</span>
            <span>{{ e.totalEtudiantsSuivis }} étudiants</span>
            <span>·</span>
            <span>{{ e.totalExamensCrees }} examens</span>
          </div>
        </div>
        <div class="ens-score">
          <span class="score-val">{{ e.moyenneEtudiantsParModule }}</span>
          <span class="score-lbl">moy/module</span>
        </div>
      </div>
      <p class="no-data" *ngIf="performanceEnseignants.length === 0">Aucun enseignant disponible</p>
    </div>
  </div>

  <!-- ── DISTRIBUTION PAR ANNÉE ────────────────────────────────── -->
  <div class="chart-card full" *ngIf="distributionAnnee.length > 0">
    <h2 class="chart-title">📅 Distribution par Année Universitaire</h2>
    <div class="annee-grid">
      <div *ngFor="let a of distributionAnnee" class="annee-card">
        <div class="annee-year">{{ a.anneeUniversitaire }}/{{ a.anneeUniversitaire + 1 }}</div>
        <div class="annee-stats">
          <div class="annee-stat">
            <span class="annee-val blue">{{ a.nombreFilieres }}</span>
            <span class="annee-lbl">Filières</span>
          </div>
          <div class="annee-stat">
            <span class="annee-val purple">{{ a.nombreModules }}</span>
            <span class="annee-lbl">Modules</span>
          </div>
          <div class="annee-stat">
            <span class="annee-val green">{{ a.nombreEtudiants }}</span>
            <span class="annee-lbl">Étudiants</span>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- ── GUIDE POWER BI ────────────────────────────────────────── -->
  <div class="powerbi-guide">
    <h2 class="chart-title">⚡ Connecter Power BI Desktop</h2>
    <div class="guide-steps">
      <div class="guide-step">
        <div class="step-num">1</div>
        <div class="step-content">
          <div class="step-title">Ouvrir Power BI Desktop</div>
          <div class="step-desc">Télécharger sur <strong>powerbi.microsoft.com</strong> → Obtenir données → Web</div>
        </div>
      </div>
      <div class="guide-step">
        <div class="step-num">2</div>
        <div class="step-content">
          <div class="step-title">Connecter les tables</div>
          <div class="step-desc">Coller ces URLs une par une dans Power BI :</div>
          <div class="url-list">
            <div class="url-item" *ngFor="let url of powerBiUrls">
              <span class="url-label">{{ url.label }}</span>
              <code class="url-code">{{ url.url }}</code>
              <button (click)="copierUrl(url.url)" class="btn-copy">📋 Copier</button>
            </div>
          </div>
        </div>
      </div>
      <div class="guide-step">
        <div class="step-num">3</div>
        <div class="step-content">
          <div class="step-title">Créer les visualisations</div>
          <div class="step-desc">
            Glisser les champs vers les visuels :
            <strong>Graphique barres</strong> (filiereNom / nombreEtudiants) •
            <strong>Tableau</strong> (moduleNom / nombreInscrits) •
            <strong>Jauge</strong> (tauxInscriptionMoyen)
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- Loading -->
  <div class="loading-overlay" *ngIf="loading">
    <div class="spinner"></div>
    <p>Chargement des données analytiques...</p>
  </div>

</div>
  `,
  styles: [`
    .bi-wrapper {
      padding: 1.5rem;
      background: #f8fafc;
      min-height: 100vh;
      font-family: 'Segoe UI', sans-serif;
      position: relative;
    }

    /* Header */
    .bi-header {
      display: flex; align-items: flex-start;
      justify-content: space-between; flex-wrap: wrap; gap: 1rem;
      margin-bottom: 1.5rem;
    }
    .bi-title { font-size: 1.5rem; font-weight: 700; color: #1e293b; margin: 0; }
    .bi-subtitle { font-size: 0.85rem; color: #64748b; margin: 0.2rem 0 0; }
    .bi-actions { display: flex; gap: 0.75rem; flex-wrap: wrap; }
    .btn-export {
      background: #2563eb; color: #fff; border: none;
      padding: 0.5rem 1rem; border-radius: 8px; cursor: pointer;
      font-size: 0.85rem; font-weight: 500;
    }
    .btn-export:hover { background: #1d4ed8; }
    .btn-refresh {
      background: #fff; color: #475569; border: 1px solid #e2e8f0;
      padding: 0.5rem 1rem; border-radius: 8px; cursor: pointer;
      font-size: 0.85rem;
    }
    .btn-refresh:hover { background: #f1f5f9; }

    /* KPI Grid */
    .kpi-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
      gap: 1rem; margin-bottom: 1.5rem;
    }
    .kpi-card {
      background: #fff; border-radius: 12px;
      padding: 1.25rem; text-align: center;
      box-shadow: 0 1px 3px rgba(0,0,0,0.08);
      border-top: 4px solid transparent;
    }
    .kpi-card.blue   { border-top-color: #3b82f6; }
    .kpi-card.green  { border-top-color: #22c55e; }
    .kpi-card.purple { border-top-color: #a855f7; }
    .kpi-card.orange { border-top-color: #f97316; }
    .kpi-card.teal   { border-top-color: #14b8a6; }
    .kpi-card.red    { border-top-color: #ef4444; }
    .kpi-icon { font-size: 1.5rem; margin-bottom: 0.5rem; }
    .kpi-val { font-size: 1.8rem; font-weight: 700; color: #1e293b; }
    .kpi-lbl { font-size: 0.75rem; color: #94a3b8; margin-top: 0.25rem; }

    /* Charts */
    .charts-row {
      display: flex; gap: 1rem; margin-bottom: 1rem; flex-wrap: wrap;
    }
    .chart-card {
      background: #fff; border-radius: 12px; padding: 1.25rem;
      box-shadow: 0 1px 3px rgba(0,0,0,0.08); flex: 1; min-width: 280px;
    }
    .chart-card.large  { flex: 2; }
    .chart-card.medium { flex: 1.5; }
    .chart-card.small  { flex: 0.8; }
    .chart-card.full   { width: 100%; margin-bottom: 1rem; }
    .chart-title {
      font-size: 0.95rem; font-weight: 600; color: #1e293b;
      margin: 0 0 1rem 0; padding-bottom: 0.75rem;
      border-bottom: 1px solid #f1f5f9;
    }

    /* Bar Chart */
    .chart-container { display: flex; flex-direction: column; gap: 0.6rem; }
    .bar-item { display: flex; align-items: center; gap: 0.5rem; }
    .bar-label {
      width: 120px; font-size: 0.78rem; color: #475569;
      white-space: nowrap; overflow: hidden; text-overflow: ellipsis; flex-shrink: 0;
    }
    .bar-track {
      flex: 1; height: 20px; background: #f1f5f9; border-radius: 4px; overflow: hidden;
    }
    .bar-fill { height: 100%; border-radius: 4px; transition: width 0.8s ease; }
    .bar-fill.blue { background: linear-gradient(90deg, #3b82f6, #60a5fa); }
    .bar-val { font-size: 0.78rem; color: #1e293b; font-weight: 600; min-width: 70px; }
    .pct { color: #94a3b8; font-weight: 400; }

    /* Donut */
    .donut-wrapper { text-align: center; padding: 1rem 0; }
    .donut-circle {
      width: 100px; height: 100px; border-radius: 50%;
      background: conic-gradient(#3b82f6 0% 75%, #e2e8f0 75% 100%);
      display: flex; flex-direction: column; align-items: center;
      justify-content: center; margin: 0 auto 0.75rem;
      box-shadow: inset 0 0 0 25px #fff;
    }
    .donut-val { font-size: 1.4rem; font-weight: 700; color: #1e293b; }
    .donut-unit { font-size: 0.6rem; color: #94a3b8; }
    .donut-desc { font-size: 0.78rem; color: #64748b; }
    .insight-box { margin-top: 1rem; display: flex; flex-direction: column; gap: 0.5rem; }
    .insight-item { display: flex; align-items: center; gap: 0.5rem; font-size: 0.8rem; color: #475569; }
    .dot { width: 8px; height: 8px; border-radius: 50%; flex-shrink: 0; }
    .dot.blue  { background: #3b82f6; }
    .dot.green { background: #22c55e; }

    /* Table */
    .bi-table { width: 100%; border-collapse: collapse; font-size: 0.8rem; }
    .bi-table th {
      background: #f8fafc; padding: 0.5rem 0.75rem;
      text-align: left; font-size: 0.72rem; color: #64748b;
      text-transform: uppercase; letter-spacing: 0.05em;
      border-bottom: 1px solid #e2e8f0;
    }
    .bi-table td { padding: 0.5rem 0.75rem; border-bottom: 1px solid #f1f5f9; }
    .bi-table tr:last-child td { border-bottom: none; }
    .bi-table tr:hover td { background: #f8fafc; }
    .fw-medium { font-weight: 500; color: #1e293b; }
    .text-gray { color: #64748b; }
    .rank {
      display: inline-flex; align-items: center; justify-content: center;
      width: 22px; height: 22px; border-radius: 50%;
      background: #e2e8f0; font-size: 0.72rem; font-weight: 600; color: #475569;
    }
    .rank.gold   { background: #fef08a; color: #854d0e; }
    .rank.silver { background: #e2e8f0; color: #475569; }
    .rank.bronze { background: #fed7aa; color: #9a3412; }
    .badge {
      display: inline-block; padding: 0.15rem 0.5rem;
      border-radius: 9999px; font-size: 0.72rem; font-weight: 600;
    }
    .badge.blue   { background: #dbeafe; color: #1d4ed8; }
    .badge.purple { background: #ede9fe; color: #7c3aed; }

    /* Enseignant rows */
    .enseignant-row {
      display: flex; align-items: center; gap: 0.75rem;
      padding: 0.75rem 0; border-bottom: 1px solid #f1f5f9;
    }
    .enseignant-row:last-child { border-bottom: none; }
    .ens-avatar {
      width: 36px; height: 36px; border-radius: 50%;
      background: linear-gradient(135deg, #6366f1, #8b5cf6);
      color: #fff; display: flex; align-items: center; justify-content: center;
      font-size: 0.75rem; font-weight: 700; flex-shrink: 0;
    }
    .ens-info { flex: 1; }
    .ens-nom { font-size: 0.85rem; font-weight: 600; color: #1e293b; }
    .ens-stats { font-size: 0.72rem; color: #94a3b8; display: flex; gap: 0.3rem; }
    .ens-score { text-align: right; }
    .score-val { display: block; font-size: 1.1rem; font-weight: 700; color: #3b82f6; }
    .score-lbl { font-size: 0.65rem; color: #94a3b8; }

    /* Distribution annee */
    .annee-grid {
      display: flex; flex-wrap: wrap; gap: 1rem;
    }
    .annee-card {
      background: #f8fafc; border-radius: 10px; padding: 1rem;
      min-width: 160px; flex: 1; text-align: center;
    }
    .annee-year { font-size: 0.9rem; font-weight: 700; color: #1e293b; margin-bottom: 0.75rem; }
    .annee-stats { display: flex; justify-content: center; gap: 1rem; }
    .annee-stat { display: flex; flex-direction: column; align-items: center; }
    .annee-val { font-size: 1.3rem; font-weight: 700; }
    .annee-val.blue   { color: #3b82f6; }
    .annee-val.purple { color: #a855f7; }
    .annee-val.green  { color: #22c55e; }
    .annee-lbl { font-size: 0.65rem; color: #94a3b8; }

    /* Power BI Guide */
    .powerbi-guide {
      background: #fff; border-radius: 12px; padding: 1.25rem;
      box-shadow: 0 1px 3px rgba(0,0,0,0.08); margin-bottom: 1rem;
    }
    .guide-steps { display: flex; flex-direction: column; gap: 1rem; }
    .guide-step { display: flex; gap: 1rem; align-items: flex-start; }
    .step-num {
      width: 32px; height: 32px; border-radius: 50%;
      background: #2563eb; color: #fff; display: flex;
      align-items: center; justify-content: center;
      font-weight: 700; font-size: 0.85rem; flex-shrink: 0;
    }
    .step-title { font-weight: 600; color: #1e293b; font-size: 0.9rem; margin-bottom: 0.25rem; }
    .step-desc { font-size: 0.82rem; color: #64748b; }
    .url-list { display: flex; flex-direction: column; gap: 0.5rem; margin-top: 0.5rem; }
    .url-item {
      display: flex; align-items: center; gap: 0.5rem; flex-wrap: wrap;
      background: #f8fafc; border-radius: 6px; padding: 0.4rem 0.6rem;
    }
    .url-label { font-size: 0.75rem; font-weight: 600; color: #475569; min-width: 120px; }
    .url-code {
      font-family: monospace; font-size: 0.75rem; color: #2563eb;
      flex: 1; word-break: break-all;
    }
    .btn-copy {
      background: none; border: 1px solid #e2e8f0; border-radius: 4px;
      padding: 0.2rem 0.4rem; font-size: 0.7rem; cursor: pointer; color: #475569;
    }
    .btn-copy:hover { background: #e2e8f0; }

    /* Loading */
    .loading-overlay {
      position: fixed; top: 0; left: 0; right: 0; bottom: 0;
      background: rgba(248,250,252,0.85); display: flex;
      flex-direction: column; align-items: center; justify-content: center;
      z-index: 50; gap: 1rem;
    }
    .spinner {
      width: 48px; height: 48px; border: 4px solid #e2e8f0;
      border-top-color: #3b82f6; border-radius: 50%;
      animation: spin 0.8s linear infinite;
    }
    @keyframes spin { to { transform: rotate(360deg); } }
    .no-data { color: #94a3b8; font-size: 0.85rem; text-align: center; padding: 1rem; }
  `]
})
export class BiDashboardComponent implements OnInit {

  loading = false;
  kpis:                  any = null;
  etudiantsFiliere:      any[] = [];
  inscriptionsModule:    any[] = [];
  performanceEnseignants: any[] = [];
  distributionAnnee:     any[] = [];
  maxEtudiants = 1;

  readonly BASE = 'https://studentspace-api.onrender.com/api/bi';

powerBiUrls = [
    { label: 'KPIs Globaux',       url: 'https://studentspace-api.onrender.com/api/bi/kpis' },
    { label: 'Table Étudiants',    url: 'https://studentspace-api.onrender.com/api/bi/export/etudiants' },
    { label: 'Table Modules',      url: 'https://studentspace-api.onrender.com/api/bi/export/modules' },
    { label: 'Table Inscriptions', url: 'https://studentspace-api.onrender.com/api/bi/export/inscriptions' },
    { label: 'Perf. Enseignants',  url: 'https://studentspace-api.onrender.com/api/bi/performance-enseignants' },
  ];

  constructor(private http: HttpClient) {}

  ngOnInit(): void { this.chargerDonnees(); }

  chargerDonnees(): void {
    this.loading = true;
    const token  = localStorage.getItem('token') || '';
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });

    Promise.all([
      this.http.get<any>(`${this.BASE}/kpis`, { headers }).toPromise(),
      this.http.get<any[]>(`${this.BASE}/etudiants-filiere`, { headers }).toPromise(),
      this.http.get<any[]>(`${this.BASE}/inscriptions-module`, { headers }).toPromise(),
      this.http.get<any[]>(`${this.BASE}/performance-enseignants`, { headers }).toPromise(),
      this.http.get<any[]>(`${this.BASE}/distribution-annee`, { headers }).toPromise(),
    ]).then(([kpis, filiere, modules, enseignants, annee]) => {
      this.kpis                   = kpis;
      this.etudiantsFiliere       = filiere || [];
      this.inscriptionsModule     = modules || [];
      this.performanceEnseignants = enseignants || [];
      this.distributionAnnee      = annee || [];
      this.maxEtudiants = Math.max(1, ...this.etudiantsFiliere.map(f => f.nombreEtudiants));
      this.loading = false;
    }).catch(() => { this.loading = false; });
  }

  getBarWidth(val: number, max: number): string {
    return Math.round((val / max) * 100) + '%';
  }

  getInitials(nom: string): string {
    return (nom || '').split(' ').map((n: string) => n[0]).join('').slice(0, 2).toUpperCase();
  }

  copierUrl(url: string): void {
    navigator.clipboard.writeText(url).then(() => alert('URL copiée : ' + url));
  }

  exporterPowerBi(): void {
    alert(
      '📊 URLs pour Power BI Desktop :\n\n' +
      this.powerBiUrls.map(u => `${u.label} :\n${u.url}`).join('\n\n') +
      '\n\nOuvrir Power BI → Obtenir données → Web → coller chaque URL'
    );
  }
}
