import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

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
            a.ajoutFils(new Arbre('0'));
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
    public void printTreeBinaire(ABR a, String prefix, boolean isTail) {
        String childIndicator = (a.getFG() != null) ? "FG" : ((a.getFD() != null) ? "FD" : "");
        System.out.println(prefix + (isTail ? "└── " : "├── ") + a.getValeur() + " (" + childIndicator + ")");
    
        if (a.getFG() != null) {
            printTreeBinaire(a.getFG(), prefix + (isTail ? "    " : "│   "), a.getFD() == null);
        }
    
        if (a.getFD() != null) {
            printTreeBinaire(a.getFD(), prefix + (isTail ? "    " : "│   "), a.getFD().getFD() == null);
        }
    }

    public ABR arbreBinaire(String nomDictionnaire){
        Arbre aN=arbreNAire(nomDictionnaire);
        ABR a=new ABR(aN.getValeur());
        Queue<Arbre> file = new LinkedList<>();
        file.offer(aN);
        parcoursFG(a, file);
        a=a.getFG();
        return a;
    }
    public void parcoursFG(ABR a, Queue<Arbre> file){
        if (!file.isEmpty()){
            Arbre parent=file.poll();

            parcoursFD(a, file);

            for (Arbre fils : parent.getFils()){
                if (fils!=null){
                    file.offer(fils);
                }
            }

            for (Arbre fils : parent.getFils()){  //Pour tester qu'il ne s'sagit pas d'une feuille (et donc ne pas lui ajouter un FG car sa valeur est nulle)
                if (fils!=null){
                    a.ajoutFG(new ABR(file.peek().getValeur()));
                    break;
                }
            }            
            parcoursFG(a.getFG(), file);
        }
        
    }
    public void parcoursFD(ABR a, Queue<Arbre> file){
        if (!file.isEmpty()){
            ABR noeudBinaire=new ABR(file.peek().getValeur());
            a.ajoutFD(noeudBinaire);
            parcoursFG(a.getFD(), file);
            file.poll();
            parcoursFD(a.getFD(), file);
        }
    }
}
