/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  ENSEIGNANT SERVICE — manquant dans services.ts              ║
 * ║  Auteur : Abdessamad Ouchaib                                 ║
 * ╚══════════════════════════════════════════════════════════════╝
 * Ce fichier complète le EnseignantService déjà dans services.ts.
 * En production, regrouper dans services.ts.
 */

// NOTE : EnseignantService est déjà déclaré dans services.ts.
// Ce fichier documente le service manquant dans la classe AdminComponent.
// Pour corriger : dans admin.component.ts, importer EnseignantService depuis shared/services.ts
// Le service est identique à EtudiantService mais pour les enseignants.

export const ENSEIGNANT_SERVICE_NOTE = `
  EnseignantService est déclaré dans src/app/shared/services.ts
  à la ligne "export class EnseignantService".
  Il expose : listerTous(), trouverParId(), creer(), modifier(), supprimer()
`;
