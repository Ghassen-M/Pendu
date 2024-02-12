import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//Classe ayant une méthode qui retourne la fréquence d'apparition des lettres dans la langue française
//Utile dans le calcul de difficulté d'un mot
public class LetterFrequency {
    public  Map<Character, Float> createLetterFrequencyMap() {
        Map<Character, Float> tempMap = new HashMap<>();
        
        tempMap.put('e', 12.1f);
        tempMap.put('a', 7.11f);
        tempMap.put('i', 6.59f);
        tempMap.put('s', 6.51f);
        tempMap.put('n', 6.39f);
        tempMap.put('r', 6.07f);
        tempMap.put('t', 5.92f);
        tempMap.put('o', 5.02f);
        tempMap.put('l', 4.96f);
        tempMap.put('u', 4.49f);
        tempMap.put('d', 3.67f);
        tempMap.put('c', 3.18f);
        tempMap.put('m', 2.62f);
        tempMap.put('p', 2.49f);
        tempMap.put('g', 1.23f);
        tempMap.put('b', 1.14f);
        tempMap.put('v', 1.11f);
        tempMap.put('h', 1.11f);
        tempMap.put('f', 1.11f);
        tempMap.put('q', 0.65f);
        tempMap.put('y', 0.46f);
        tempMap.put('x', 0.38f);
        tempMap.put('j', 0.34f);
        tempMap.put('k', 0.29f);
        tempMap.put('w', 0.17f);
        tempMap.put('z', 0.15f);

        return Collections.unmodifiableMap(tempMap);
    }
}