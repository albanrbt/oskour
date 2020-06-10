package projet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Joueur implements Serializable {
    private  int numero = 100;
//    private char nom = 64; // '@' ascii
    private  String nom;
    private int score = 0;
    private String etat = "en attente";
    private ArrayList<String> listeEtats = new ArrayList<>(Arrays.asList("s\u00e9lectionn\u00e9", "gagnant", "super gagnant", "\u00e9limin\u00e9", "en attente"));

    public Joueur() {
        numero += 10;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public Joueur(String nomJ, int numJ) {
        numero += numJ;
        nom = nomJ;
    }



    public boolean saisir(Question Question) {
        System.out.println("Saisir la r\u00e9ponse : ");
        Scanner sc = new Scanner(System.in);
        String reponse = sc.nextLine();
        return Question.saisir(reponse);
    }

    public void afficher() {
        System.out.println("Joueur :");
        System.out.println("N° " + numero);
        System.out.println("Nom   : " + nom);
        System.out.println("Score : " + this.score);
        System.out.println("Etat  : " + this.etat);
    }

    public void changerEtat() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Saisissez l'\u00e9tat :");
        String etat = sc.nextLine();
        if (!listeEtats.contains(etat)) {
            throw new NullPointerException("Mauvais \u00e9tat !");
        }
        this.etat = etat;
    }

    public void setScore(int score) {
        this.score += score;
    }
}
