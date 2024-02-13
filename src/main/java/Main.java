import java.util.InputMismatchException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Dictionnaire dict;
    private static int difficulty=0;
    private static String word="";
    //Chargement de l'interface graphique du jeu
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Game gameController = new Game();
        loader.setController(gameController);
        gameController.setNomDictionnaire(dict);
        gameController.setDifficulty(difficulty);
        gameController.setWord(word);

        Parent root = loader.load();
        Scene scene = new Scene(root);

        // Set onCloseRequest handler for the primary stage
        primaryStage.setOnCloseRequest(event -> {
            // Handle the close event
            // You can perform any necessary cleanup or save operations here
            // Then exit the application
            System.exit(0);
        });
        primaryStage.setScene(scene);
        primaryStage.show();

        
    }

    public static void main(String[] args) {

        //Exécution du programme dans la console
        while (true) {
            //Insertion du dictionnaire
            System.out.println("Avant de déclencher le programme, entrer le nom du dictionnaire (sans extension):");
            Scanner scanner = new Scanner(System.in);
            String nomDictionnaire = scanner.nextLine();
            dict = new Dictionnaire(nomDictionnaire);
            //Contrôle de saisie du nom du dictionnaire
            while (!dict.existenceFichier()) {
                System.out.println("Ce fichier n'existe pas! Ecrire un nom du fichier valide!");
                nomDictionnaire = scanner.nextLine();
                dict.setNomDictionnaire(nomDictionnaire);
            }
            //Affichage du menu
            int choix = -1;
            System.out.println("\nBienvenue dans le menu! Veuiller choisir une de ces options ci-dessous:");
            while (true) {
                System.out.println("======================================================================");
                System.out.println("1) Visualiser le dictionnaire (arbre N-aire)");
                System.out.println("2) Visualiser le dictionnaire (arbre binaire)");
                System.out.println("3) Ajouter un mot");
                System.out.println("4) Supprimer un mot");
                System.out.println("5) Trouver un mot");
                System.out.println("6) Jouer!");
                System.out.println("0) Choisir un autre dictionnaire (quitter le menu)");
                //Contrôle de saisie du choix
                do {
                    try {
                        choix = scanner.nextInt();

                        if ((choix != 0) && (choix != 1) && (choix != 2) && (choix != 3) && (choix != 4) && (choix != 5)
                                && (choix != 6))
                            System.out.println("Saisir le nombre correspondant s'il vous plaît!");
                        
                    } //Si la valeur inséré du choix n'est pas numérique, on conserve la boucle sans sortir du programme
                    catch (InputMismatchException e) { 
                        System.out.println("Il faut saisir un nombre!");
                        scanner.next();
                        choix = scanner.nextInt();
                    }
                } while ((choix != 0) && (choix != 1) && (choix != 2) && (choix != 3) && (choix != 4) && (choix != 5) && (choix != 6));
                 //Quitter le menu si choix=0
                if (choix == 0)
                    break;
                //Création d'arbre N-aire à partir du fichier dictionnaire
                Arbre aN = dict.arbreNAire();
                //Création d'arbre binaire à partir d'arbre N-aire
                ABR a = dict.arbreBinaire(aN);
                //La variable mot est utile pour quelques manipulations dans le menu
                String mot = "";
                switch (choix) {
                    case 1:
                        dict.afficherArbre(aN, "", false);
                        break;
                    case 2:
                        dict.afficherArbreBinaire(a, "", false);
                        break;
                    case 3:
                        System.out.println("Donner le mot à ajouter:");
                        scanner.nextLine();
                        mot = scanner.nextLine();
                        if (!dict.motExiste(mot, a))
                            dict.ajoutMotFichier(mot);
                        else 
                            System.out.println("Ce mot existe dans le fichier!");     
                        break;
                    case 4:
                        if (a==null)
                            System.out.println("Rien à supprimer car votre dictionnaire est vide!");
                        else
                        {
                            System.out.println("Donner le mot à supprimer:");
                            scanner.nextLine();
                            mot = scanner.nextLine();
                            if (dict.motExiste(mot, a))
                                dict.suppressionMotFichier(mot);
                            else    
                                System.out.println("Ce mot n'existe pas!");
                        }
                        break;
                    case 5:
                        if (a==null)
                            System.out.println("Rien à afficher car votre dictionnaire est vide!");
                        else
                        {
                            System.out.println("Donner le mot à trouver:");
                            scanner.nextLine();
                            mot = scanner.nextLine();
                            if (dict.motExiste(mot, a))
                                System.out.println("Ce mot existe!");
                            else
                                System.out.println("Ce mot n'existe pas!");
                        }
                        break;
                    case 6:
                        if (a==null) 
                            System.out.println("Votre dictionnaire est vide! On ne peut pas démarrer la partie!");
                        else{
                        System.out.println(
                                " .----------------.  .----------------.  .-----------------. .----------------.  .----------------.   \r\n"
                                        + //
                                        "| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |  \r\n"
                                        + //
                                        "| |   ______     | || |  _________   | || | ____  _____  | || |  ________    | || | _____  _____ | |  \r\n"
                                        + //
                                        "| |  |_   __ \\   | || | |_   ___  |  | || ||_   \\|_   _| | || | |_   ___ `.  | || ||_   _||_   _|| |  \r\n"
                                        + //
                                        "| |    | |__) |  | || |   | |_  \\_|  | || |  |   \\ | |   | || |   | |   `. \\ | || |  | |    | |  | |  \r\n"
                                        + //
                                        "| |    |  ___/   | || |   |  _|  _   | || |  | |\\ \\| |   | || |   | |    | | | || |  | '    ' |  | |  \r\n"
                                        + //
                                        "| |   _| |_      | || |  _| |___/ |  | || | _| |_\\   |_  | || |  _| |___.' / | || |   \\ `--' /   | |  \r\n"
                                        + //
                                        "| |  |_____|     | || | |_________|  | || ||_____|\\____| | || | |________.'  | || |    `.__.'    | |  \r\n"
                                        + //
                                        "| |              | || |              | || |              | || |              | || |              | |  \r\n"
                                        + //
                                        "| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |  \r\n"
                                        + //
                                        " '----------------'  '----------------'  '----------------'  '----------------'  '----------------'   ");
                        System.out.println(
                                "Bienvenue dans le jeu du pendu! Veuillez choisir un mode de jeu parmi les suivants:\n1)Solo\n2)1vs1");
                        int mode=0;
                        //Séléction d'un mode du jeu (Solo;1v1)
                        do {
                            try {
                                mode = scanner.nextInt();
                                scanner.nextLine();
                                if ((mode != 1) && (mode != 2))
                                    System.out.println("Saisir le nombre correspondant s'il vous plaît!");
                            } catch (InputMismatchException e) {
                                System.out.println("Il faut saisir un nombre!");
                                scanner.next();
                            }
                        } while ((mode != 1) && (mode != 2));
                        //Dans le mode 1v1, un joueur insère un mot (qui doit exister dans le dictionnaire)
                        //à deviner par l'autre joueur
                        if(mode == 2){
                            Boolean wordExists=false;
                            System.out.println("Veuillez saisir un mot parmi les mots dans le dictionnaire");
                            do {

                                try {
                                    word = scanner.nextLine();
                                    wordExists = dict.motExiste(word, a);
                                    if (!wordExists)
                                        System.out.println("Saisir un mot qui existe dans le dictionnaire s'il vous plaît!");
                                } catch (InputMismatchException e) {
                                    System.out.println("Il faut saisir un mot!");
                                    scanner.next();
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            } while (!wordExists);
                            
                        }
                        System.out.println(
                            "Veuillez choisir un niveau de difficulté parmi les suivants:\n1)facile\n2)moyenne\n3)difficile");
                        do {
                            try {
                                difficulty = scanner.nextInt();

                                if ((difficulty != 1) && (difficulty != 2) && (difficulty != 3))
                                    System.out.println("Saisir le nombre correspondant s'il vous plaît!");
                            } catch (InputMismatchException e) {
                                System.out.println("Il faut saisir un nombre!");
                                scanner.next();
                            }
                        } while ((difficulty != 1) && (difficulty != 2) && (difficulty != 3));
                        
                        //Démarrage de l'interface graphique
                        launch(args);
                        break;
                        }
                    default:
                        break;
                }
            }
        }
    }

}
