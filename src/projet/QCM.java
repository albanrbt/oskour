package projet;

import java.util.ArrayList;
import java.util.HashSet;

import static java.lang.Character.isDigit;
import static java.util.stream.IntStream.range;

public class QCM implements QType {
    private String texte;
    private ArrayList<String> reponses;
    private String bonneRep;

    public String gettexte() {
        return texte;
    }

    public ArrayList<String> getReponses() {
        return reponses;
    }

    public String getBonneRep() {
        return bonneRep;
    }

    public QCM(String texte, ArrayList<String> reponses, String bonneRep) {
        // Check l'input de l'user
        if (texte == null || reponses == null || bonneRep == null) {
            throw new NullPointerException("Votre input est null !");
        }
        // hashset peut pas avoir de duplicates donc on enleve tout duplicates possible
        reponses = new ArrayList<>(new HashSet<>(reponses));

        // les reponses ne doivent pas etre vides
        ArrayList<String> list = new ArrayList<>();
        for (String rep : reponses) {
            if (!rep.isBlank()) {
                list.add(rep);
            }
        }
        reponses = list;
        // pas de texte pour la question ou pas de reponses
        if (texte.isBlank() || bonneRep.isBlank()) {
            throw new IllegalArgumentException("Input pour le texte ou la bonne r\u00e9ponse est vide.");
        }
        // liste vide
        else if (reponses.isEmpty()) {
            throw new IllegalArgumentException("R\u00e9ponses vides.");
        }
        // pas de bonne rep
        else if (!reponses.contains(bonneRep)) {
            throw new IllegalArgumentException(String.format("Les r\u00e9ponses ne contiennent pas : '%s'.", bonneRep));
        }
        this.texte = texte;
        this.reponses = reponses;
        this.bonneRep = bonneRep;
    }

    // verifie si l'input est juste un nombre
    public boolean isNumber(String s)
    {
        return range(0, s.length()).allMatch(i -> isDigit(s.charAt(i)));
    }

    @Override
    public boolean check(String reponse) {
        if (isNumber(reponse)) {
            return Integer.parseInt(reponse) == reponses.indexOf(this.bonneRep);
        } else {
            return reponse.equals(this.bonneRep);
        }
    }

    @Override
    public void afficher() {
        System.out.println(texte + "\n" +
                "R\u00e9ponses :");
        for (String rep : reponses) {
            System.out.printf("%d) %s%n", reponses.indexOf(rep), rep);
        }
    }

}
