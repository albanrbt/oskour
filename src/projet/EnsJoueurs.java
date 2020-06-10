package projet;

import java.io.Serializable;
import java.util.Vector;

public class EnsJoueurs {
    private Vector<Joueur> vector = new Vector<Joueur>(20);

    public void creer(Vector<Joueur> vector) {
        this.vector = vector;
    }

    public void afficher() {
        System.out.println("Liste des joueurs :");
        vector.forEach(Joueur::afficher);
    }

    public Joueur selectionnerJoueur() {

        int random = (int) (Math.random() * 100) % vector.size();
        return vector.get(random);
    }

    public Joueur getJoueur(int index) {
        return vector.get(index);
    }

    public int size(){
        return vector.size();
    }

    public void remove(Joueur ToRemove){
        ToRemove.setEtat("\u00e9limin\u00e9");
        vector.remove(ToRemove);
    }
    public Vector<Joueur> getVector() {
        return vector;
    }
}
