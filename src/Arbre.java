import java.util.ArrayList;
import java.util.List;

class Arbre {
    char lettre;
    List<Arbre> fils;

    public Arbre(char lettre) {
        this.lettre = lettre;
        this.fils = new ArrayList<>();
    }

    public void ajoutNoeud(Arbre noeud) {
        this.fils.add(noeud);
    }
}