package org.cis1200.Wordle;

import java.awt.*;

import javax.swing.*;


public class Instructions extends JFrame {

    private final JLabel aboutLabel;
    private final JLabel howTo;
    // Help Pop-Up Window Constructor
    public Instructions() {


        this.setTitle("Welcome to Wordle!");

        this.aboutLabel = new JLabel(
                "<html>" + "Wordle is a fun vocabulary game!" + "</html>"
        );

        this.howTo = new JLabel(
                """
                        <html>You have 6 tries to guess a word via typing.\s
                        Each guess must be a valid 5-letter word.
                        The color of the tiles will change to show how close your
                        guess is to the word.
                        Green: Letter in word and the right position.
                        Yellow: Letter in word but wrong position.
                        Dark Grey: Letter not in word.
                        There are five buttons below:
                        -Save: Will save current game for future play
                        -Reset: Will reset board and wipe saved game
                        -Load: Will load saved game
                        -Next: Will be activated for user to advance
                               to next word if guess correctly
                        -Help: Will create pop-up window with this prompt
                               to aid user experience"""
        );

        this.add(aboutLabel, BorderLayout.NORTH);
        this.add(howTo, BorderLayout.CENTER);
        // Close the window when the user clicks the close button
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Set the size of the window
        this.setSize(400, 300);
        this.setVisible(true);



    }



}

