import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

//Classe des fonctions de manipulation du dictionnaire
public class Dictionnaire{
    //Variable contenant le nom du dictionnaire du répertoire assets
    private String nomDictionnaire;
    //Constructeur dictionnaire
    public Dictionnaire(String nomDictionnaire){
        this.nomDictionnaire = nomDictionnaire;
    }
    //Setter
    public void setNomDictionnaire(String nom){
        nomDictionnaire=nom;
    }
    /* Vérifier l'existence du nom d'un fichier donné dans le répertoire assets */
    public boolean existenceFichier(){
        //Récupérer le chemin absolu du fichier dictionnaire à partir de l'emplacement actuel du code
        Path filePath = FileSystems.getDefault().getPath("assets/"+nomDictionnaire+".txt");
        //Vérifier l'éxistence du chemin dans le système
        return Files.exists(filePath);
    }


    /* Transformer le contenu du fichier dictionnaire en liste des chaînes (mots) */
    public List<String> fileToList() {
        //Chemin relatif du fichier
        String path = "assets/"+nomDictionnaire+".txt";
        //Variable de type List qui correspond au mots présents dans le fichier dictionnaire
        List<String> lines = new ArrayList<>();
        //Lire le contenu du fichier 
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            //Tant qu'on a une ligne non vide dans le fichier, c'est un mot! On l'ajoute dans notre variable
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {e.printStackTrace();}

        return lines;
    }
    
    /* Initialisation d'arbre N-aire */
    public Arbre arbreNAire(){
        List<String> mots=fileToList();
        //Création de la racine
        Arbre a = new Arbre('0');
        //Ajout des mots de la liste crée
        for (String mot : mots){
            ajoutMot(a,mot,0);
        }
        return a;
    }

    //Fonction d'ajout d'un mot dans l'arbre 
    //La variable i est un compteur de nombre des caractères du mot
    public void ajoutMot(Arbre a, String mot, int i){
        // Ajout d'un noeud feuille dont la valeur est '0' pour indiquer la fin du mot ajouté
        if (i==mot.length())
            a.ajoutFils(new Arbre('0'));
        //Si le mot n'est pas encore totalement ajoutée
        else {
            //Vérifier l'existence du caractère à ajouter parmi les racines du noeud courant 
            //Cas d'existence d'autre mot qui partage une séquence avec le mot à ajouter
            if (!existe(mot.charAt(i),a))
                //Ajouter le caractère s'il n'est pas présent
                a.ajoutFils(new Arbre(mot.charAt(i)));
                //Chercher le caractère ajouté pour ajouter le reste de la séquence de la même manière
                for (Arbre fils : a.getFils()) {
                    if (mot.charAt(i) == fils.getValeur()) {
                        ajoutMot(fils, mot, i + 1);
                        break;
                    }
                }
        }
    }
    //Vérifier l'existence du caractère à ajouter parmi les racines du noeud courant 
    public boolean existe(char c, Arbre noeud){
        for (Arbre fils :noeud.getFils()){
            if (c==fils.getValeur())
                return true;
        }
        return false;
    }
    /* Transformaiton d'arbre N-aire en arbre binaire */
    public ABR arbreBinaire(Arbre arbreNaire){
        // Création d'arbre binaire ayant la même valeur de la racine d'arbre N-aire '0'
        ABR a=new ABR(arbreNaire.getValeur());

        // Création d'une variabale file pour le parcours en largeur d'arbre N-aire
        Queue<Arbre> file = new LinkedList<>();

        // Offer: ajout de la racine dans la file
        file.offer(arbreNaire);
        
        //Création de l'arbre
        parcoursFG(a, file);
        //Changer la racine ancienne '0' en racine qui correspond au fils gauche ('0'->'c' dans l'exemple de l'énoncé)
        a=a.getFG();
        return a;
    }

