/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  ADMIN COMPONENT — Tableau de bord Administrateur            ║
 * ║  Auteur : Abdessamad Ouchaib                                 ║
 * ║  Reproduit les figures 4.4.6 → 4.4.18 du rapport PFE        ║
 * ╚══════════════════════════════════════════════════════════════╝
 */

import { Component, OnInit }   from '@angular/core';
import { CommonModule }         from '@angular/common';
import { FormsModule }          from '@angular/forms';
import { AuthService, EtudiantService, EnseignantService,
         FiliereService, ModuleService, ExamenService,
         InscriptionService }   from '../shared/services';
import * as M                   from '../shared/models';
import { BiDashboardComponent }   from '../bi/bi-dashboard.component';

type Section = 'dashboard' | 'filieres' | 'modules' | 'etudiants' |
               'enseignants' | 'examens' | 'inscriptions' | 'bi';

@Component({
  selector:   'app-admin',
  standalone: true,
  imports:    [CommonModule, FormsModule, BiDashboardComponent],
  template: `
<div class="min-h-screen bg-gray-50 flex">

  <!-- ── SIDEBAR (figure 4.4.6) ──────────────────────────────── -->
  <aside class="sidebar">
    <!-- Logo -->
    <div class="p-4 border-b border-gray-100">
      <div class="flex items-center gap-2">
        <div class="w-8 h-8 bg-blue-600 rounded-lg flex items-center justify-center">
          <span class="text-white text-sm">🎓</span>
        </div>
        <span class="font-bold text-gray-900 text-sm">StudentSpace</span>
      </div>
    </div>

    <!-- Navigation -->
    <nav class="py-4">
      <p class="px-4 text-xs font-semibold text-gray-400 uppercase tracking-wider mb-2">
        Gérer les entités
      </p>
      <a *ngFor="let item of menuItems"
         (click)="section = item.key; chargerSection(item.key)"
         [class.active]="section === item.key"
         class="sidebar-link cursor-pointer">
        <span>{{ item.icon }}</span>
        <span>{{ item.label }}</span>
      </a>
    </nav>

    <!-- Déconnexion -->
    <div class="absolute bottom-4 left-0 right-0 px-4">
      <button (click)="auth.deconnecter()"
        class="w-full text-left sidebar-link text-red-500 hover:text-red-600 hover:bg-red-50">
        🚪 Déconnexion
      </button>
    </div>
  </aside>

  <!-- ── CONTENU PRINCIPAL ────────────────────────────────────── -->
  <main class="main-content">

    <!-- ── DASHBOARD ─────────────────────────────────────────── -->
    <div *ngIf="section === 'dashboard'">
      <h1 class="text-2xl font-bold text-blue-700 mb-2">
        Bienvenue sur le panneau d'administration
      </h1>
      <p class="text-gray-500 text-sm mb-6">Vue d'ensemble de la plateforme StudentSpace</p>

      <div class="grid grid-cols-2 lg:grid-cols-3 gap-4 mb-8">
        <div *ngFor="let stat of stats" class="card text-center">
          <div class="text-3xl font-bold text-blue-600 mb-1">{{ stat.valeur }}</div>
          <div class="text-sm text-gray-500">{{ stat.label }}</div>
        </div>
      </div>

      <div class="card">
        <h2 class="font-bold text-gray-900 mb-3">Accès rapide — Gérer les entités</h2>
        <div class="grid grid-cols-2 md:grid-cols-3 gap-3">
          <button *ngFor="let item of menuItems.slice(1)"
            (click)="section = item.key; chargerSection(item.key)"
            class="flex flex-col items-center gap-2 p-4 border border-gray-100
                   rounded-xl hover:border-blue-300 hover:bg-blue-50 transition-all">
            <span class="text-2xl">{{ item.icon }}</span>
            <span class="text-sm font-medium text-gray-700">{{ item.label }}</span>
          </button>
        </div>
      </div>
    </div>

    <!-- ── FILIÈRES (figure 4.4.7 + 4.4.8) ──────────────────── -->
    <div *ngIf="section === 'filieres'">
      <div class="flex items-center justify-between mb-6">
        <h1 class="text-xl font-bold text-gray-900">Filières</h1>
        <button (click)="ouvrirModal('filiere')" class="btn-primary">
          + Ajouter une nouvelle filière
        </button>
      </div>
      <div class="card">
        <div class="table-container">
          <table class="data-table">
            <thead>
              <tr><th>Id</th><th>Nom</th><th>Année universitaire</th><th>Semestre</th><th>Actions</th></tr>
            </thead>
            <tbody>
              <tr *ngFor="let f of filieres">
                <td class="text-gray-400 font-mono text-xs">{{ f.id }}</td>
                <td class="font-medium">{{ f.nom }}</td>
                <td>{{ f.anneeUniversitaire }}</td>
                <td>{{ f.semestre }}</td>
                <td class="space-x-2">
                  <button (click)="editerFiliere(f)" class="btn-secondary text-xs px-2 py-1">Modifier</button>
                  <button (click)="supprimerFiliere(f.id)" class="btn-danger">Supprimer</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <p class="text-xs text-gray-400 text-center mt-4">© 2024-2025 StudentSpace. All rights reserved.</p>
      </div>
    </div>

    <!-- ── ENSEIGNANTS (figure 4.4.9 + 4.4.10) ──────────────── -->
    <div *ngIf="section === 'enseignants'">
      <div class="flex items-center justify-between mb-6">
        <h1 class="text-xl font-bold text-gray-900">Enseignants</h1>
        <button (click)="ouvrirModal('enseignant')" class="btn-primary">
          + Ajouter un nouvel enseignant
        </button>
      </div>
      <div class="card">
        <div class="table-container">
          <table class="data-table">
            <thead>
              <tr><th>Prénom</th><th>Nom</th><th>Email</th><th>Téléphone</th><th>Spécialité</th><th>Actions</th></tr>
            </thead>
            <tbody>
              <tr *ngFor="let e of enseignants">
                <td>{{ e.prenom }}</td>
                <td class="font-medium">{{ e.nom }}</td>
                <td class="text-xs">{{ e.email }}</td>
                <td>{{ e.telephone }}</td>
                <td><span class="badge-blue">{{ e.specialite }}</span></td>
                <td class="space-x-2">
                  <button (click)="editerEnseignant(e)" class="btn-secondary text-xs px-2 py-1">Modifier</button>
                  <button (click)="supprimerEnseignant(e.id)" class="btn-danger">Supprimer</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- ── MODULES (figure 4.4.11 + 4.4.12) ─────────────────── -->
    <div *ngIf="section === 'modules'">
      <div class="flex items-center justify-between mb-6">
        <h1 class="text-xl font-bold text-gray-900">Modules</h1>
        <button (click)="ouvrirModal('module')" class="btn-primary">
          + Ajouter un nouveau module
        </button>
      </div>
      <div class="card">
        <div class="table-container">
          <table class="data-table">
            <thead>
              <tr><th>Id</th><th>Nom</th><th>Filière</th><th>Enseignant</th><th>Étudiants</th><th>Actions</th></tr>
            </thead>
            <tbody>
              <tr *ngFor="let m of modules">
                <td class="text-gray-400 font-mono text-xs">{{ m.id }}</td>
                <td class="font-medium">{{ m.nom }}</td>
                <td>{{ m.filiereId }}</td>
                <td>{{ m.enseignantId }}</td>
                <td><span class="badge-green">{{ m.nombreEtudiants }}</span></td>
                <td class="space-x-2">
                  <button (click)="editerModule(m)" class="btn-secondary text-xs px-2 py-1">Modifier</button>
                  <button (click)="supprimerModule(m.id)" class="btn-danger">Supprimer</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- ── ÉTUDIANTS (figure 4.4.13 + 4.4.14) ───────────────── -->
    <div *ngIf="section === 'etudiants'">
      <div class="flex items-center justify-between mb-6">
        <h1 class="text-xl font-bold text-gray-900">Étudiants</h1>
        <button (click)="ouvrirModal('etudiant')" class="btn-primary">
          + Ajouter un nouvel étudiant
        </button>
      </div>
      <div class="card">
        <div class="table-container">
          <table class="data-table">
            <thead>
              <tr><th>Numéro APOGEE</th><th>Prénom</th><th>Nom</th><th>Email</th><th>Filière</th><th>Actions</th></tr>
            </thead>
            <tbody>
              <tr *ngFor="let e of etudiants">
                <td class="font-mono text-xs">{{ e.numeroApogee }}</td>
                <td>{{ e.prenom }}</td>
                <td class="font-medium">{{ e.nom }}</td>
                <td class="text-xs">{{ e.email }}</td>
                <td>{{ e.filiereId }}</td>
                <td class="space-x-2">
                  <button (click)="editerEtudiant(e)" class="btn-secondary text-xs px-2 py-1">Modifier</button>
                  <button (click)="supprimerEtudiant(e.id)" class="btn-danger">Supprimer</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- ── EXAMENS (figure 4.4.15 + 4.4.16) ─────────────────── -->
    <div *ngIf="section === 'examens'">
      <div class="flex items-center justify-between mb-6">
        <h1 class="text-xl font-bold text-gray-900">Examens</h1>
        <button (click)="ouvrirModal('examen')" class="btn-primary">
          + Ajouter un nouvel examen
        </button>
      </div>
      <div class="card">
        <div class="table-container">
          <table class="data-table">
            <thead>
              <tr><th>Id</th><th>Titre</th><th>Année d'examen</th><th>Fichier</th><th>Date d'envoi</th><th>Module</th><th>Actions</th></tr>
            </thead>
            <tbody>
              <tr *ngFor="let ex of examens">
                <td class="text-gray-400 font-mono text-xs">{{ ex.id }}</td>
                <td class="font-medium">{{ ex.titre }}</td>
                <td>{{ ex.anneeExamen }}</td>
                <td>
                  <a *ngIf="ex.fichierUrl" [href]="ex.fichierUrl" target="_blank"
                     class="text-blue-600 hover:underline text-xs truncate max-w-xs block">
                    {{ ex.fichierUrl | slice:0:30 }}...
                  </a>
                </td>
                <td class="text-xs text-gray-500">{{ ex.dateTeleversement | date:'dd/MM/yyyy' }}</td>
                <td>{{ ex.moduleId }}</td>
                <td class="space-x-2">
                  <button (click)="editerExamen(ex)" class="btn-secondary text-xs px-2 py-1">Modifier</button>
                  <button (click)="supprimerExamen(ex.id)" class="btn-danger">Supprimer</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- ── INSCRIPTIONS (figure 4.4.17 + 4.4.18) ────────────── -->
    <div *ngIf="section === 'inscriptions'">
      <div class="flex items-center justify-between mb-6">
        <h1 class="text-xl font-bold text-gray-900">Inscriptions aux modules</h1>
        <button (click)="ouvrirModal('inscription')" class="btn-primary">
          + Ajouter une nouvelle inscription
        </button>
      </div>
      <div class="card">
        <div class="table-container">
          <table class="data-table">
            <thead>
              <tr><th>Id</th><th>Étudiant</th><th>Module</th><th>Date d'inscription</th><th>Actions</th></tr>
            </thead>
            <tbody>
              <tr *ngFor="let ins of inscriptions">
                <td class="text-gray-400 font-mono text-xs">{{ ins.id }}</td>
                <td class="font-medium">{{ ins.etudiantId }}</td>
                <td>{{ ins.moduleId }}</td>
                <td class="text-xs text-gray-500">{{ ins.dateInscription }}</td>
                <td class="space-x-2">
                  <button class="btn-secondary text-xs px-2 py-1">Modifier</button>
                  <button (click)="supprimerInscription(ins.id)" class="btn-danger">Supprimer</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </main>


    <!-- ── BI DASHBOARD ─────────────────────────────────────── -->
    <div *ngIf="section === 'bi'">
      <app-bi-dashboard></app-bi-dashboard>
    </div>

    <!-- ── MODALS ───────────────────────────────────────────────── -->
  <div *ngIf="modalOuvert" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
    <div class="bg-white rounded-2xl w-full max-w-lg shadow-xl overflow-y-auto max-h-[90vh]">

      <!-- Header modal -->
      <div class="flex items-center justify-between p-6 border-b">
        <h3 class="font-bold text-gray-900 text-lg">{{ titrModal }}</h3>
        <button (click)="fermerModal()" class="text-gray-400 hover:text-gray-600 text-xl">✕</button>
      </div>

      <div class="p-6">

        <!-- Form Filière -->
        <form *ngIf="typeModal === 'filiere'" (ngSubmit)="sauvegarder()">
          <div class="mb-4">
            <label class="form-label">Nom *</label>
            <input class="form-input" type="text" [(ngModel)]="filiereForm.nom" name="nom"
              [placeholder]="'Ex: Intelligent Processing Systems'" required>
          </div>
          <div class="mb-4">
            <label class="form-label">Année universitaire *</label>
            <input class="form-input" type="number" [(ngModel)]="filiereForm.anneeUniversitaire"
              name="annee" placeholder="2025">
          </div>
          <div class="mb-4">
            <label class="form-label">Description</label>
            <textarea class="form-input" rows="3" [(ngModel)]="filiereForm.description"
              name="description" placeholder="Description de la filière..."></textarea>
          </div>
          <div *ngIf="erreurModal" class="text-red-500 text-sm mb-3">{{ erreurModal }}</div>
          <div class="flex gap-3">
            <button type="button" (click)="fermerModal()" class="btn-secondary flex-1">Annuler</button>
            <button type="submit" class="btn-primary flex-1">
              {{ modeEdition ? 'Edit Filiere' : 'Add Filiere' }}
            </button>
          </div>
        </form>

        <!-- Form Enseignant -->
        <form *ngIf="typeModal === 'enseignant'" (ngSubmit)="sauvegarder()">
          <div class="grid grid-cols-2 gap-4 mb-4">
            <div>
              <label class="form-label">First Name *</label>
              <input class="form-input" type="text" [(ngModel)]="enseignantForm.prenom"
                name="prenom" required>
            </div>
            <div>
              <label class="form-label">Last Name *</label>
              <input class="form-input" type="text" [(ngModel)]="enseignantForm.nom"
                name="nom" required>
            </div>
          </div>
          <div class="mb-4">
            <label class="form-label">Email *</label>
            <input class="form-input" type="email" [(ngModel)]="enseignantForm.email"
              name="email" required>
          </div>
          <div class="mb-4">
            <label class="form-label">Password *</label>
            <input class="form-input" type="password" [(ngModel)]="enseignantForm.motDePasse"
              name="password">
          </div>
          <div class="mb-4">
            <label class="form-label">Phone *</label>
            <input class="form-input" type="text" [(ngModel)]="enseignantForm.telephone"
              name="phone">
          </div>
          <div class="mb-4">
            <label class="form-label">Specialty</label>
            <input class="form-input" type="text" [(ngModel)]="enseignantForm.specialite"
              name="specialite" placeholder="Ex: physique quantique">
          </div>
          <div *ngIf="erreurModal" class="text-red-500 text-sm mb-3">{{ erreurModal }}</div>
          <div class="flex gap-3">
            <button type="button" (click)="fermerModal()" class="btn-secondary flex-1">Annuler</button>
            <button type="submit" class="btn-primary flex-1">
              {{ modeEdition ? 'Edit Teacher' : 'Add Teacher' }}
            </button>
          </div>
        </form>

        <!-- Form Module -->
        <form *ngIf="typeModal === 'module'" (ngSubmit)="sauvegarder()">
          <div class="mb-4">
            <label class="form-label">Id</label>
            <input class="form-input" type="number" [(ngModel)]="moduleForm.filiereId"
              name="id" placeholder="Id du module">
          </div>
          <div class="mb-4">
            <label class="form-label">Name *</label>
            <input class="form-input" type="text" [(ngModel)]="moduleForm.nom"
              name="nom" placeholder="Ex: ingénierie logicielle" required>
          </div>
          <div class="mb-4">
            <label class="form-label">Description</label>
            <textarea class="form-input" rows="2" [(ngModel)]="moduleForm.description"
              name="desc" placeholder="Description du module..."></textarea>
          </div>
          <div class="mb-4">
            <label class="form-label">Filière *</label>
            <select class="form-input" [(ngModel)]="moduleForm.filiereId" name="filiere">
              <option value="">Please select</option>
              <option *ngFor="let f of filieres" [value]="f.id">{{ f.nom }}</option>
            </select>
          </div>
          <div class="mb-4">
            <label class="form-label">Teacher</label>
            <select class="form-input" [(ngModel)]="moduleForm.enseignantId" name="teacher">
              <option value="">Please select</option>
              <option *ngFor="let e of enseignants" [value]="e.id">{{ e.nom }}</option>
            </select>
          </div>
          <div *ngIf="erreurModal" class="text-red-500 text-sm mb-3">{{ erreurModal }}</div>
          <div class="flex gap-3">
            <button type="button" (click)="fermerModal()" class="btn-secondary flex-1">Annuler</button>
            <button type="submit" class="btn-primary flex-1">
              {{ modeEdition ? 'Edit Modulee' : 'Add Module' }}
            </button>
          </div>
        </form>

        <!-- Form Étudiant -->
        <form *ngIf="typeModal === 'etudiant'" (ngSubmit)="sauvegarder()">
          <div class="mb-4">
            <label class="form-label">Apogee Number *</label>
            <input class="form-input" type="text" [(ngModel)]="etudiantForm.numeroApogee"
              name="apogee" required>
          </div>
          <div class="mb-4">
            <label class="form-label">First Name *</label>
            <input class="form-input" type="text" [(ngModel)]="etudiantForm.prenom"
              name="prenom" required>
          </div>
          <div class="mb-4">
            <label class="form-label">Last Name *</label>
            <input class="form-input" type="text" [(ngModel)]="etudiantForm.nom"
              name="nom" required>
          </div>
          <div class="mb-4">
            <label class="form-label">Email *</label>
            <input class="form-input" type="email" [(ngModel)]="etudiantForm.email"
              name="email" required>
          </div>
          <div class="mb-4">
            <label class="form-label">Password *</label>
            <input class="form-input" type="password" [(ngModel)]="etudiantForm.motDePasse"
              name="password">
          </div>
          <div class="mb-4">
            <label class="form-label">Filiere</label>
            <select class="form-input" [(ngModel)]="etudiantForm.filiereId" name="filiere">
              <option value="">Please select</option>
              <option *ngFor="let f of filieres" [value]="f.id">{{ f.nom }}</option>
            </select>
          </div>
          <div *ngIf="erreurModal" class="text-red-500 text-sm mb-3">{{ erreurModal }}</div>
          <div class="flex gap-3">
            <button type="button" (click)="fermerModal()" class="btn-secondary flex-1">Annuler</button>
            <button type="submit" class="btn-primary flex-1">
              {{ modeEdition ? 'Edit Student' : 'Add Student' }}
            </button>
          </div>
        </form>

        <!-- Form Examen -->
        <form *ngIf="typeModal === 'examen'" (ngSubmit)="sauvegarder()">
          <div class="mb-4">
            <label class="form-label">Id</label>
            <input class="form-input" type="number" [(ngModel)]="examenAdminForm.moduleId"
              name="id" placeholder="Id de l'examen">
          </div>
          <div class="mb-4">
            <label class="form-label">Title *</label>
            <input class="form-input" type="text" [(ngModel)]="examenAdminForm.titre"
              name="titre" required>
          </div>
          <div class="mb-4">
            <label class="form-label">Exam Year *</label>
            <input class="form-input" type="number" [(ngModel)]="examenAdminForm.anneeExamen"
              name="annee">
          </div>
          <div class="mb-4">
            <label class="form-label">File Url *</label>
            <input class="form-input" type="url" [(ngModel)]="examenAdminForm.fichierUrl"
              name="file">
          </div>
          <div class="mb-4">
            <label class="form-label">Modulee *</label>
            <select class="form-input" [(ngModel)]="examenAdminForm.moduleId" name="module">
              <option value="">Please select</option>
              <option *ngFor="let m of modules" [value]="m.id">{{ m.nom }}</option>
            </select>
          </div>
          <div *ngIf="erreurModal" class="text-red-500 text-sm mb-3">{{ erreurModal }}</div>
          <div class="flex gap-3">
            <button type="button" (click)="fermerModal()" class="btn-secondary flex-1">Annuler</button>
            <button type="submit" class="btn-primary flex-1">
              {{ modeEdition ? 'Edit Exam' : 'Add Exam' }}
            </button>
          </div>
        </form>

        <!-- Form Inscription -->
        <form *ngIf="typeModal === 'inscription'" (ngSubmit)="sauvegarder()">
          <div class="mb-4">
            <label class="form-label">Enrollment Date</label>
            <input class="form-input" type="date" [(ngModel)]="inscriptionDate" name="date">
          </div>
          <div class="mb-4">
            <label class="form-label">Student User *</label>
            <select class="form-input" [(ngModel)]="inscriptionForm.etudiantId" name="etudiant">
              <option value="">Please select</option>
              <option *ngFor="let e of etudiants" [value]="e.id">{{ e.prenom }} {{ e.nom }}</option>
            </select>
          </div>
          <div class="mb-4">
            <label class="form-label">Modulee *</label>
            <select class="form-input" [(ngModel)]="inscriptionForm.moduleId" name="module">
              <option value="">Please select</option>
              <option *ngFor="let m of modules" [value]="m.id">{{ m.nom }}</option>
            </select>
          </div>
          <div *ngIf="erreurModal" class="text-red-500 text-sm mb-3">{{ erreurModal }}</div>
          <div class="flex gap-3">
            <button type="button" (click)="fermerModal()" class="btn-secondary flex-1">Annuler</button>
            <button type="submit" class="btn-primary flex-1">
              Edit Student Module Enrollment
            </button>
          </div>
        </form>

      </div>
    </div>
  </div>

</div>
  `
})
export class AdminComponent implements OnInit {

