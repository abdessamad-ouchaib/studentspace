/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  SERVICES Angular — Appels API REST vers Spring Boot         ║
 * ║  Auteur : Abdessamad Ouchaib                                 ║
 * ╚══════════════════════════════════════════════════════════════╝
 *
 * Concepts Angular utilisés :
 *   ✔ @Injectable          — service injectable
 *   ✔ HttpClient           — appels HTTP REST
 *   ✔ Observable<T>        — flux de données asynchrones (RxJS)
 *   ✔ localStorage         — persistance du token JWT
 *   ✔ HttpHeaders          — ajout du token Authorization
 */

import { Injectable }    from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable }    from 'rxjs';
import { tap }           from 'rxjs/operators';
import * as M            from './models';
const API = 'https://studentspace-7uge.onrender.com/api';
// ── Helper : headers avec JWT ─────────────────────────────────────────────────
function authHeaders(): HttpHeaders {
  const token = localStorage.getItem('token');
  return new HttpHeaders({
    'Content-Type':  'application/json',
    'Authorization': token ? `Bearer ${token}` : ''
  });
}

// ── AUTH SERVICE ──────────────────────────────────────────────────────────────
@Injectable({ providedIn: 'root' })
export class AuthService {

  constructor(private http: HttpClient) {}

  /** Login étudiant par numéro APOGEE */
  loginEtudiant(req: M.LoginEtudiantRequest): Observable<M.AuthResponse> {
    return this.http.post<M.AuthResponse>(`${API}/auth/etudiant`, req).pipe(
      tap(res => this.sauvegarderSession(res))
    );
  }

  /** Login enseignant par email + mot de passe */
  loginEnseignant(req: M.LoginEnseignantRequest): Observable<M.AuthResponse> {
    return this.http.post<M.AuthResponse>(`${API}/auth/enseignant`, req).pipe(
      tap(res => this.sauvegarderSession(res))
    );
  }

  /** Login admin */
  loginAdmin(req: M.LoginEnseignantRequest): Observable<M.AuthResponse> {
    return this.http.post<M.AuthResponse>(`${API}/auth/admin`, req).pipe(
      tap(res => this.sauvegarderSession(res))
    );
  }

  /** Sauvegarde le token et les infos utilisateur dans localStorage */
  private sauvegarderSession(res: M.AuthResponse): void {
    localStorage.setItem('token',  res.token);
    localStorage.setItem('role',   res.role);
    localStorage.setItem('userId', String(res.userId));
    if (res.nom)         localStorage.setItem('nom',    res.nom);
    if (res.prenom)      localStorage.setItem('prenom', res.prenom);
    if (res.numeroApogee) localStorage.setItem('apogee', res.numeroApogee);
  }

  /** Déconnexion — efface la session */
  deconnecter(): void {
    localStorage.clear();
    window.location.href = '/auth';
  }

  estConnecte():   boolean { return !!localStorage.getItem('token'); }
  getRole():       string  { return localStorage.getItem('role') || ''; }
  getUserId():     number  { return Number(localStorage.getItem('userId')); }
  getNomComplet(): string  {
    const p = localStorage.getItem('prenom') || '';
    const n = localStorage.getItem('nom') || '';
    return `${p} ${n}`.trim();
  }
}


// ── ETUDIANT SERVICE ──────────────────────────────────────────────────────────
@Injectable({ providedIn: 'root' })
export class EtudiantService {

  constructor(private http: HttpClient) {}

  listerTous(): Observable<M.EtudiantResponse[]> {
    return this.http.get<M.EtudiantResponse[]>(`${API}/etudiants`,
      { headers: authHeaders() });
  }

  trouverParId(id: number): Observable<M.EtudiantResponse> {
    return this.http.get<M.EtudiantResponse>(`${API}/etudiants/${id}`,
      { headers: authHeaders() });
  }

  trouverParApogee(apogee: string): Observable<M.EtudiantResponse> {
    return this.http.get<M.EtudiantResponse>(`${API}/etudiants/apogee/${apogee}`,
      { headers: authHeaders() });
  }

  creer(req: M.EtudiantRequest): Observable<M.EtudiantResponse> {
    return this.http.post<M.EtudiantResponse>(`${API}/etudiants`, req,
      { headers: authHeaders() });
  }

  modifier(id: number, req: M.EtudiantRequest): Observable<M.EtudiantResponse> {
    return this.http.put<M.EtudiantResponse>(`${API}/etudiants/${id}`, req,
      { headers: authHeaders() });
  }

  supprimer(id: number): Observable<void> {
    return this.http.delete<void>(`${API}/etudiants/${id}`,
      { headers: authHeaders() });
  }
}


// ── ENSEIGNANT SERVICE ────────────────────────────────────────────────────────
@Injectable({ providedIn: 'root' })
export class EnseignantService {

  constructor(private http: HttpClient) {}

  listerTous(): Observable<M.EnseignantResponse[]> {
    return this.http.get<M.EnseignantResponse[]>(`${API}/enseignants`,
      { headers: authHeaders() });
  }

  trouverParId(id: number): Observable<M.EnseignantResponse> {
    return this.http.get<M.EnseignantResponse>(`${API}/enseignants/${id}`,
      { headers: authHeaders() });
  }

  creer(req: M.EnseignantRequest): Observable<M.EnseignantResponse> {
    return this.http.post<M.EnseignantResponse>(`${API}/enseignants`, req,
      { headers: authHeaders() });
  }

  modifier(id: number, req: M.EnseignantRequest): Observable<M.EnseignantResponse> {
    return this.http.put<M.EnseignantResponse>(`${API}/enseignants/${id}`, req,
      { headers: authHeaders() });
  }

