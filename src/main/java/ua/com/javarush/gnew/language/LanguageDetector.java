package ua.com.javarush.gnew.language;

public class LanguageDetector {
    public static Language.LanguageEnum detectLanguage(String text) {
        int englishCount = 0;
        int ukrainianCount = 0;

        for (char ch : text.toCharArray()) {
            if (isEnglishCharacter(ch)) {
                englishCount++;
            } else if (isUkrainianCharacter(ch)) {
                ukrainianCount++;
            }
        }

        return (ukrainianCount > englishCount) ? Language.LanguageEnum.UKRAINIAN : Language.LanguageEnum.ENGLISH;
    }

    private static boolean isEnglishCharacter(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }

    private static boolean isUkrainianCharacter(char ch) {
        return (ch >= 'Ѓ' && ch <= 'ї');
    }
}