  section: Section    = 'dashboard';
  modalOuvert         = false;
  typeModal           = '';
  titrModal           = '';
  modeEdition         = false;
  idEdition?:         number;
  erreurModal         = '';
  inscriptionDate     = new Date().toISOString().split('T')[0];

  // Données
  filieres:     M.FiliereResponse[]    = [];
  modules:      M.ModuleResponse[]     = [];
  etudiants:    M.EtudiantResponse[]   = [];
  enseignants:  M.EnseignantResponse[] = [];
  examens:      M.ExamenResponse[]     = [];
  inscriptions: M.InscriptionResponse[] = [];

  // Statistiques
  stats = [
    { label: 'Étudiants',    valeur: 0 },
    { label: 'Enseignants',  valeur: 0 },
    { label: 'Modules',      valeur: 0 },
    { label: 'Filières',     valeur: 0 },
    { label: 'Examens',      valeur: 0 },
    { label: 'Inscriptions', valeur: 0 },
  ];

  // Menu sidebar
  menuItems = [
    { key: 'dashboard' as Section,    icon: '📊', label: 'Dashboard' },
    { key: 'filieres' as Section,     icon: '🏫', label: 'Filières' },
    { key: 'modules' as Section,      icon: '📚', label: 'Modules' },
    { key: 'etudiants' as Section,    icon: '👨‍🎓', label: 'Étudiants' },
    { key: 'enseignants' as Section,  icon: '👨‍🏫', label: 'Enseignants' },
    { key: 'examens' as Section,      icon: '📋', label: 'Examens' },
    { key: 'inscriptions' as Section, icon: '📝', label: 'Inscriptions aux modules' },
    { key: 'bi' as Section,            icon: '📊', label: 'Tableau de Bord BI' },
  ];

