package ua.com.javarush.gnew.crypto;

import ua.com.javarush.gnew.language.Language;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Cypher {
    private final Language language;

    public Cypher(Language language) {
        this.language = language;
    }

    public String encrypt(String input, int key) {
        key = Math.negateExact(key);
        ArrayList<Character> rotatedAlphabet = rotateAlphabet(key);
        return transform(input, rotatedAlphabet);
    }

    public String decrypt(String input, int key) {
        ArrayList<Character> rotatedAlphabet = rotateAlphabet(key);
        return transform(input, rotatedAlphabet);
    }

    public String bruteForceDecrypt(String input) {
        for (int key = 0; key < language.getAlphabet().size(); key++) {
            ArrayList<Character> rotatedAlphabet = rotateAlphabet(key);
            String decryptedText = transform(input, rotatedAlphabet);

            if (containsEngWords(decryptedText)) {
                return decryptedText;
            }
        }
        return null;
    }

    private String transform(String input, ArrayList<Character> rotatedAlphabet) {
        StringBuilder builder = new StringBuilder();
        for (char symbol : input.toCharArray()) {
            builder.append(processSymbol(symbol, rotatedAlphabet));
        }
        return builder.toString();
    }

    private ArrayList<Character> rotateAlphabet(int key) {
        ArrayList<Character> rotatedAlphabet = new ArrayList<>(language.getAlphabet());
        Collections.rotate(rotatedAlphabet, key);
        return rotatedAlphabet;
    }

    private Character processSymbol(char symbol, ArrayList<Character> rotatedAlphabet) {
        boolean isUpperCase = Character.isUpperCase(symbol);
        char symbolLowerCase = Character.toLowerCase(symbol);

        if (!language.getAlphabet().contains(symbolLowerCase)) {
            return symbol;
        }

        int index = language.getAlphabet().indexOf(symbolLowerCase);
        char rotatedChar = rotatedAlphabet.get(index);

        return isUpperCase ? Character.toUpperCase(rotatedChar) : rotatedChar;
    }

    private boolean containsEngWords(String decryptedText) {
        ArrayList<String> words = new ArrayList<>(Arrays.asList(decryptedText.split("[ //,//.]")));
        for (String word : words) {
            if (language.getCommonWords().contains(word)) {
                return true;
            }
        }
        return false;
    }
}
