package ua.com.javarush.gnew.crypto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Cypher {
    private final ArrayList<Character> originalAlphabet = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));
    public static final ArrayList<String> ENG_WORDS = new ArrayList<>(Arrays.asList("to", "this", "This", "those", "at", "the", "The", "are", "was", "and", "you", "for"));

    public String encrypt(String input, int key) {
        key = Math.negateExact(key);

        ArrayList<Character> rotatedAlphabet = new ArrayList<>(originalAlphabet);
        Collections.rotate(rotatedAlphabet, key);
        char[] charArray = input.toCharArray();

        StringBuilder builder = new StringBuilder();
        for (char symbol : charArray) {
            builder.append(processSymbol(symbol, rotatedAlphabet));
        }
        return builder.toString();
    }

    public String decrypt(String input, int key) {
        ArrayList<Character> rotatedAlphabet = new ArrayList<>(originalAlphabet);
        Collections.rotate(rotatedAlphabet, key);
        char[] charArray = input.toCharArray();

        StringBuilder builder = new StringBuilder();
        for (char symbol : charArray) {
            builder.append(processSymbol(symbol, rotatedAlphabet));
        }
        return builder.toString();
    }

    public String bruteForceDecrypt(String input) {
        for (int key = 0; key < originalAlphabet.size(); key++) {
            // Create a new rotated alphabet for each key
            ArrayList<Character> rotatedAlphabet = new ArrayList<>(originalAlphabet);
            Collections.rotate(rotatedAlphabet, key);

            StringBuilder builder = new StringBuilder();
            char[] charArray = input.toCharArray();

            for (char symbol : charArray) {
                builder.append(processSymbol(symbol, rotatedAlphabet));
            }

            String decryptedText = builder.toString();
            // Debugging: Print out each attempt
            System.out.println("Key: " + key + " -> " + decryptedText);

            String[] myWords = decryptedText.split(" ");
            // Check if the decrypted message contains any of the English words
            for (String word : ENG_WORDS) {
                for (String word1 : myWords){
                    if (word.equals(word1)) {
                        return decryptedText;
                    }
                }
            }
        }
        return null;
    }

    private Character processSymbol(char symbol, ArrayList<Character> rotatedAlphabet) {
        if (!originalAlphabet.contains(symbol)) {
            return symbol;
        }
        int index = originalAlphabet.indexOf(symbol);

        return rotatedAlphabet.get(index);
    }
}
