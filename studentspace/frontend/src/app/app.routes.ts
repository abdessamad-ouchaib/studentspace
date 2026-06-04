/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  APP ROUTING & MODULE — StudentSpace                         ║
 * ║  Auteur : Abdessamad Ouchaib                                 ║
 * ╚══════════════════════════════════════════════════════════════╝
 */

import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { inject }               from '@angular/core';
import { AuthService }          from './shared/services';
import { Router }               from '@angular/router';

// ── Auth Guard ────────────────────────────────────────────────────────────────
export function authGuard(): boolean {
  const auth   = inject(AuthService);
  const router = inject(Router);
  if (auth.estConnecte()) return true;
  router.navigate(['/auth']);
  return false;
}

export function adminGuard(): boolean {
  const auth   = inject(AuthService);
  const router = inject(Router);
  if (auth.estConnecte() && auth.getRole() === 'ADMIN') return true;
  router.navigate(['/auth']);
  return false;
}

// ── Routes ────────────────────────────────────────────────────────────────────
export const routes: Routes = [
  // Redirection par défaut
  { path: '', redirectTo: '/auth', pathMatch: 'full' },

  // Authentification
  {
    path: 'auth',
    loadComponent: () =>
      import('./auth/auth.component').then(m => m.AuthComponent)
  },

  // Espace étudiant
  {
    path: 'etudiant',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./student/student.component').then(m => m.StudentComponent)
  },

  // Espace enseignant
  {
    path: 'enseignant',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./teacher/teacher.component').then(m => m.TeacherComponent)
  },

  // Espace admin
  {
    path: 'admin',
    canActivate: [adminGuard],
    loadComponent: () =>
      import('./admin/admin.component').then(m => m.AdminComponent)
  },

  // 404
  { path: '**', redirectTo: '/auth' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
