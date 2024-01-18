
class ABR {

    private char valeur;

    private ABR filsDroit;
    private ABR filsGauche;

    public ABR(char valeur) {
        this.valeur = valeur;
    }

    public void ajoutFD(ABR noeud) {
        this.filsDroit=noeud;
    }
    public void ajoutFG(ABR noeud) {
        this.filsGauche=noeud;
    }


    public ABR getFD() {
        return filsDroit;
    }

    public ABR getFG() {
        return filsGauche;
    }

    public char getValeur() {
        return valeur;
    }

    public void setValeur(char valeur) {
        this.valeur = valeur;
    }

}