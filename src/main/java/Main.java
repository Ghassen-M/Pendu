import java.util.InputMismatchException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Dictionnaire dict;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Game gameController = new Game();
        loader.setController(gameController);
        gameController.setNomDictionnaire(dict);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        while (true) {         

            System.out.println("Avant de déclencher le programme, entrer le nom du dictionnaire (sans extension):");

            Scanner scanner = new Scanner(System.in);
            String nomDictionnaire=scanner.nextLine();
            dict=new Dictionnaire(nomDictionnaire);
            while(!dict.existenceFichier()){
                System.out.println("Ce fichier n'existe pas! Ecrire un nom du fichier valide!");
                nomDictionnaire=scanner.nextLine();
                dict.setNomDictionnaire(nomDictionnaire);
            }
            int choix=0;
            System.out.println("\nBienvenue dans le menu! Veuiller choisir une de ces options ci-dessous:");
            System.out.println("======================================================================");
            while(true){
            System.out.println("1) Visualiser le dictionnaire (arbre N-aire)"); 
            System.out.println("2) Visualiser le dictionnaire (arbre binaire)");         
            System.out.println("3) Ajouter un mot");         
            System.out.println("4) Supprimer un mot");
            System.out.println("5) Trouver un mot");
            System.out.println("6) Jouer!");
            System.out.println("0) Choisir un autre dictionnaire (quitter le menu)"); 
            do{
                try {
                    choix=scanner.nextInt();                    
                
                    if ((choix!=0) && (choix!=1) && (choix!=2) &&(choix!=3) && (choix!=4) && (choix!=5) &&(choix!=6) &&(choix!=7))
                        System.out.println("Saisir le nombre correspondant s'il vous plaît!");
                }catch (InputMismatchException e) {System.out.println("Il faut saisir un nombre!");scanner.next();} 
            }while((choix!=0) && (choix!=1) && (choix!=2) &&(choix!=3) && (choix!=4) && (choix!=5) &&(choix!=7)); 

            Arbre aN=dict.arbreNAire();
            ABR a=dict.arbreBinaire(aN);
            String mot="";

            if (choix==0)
                break;
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
                    mot=scanner.nextLine();
                    dict.ajoutMOTABR(mot,a,false);
                    break;
                case 4:   
                    System.out.println("Donner le mot à supprimer:");
                    scanner.nextLine();
                    mot=scanner.nextLine();
                    dict.suppressionMotFichier(mot);
                    break;
                case 5:   
                    System.out.println("Donner le mot à trouver:");
                    scanner.nextLine();
                    mot=scanner.nextLine();
                    if (dict.motExiste(mot,a))
                        System.out.println("Ce mot existe!");
                    else 
                        System.out.println("Ce mot n'existe pas!");
                    break;
                case 6: 
                System.out.println(" .----------------.  .----------------.  .-----------------. .----------------.  .----------------.   \r\n" + //
                "| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |  \r\n" + //
                "| |   ______     | || |  _________   | || | ____  _____  | || |  ________    | || | _____  _____ | |  \r\n" + //
                "| |  |_   __ \\   | || | |_   ___  |  | || ||_   \\|_   _| | || | |_   ___ `.  | || ||_   _||_   _|| |  \r\n" + //
                "| |    | |__) |  | || |   | |_  \\_|  | || |  |   \\ | |   | || |   | |   `. \\ | || |  | |    | |  | |  \r\n" + //
                "| |    |  ___/   | || |   |  _|  _   | || |  | |\\ \\| |   | || |   | |    | | | || |  | '    ' |  | |  \r\n" + //
                "| |   _| |_      | || |  _| |___/ |  | || | _| |_\\   |_  | || |  _| |___.' / | || |   \\ `--' /   | |  \r\n" + //
                "| |  |_____|     | || | |_________|  | || ||_____|\\____| | || | |________.'  | || |    `.__.'    | |  \r\n" + //
                "| |              | || |              | || |              | || |              | || |              | |  \r\n" + //
                "| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |  \r\n" + //
                " '----------------'  '----------------'  '----------------'  '----------------'  '----------------'   ");
                System.out.println("Bienvenue dans le jeu du pendu! Veuillez choisir un niveau de difficulté parmi les suivants:\n1)Facile\n2)Moyen\n3)Difficile");  
                do{
                    try {
                        choix=scanner.nextInt();                    
                    
                        if ((choix!=1) && (choix!=2) && (choix!=3))
                            System.out.println("Saisir le nombre correspondant s'il vous plaît!");
                    }catch (InputMismatchException e) {System.out.println("Il faut saisir un nombre!");scanner.next();} 
                    }while((choix!=1) && (choix!=2) &&(choix!=3)); 
                    break;
                case 7:
                    launch(args);
                default:
                    break;
            }        
            }
            scanner.close();
    }
}
}
