package org.example;

/**
 * Classe pour le calcul de factorielle
 * Utilisée pour démonstration dans le pipeline Jenkins
 */
public class Factorielle {

    /**
     * Calcule la factorielle d'un nombre entier
     * @param n le nombre dont on veut calculer la factorielle
     * @return la factorielle de n
     * @throws IllegalArgumentException si n est négatif ou trop grand
     */
    public static long calculer(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Le nombre doit être positif ou zéro");
        }

        if (n > 20) {
            throw new IllegalArgumentException("Le nombre est trop grand (max 20 pour éviter l'overflow)");
        }

        if (n == 0 || n == 1) {
            return 1;
        }

        long resultat = 1;
        for (int i = 2; i <= n; i++) {
            resultat *= i;
        }

        return resultat;
    }

    /**
     * Vérifie si un nombre est valide pour le calcul de factorielle
     * @param n le nombre à vérifier
     * @return true si le nombre est valide, false sinon
     */
    public static boolean estValide(int n) {
        return n >= 0 && n <= 20;
    }

    /**
     * Calcule la factorielle de manière récursive
     * @param n le nombre dont on veut calculer la factorielle
     * @return la factorielle de n
     */
    public static long calculerRecursif(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Le nombre doit être positif ou zéro");
        }

        if (n > 20) {
            throw new IllegalArgumentException("Le nombre est trop grand (max 20 pour éviter l'overflow)");
        }

        if (n == 0 || n == 1) {
            return 1;
        }

        return n * calculerRecursif(n - 1);
    }
}

