package ua.com.javarush.gnew.crypto;

import ua.com.javarush.gnew.language.Language;
import ua.com.javarush.gnew.language.LanguageDetector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Cypher {
    private final ArrayList<Character> alphabet;
    private final ArrayList<String> commonWords;

    public Cypher(Language.LanguageEnum language) {
        if (language == Language.LanguageEnum.ENGLISH) {
            this.alphabet = new ArrayList<>(Arrays.asList(
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));
            this.commonWords = new ArrayList<>(Arrays.asList(
                    "to", "this", "This", "those", "at", "the", "The", "are", "was", "and", "you", "for"));
        } else if (language == Language.LanguageEnum.UKRAINIAN) {
            this.alphabet = new ArrayList<>(Arrays.asList(
                    'а', 'б', 'в', 'г', 'ґ', 'д', 'е', 'є', 'ж', 'з', 'и', 'і', 'ї', 'й', 'к', 'л',
                    'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ь', 'ю', 'я'));
            this.commonWords = new ArrayList<>(Arrays.asList(
                    "і", "та", "це", "той", "ця", "ти", "що", "був", "я", "він", "вона"));
        } else {
            throw new IllegalArgumentException("Unsupported language");
        }
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

    private String transform(String input, ArrayList<Character> rotatedAlphabet) {
        StringBuilder builder = new StringBuilder();
        for (char symbol : input.toCharArray()) {
            builder.append(processSymbol(symbol, rotatedAlphabet));
        }
        return builder.toString();
    }

    private ArrayList<Character> rotateAlphabet(int key) {
        ArrayList<Character> rotatedAlphabet = new ArrayList<>(this.alphabet);
        Collections.rotate(rotatedAlphabet, key);
        return rotatedAlphabet;
    }

    private Character processSymbol(char symbol, ArrayList<Character> rotatedAlphabet) {
        boolean isUpperCase = Character.isUpperCase(symbol);
        char symbolLowerCase = Character.toLowerCase(symbol);

        if (!this.alphabet.contains(symbolLowerCase)) {
            return symbol; // Non-alphabet characters remain unchanged
        }

        int index = this.alphabet.indexOf(symbolLowerCase);
        char rotatedChar = rotatedAlphabet.get(index);

        return isUpperCase ? Character.toUpperCase(rotatedChar) : rotatedChar;
    }

    public String bruteForceDecrypt(String input) {
        for (int key = 0; key < alphabet.size(); key++) {
            ArrayList<Character> rotatedAlphabet = rotateAlphabet(key);
            String decryptedText = transform(input, rotatedAlphabet);

            if (containsEngWords(decryptedText)) {
                return decryptedText;
            }
        }
        return null;
    }

    private boolean containsEngWords(String decryptedText) {
        ArrayList<String> words = new ArrayList<>(Arrays.asList(decryptedText.split(" ")));
        for (String word : words) {
            if (this.commonWords.contains(word)) {
                return true;
            }
        }
        return false;
    }
}