  // Formulaires
  filiereForm:    M.FiliereRequest     = { nom: '' };
  moduleForm:     M.ModuleRequest      = { nom: '' };
  etudiantForm:   M.EtudiantRequest    = { numeroApogee:'', nom:'', prenom:'', email:'', motDePasse:'' };
  enseignantForm: M.EnseignantRequest  = { nom:'', prenom:'', email:'', motDePasse:'' };
  examenAdminForm:M.ExamenRequest      = { titre:'', moduleId:0 };
  inscriptionForm:M.InscriptionRequest = { etudiantId:0, moduleId:0 };

  constructor(
    public  auth:               AuthService,
    private etudiantSvc:        EtudiantService,
    private enseignantSvc:      EnseignantService,
    private filiereSvc:         FiliereService,
    private moduleSvc:          ModuleService,
    private examenSvc:          ExamenService,
    private inscriptionSvc:     InscriptionService
  ) {}

  ngOnInit() { this.chargerTout(); }

  chargerTout() {
    this.filiereSvc.listerToutes().subscribe(d => { this.filieres = d; this.stats[3].valeur = d.length; });
    this.moduleSvc.listerTous().subscribe(d => { this.modules = d; this.stats[2].valeur = d.length; });
    this.etudiantSvc.listerTous().subscribe(d => { this.etudiants = d; this.stats[0].valeur = d.length; });
    this.enseignantSvc.listerTous().subscribe(d => { this.enseignants = d; this.stats[1].valeur = d.length; });
    this.examenSvc.listerTous().subscribe(d => { this.examens = d; this.stats[4].valeur = d.length; });
    this.inscriptionSvc.listerToutes().subscribe(d => { this.inscriptions = d; this.stats[5].valeur = d.length; });
  }

