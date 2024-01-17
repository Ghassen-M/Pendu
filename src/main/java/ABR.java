
class ABR {

    private char valeur;

    private ABR filsDroit;
    private ABR filsGauche;

    public ABR(char valeur) {
        this.valeur = valeur;
    }

    
    public ABR getFilsDroit() {
        return filsDroit;
    }

    public void setFilsDroit(ABR filsDroit) {
        this.filsDroit = filsDroit;
    }

    public ABR getFilsGauche() {
        return filsGauche;
    }

    public void setFilsGauche(ABR filsGauche) {
        this.filsGauche = filsGauche;
    }

    public char getValeur() {
        return valeur;
    }

    public void setValeur(char valeur) {
        this.valeur = valeur;
    }

}