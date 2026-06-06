/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  AUTH COMPONENT — Page de connexion StudentSpace             ║
 * ║  Auteur : Abdessamad Ouchaib                                 ║
 * ║  Reproduit fidèlement les interfaces du rapport PFE          ║
 * ╚══════════════════════════════════════════════════════════════╝
 */

import { Component, OnInit }            from '@angular/core';
import { CommonModule }                  from '@angular/common';
import { FormsModule }                   from '@angular/forms';
import { Router }                        from '@angular/router';
import { AuthService }                   from '../shared/services';

@Component({
  selector:    'app-auth',
  standalone:  true,
  imports:     [CommonModule, FormsModule],
  template: `
<!-- Page d'authentification — identique aux figures 4.4.1 et 4.4.2 du rapport -->
<div class="min-h-screen bg-gray-50 flex items-center justify-center p-4">
  <div class="bg-white rounded-2xl shadow-sm border border-gray-100 w-full max-w-sm p-8">

    <!-- Logo + Titre (identique à StudentSpace du rapport) -->
    <div class="text-center mb-6">
      <div class="w-12 h-12 bg-blue-600 rounded-xl flex items-center justify-center mx-auto mb-3">
        <span class="text-white text-2xl">🎓</span>
      </div>
      <h1 class="text-xl font-bold text-gray-900">StudentSpace</h1>
      <p class="text-sm text-gray-500 mt-1">Connectez-vous pour accéder à votre tableau de bord</p>
    </div>

    <!-- Onglets Étudiant / Enseignant (identique au rapport) -->
    <div class="flex border-b border-gray-200 mb-6">
      <button
        (click)="onglet = 'etudiant'"
        [class.border-b-2]="onglet === 'etudiant'"
        [class.border-blue-600]="onglet === 'etudiant'"
        [class.text-blue-600]="onglet === 'etudiant'"
        [class.text-gray-500]="onglet !== 'etudiant'"
        class="flex-1 py-2 text-sm font-medium transition-colors">
        Étudiant
      </button>
      <button
        (click)="onglet = 'enseignant'"
        [class.border-b-2]="onglet === 'enseignant'"
        [class.border-blue-600]="onglet === 'enseignant'"
        [class.text-blue-600]="onglet === 'enseignant'"
        [class.text-gray-500]="onglet !== 'enseignant'"
        class="flex-1 py-2 text-sm font-medium transition-colors">
        Enseignant
      </button>
      <button
        (click)="onglet = 'admin'"
        [class.border-b-2]="onglet === 'admin'"
        [class.border-blue-600]="onglet === 'admin'"
        [class.text-blue-600]="onglet === 'admin'"
        [class.text-gray-500]="onglet !== 'admin'"
        class="flex-1 py-2 text-sm font-medium transition-colors">
        Admin
      </button>
    </div>

    <!-- Formulaire Étudiant -->
    <form *ngIf="onglet === 'etudiant'" (ngSubmit)="loginEtudiant()">
      <div class="mb-4">
        <label class="form-label">Numéro APOGEE</label>
        <input
          class="form-input"
          type="text"
          placeholder="Ex: 2005050"
          [(ngModel)]="apogee"
          name="apogee"
          required>
      </div>
      <div *ngIf="erreur" class="text-red-500 text-sm mb-3">{{ erreur }}</div>
      <button type="submit" class="btn-primary w-full justify-center" [disabled]="chargement">
        <span *ngIf="chargement" class="spinner mr-2"></span>
        Connexion
      </button>
    </form>

    <!-- Formulaire Enseignant -->
    <form *ngIf="onglet === 'enseignant'" (ngSubmit)="loginEnseignant()">
      <div class="mb-4">
        <label class="form-label">Email</label>
        <input
          class="form-input"
          type="email"
          placeholder="enseignant@fsr.ac.ma"
          [(ngModel)]="email"
          name="email"
          required>
      </div>
      <div class="mb-4">
        <label class="form-label">Mot de passe</label>
        <input
          class="form-input"
          type="password"
          placeholder="••••••••"
          [(ngModel)]="motDePasse"
          name="motDePasse"
          required>
      </div>
      <div *ngIf="erreur" class="text-red-500 text-sm mb-3">{{ erreur }}</div>
      <button type="submit" class="btn-primary w-full justify-center" [disabled]="chargement">
        <span *ngIf="chargement" class="spinner mr-2"></span>
        Connexion
      </button>
    </form>

    <!-- Formulaire Admin -->
    <form *ngIf="onglet === 'admin'" (ngSubmit)="loginAdmin()">
      <div class="mb-4">
        <label class="form-label">Email Admin</label>
        <input
          class="form-input"
          type="email"
          placeholder="admin@fsr.ac.ma"
          [(ngModel)]="emailAdmin"
          name="emailAdmin"
          required>
      </div>
      <div class="mb-4">
        <label class="form-label">Mot de passe</label>
        <input
          class="form-input"
          type="password"
          placeholder="••••••••"
          [(ngModel)]="motDePasseAdmin"
          name="motDePasseAdmin"
          required>
      </div>
      <div *ngIf="erreur" class="text-red-500 text-sm mb-3">{{ erreur }}</div>
      <button type="submit" class="btn-primary w-full justify-center" [disabled]="chargement">
        <span *ngIf="chargement" class="spinner mr-2"></span>
        Connexion Admin
      </button>
    </form>

    <!-- Comptes de test -->
    <div class="mt-6 p-3 bg-blue-50 rounded-lg text-xs text-blue-700">
      <p class="font-semibold mb-1">🧪 Comptes de test :</p>
      <p>Étudiant APOGEE : <strong>2005050</strong></p>
      <p>Enseignant email : <strong>test&#64;mail.com</strong> / mdp : <strong>admin123</strong></p>
      <p>Admin email : <strong>admin&#64;fsr.ac.ma</strong> / mdp : <strong>admin123</strong></p>
    </div>
  </div>
</div>
  `
})
export class AuthComponent implements OnInit {
  onglet       = 'etudiant';
  apogee       = '';
  email        = '';
  motDePasse   = '';
  emailAdmin   = '';
  motDePasseAdmin = '';
  erreur       = '';
  chargement   = false;

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit() {
    // Rediriger si déjà connecté
    if (this.auth.estConnecte()) {
      this.redirigerParRole(this.auth.getRole());
    }
  }

