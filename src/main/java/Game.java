import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;

import java.io.File;


public class Game{

    @FXML
    private Pane man;
    @FXML
    private Pane base1;
    @FXML
    private Pane base2;
    @FXML
    private Pane base3;
    @FXML
    private Pane pole;
    @FXML
    private Pane rod;
    @FXML
    private Pane rope1;
    @FXML
    private Pane rope2;
    @FXML
    private Text text;
    @FXML
    private Pane buttons;
    @FXML
    private Text winStatus;
    @FXML
    private Text realWord;
    @FXML
    private Button replayButton;

    private int mistakes;
    private int correct;
    private String myWord="";
    private List<String> myLetters;
    private List<String> answer;
    private Dictionnaire dict;
    private int difficulty;
    private  AudioClip audioClip;
    private AudioClip audioClipE;
    public Game() throws FileNotFoundException{
    }

    public void setNomDictionnaire(Dictionnaire d){
        this.dict = d;
    }
    public void setDifficulty(int d){
        this.difficulty = d;
    }

    public void setWord(String w){
        this.myWord = w;
    }
    public void initialize() {
        base1.setVisible(false);
        base2.setVisible(false);
        base3.setVisible(false);
        pole.setVisible(false);
        rod.setVisible(false);
        rope1.setVisible(false);
        rope2.setVisible(false);
        man.setVisible(false);
        replayButton.setDisable(true);
        replayButton.setVisible(false);
        mistakes=0;
        correct=0;
        if (myWord.equals("")){

            replayButton.setDisable(false);
            replayButton.setVisible(true);
            myWord = dict.selectRandomWord(difficulty);
        }
        myWord = myWord.toUpperCase();
        myLetters = Arrays.asList(myWord.split(""));
        answer = Arrays.asList(new String[myLetters.size() * 2]);
        for (int i = 0; i < myLetters.size() * 2; i++) {
            if (i % 2 == 0) {
                answer.set(i, "_");
            } else {
                if (myLetters.get(i / 2).equals(" ")) {
                    // setting three spaces in anwser if the letter is a space
                    answer.set(i-1,"");
                    answer.set(i, "   ");
                } else {
                    answer.set(i, " ");
                }
            }
            String soundFile = "assets/sfx/succes.mp3"; 
            audioClip = new AudioClip(new File(soundFile).toURI().toString());
            String soundFileE = "assets/sfx/echec.mp3"; 
            audioClipE = new AudioClip(new File(soundFileE).toURI().toString());
        }

        String res = String.join("", answer);
        text.setText(res);
        winStatus.setText("");
        realWord.setText("");
        buttons.setDisable(false);

    }


    public void onClick(ActionEvent event) {
        String myWordWithoutSpaces = myWord.replaceAll(" ", "");
        int lengthWithoutSpaces = myWordWithoutSpaces.length();

        String letter = ((Button) event.getSource()).getText();
        ((Button) event.getSource()).setDisable(true);
        if (myLetters.contains(letter)) {
            for (int i = 0; i < myLetters.size(); i++) {
                if (myLetters.get(i).equals(letter)) {
                    // revealing all occurrences of the guessed letter
                    answer.set(i * 2, letter);
                    correct++;

                }
            }
            String res = String.join("", answer);
            text.setText(res);
            if (correct == lengthWithoutSpaces) {
                audioClip.play(); 
                winStatus.setText("Vous avez Gagné!");
                buttons.setDisable(true);
            }
        } else {
            mistakes++;
            if (mistakes == 1)
                base1.setVisible(true);
            else if (mistakes == 2)
                base2.setVisible(true);
            else if (mistakes == 3)
                base3.setVisible(true);
            else if (mistakes == 4)
                pole.setVisible(true);
            else if (mistakes == 5)
                rod.setVisible(true);
            else if (mistakes == 6)
                rope1.setVisible(true);
            else if (mistakes == 7)
                rope2.setVisible(true);
            else if (mistakes == 8) {
                rope2.setVisible(false);
                man.setVisible(true);

                audioClipE.play();   
                winStatus.setText("Vous avez Perdu!");
                realWord.setText("Le mot à deviner était " + myWord);
                buttons.setDisable(true);
            }
        }
    }
    public void newGame() {
        myWord="";
        for (int i = 0; i < 26; i++) {
            buttons.getChildren().get(i).setDisable(false);
        }
        initialize();
    }

}