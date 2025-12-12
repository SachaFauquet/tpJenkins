package org.example;

/**
 * Application principale de calcul de factorielle
 * TP Jenkins - Automatisation
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Application Factorielle ===");
        System.out.println("Version: 1.0");

        // Exemples de calculs de factorielles
        for (int i = 0; i <= 10; i++) {
            try {
                long resultat = Factorielle.calculer(i);
                System.out.println("Factorielle de " + i + " = " + resultat);
            } catch (IllegalArgumentException e) {
                System.err.println("Erreur pour " + i + ": " + e.getMessage());
            }
        }

        System.out.println("\n=== Tests de validation ===");
        System.out.println("Factorielle(5) = " + Factorielle.calculer(5) + " (attendu: 120)");
        System.out.println("Factorielle(0) = " + Factorielle.calculer(0) + " (attendu: 1)");
        System.out.println("\nApplication terminée avec succès!");
    }
}