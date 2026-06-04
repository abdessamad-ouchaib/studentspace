/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  TEACHER COMPONENT — Espace Enseignant                       ║
 * ║  Auteur : Abdessamad Ouchaib                                 ║
 * ║  Reproduit la figure 4.4.4 du rapport PFE                    ║
 * ╚══════════════════════════════════════════════════════════════╝
 */

import { Component, OnInit }   from '@angular/core';
import { CommonModule }         from '@angular/common';
import { FormsModule }          from '@angular/forms';
import { AuthService, EnseignantService,
         ModuleService, ExamenService,
         MessageService, InscriptionService } from '../shared/services';
import { EnseignantResponse, ModuleResponse,
         ExamenResponse, MessageResponse,
         InscriptionResponse, ExamenRequest } from '../shared/models';

@Component({
  selector:   'app-teacher',
  standalone: true,
  imports:    [CommonModule, FormsModule],
  template: `
<div class="min-h-screen bg-gray-50">

  <!-- Navbar -->
  <nav class="bg-white border-b border-gray-100 px-6 py-3 flex items-center justify-between">
    <div class="flex items-center gap-2">
      <div class="w-8 h-8 bg-blue-600 rounded-lg flex items-center justify-center">
        <span class="text-white text-sm">🎓</span>
      </div>
      <span class="font-bold text-gray-900">StudentSpace</span>
    </div>
    <div class="flex items-center gap-3">
      <span class="text-sm font-medium text-gray-700">
        {{ enseignant?.prenom }} {{ enseignant?.nom }}
      </span>
      <button (click)="auth.deconnecter()"
        class="bg-red-50 text-red-600 px-3 py-1.5 rounded-lg text-sm font-medium hover:bg-red-100">
        Déconnexion
      </button>
    </div>
  </nav>

  <div class="max-w-6xl mx-auto p-6">

    <!-- Bienvenue -->
    <div class="mb-6">
      <h1 class="text-2xl font-bold text-blue-700">
        Bienvenue, {{ enseignant?.prenom }} !
      </h1>
      <p class="text-gray-500 text-sm mt-1">Heureux de vous revoir sur votre espace enseignant.</p>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">

      <!-- Infos personnelles -->
      <div class="card flex flex-col items-center text-center">
        <div class="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center mb-3">
          <span class="text-3xl">👤</span>
        </div>
        <p class="font-bold text-gray-900 text-lg">{{ enseignant?.prenom }} {{ enseignant?.nom }}</p>
        <p class="text-sm text-gray-500">Professeur</p>
        <div class="mt-4 w-full space-y-2 text-left text-sm">
          <div class="flex justify-between">
            <span class="text-gray-500">Email :</span>
            <span class="font-medium text-xs">{{ enseignant?.email }}</span>
          </div>
          <div class="flex justify-between">
            <span class="text-gray-500">Téléphone :</span>
            <span class="font-medium">{{ enseignant?.telephone || 'N/A' }}</span>
          </div>
          <div class="flex justify-between">
            <span class="text-gray-500">Spécialité :</span>
            <span class="font-medium text-xs">{{ enseignant?.specialite || 'N/A' }}</span>
          </div>
          <div class="flex justify-between">
            <span class="text-gray-500">Département :</span>
            <span class="font-medium text-xs">{{ enseignant?.departement || 'N/A' }}</span>
          </div>
          <div class="flex justify-between">
            <span class="text-gray-500">Bureau :</span>
            <span class="font-medium text-xs">{{ enseignant?.bureau || 'N/A' }}</span>
          </div>
        </div>
      </div>

      <!-- Mes modules -->
      <div class="lg:col-span-2 card">
        <h2 class="font-bold text-gray-900 mb-4 flex items-center gap-2">
          <span>📚</span> Mes Modules
        </h2>

        <div *ngIf="chargement" class="flex justify-center py-8">
          <div class="spinner"></div>
        </div>

        <div *ngIf="!chargement && modules.length === 0"
          class="text-center py-8 text-gray-400">
          Aucun module assigné.
        </div>

        <div *ngFor="let mod of modules"
          class="border border-gray-100 rounded-xl p-4 mb-3 hover:border-blue-200 transition-colors">
          <div class="flex items-center justify-between mb-2">
            <div>
              <p class="font-semibold text-gray-800">
                {{ mod.nom }}
                <span class="badge-gray ml-2">{{ mod.id }}</span>
              </p>
              <p class="text-xs text-gray-400 mt-1">
                Nombre d'étudiants inscrits :
                <span class="font-semibold text-gray-600">{{ mod.nombreEtudiants }}</span>
              </p>
            </div>
          </div>

          <!-- Boutons d'action -->
          <div class="flex flex-wrap gap-2 mt-3">
            <button (click)="ouvrirChat(mod.id)"
              class="flex items-center gap-1.5 px-3 py-1.5 bg-gray-100 text-gray-700
                     text-xs rounded-lg hover:bg-gray-200 transition-colors">
              💬 Chat
            </button>
            <button (click)="voirEtudiants(mod.id)"
              class="flex items-center gap-1.5 px-3 py-1.5 bg-blue-50 text-blue-700
                     text-xs rounded-lg hover:bg-blue-100 transition-colors">
              👥 Liste des étudiants
            </button>
            <button (click)="programmerExamen(mod)"
              class="flex items-center gap-1.5 px-3 py-1.5 bg-green-50 text-green-700
                     text-xs rounded-lg hover:bg-green-100 transition-colors">
              📋 Programmer un examen
            </button>
          </div>
        </div>

        <!-- Afficher plus -->
        <button *ngIf="modules.length > 3"
          class="w-full text-center text-sm text-blue-600 hover:underline mt-2">
          Afficher plus de modules
        </button>
      </div>
    </div>

    <!-- Liste étudiants d'un module -->
    <div *ngIf="etudiantsModule.length > 0" class="card mt-6">
      <h2 class="font-bold text-gray-900 mb-4">
        👥 Étudiants inscrits — Module sélectionné
        <span class="badge-blue ml-2">{{ etudiantsModule.length }}</span>
      </h2>
      <div class="table-container">
        <table class="data-table">
          <thead>
            <tr>
              <th>Étudiant</th>
              <th>Date d'inscription</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let ins of etudiantsModule">
              <td class="font-medium">{{ ins.etudiantNom }}</td>
              <td class="text-gray-500 text-xs">{{ ins.dateInscription }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>

  <!-- Modal Chat (identique figure 4.4.5) -->
  <div *ngIf="chatOuvert" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
    <div class="bg-white rounded-2xl w-full max-w-md h-[500px] flex flex-col shadow-xl">
      <div class="flex items-center justify-between p-4 border-b">
        <h3 class="font-bold text-gray-900">Chat du module</h3>
        <button (click)="fermerChat()" class="text-gray-400 hover:text-gray-600 text-xl">✕</button>
      </div>
      <div class="flex-1 overflow-y-auto p-4 space-y-3">
        <div *ngFor="let msg of messages" class="flex flex-col">
          <div [class.items-end]="msg.expediteurId === auth.getUserId()"
               [class.items-start]="msg.expediteurId !== auth.getUserId()"
               class="flex flex-col">
            <span class="text-xs text-gray-400 mb-1">
              {{ msg.expediteurNom }} · {{ msg.dateEnvoi | date:'HH:mm' }}
            </span>
            <div [class.bg-blue-600]="msg.expediteurId === auth.getUserId()"
                 [class.text-white]="msg.expediteurId === auth.getUserId()"
                 [class.bg-gray-100]="msg.expediteurId !== auth.getUserId()"
                 class="rounded-2xl px-3 py-2 text-sm max-w-xs">
              {{ msg.contenu }}
            </div>
          </div>
        </div>
      </div>
      <div class="p-4 border-t flex gap-2">
        <input class="form-input flex-1" placeholder="Votre message..."
          [(ngModel)]="nouveauMessage" (keyup.enter)="envoyerMessage()" type="text">
        <button (click)="envoyerMessage()" class="btn-primary px-4">Envoyer</button>
      </div>
    </div>
  </div>

  <!-- Modal Programmer examen -->
  <div *ngIf="examenModalOuvert" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
    <div class="bg-white rounded-2xl w-full max-w-md shadow-xl p-6">
      <div class="flex items-center justify-between mb-4">
        <h3 class="font-bold text-gray-900">Programmer un examen</h3>
        <button (click)="examenModalOuvert = false" class="text-gray-400 hover:text-gray-600">✕</button>
      </div>
      <form (ngSubmit)="sauvegarderExamen()">
        <div class="mb-4">
          <label class="form-label">Titre *</label>
          <input class="form-input" type="text" [(ngModel)]="examenForm.titre"
            name="titre" placeholder="Ex: Examen final PHP 2025" required>
        </div>
        <div class="mb-4">
          <label class="form-label">Année</label>
          <input class="form-input" type="number" [(ngModel)]="examenForm.anneeExamen"
            name="annee" placeholder="2025">
        </div>
        <div class="mb-4">
          <label class="form-label">Lien fichier (URL)</label>
          <input class="form-input" type="url" [(ngModel)]="examenForm.fichierUrl"
            name="fichier" placeholder="https://...">
        </div>
        <div *ngIf="erreurExamen" class="text-red-500 text-sm mb-3">{{ erreurExamen }}</div>
        <div class="flex gap-3">
          <button type="button" (click)="examenModalOuvert = false" class="btn-secondary flex-1">
            Annuler
          </button>
          <button type="submit" class="btn-primary flex-1">Sauvegarder</button>
        </div>
      </form>
    </div>
  </div>

</div>
  `
})
export class TeacherComponent implements OnInit {

