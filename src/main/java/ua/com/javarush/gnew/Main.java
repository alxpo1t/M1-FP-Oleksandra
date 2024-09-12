package ua.com.javarush.gnew;

import ua.com.javarush.gnew.crypto.Cypher;
import ua.com.javarush.gnew.file.FileManager;
import ua.com.javarush.gnew.language.Language;
import ua.com.javarush.gnew.language.LanguageDetector;
import ua.com.javarush.gnew.runner.ArgumentsParser;
import ua.com.javarush.gnew.runner.Command;
import ua.com.javarush.gnew.runner.RunOptions;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        LanguageDetector languageDetector = new LanguageDetector();
        //Cypher cypher = new Cypher(languageDetector.detectLanguage());
        FileManager fileManager = new FileManager();
        ArgumentsParser argumentsParser = new ArgumentsParser();
        RunOptions runOptions = argumentsParser.parse(args);

        try {

            String content = fileManager.read(runOptions.getFilePath());
            Language.LanguageEnum detectedLanguage = LanguageDetector.detectLanguage(content);
            Cypher cypher = new Cypher(detectedLanguage);

            if (runOptions.getCommand() == Command.ENCRYPT) {
                String encryptedContent = cypher.encrypt(content, runOptions.getKey());
                String fileName = runOptions.getFilePath().getFileName().toString();
                String newFileName = fileName.substring(0, fileName.length() - 4) + " [ENCRYPTED].txt";

                Path newFilePath = runOptions.getFilePath().resolveSibling(newFileName);
                fileManager.write(newFilePath, encryptedContent);
            } else if (runOptions.getCommand() == Command.DECRYPT) {
                String decryptedContent = cypher.decrypt(content, runOptions.getKey());
                String fileName = runOptions.getFilePath().getFileName().toString();
                String newFileName = fileName.substring(0, fileName.length() - 4) + " [DECRYPTED].txt";

                Path newFilePath = runOptions.getFilePath().resolveSibling(newFileName);
                fileManager.write(newFilePath, decryptedContent);
            } else if (runOptions.getCommand() == Command.BRUTEFORCE) {
                String decryptedContent = cypher.bruteForceDecrypt(content);
                String fileName = runOptions.getFilePath().getFileName().toString();
                String newFileName = fileName.substring(0, fileName.length() - 4) + " [BRUTEFORCE].txt";

                Path newFilePath = runOptions.getFilePath().resolveSibling(newFileName);
                fileManager.write(newFilePath, decryptedContent);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}