package ua.com.javarush.gnew;

import ua.com.javarush.gnew.crypto.Cypher;
import ua.com.javarush.gnew.file.FileManager;
import ua.com.javarush.gnew.language.Language;
import ua.com.javarush.gnew.language.LanguageDetector;
import ua.com.javarush.gnew.runner.ArgumentsParser;
import ua.com.javarush.gnew.runner.Command;
import ua.com.javarush.gnew.runner.RunOptions;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        System.out.println("_________________");
        LanguageDetector languageDetector = new LanguageDetector();
        FileManager fileManager = new FileManager();
        ArgumentsParser argumentsParser = new ArgumentsParser();
        RunOptions runOptions = argumentsParser.parse(args);

        try {
            String content = fileManager.read(runOptions.getFilePath());
            Language language = new Language(languageDetector.detectLanguage(content));
            Cypher cypher = new Cypher(language);

            String processedContent = processContent(runOptions, cypher, content);
            writeFile(runOptions, fileManager, processedContent);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void writeFile(RunOptions runOptions, FileManager fileManager, String processedContent) throws IOException {
        String fileName = runOptions.getFilePath().getFileName().toString();
        String newFileName = fileName.substring(0, fileName.length() - 4) + getSuffix(runOptions.getCommand());
        Path newFilePath = runOptions.getFilePath().resolveSibling(newFileName);
        fileManager.write(newFilePath, processedContent);
    }

    private static String processContent(RunOptions runOptions, Cypher cypher, String content) {
        switch (runOptions.getCommand()) {
            case ENCRYPT:
                return cypher.encrypt(content, runOptions.getKey());
            case DECRYPT:
                return cypher.decrypt(content, runOptions.getKey());
            case BRUTEFORCE:
                return cypher.bruteForceDecrypt(content);
            default:
                throw new IllegalArgumentException("Unknown command: " + runOptions.getCommand());
        }
    }

    private static String getSuffix(Command cmd) {
        switch (cmd) {
            case ENCRYPT:
                return " [ENCRYPTED]";
            case DECRYPT:
                return " [DECRYPTED]";
            case BRUTEFORCE:
                return " [BRUTEFORCE]";
            default:
                return "";
        }
    }
}