  enseignant?:      EnseignantResponse;
  modules:          ModuleResponse[]      = [];
  etudiantsModule:  InscriptionResponse[] = [];
  messages:         MessageResponse[]     = [];
  nouveauMessage    = '';
  chatOuvert        = false;
  examenModalOuvert = false;
  moduleIdChat?:    number;
  moduleIdExamen?:  number;
  chargement        = true;
  erreurExamen      = '';

  examenForm: ExamenRequest = { titre: '', anneeExamen: new Date().getFullYear(),
                                 fichierUrl: '', moduleId: 0 };

  constructor(
    public  auth:               AuthService,
    private enseignantService:  EnseignantService,
    private moduleService:      ModuleService,
    private examenService:      ExamenService,
    private messageService:     MessageService,
    private inscriptionService: InscriptionService
  ) {}

  ngOnInit() {
    const userId = this.auth.getUserId();
    this.enseignantService.trouverParId(userId).subscribe({
      next: e => { this.enseignant = e; this.chargement = false; }
    });
    this.moduleService.parEnseignant(userId).subscribe({
      next: m => { this.modules = m; this.chargement = false; }
    });
  }

  voirEtudiants(moduleId: number) {
    this.inscriptionService.parEtudiant(moduleId).subscribe({
      next: ins => this.etudiantsModule = ins
    });
  }