  chargerSection(s: Section) {
    if (s === 'dashboard') this.chargerTout();
  }

  // ── MODALS ──────────────────────────────────────────────────
  ouvrirModal(type: string) {
    this.typeModal   = type;
    this.modeEdition = false;
    this.idEdition   = undefined;
    this.erreurModal = '';
    this.titrModal   = { filiere:'Add Filiere', enseignant:'Add Teacher',
                          module:'Add Module', etudiant:'Add Student',
                          examen:'Add Exam', inscription:'Add Inscription' }[type] || '';
    this.reinitForms();
    this.modalOuvert = true;
  }

  fermerModal() { this.modalOuvert = false; this.erreurModal = ''; }

  reinitForms() {
    this.filiereForm     = { nom: '' };
    this.moduleForm      = { nom: '' };
    this.etudiantForm    = { numeroApogee:'', nom:'', prenom:'', email:'', motDePasse:'' };
    this.enseignantForm  = { nom:'', prenom:'', email:'', motDePasse:'' };
    this.examenAdminForm = { titre:'', moduleId:0 };
    this.inscriptionForm = { etudiantId:0, moduleId:0 };
  }

  // ── EDIT HELPERS ─────────────────────────────────────────────
  editerFiliere(f: M.FiliereResponse) {
    this.ouvrirModal('filiere'); this.modeEdition = true; this.idEdition = f.id;
    this.titrModal = 'Edit Filiere';
    this.filiereForm = { nom: f.nom, description: f.description,
                          anneeUniversitaire: f.anneeUniversitaire, semestre: f.semestre };
  }

