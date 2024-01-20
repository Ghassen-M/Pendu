import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Dictionnaire{
    private String nomDictionnaire;

    public Dictionnaire(String nomDictionnaire){
        this.nomDictionnaire = nomDictionnaire;
    }
    
    public void setNomDictionnaire(String nom){
        nomDictionnaire=nom;
    }
    /* Checks whether a file with the given dictionary name exists in our assets folder */
    public boolean existenceFichier(){
        Path filePath = FileSystems.getDefault().getPath("assets/"+nomDictionnaire+".txt");
        return Files.exists(filePath);
    }


    /* Reads the contents of our dictionary into a list of strings */
    public List<String> fileToList() {
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

    public String selectRandomWord(){
        List<String> mots = fileToList();
        if (mots == null || mots.isEmpty()) {
            throw new IllegalArgumentException("Le dictionnaire est nul ou vide");
        }
        else{
            Random random = new Random();
            int randomIndex = random.nextInt(mots.size());
    
            return mots.get(randomIndex);
        }
    }
    /* N-ary tree creation */
    public Arbre arbreNAire(){
        List<String> mots=fileToList();

        Arbre a = new Arbre('0');

        for (String mot : mots){
            ajoutMot(a,mot,0);
        }
        return a;
    }

    /* Adding a word to the tree */
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
    /* Checking if a character exists in the tree */
    public boolean existe(char c, Arbre noeud){
        for (Arbre fils :noeud.getFils()){
            if (c==fils.getValeur())
                return true;
        }
        return false;
    }
    /* Converting N-ary tree to BST */
    public ABR arbreBinaire(Arbre arbreNaire){

        // creating a new binary search tree with the root value of the given N-ary tree
        ABR a=new ABR(arbreNaire.getValeur());

        // creating a queue to perform level-order traversal on the N-ary tree
        Queue<Arbre> file = new LinkedList<>();

        // add the root of the N-ary tree to the queue
        file.offer(arbreNaire);
        

        parcoursFG(a, file);
        a=a.getFG();
        return a;
    }

    public void parcoursFG(ABR a, Queue<Arbre> file){
        if (!file.isEmpty()){
            Arbre parent=file.poll();

            parcoursFD(a, file);

            for (Arbre fils : parent.getFils())
                if (fils!=null)
                    file.offer(fils);
            
            for (Arbre fils : parent.getFils()) //Pour tester qu'il ne s'sagit pas d'une feuille (et donc ne pas ajouter son FG dans l'ABR car sa valeur est nulle)
                if (fils!=null){
                    a.ajoutFG(new ABR(file.peek().getValeur()));
                    break;
                }
                       
            parcoursFG(a.getFG(), file);
        }
        
    }
    public void parcoursFD(ABR a, Queue<Arbre> file){
        if (!file.isEmpty()){
            char headValue = file.peek().getValeur();
            ABR noeudBinaire=new ABR(headValue);
            a.ajoutFD(noeudBinaire);
            parcoursFG(a.getFD(), file);
            file.poll();
            parcoursFD(a.getFD(), file);
        }
    }

    /* search the word from a dictionary that matches the string input */
    public boolean motExiste(String mot){
        String path = "assets/"+nomDictionnaire+".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(mot)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // Handle file reading exceptions
            e.printStackTrace();
        }

        return false; 
    }
    public boolean motExisteABR(String mot, ABR a){

        if ((mot.charAt(0) != a.getValeur()) && (a.getFD() == null)) // cas: lettre non trouvée dans le reste du chemin de l'arbre
            return false;
        else if ((mot.charAt(0) == a.getValeur()) && (mot.length() == 1) && (a.getFG().getValeur()=='0')) // cas: dernière lettre trouvée et ABR accepte le mot (FG=0)
            return true;
        else if ((mot.charAt(0) == a.getValeur()) && (mot.length() == 1) && (a.getFG().getValeur()!='0')) // cas: dernière lettre trouvée mais ABR prévoit d'autre lettres (FG!=0)
            return false;
        else if (mot.charAt(0) == a.getValeur()) // cas: lettre trouvée dans l'arbre
            return motExisteABR(mot.substring(1), a.getFG());
        else   //cas:lettre non trouvée dans le noeud
            return motExisteABR(mot, a.getFD());
        
    }

    public void ajoutMot(String mot){
        String path = "assets/"+nomDictionnaire+".txt";
        if (!motExiste(mot)){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
                writer.newLine(); // Add a new line before appending the new word
                writer.write(mot);
                System.out.println("Le mot a été ajouté au dictionnaire.");
            } catch (IOException e) {
                // Handle file writing exceptions
                e.printStackTrace();
            }           
        }
        else
            System.out.println("Ce mot existe dans le dictionnaire!");
    }
    /* delete the word from a dictionary that matches the string input */
    public void suppressionMot(String mot){
        if (motExiste(mot)){
            List<String> lines = new ArrayList<>();

            // Read the content of the file and exclude the specified word
            String path = "assets/"+nomDictionnaire+".txt";
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.equals(mot)) {
                        lines.add(line);
                    }
                }
            } catch (IOException e) {
                // Handle file reading exceptions
                e.printStackTrace();
            }
    
            // Write the updated content back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                for (int i = 0; i < lines.size(); i++) {
                    writer.write(lines.get(i));
                    if (i < lines.size() - 1) {
                        // Add a new line if the current line is not the last line
                        writer.newLine();
                    }
                }
                System.out.println("Le mot a été supprimé du dictionnaire.");
            } catch (IOException e) {
                // Handle file writing exceptions
                e.printStackTrace();
            }
        }
        else
            System.out.println("Ce mot n'existe pas dans le dictionnaire!");
    }

    public void afficherArbre(Arbre a, String prefixe, boolean feuille) {
        System.out.println(prefixe + (feuille ? "└── " : "├── ") + a.getValeur());

        for (int i = 0; i < a.getFils().size(); i++)
            afficherArbre(a.getFils().get(i), prefixe + (feuille ? "    " : "│   "), i == a.getFils().size() - 1);
    }

    /* fama machekel mtaa FG,FD fel affichage */
    public void afficherArbreBinaire(ABR a, String prefixe, boolean feuille) {
        String childIndicator = (a.getFG() != null) ? "FG" : ((a.getFD() != null) ? "FD" : "");
        System.out.println(prefixe + (feuille ? "└── " : "├── ") + a.getValeur() + " (" + childIndicator + ")");
    
        if (a.getFG() != null)
            afficherArbreBinaire(a.getFG(), prefixe + (feuille ? "    " : "│   "), a.getFD() == null);
    
        if (a.getFD() != null)
            afficherArbreBinaire(a.getFD(), prefixe + (feuille ? "    " : "│   "), a.getFD().getFD() == null);
    }
}