  ouvrirChat(moduleId: number) {
    this.moduleIdChat = moduleId;
    this.chatOuvert   = true;
    this.messageService.parModule(moduleId).subscribe({
      next: msgs => this.messages = msgs
    });
  }

  fermerChat() { this.chatOuvert = false; this.messages = []; }

  envoyerMessage() {
    if (!this.nouveauMessage.trim() || !this.moduleIdChat) return;
    this.messageService.envoyer({
      contenu: this.nouveauMessage.trim(), moduleId: this.moduleIdChat
    }).subscribe({
      next: msg => { this.messages.push(msg); this.nouveauMessage = ''; }
    });
  }

  programmerExamen(mod: ModuleResponse) {
    this.moduleIdExamen        = mod.id;
    this.examenForm.moduleId   = mod.id;
    this.examenForm.titre      = '';
    this.examenForm.fichierUrl = '';
    this.examenModalOuvert     = true;
    this.erreurExamen          = '';
  }

  sauvegarderExamen() {
    if (!this.examenForm.titre.trim()) {
      this.erreurExamen = 'Le titre est obligatoire.'; return;
    }
    this.examenService.creer(this.examenForm).subscribe({
      next: () => { this.examenModalOuvert = false; },
      error: err => { this.erreurExamen = err.error?.erreur || 'Erreur.'; }
    });
  }
}
