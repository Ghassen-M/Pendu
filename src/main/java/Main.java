
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {
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
            int choix=0;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Bienvenue dans le jeu du pendu! Veuillez choisir un niveau de difficulté parmi les suivants:\n1)Facile\n2)Moyen\n3)Difficile");  

            do{
                try {

                    choix=scanner.nextInt();                    
                
                if ((choix!=1) && (choix!=2) && (choix!=3))
                    System.out.println("Saisir le nombre correspondant s'il vous plaît!");

                }catch (InputMismatchException e) {System.out.println("Il faut saisir un nombre!");scanner.next();} 

                }while((choix!=1) && (choix!=2) &&(choix!=3)); 
            /* Test lecture mots du dictionnaire et représentation*/
            
            Dictionnaire d=new Dictionnaire();
            ABR a=d.arbreBinaire("dictionnaireExempleEnonce");
            d.afficherArbreBinaire(a, "", false);
            
            break;
    }
}
}