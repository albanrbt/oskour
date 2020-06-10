package projet;

import static java.lang.Character.isDigit;
import static java.util.stream.IntStream.range;

public class VF implements QType {
    private String texte;
    private boolean bonneRep;

    public String getTexte() {
        return texte;
    }

    public boolean isBonneRep() {
        return bonneRep;
    }

    public VF(String texte, boolean bonneRep) {
        // Check l'input de l'user, pas de texte pour la question ou pas de reponse
        if (texte != null && !texte.isBlank())
        {
            this.texte = texte;
            this.bonneRep = bonneRep;
        }
        else {
            throw new NullPointerException("Votre input est null !");
        }
    }

    // verifie si l'input est juste un nombre
    public boolean isNumber(String s)
    {
        return range(0, s.length()).allMatch(i -> isDigit(s.charAt(i)));
    }

    @Override
    public String toString() {
        return texte;
    }

    @Override
    public boolean check(String reponse) {
        if(isNumber(reponse)) {
            return Integer.parseInt(reponse) == 1 && this.bonneRep ||
                    Integer.parseInt(reponse) == 2 && !this.bonneRep;
        } else {
            return Boolean.parseBoolean(reponse) == this.bonneRep;
        }
    }
    @Override
    public void afficher() {
        System.out.println(toString());
        System.out.println("Votre r\u00e9ponse :");
        System.out.println("1) True");
        System.out.println("2) False");
    }
}