    public void parcoursFG(ABR a, Queue<Arbre> file){
        // Traitement récursif tant que la file n'est pas vide
        if (!file.isEmpty()){
            //Extraire le noeud de la file
            Arbre parent=file.poll();
            //Remplir le fils droit de l'arbre binaire
            parcoursFD(a, file);
            //Remplir la file par les fils de la variable parent
            for (Arbre fils : parent.getFils())
                if (fils!=null)
                    file.offer(fils);
            for (Arbre fils : parent.getFils()) 
            //Pour tester qu'il ne s'sagit pas d'une feuille 
            //(et donc ne pas ajouter son FG dans l'ABR car sa valeur est nulle)  
            if (fils!=null){
                a.ajoutFG(new ABR(file.peek().getValeur()));
                break;
            }
            //Ajout le reste des fils dans la partie gauche            
            parcoursFG(a.getFG(), file);
        }
        
    }
    public void parcoursFD(ABR a, Queue<Arbre> file){
        if (!file.isEmpty()){
            //Création du noeud
            char headValue = file.peek().getValeur();
            ABR noeudBinaire=new ABR(headValue);
            a.ajoutFD(noeudBinaire);
            //Transformation du reste du chemin du noeud ajouté
            parcoursFG(a.getFD(), file);
            //Suppression de l'élément dont le noeud est ajouté
            file.poll();
            //Transformation de l'arbre N-aire
            parcoursFD(a.getFD(), file);
        }
    }

