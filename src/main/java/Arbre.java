import java.util.ArrayList;
import java.util.List;

class Arbre {

    private char valeur;
    private List<Arbre> fils;

    public Arbre(char valeur) {
        this.valeur = valeur;
        this.fils = new ArrayList<>();
    }

    public void ajoutFils(Arbre noeud) {
        this.fils.add(noeud);
    }

    public void setFils(List<Arbre> fils) {
        this.fils = fils;
    }

    public List<Arbre> getFils() {
        return fils;
    }

    public char getValeur() {
        return valeur;
    }

    public void setValeur(char valeur) {
        this.valeur = valeur;
    }

}