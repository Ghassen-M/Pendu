
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {          
            System.out.println("Avant de déclencher le programme, entrer le nom du dictionnaire (sans extension)");
            Dictionnaire d=new Dictionnaire();

            Scanner scanner = new Scanner(System.in);
            String nomDictionnaire=scanner.nextLine();
            while(!d.existenceFichier(nomDictionnaire)){
                System.out.println("Ce fichier n'existe pas! Ecrire un nom du fichier valide!");
                nomDictionnaire=scanner.nextLine();
            }

            int choix=0;
            System.out.println("Bienvenue dans le menu! Veuiller choisir une de ces options ci-dessous:");
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
                
                    if ((choix!=0) && (choix!=1) && (choix!=2) &&(choix!=3) && (choix!=4) && (choix!=5) &&(choix!=6))
                        System.out.println("Saisir le nombre correspondant s'il vous plaît!");
                }catch (InputMismatchException e) {System.out.println("Il faut saisir un nombre!");scanner.next();} 
            }while((choix!=0) && (choix!=1) && (choix!=2) &&(choix!=3) && (choix!=4) && (choix!=5) &&(choix!=6)); 

            Arbre aN=d.arbreNAire(nomDictionnaire);
            ABR a=d.arbreBinaire(aN);
            String mot="";

            if (choix==0)
                break;
                switch (choix) {
                case 1: 
                    d.afficherArbre(aN, "", false);  
                    break;
                case 2: 
                    d.afficherArbreBinaire(a, "", false);
                    break;
                case 3:   
                    System.out.println("Donner le mot à ajouter:");
                    scanner.nextLine();
                    mot=scanner.nextLine();
                    d.ajoutMot(nomDictionnaire, mot);
                    break;
                case 4:   
                    System.out.println("Donner le mot à supprimer:");
                    scanner.nextLine();
                    mot=scanner.nextLine();
                    d.suppressionMot(nomDictionnaire, mot);
                    break;
                case 5:   
                    System.out.println("Donner le mot à trouver:");
                    scanner.nextLine();
                    mot=scanner.nextLine();
                    if (d.motExiste(nomDictionnaire, mot))
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
                default:
                    break;
            }        
            }
    }
}
}