  loginEtudiant() {
    if (!this.apogee.trim()) { this.erreur = 'Entrez votre numéro APOGEE.'; return; }
    this.chargement = true; this.erreur = '';
    this.auth.loginEtudiant({ numeroApogee: this.apogee }).subscribe({
      next: () => this.redirigerParRole('ETUDIANT'),
      error: err => {
        this.erreur = err.error?.erreur || 'Numéro APOGEE introuvable.';
        this.chargement = false;
      }
    });
  }

  loginEnseignant() {
    if (!this.email || !this.motDePasse) { this.erreur = 'Remplissez tous les champs.'; return; }
    this.chargement = true; this.erreur = '';
    this.auth.loginEnseignant({ email: this.email, motDePasse: this.motDePasse }).subscribe({
      next: () => this.redirigerParRole('ENSEIGNANT'),
      error: err => {
        this.erreur = err.error?.erreur || 'Email ou mot de passe incorrect.';
        this.chargement = false;
      }
    });
  }

  loginAdmin() {
    if (!this.emailAdmin || !this.motDePasseAdmin) { this.erreur = 'Remplissez tous les champs.'; return; }
    this.chargement = true; this.erreur = '';
    this.auth.loginAdmin({ email: this.emailAdmin, motDePasse: this.motDePasseAdmin }).subscribe({
      next: () => this.redirigerParRole('ADMIN'),
      error: err => {
        this.erreur = err.error?.erreur || 'Identifiants admin incorrects.';
        this.chargement = false;
      }
    });
  }

  private redirigerParRole(role: string) {
    const routes: Record<string, string> = {
      ETUDIANT: '/etudiant', ENSEIGNANT: '/enseignant', ADMIN: '/admin'
    };
    this.router.navigate([routes[role] || '/auth']);
  }
}