  supprimer(id: number): Observable<void> {
    return this.http.delete<void>(`${API}/enseignants/${id}`,
      { headers: authHeaders() });
  }
}


// ── FILIERE SERVICE ───────────────────────────────────────────────────────────
@Injectable({ providedIn: 'root' })
export class FiliereService {

  constructor(private http: HttpClient) {}

  listerToutes(): Observable<M.FiliereResponse[]> {
    return this.http.get<M.FiliereResponse[]>(`${API}/filieres`,
      { headers: authHeaders() });
  }

  trouverParId(id: number): Observable<M.FiliereResponse> {
    return this.http.get<M.FiliereResponse>(`${API}/filieres/${id}`,
      { headers: authHeaders() });
  }

  creer(req: M.FiliereRequest): Observable<M.FiliereResponse> {
    return this.http.post<M.FiliereResponse>(`${API}/filieres`, req,
      { headers: authHeaders() });
  }

  modifier(id: number, req: M.FiliereRequest): Observable<M.FiliereResponse> {
    return this.http.put<M.FiliereResponse>(`${API}/filieres/${id}`, req,
      { headers: authHeaders() });
  }

  supprimer(id: number): Observable<void> {
    return this.http.delete<void>(`${API}/filieres/${id}`,
      { headers: authHeaders() });
  }
}


// ── MODULE SERVICE ────────────────────────────────────────────────────────────
@Injectable({ providedIn: 'root' })
export class ModuleService {

  constructor(private http: HttpClient) {}

  listerTous(): Observable<M.ModuleResponse[]> {
    return this.http.get<M.ModuleResponse[]>(`${API}/modules`,
      { headers: authHeaders() });
  }

  parEnseignant(id: number): Observable<M.ModuleResponse[]> {
    return this.http.get<M.ModuleResponse[]>(`${API}/modules/enseignant/${id}`,
      { headers: authHeaders() });
  }

  parFiliere(id: number): Observable<M.ModuleResponse[]> {
    return this.http.get<M.ModuleResponse[]>(`${API}/modules/filiere/${id}`,
      { headers: authHeaders() });
  }

  creer(req: M.ModuleRequest): Observable<M.ModuleResponse> {
    return this.http.post<M.ModuleResponse>(`${API}/modules`, req,
      { headers: authHeaders() });
  }

  modifier(id: number, req: M.ModuleRequest): Observable<M.ModuleResponse> {
    return this.http.put<M.ModuleResponse>(`${API}/modules/${id}`, req,
      { headers: authHeaders() });
  }

  supprimer(id: number): Observable<void> {
    return this.http.delete<void>(`${API}/modules/${id}`,
      { headers: authHeaders() });
  }
}


// ── EXAMEN SERVICE ────────────────────────────────────────────────────────────
@Injectable({ providedIn: 'root' })
export class ExamenService {

  constructor(private http: HttpClient) {}

  listerTous(): Observable<M.ExamenResponse[]> {
    return this.http.get<M.ExamenResponse[]>(`${API}/examens`,
      { headers: authHeaders() });
  }

  parModule(moduleId: number): Observable<M.ExamenResponse[]> {
    return this.http.get<M.ExamenResponse[]>(`${API}/examens/module/${moduleId}`,
      { headers: authHeaders() });
  }

  creer(req: M.ExamenRequest): Observable<M.ExamenResponse> {
    return this.http.post<M.ExamenResponse>(`${API}/examens`, req,
      { headers: authHeaders() });
  }

  modifier(id: number, req: M.ExamenRequest): Observable<M.ExamenResponse> {
    return this.http.put<M.ExamenResponse>(`${API}/examens/${id}`, req,
      { headers: authHeaders() });
  }

  supprimer(id: number): Observable<void> {
    return this.http.delete<void>(`${API}/examens/${id}`,
      { headers: authHeaders() });
  }
}


// ── MESSAGE SERVICE ───────────────────────────────────────────────────────────
@Injectable({ providedIn: 'root' })
export class MessageService {

  constructor(private http: HttpClient) {}

  parModule(moduleId: number): Observable<M.MessageResponse[]> {
    return this.http.get<M.MessageResponse[]>(`${API}/messages/module/${moduleId}`,
      { headers: authHeaders() });
  }

  envoyer(req: M.MessageRequest): Observable<M.MessageResponse> {
    const userId = localStorage.getItem('userId') || '0';
    const headers = authHeaders().set('X-User-Id', userId);
    return this.http.post<M.MessageResponse>(`${API}/messages`, req, { headers });
  }
}


// ── INSCRIPTION SERVICE ───────────────────────────────────────────────────────
@Injectable({ providedIn: 'root' })
export class InscriptionService {

  constructor(private http: HttpClient) {}

  listerToutes(): Observable<M.InscriptionResponse[]> {
    return this.http.get<M.InscriptionResponse[]>(`${API}/inscriptions`,
      { headers: authHeaders() });
  }

  parEtudiant(id: number): Observable<M.InscriptionResponse[]> {
    return this.http.get<M.InscriptionResponse[]>(`${API}/inscriptions/etudiant/${id}`,
      { headers: authHeaders() });
  }

  inscrire(req: M.InscriptionRequest): Observable<M.InscriptionResponse> {
    return this.http.post<M.InscriptionResponse>(`${API}/inscriptions`, req,
      { headers: authHeaders() });
  }

  desinscrire(id: number): Observable<void> {
    return this.http.delete<void>(`${API}/inscriptions/${id}`,
      { headers: authHeaders() });
  }
}
