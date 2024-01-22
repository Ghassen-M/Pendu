import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class Dictionnaire{
    
    public boolean existenceFichier(String nomDictionnaire){
        Path filePath = FileSystems.getDefault().getPath("assets/"+nomDictionnaire+".txt");
        return Files.exists(filePath);
    }

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

    public void afficherArbre(Arbre a, String prefixe, boolean feuille) {
        System.out.println(prefixe + (feuille ? "└── " : "├── ") + a.getValeur());

        for (int i = 0; i < a.getFils().size(); i++)
            afficherArbre(a.getFils().get(i), prefixe + (feuille ? "    " : "│   "), i == a.getFils().size() - 1);
    }
    public void afficherArbreBinaire(ABR a, String prefixe, boolean feuille) {
        String childIndicator = (a.getFG() != null) ? "FG" : ((a.getFD() != null) ? "FD" : "");
        System.out.println(prefixe + (feuille ? "└── " : "├── ") + a.getValeur() + " (" + childIndicator + ")");
    
        if (a.getFG() != null)
            afficherArbreBinaire(a.getFG(), prefixe + (feuille ? "    " : "│   "), a.getFD() == null);
    
        if (a.getFD() != null)
            afficherArbreBinaire(a.getFD(), prefixe + (feuille ? "    " : "│   "), a.getFD().getFD() == null);
    }

    public ABR arbreBinaire(Arbre arbreNaire){
        ABR a=new ABR(arbreNaire.getValeur());
        Queue<Arbre> file = new LinkedList<>();
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
            ABR noeudBinaire=new ABR(file.peek().getValeur());
            a.ajoutFD(noeudBinaire);
            parcoursFG(a.getFD(), file);
            file.poll();
            parcoursFD(a.getFD(), file);
        }
    }

    public boolean motExiste(String mot, ABR a){

        if ((mot.charAt(0) != a.getValeur()) && (a.getFD() == null)) // cas: lettre non trouvée dans le reste du chemin de l'arbre
            return false;
        else if ((mot.charAt(0) == a.getValeur()) && (mot.length() == 1) && (a.getFG().getValeur()=='0')) // cas: dernière lettre trouvée et ABR accepte le mot (FG=0)
            return true;
        else if ((mot.charAt(0) == a.getValeur()) && (mot.length() == 1) && (a.getFG().getValeur()!='0')) // cas: dernière lettre trouvée mais ABR prévoit d'autre lettres (FG!=0)
            return false;
        else if (mot.charAt(0) == a.getValeur()) // cas: lettre trouvée dans l'arbre
            return motExiste(mot.substring(1), a.getFG());
        else   //cas:lettre non trouvée dans le noeud
            return motExiste(mot, a.getFD());
        
    }

    public void ajoutMOTABR(String mot, ABR a, boolean initialise){
        if (mot.length()==0)
        {
            ABR n=a;
            ABR noeudIntermediaire=new ABR('0');
            if (a.getFG()!=null)
                noeudIntermediaire.ajoutFD(n.getFG());
            a.ajoutFG(noeudIntermediaire);                 
        }
        else if ((mot.charAt(0)!=a.getValeur()) && (a.getFD()!=null))
        {   
            ajoutMOTABR(mot, a.getFD(),initialise);
        }
        else if ((mot.charAt(0)!=a.getValeur()) && (a.getFD()==null))
            {
                ABR n=new ABR(mot.charAt(0));
                if (initialise==false)
                {
                    a.ajoutFD(n);
                    ajoutMOTABR(mot.substring(1), a.getFD(), true);
                }
                else 
                {
                    a.ajoutFG(n);
                    ajoutMOTABR(mot.substring(1), a.getFG(), true);
                }
            }
        else if ((mot.charAt(0)==a.getValeur()) && ((a.getFG().getValeur()!='0')|| (a.getFG()!=null)))
            {
                ajoutMOTABR(mot.substring(1), a.getFG(),initialise);
            }

        else if ((mot.charAt(0)==a.getValeur()) && ((a.getFG().getValeur()=='0')|| (a.getFG()==null)))
            {
                if (mot.length()>1)
                {
                    ABR n=new ABR(mot.charAt(1));
                    a.ajoutFD(n);
                    ajoutMOTABR(mot.substring(1), a.getFG(),initialise);
                }
                else 
                    ajoutMOTABR(mot.substring(1), a,initialise);
            }
    }

    public void ajoutMotFichier(String nomDictionnaire, String mot){
        String path = "assets/"+nomDictionnaire+".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
                writer.newLine(); // Add a new line before appending the new word
                writer.write(mot);
                System.out.println("Le mot a été ajouté au dictionnaire.");
            } catch (IOException e) {
                // Handle file writing exceptions
                e.printStackTrace();
            }           
    }
    public void suppressionMotFichier(String nomDictionnaire, String mot){
            List<String> lines = new ArrayList<>();

            // Read the content of the file and exclude the specified word
            String path = "assets/"+nomDictionnaire+".txt";
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.contains(mot)) {
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
}
