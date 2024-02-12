//Classe d'arbre binaire
class ABR {
    //Valeur d'un noeud dans l' arbre binaire
    private char valeur;
    //Fils droit
    private ABR filsDroit;
    //Fils gauche
    private ABR filsGauche;
    //Constructeur d'un noeud
    public ABR(char valeur) {
        this.valeur = valeur;
    }
    //Ajout d'un fils droit
    public void ajoutFD(ABR noeud) {
        this.filsDroit=noeud;
    }
    //Ajout d'un fils gauche
    public void ajoutFG(ABR noeud) {
        this.filsGauche=noeud;
    }

    //Getter Fils Droit
    public ABR getFD() {
        return filsDroit;
    }
    //Getter Fils Gauche
    public ABR getFG() {
        return filsGauche;
    }
    //Getter Valeur du noeud
    public char getValeur() {
        return valeur;
    }
    //Setter Valeur
    public void setValeur(char valeur) {
        this.valeur = valeur;
    }

}