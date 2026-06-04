/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  STUDENT COMPONENT — Espace Étudiant                         ║
 * ║  Auteur : Abdessamad Ouchaib                                 ║
 * ║  Reproduit la figure 4.4.3 du rapport PFE                    ║
 * ╚══════════════════════════════════════════════════════════════╝
 */

import { Component, OnInit }   from '@angular/core';
import { CommonModule }         from '@angular/common';
import { FormsModule }          from '@angular/forms';
import { AuthService, EtudiantService,
         InscriptionService, ExamenService,
         MessageService }       from '../shared/services';
import { EtudiantResponse, InscriptionResponse,
         ExamenResponse, MessageResponse }  from '../shared/models';

@Component({
  selector:   'app-student',
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
        {{ etudiant?.prenom }} {{ etudiant?.nom }}
      </span>
      <button (click)="auth.deconnecter()"
        class="bg-red-50 text-red-600 px-3 py-1.5 rounded-lg text-sm font-medium hover:bg-red-100">
        Déconnexion
      </button>
    </div>
  </nav>

  <div class="max-w-6xl mx-auto p-6">
    <!-- Message de bienvenue (identique figure 4.4.3) -->
    <div class="mb-6">
      <h1 class="text-2xl font-bold text-blue-700">
        Bienvenue, {{ etudiant?.prenom }} !
      </h1>
      <p class="text-gray-500 text-sm mt-1">Heureux de vous revoir sur votre espace étudiant.</p>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">

      <!-- Infos personnelles (gauche) -->
      <div class="card flex flex-col items-center text-center">
        <div class="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center mb-3">
          <span class="text-3xl">👤</span>
        </div>
        <p class="font-bold text-gray-900 text-lg">{{ etudiant?.prenom }} {{ etudiant?.nom }}</p>
        <p class="text-sm text-gray-500">Étudiant(e)</p>
        <div class="mt-4 w-full space-y-2 text-left">
          <div class="flex justify-between text-sm">
            <span class="text-gray-500">Numéro APOGEE :</span>
            <span class="font-medium">{{ etudiant?.numeroApogee }}</span>
          </div>
          <div class="flex justify-between text-sm">
            <span class="text-gray-500">Filière :</span>
            <span class="font-medium text-right text-xs">{{ etudiant?.filiere || 'N/A' }}</span>
          </div>
          <div class="flex justify-between text-sm">
            <span class="text-gray-500">Année :</span>
            <span class="font-medium">2024-2025</span>
          </div>
        </div>
      </div>

      <!-- Modules (droite) -->
      <div class="lg:col-span-2 card">
        <h2 class="font-bold text-gray-900 mb-4 flex items-center gap-2">
          <span>📚</span> Mes Modules
        </h2>

        <div *ngIf="chargement" class="flex justify-center py-8">
          <div class="spinner"></div>
        </div>

        <div *ngIf="!chargement && inscriptions.length === 0"
          class="text-center py-8 text-gray-400">
          Aucun module inscrit pour le moment.
        </div>

        <div *ngFor="let ins of inscriptions" class="border border-gray-100 rounded-xl p-4 mb-3">
          <div class="flex items-start justify-between">
            <div>
              <p class="font-semibold text-gray-800">{{ ins.moduleNom }}</p>
              <p class="text-xs text-gray-400 mt-1">
                Enseigné par : <span class="text-blue-600">Prof. {{ ins.moduleNom }}</span>
              </p>
            </div>
          </div>

          <!-- Boutons Chat et Examens -->
          <div class="flex gap-2 mt-3">
            <button
              (click)="ouvrirChat(ins.moduleId)"
              class="flex items-center gap-1.5 px-3 py-1.5 bg-gray-100 text-gray-700
                     text-xs rounded-lg hover:bg-gray-200 transition-colors">
              💬 Chat
            </button>
            <button
              (click)="voirExamens(ins.moduleId)"
              class="flex items-center gap-1.5 px-3 py-1.5 bg-gray-100 text-gray-700
                     text-xs rounded-lg hover:bg-gray-200 transition-colors">
              📋 Examens
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Liste des examens -->
    <div *ngIf="examensAffiches.length > 0" class="card mt-6">
      <h2 class="font-bold text-gray-900 mb-4">📋 Examens du module sélectionné</h2>
      <div class="table-container">
        <table class="data-table">
          <thead>
            <tr>
              <th>Titre</th>
              <th>Année</th>
              <th>Fichier</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let ex of examensAffiches">
              <td class="font-medium">{{ ex.titre }}</td>
              <td>{{ ex.anneeExamen }}</td>
              <td>
                <a *ngIf="ex.fichierUrl" [href]="ex.fichierUrl" target="_blank"
                   class="text-blue-600 hover:underline text-xs">Télécharger</a>
                <span *ngIf="!ex.fichierUrl" class="text-gray-400 text-xs">—</span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>

  <!-- Modal Chat (figure 4.4.5) -->
  <div *ngIf="chatOuvert" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
    <div class="bg-white rounded-2xl w-full max-w-md h-[500px] flex flex-col shadow-xl">

      <!-- Header chat -->
      <div class="flex items-center justify-between p-4 border-b">
        <h3 class="font-bold text-gray-900">Chat du module</h3>
        <button (click)="fermerChat()" class="text-gray-400 hover:text-gray-600 text-xl">✕</button>
      </div>

      <!-- Messages -->
      <div #messagesContainer class="flex-1 overflow-y-auto p-4 space-y-3">
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

      <!-- Saisie message -->
      <div class="p-4 border-t flex gap-2">
        <input
          class="form-input flex-1"
          placeholder="Votre message..."
          [(ngModel)]="nouveauMessage"
          (keyup.enter)="envoyerMessage()"
          type="text">
        <button (click)="envoyerMessage()" class="btn-primary px-4">Envoyer</button>
      </div>
    </div>
  </div>

</div>
  `
})
export class StudentComponent implements OnInit {

  etudiant?:       EtudiantResponse;
  inscriptions:    InscriptionResponse[] = [];
  examensAffiches: ExamenResponse[]      = [];
  messages:        MessageResponse[]     = [];
  nouveauMessage   = '';
  chatOuvert       = false;
  moduleIdChat?:   number;
  chargement       = true;

  constructor(
    public  auth:              AuthService,
    private etudiantService:   EtudiantService,
    private inscriptionService:InscriptionService,
    private examenService:     ExamenService,
    private messageService:    MessageService
  ) {}

  ngOnInit() {
    const userId = this.auth.getUserId();
    // Charger le profil étudiant
    this.etudiantService.trouverParId(userId).subscribe({
      next:  e => { this.etudiant = e; this.chargement = false; },
      error: () => { this.chargement = false; }
    });
    // Charger les inscriptions
    this.inscriptionService.parEtudiant(userId).subscribe({
      next: ins => this.inscriptions = ins
    });
  }

  voirExamens(moduleId: number) {
    this.examenService.parModule(moduleId).subscribe({
      next: ex => this.examensAffiches = ex
    });
  }

  ouvrirChat(moduleId: number) {
    this.moduleIdChat = moduleId;
    this.chatOuvert   = true;
    this.chargerMessages();
  }

  fermerChat() {
    this.chatOuvert = false;
    this.messages   = [];
  }

  chargerMessages() {
    if (!this.moduleIdChat) return;
    this.messageService.parModule(this.moduleIdChat).subscribe({
      next: msgs => this.messages = msgs
    });
  }

  envoyerMessage() {
    if (!this.nouveauMessage.trim() || !this.moduleIdChat) return;
    this.messageService.envoyer({
      contenu:  this.nouveauMessage.trim(),
      moduleId: this.moduleIdChat
    }).subscribe({
      next: msg => {
        this.messages.push(msg);
        this.nouveauMessage = '';
      }
    });
  }
}
