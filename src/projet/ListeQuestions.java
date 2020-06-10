package projet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class ListeQuestions   implements Serializable {
    private LinkedList<Question<? extends QType>> liste = null;
    private int indicateur = 0;

    public ListeQuestions(LinkedList<Question<? extends QType>> liste) {
        if (liste == null) {
            throw new NullPointerException("La liste ne peut pas \u00eatre null !");
        }
        this.liste = liste;
        if (liste.isEmpty()) {
            this.indicateur = -1;
        } else {
            this.indicateur = 0;
        }
    }


    public void afficherListe() {
        System.out.println("Affichage des questions :");
        liste.forEach(Question::afficher);
    }

    public void ajouterQuestion(Question<? extends QType> question) {
        if (indicateur == -1) {
            indicateur = 0;
        }
        this.liste.add(question);
    }

    public void supprimerQuestion() {
        Scanner sc = new Scanner(System.in);
        boolean input = false;
        do {
            System.out.print("Num\u00e9ro de la question \u00e0 supprimer :");
            int numero = sc.nextInt() - 1;
            if (numero > liste.size() || numero < 0) {
                System.out.println("Erreur, saisissez un nombre correct.");
            } else {
                this.liste.remove(numero);
                input = true;
            }
        } while (!input);
    }

    public Question<? extends QType> selectionnerQuestion(int niveau) {
        LinkedList<Question<? extends QType>> q = new LinkedList<>();
        for(int i=0; i<liste.size();i++){
            if ((liste.get(i).getNiveau()) == niveau){
                q.add(liste.get(i));
            }
        }
        int  x = (int) (Math.random() * 100) % q.size();
        return  q.get(x);
    }

}