  editerEnseignant(e: M.EnseignantResponse) {
    this.ouvrirModal('enseignant'); this.modeEdition = true; this.idEdition = e.id;
    this.titrModal = 'Edit Teacher';
    this.enseignantForm = { nom: e.nom, prenom: e.prenom, email: e.email,
                             motDePasse: '', telephone: e.telephone, specialite: e.specialite,
                             departement: e.departement, bureau: e.bureau };
  }

  editerModule(m: M.ModuleResponse) {
    this.ouvrirModal('module'); this.modeEdition = true; this.idEdition = m.id;
    this.titrModal = 'Edit Module';
    this.moduleForm = { nom: m.nom, description: m.description,
                         filiereId: m.filiereId, enseignantId: m.enseignantId };
  }

  editerEtudiant(e: M.EtudiantResponse) {
    this.ouvrirModal('etudiant'); this.modeEdition = true; this.idEdition = e.id;
    this.titrModal = 'Edit Student';
    this.etudiantForm = { numeroApogee: e.numeroApogee, nom: e.nom, prenom: e.prenom,
                           email: e.email, motDePasse: '', filiereId: e.filiereId };
  }

  editerExamen(ex: M.ExamenResponse) {
    this.ouvrirModal('examen'); this.modeEdition = true; this.idEdition = ex.id;
    this.titrModal = 'Edit Exam';
    this.examenAdminForm = { titre: ex.titre, anneeExamen: ex.anneeExamen,
                              fichierUrl: ex.fichierUrl, moduleId: ex.moduleId || 0 };
  }