    /* Vérifier l'existence d'un mot donné dans l'arbre binaire */
    public boolean motExiste(String mot, ABR a){
        // cas: lettre non trouvée dans le reste du chemin de l'arbre
        if ((mot.charAt(0) != a.getValeur()) && (a.getFD() == null)) 
            return false;
        // cas: dernière lettre trouvée et ABR accepte le mot (FG=0)
        else if ((mot.charAt(0) == a.getValeur()) && (mot.length() == 1) && (a.getFG().getValeur()=='0')) 
            return true;
        // cas: dernière lettre trouvée mais ABR prévoit d'autre lettres (FG!=0)
        else if ((mot.charAt(0) == a.getValeur()) && (mot.length() == 1) && (a.getFG().getValeur()!='0')) 
            return false;
        // cas: lettre trouvée dans l'arbre
        else if (mot.charAt(0) == a.getValeur()) 
            return motExiste(mot.substring(1), a.getFG());
        //cas:lettre non trouvée dans le noeud
        else   
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
    //Fonction pour ajouter un mot donné dans le fichier dictionnaire
    public void ajoutMotFichier(String mot){
        //Chemin relatif du fichier
        String path = "assets/"+nomDictionnaire+".txt";
            //Lecture du fichier avec autorisation d'ajout
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
                // Ajouter une nouvelle ligne avant la lecture
                writer.newLine(); 
                //Ajouter le mot
                writer.write(mot);
                //Transmission du résultat à l'utilisateur
                System.out.println("Le mot a été ajouté au dictionnaire.");
            } catch (IOException e) {
                
                e.printStackTrace();
            }           
    }
    //Fonction pour supprimer un mot donné du fichier dictionnaire
    public void suppressionMotFichier(String mot){
        List<String> lines = new ArrayList<>();

        //Chemin relatif du fichier
        String path = "assets/"+nomDictionnaire+".txt";
        //Lecture du fichier
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            //Variable qui ne contient aucune valeur
            String line; 
            //Recherche dans l'ensemble des mots du fichier ligne par ligne
            while ((line = reader.readLine()) != null) {
                //Ajouter les lignes qui ne correspondent au mot dans la variable listes
                if (!line.contains(mot)) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Réecrire le contenu de la variable liste dans le fichier
        // La suppression classique laisse une ligne vide qui ne contient que le curseur
        // ce qui peut causer des problème si on lit le dictionnaire à nouveau
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < lines.size(); i++) {
                writer.write(lines.get(i));
                //Mettre la variable writer dans la ligne suivante jusqu'à atteindre la dernière ligne
                if (i < lines.size() - 1) {
                    writer.newLine();
                }
            }
            //Transmission du résultat à l'utilisateur
            System.out.println("Le mot a été supprimé du dictionnaire.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Fonction d'affichage d'arbre N-aire
    public void afficherArbre(Arbre a, String prefixe, boolean feuille) {
        //Instruction qui fixe le noeud dans son affichage
        //La variable prefixe est utile pour la tabulation
        System.out.println(prefixe + (feuille ? "└── " : "├── ") + a.getValeur());
        //Affichage des fils respectifs du noeud courant en descente jusqu'à atteindre les feuilles
        for (int i = 0; i < a.getFils().size(); i++)
            afficherArbre(a.getFils().get(i), prefixe + (feuille ? "    " : "│   "), i == a.getFils().size() - 1);
    }

    //Fonction d'affichage d'arbre binaire en utilisant un parcours préfixe
    public void afficherArbreBinaire(ABR a, String prefixe, boolean feuille) {
        //Variable qui indique la nature du noeud par rapport à son parent (fils gauche ou fils droit)
        String childIndicator = (a.getFG() != null) ? "FG" : ((a.getFD() != null) ? "FD" : "");
        //La variable prefixe est utile pour la tabulation
        System.out.println(prefixe + (feuille ? "└── " : "├── ") + a.getValeur() + " (" + childIndicator + ")");
        
        if (a.getFG() != null)
            afficherArbreBinaire(a.getFG(), prefixe + (feuille ? "    " : "│   "), a.getFD() == null);
    
        if (a.getFD() != null)
            afficherArbreBinaire(a.getFD(), prefixe + (feuille ? "    " : "│   "), a.getFD().getFD() == null);
    }

    //Fonction qui retourne un dictionnaire Map contenant l'occurrence de chaque lettre dans un mot donné
    public Map<Character, Integer> occurrenceMot(String mot) {
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (int i = 0; i < mot.length(); i++) {
            //Pour chaque lettre dans le mot, on incrémente par 1 si la lettre existe et on ajoute 0 si la lettre n'existe pas dans le dictionnaire
            frequencyMap.put(mot.charAt(i), frequencyMap.getOrDefault(mot.charAt(i), 0) + 1);
        }
        return frequencyMap;
    }
        //Fonction qui calcule la difficulté d'un mot selon une formule proposée par Ghassen & Hazem
        public float difficulty(String mot){
            mot=mot.toLowerCase();
            //Création du dictionnaire de "fréquence d'apparition des lettres dans la langue française" 
            LetterFrequency lf=new LetterFrequency();
            Map<Character, Float> mapLangue = lf.createLetterFrequencyMap();
            //Calcul d'occrrence de chaque lettre dans le mot
            Map<Character, Integer> mapMot = occurrenceMot(mot);
            float avg=0;
            for (Character c : mapMot.keySet()){
                //Si la lettre existe dans les deux variables mapLangue et mapMot 
                if (mapLangue.containsKey(c) && mapMot.get(c) != null) 
                {
                    //Normalisation d'intervalle des fréquence du mapLangue vers l'intervalle [1,2]
                    //pour avoir des valeurs proches lors de la division
                    float frequenceNormalise= (float) ((mapLangue.get(c) - 0.15) / (12.1 - 0.15)+1);
                    avg += (float) (1 / (frequenceNormalise * Math.pow(mapMot.get(c),2)));
                    
                }
                //Si la lettre n'existe pas dans les deux variables mapLangue et mapMot 
                else  
                    System.out.println("Key not found in one of the maps: " + c);
            }
            //Calcul de la moyenne pour toutes les lettres du mot
            avg/=mapMot.size();
            //multiplication par la longueur du mot
            return mot.length()*avg;
        }

    // Séléction d'un mot aléatoire selon le niveau de difficulté donné
    public String selectRandomWord(int level){
        //Cas d'un dictionnaire vide
        List<String> mots = fileToList();
        if (mots == null || mots.isEmpty()) {
            throw new IllegalArgumentException("Le dictionnaire est nul ou vide");
        }
        //Sélection d'un mot aléatoire qui correspond au critère de difficulté
        else{
            Random random = new Random();
            int randomIndex = random.nextInt(mots.size());
            String w= mots.get(randomIndex);
            switch (level) {
                case 1:
                    while (difficulty(w)>4.1)
                        {w= mots.get(randomIndex);}
                break;
                case 2:
                    while ((difficulty(w)<=4.1) || ((difficulty(w)>7)))
                        {w= mots.get(randomIndex);} 
                break;
                case 3:
                    while (difficulty(w)<=7)
                        {w= mots.get(randomIndex);} 
                break;
            }
            return w;
        }
    }
}
