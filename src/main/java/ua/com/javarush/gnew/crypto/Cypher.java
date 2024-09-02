package ua.com.javarush.gnew.crypto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Cypher {
    private final ArrayList<Character> originalAlphabet = new ArrayList<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));
    public static final ArrayList<String> ENG_WORDS = new ArrayList<>(Arrays.asList("to", "this", "This", "those", "at", "the", "The", "are", "was", "and", "you", "for"));


    public String encrypt(String input, int key) {
        key = Math.negateExact(key);
        ArrayList<Character> rotatedAlphabet = rotateAlphabet(key);
        return transform(input, rotatedAlphabet);
    }

    public String decrypt(String input, int key) {
        ArrayList<Character> rotatedAlphabet = rotateAlphabet(key);
        return transform(input, rotatedAlphabet);
    }

    private String transform(String input, ArrayList<Character> rotatedAlphabet){
        StringBuilder builder = new StringBuilder();
        for (char symbol : input.toCharArray()) {
            builder.append(processSymbol(symbol, rotatedAlphabet));
        }
        return builder.toString();
    }

    private ArrayList<Character> rotateAlphabet(int key) {
        ArrayList<Character> rotatedAlphabet = new ArrayList<>(originalAlphabet);
        Collections.rotate(rotatedAlphabet, key);
        return rotatedAlphabet;
    }

    public String bruteForceDecrypt(String input) {
        for (int key = 0; key < originalAlphabet.size(); key++) {
            ArrayList<Character> rotatedAlphabet = rotateAlphabet(key);
            String decryptedText = transform(input, rotatedAlphabet);

            if (containsEngWords(decryptedText)) {
                return decryptedText;
            }
        }
        return null;
    }

    private boolean containsEngWords(String decryptedText){
        ArrayList<String> words = new ArrayList<>(Arrays.asList(decryptedText.split(" ")));
        for (String word : words) {
            if (ENG_WORDS.contains(word)) {
                return true;
            }
        }
        return false;
    }

    private Character processSymbol(char symbol, ArrayList<Character> rotatedAlphabet) {
        boolean isUpperCase = Character.isUpperCase(symbol);
        char symbolLowerCase = Character.toLowerCase(symbol);

        if (!originalAlphabet.contains(symbolLowerCase)) {
            return symbol;
        }

        int index = originalAlphabet.indexOf(symbolLowerCase);
        char rotatedChar = rotatedAlphabet.get(index);

        return isUpperCase ? Character.toUpperCase(rotatedChar) : rotatedChar;
    }
}
