package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe Factorielle
 * Tests JUnit 5 pour validation du pipeline Jenkins
 */
@DisplayName("Tests de la classe Factorielle")
public class FactorielleTest {

    @Nested
    @DisplayName("Tests de calculs basiques")
    class TestsBasiques {

        @Test
        @DisplayName("Factorielle de 0 doit être 1")
        public void testFactorielleZero() {
            assertEquals(1, Factorielle.calculer(0), "La factorielle de 0 doit être 1");
        }

        @Test
        @DisplayName("Factorielle de 1 doit être 1")
        public void testFactorielleUn() {
            assertEquals(1, Factorielle.calculer(1), "La factorielle de 1 doit être 1");
        }

        @Test
        @DisplayName("Factorielle de 5 doit être 120")
        public void testFactorielleCinq() {
            assertEquals(120, Factorielle.calculer(5), "La factorielle de 5 doit être 120");
        }

        @Test
        @DisplayName("Factorielle de 10 doit être 3628800")
        public void testFactorielleDix() {
            assertEquals(3628800, Factorielle.calculer(10), "La factorielle de 10 doit être 3628800");
        }

        @Test
        @DisplayName("Factorielle de 20 doit être calculable")
        public void testFactorielleVingt() {
            assertEquals(2432902008176640000L, Factorielle.calculer(20),
                "La factorielle de 20 doit être 2432902008176640000");
        }
    }

    @Nested
    @DisplayName("Tests de validation des entrées")
    class TestsValidation {

        @Test
        @DisplayName("Factorielle d'un nombre négatif doit lever une exception")
        public void testFactorielleNegatif() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                Factorielle.calculer(-1);
            });

            assertTrue(exception.getMessage().contains("positif"),
                "Le message d'erreur doit mentionner que le nombre doit être positif");
        }

        @Test
        @DisplayName("Factorielle d'un nombre trop grand doit lever une exception")
        public void testFactorielleTropGrand() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                Factorielle.calculer(21);
            });

            assertTrue(exception.getMessage().contains("trop grand"),
                "Le message d'erreur doit mentionner que le nombre est trop grand");
        }

        @Test
        @DisplayName("estValide doit retourner true pour les nombres valides")
        public void testEstValide() {
            assertTrue(Factorielle.estValide(0), "0 doit être valide");
            assertTrue(Factorielle.estValide(10), "10 doit être valide");
            assertTrue(Factorielle.estValide(20), "20 doit être valide");
        }

        @Test
        @DisplayName("estValide doit retourner false pour les nombres invalides")
        public void testEstInvalide() {
            assertFalse(Factorielle.estValide(-1), "-1 doit être invalide");
            assertFalse(Factorielle.estValide(21), "21 doit être invalide");
        }
    }

    @Nested
    @DisplayName("Tests de la version récursive")
    class TestsRecursif {

        @Test
        @DisplayName("Factorielle récursive de 5 doit être 120")
        public void testFactorielleRecursiveCinq() {
            assertEquals(120, Factorielle.calculerRecursif(5),
                "La factorielle récursive de 5 doit être 120");
        }

        @Test
        @DisplayName("Factorielle récursive doit donner le même résultat que l'itérative")
        public void testComparaisonIterativeRecursive() {
            for (int i = 0; i <= 15; i++) {
                assertEquals(Factorielle.calculer(i), Factorielle.calculerRecursif(i),
                    "Les deux méthodes doivent donner le même résultat pour " + i);
            }
        }

        @Test
        @DisplayName("Factorielle récursive d'un nombre négatif doit lever une exception")
        public void testFactorielleRecursiveNegatif() {
            assertThrows(IllegalArgumentException.class, () -> {
                Factorielle.calculerRecursif(-1);
            });
        }
    }

    @Nested
    @DisplayName("Tests de performance basiques")
    class TestsPerformance {

        @Test
        @DisplayName("Calcul de plusieurs factorielles doit être rapide")
        public void testPerformance() {
            long debut = System.currentTimeMillis();

            for (int i = 0; i <= 20; i++) {
                Factorielle.calculer(i);
            }

            long fin = System.currentTimeMillis();
            long duree = fin - debut;

            assertTrue(duree < 100, "Le calcul de 21 factorielles doit prendre moins de 100ms");
        }
    }
}

