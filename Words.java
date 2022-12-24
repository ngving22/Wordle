package org.cis1200.Wordle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;



public class Words {


    Random rand;

    File allPossibleWords;
    BufferedReader br;

    // Gets a random common 5-letter word from txt file
    public String getWord() {
        List<String> targetWords = new ArrayList<>();
        // Use a try-with-resources block to read the file
        try (BufferedReader reader = new BufferedReader(new FileReader("target.txt"))) {
            String line;
            // Read each line from the file and split it into words
            while ((line = reader.readLine()) != null) {
                String[] wordsOnLine = line.split(" ");
                // Add each word to the list
                Collections.addAll(targetWords, wordsOnLine);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        Collections.shuffle(targetWords);

        rand = new Random();
        return targetWords.get(rand.nextInt(targetWords.size())).toLowerCase();

    }

    // Checks if 5-letter guess from user is a word that exists
    public boolean wordExists(String word) {
        String line;
        allPossibleWords = new File("dictionary.txt");

        try {
            br = new BufferedReader(new FileReader(allPossibleWords));

            while ((line = br.readLine()) != null) {
                if (line.equals(word)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
