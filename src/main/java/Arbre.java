import java.util.ArrayList;
import java.util.List;

//Classe d'arbre N-aire
class Arbre {
    //Valeur du noeud
    private char valeur;
    // Fils du noeud
    private List<Arbre> fils;
    //Constructeur d'un noeud
    public Arbre(char valeur) {
        this.valeur = valeur;
        this.fils = new ArrayList<>();
    }
    //Ajout du fils d'arbre
    public void ajoutFils(Arbre noeud) {
        this.fils.add(noeud);
    }
    //Setter (fils)
    public void setFils(List<Arbre> fils) {
        this.fils = fils;
    }
    //Getter (filq)
    public List<Arbre> getFils() {
        return fils;
    }
    //Getter (valeur)
    public char getValeur() {
        return valeur;
    }
    //Setter (valeur)
    public void setValeur(char valeur) {
        this.valeur = valeur;
    }

}