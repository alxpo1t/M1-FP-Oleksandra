package ua.com.javarush.gnew.language;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Language {

    private final ArrayList<Character> alphabet;

    public Language(ArrayList<Character> alphabet) {
        this.alphabet = alphabet;
    }
}
