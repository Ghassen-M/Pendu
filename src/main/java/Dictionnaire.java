import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dictionnaire{

    public List<String> fileToList(String nomDictionnaire) {
        String path = "assets/"+nomDictionnaire+".txt";
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {e.printStackTrace();}
        return lines;
    }

    public Arbre arbreNAire(String nomDictionnaire){
        List<String> mots=fileToList(nomDictionnaire);

        Arbre a = new Arbre('0');

        for (String mot : mots){
            ajoutMot(a,mot,0);
        }
        return a;
    }

    public void ajoutMot(Arbre a, String mot, int i){
        if (i==mot.length())
            a.ajoutFils(new Arbre('\0'));
        else {
            if (!existe(mot.charAt(i),a))
                a.ajoutFils(new Arbre(mot.charAt(i)));
                for (Arbre fils : a.getFils()) {
                    if (mot.charAt(i) == fils.getValeur()) {
                        ajoutMot(fils, mot, i + 1);
                        break;
                    }
                }
        }
    }

    public boolean existe(char c, Arbre noeud){
        for (Arbre fils :noeud.getFils()){
            if (c==fils.getValeur())
                return true;
        }
        return false;
    }

    public void printTree(Arbre a, String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + a.getValeur());

        for (int i = 0; i < a.getFils().size(); i++) {
            printTree(a.getFils().get(i), prefix + (isTail ? "    " : "│   "), i == a.getFils().size() - 1);
        }
    }
}
