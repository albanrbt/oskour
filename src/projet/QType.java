package projet;

import java.io.Serializable;

public interface QType extends Serializable {
    void afficher();
    boolean check(String answer);
}