  // ── SAVE ─────────────────────────────────────────────────────
  sauvegarder() {
    this.erreurModal = '';
    const ops: Record<string, () => void> = {
      filiere: () => {
        const obs = this.modeEdition
          ? this.filiereSvc.modifier(this.idEdition!, this.filiereForm)
          : this.filiereSvc.creer(this.filiereForm);
        obs.subscribe({ next: () => { this.fermerModal(); this.chargerTout(); },
                        error: e => { this.erreurModal = e.error?.erreur || 'Erreur.'; } });
      },
      enseignant: () => {
        const obs = this.modeEdition
          ? this.enseignantSvc.modifier(this.idEdition!, this.enseignantForm)
          : this.enseignantSvc.creer(this.enseignantForm);
        obs.subscribe({ next: () => { this.fermerModal(); this.chargerTout(); },
                        error: e => { this.erreurModal = e.error?.erreur || 'Erreur.'; } });
      },
      module: () => {
        const obs = this.modeEdition
          ? this.moduleSvc.modifier(this.idEdition!, this.moduleForm)
          : this.moduleSvc.creer(this.moduleForm);
        obs.subscribe({ next: () => { this.fermerModal(); this.chargerTout(); },
                        error: e => { this.erreurModal = e.error?.erreur || 'Erreur.'; } });
      },
      etudiant: () => {
        const obs = this.modeEdition
          ? this.etudiantSvc.modifier(this.idEdition!, this.etudiantForm)
          : this.etudiantSvc.creer(this.etudiantForm);
        obs.subscribe({ next: () => { this.fermerModal(); this.chargerTout(); },
                        error: e => { this.erreurModal = e.error?.erreur || 'Erreur.'; } });
      },
      examen: () => {
        const obs = this.modeEdition
          ? this.examenSvc.modifier(this.idEdition!, this.examenAdminForm)
          : this.examenSvc.creer(this.examenAdminForm);
        obs.subscribe({ next: () => { this.fermerModal(); this.chargerTout(); },
                        error: e => { this.erreurModal = e.error?.erreur || 'Erreur.'; } });
      },
      inscription: () => {
        this.inscriptionSvc.inscrire(this.inscriptionForm).subscribe({
          next: () => { this.fermerModal(); this.chargerTout(); },
          error: e => { this.erreurModal = e.error?.erreur || 'Erreur.'; }
        });
      }
    };
    ops[this.typeModal]?.();
  }

  // ── DELETE ───────────────────────────────────────────────────
  supprimerFiliere(id: number) {
    if (!confirm('Supprimer cette filière ?')) return;
    this.filiereSvc.supprimer(id).subscribe({ next: () => this.chargerTout() });
  }
  supprimerEnseignant(id: number) {
    if (!confirm('Supprimer cet enseignant ?')) return;
    this.enseignantSvc.supprimer(id).subscribe({ next: () => this.chargerTout() });
  }
  supprimerModule(id: number) {
    if (!confirm('Supprimer ce module ?')) return;
    this.moduleSvc.supprimer(id).subscribe({ next: () => this.chargerTout() });
  }
  supprimerEtudiant(id: number) {
    if (!confirm('Supprimer cet étudiant ?')) return;
    this.etudiantSvc.supprimer(id).subscribe({ next: () => this.chargerTout() });
  }
  supprimerExamen(id: number) {
    if (!confirm('Supprimer cet examen ?')) return;
    this.examenSvc.supprimer(id).subscribe({ next: () => this.chargerTout() });
  }
  supprimerInscription(id: number) {
    if (!confirm('Supprimer cette inscription ?')) return;
    this.inscriptionSvc.desinscrire(id).subscribe({ next: () => this.chargerTout() });
  }
}
