package ua.com.javarush.gnew.language;


import java.util.ArrayList;
import java.util.Arrays;

public class Language {
    public enum LanguageEnum {
        ENGLISH,
        UKRAINIAN
    }
    private final ArrayList<Character> alphabet;
    private final ArrayList<String> commonWords;

    public Language(LanguageEnum language) {
        switch (language) {
            case ENGLISH:
                this.alphabet = new ArrayList<>(Arrays.asList(
                        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));
                this.commonWords = new ArrayList<>(Arrays.asList(
                        "to", "this", "This", "those", "at", "the", "The", "are", "was", "and",
                        "you", "for", "or", "so", "when", "where"));
                break;
            case UKRAINIAN:
                this.alphabet = new ArrayList<>(Arrays.asList(
                        'а', 'б', 'в', 'г', 'ґ', 'д', 'е', 'є', 'ж', 'з', 'и', 'і', 'ї', 'й', 'к', 'л',
                        'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ь', 'ю', 'я'));
                this.commonWords = new ArrayList<>(Arrays.asList(
                        "і", "та", "це", "той", "ця", "ти", "що", "був", "я", "він", "вона", "або", "ми",
                        "вони", "але", "ну", "з"));
                break;
            default:
                throw new IllegalArgumentException("Unsupported language");
        }
    }

    public ArrayList<Character> getAlphabet() {
        return alphabet;
    }

    public ArrayList<String> getCommonWords() {
        return commonWords;
    }

}
