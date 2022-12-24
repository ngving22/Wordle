package org.cis1200.Wordle;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;


public class FrameTests {

    @Test
    public void checkWordCorrect() {

        Frame test = new Frame(0);
        test.word = "brave";

        JButton b0 = test.tiles[0][0];
        JButton b1 = test.tiles[0][1];
        JButton b2 = test.tiles[0][2];
        JButton b3 = test.tiles[0][3];
        JButton b4 = test.tiles[0][4];

        b0.setText("b");
        b1.setText("r");
        b2.setText("a");
        b3.setText("v");
        b4.setText("e");

        test.checkWord("brave", test.tiles);

        assertEquals(b0.getBackground(), Color.GREEN);
        assertEquals(b1.getBackground(), Color.GREEN);
        assertEquals(b2.getBackground(), Color.GREEN);
        assertEquals(b3.getBackground(), Color.GREEN);
        assertEquals(b4.getBackground(), Color.GREEN);
    }

    @Test
    public void checkWordIncorrect() {
        Frame test = new Frame(0);
        test.word = "brave";

        JButton b0 = test.tiles[0][0];
        JButton b1 = test.tiles[0][1];
        JButton b2 = test.tiles[0][2];
        JButton b3 = test.tiles[0][3];
        JButton b4 = test.tiles[0][4];

        b0.setText("b");
        b1.setText("r");
        b2.setText("e");
        b3.setText("e");
        b4.setText("d");

        test.checkWord("breed", test.tiles);

        assertEquals(b0.getBackground(), Color.GREEN);
        assertEquals(b1.getBackground(), Color.GREEN);
        assertEquals(b2.getBackground(), Color.YELLOW);
        assertEquals(b3.getBackground(), Color.darkGray);
        assertEquals(b4.getBackground(), Color.darkGray);
    }

    @Test
    public void loser() {
        Frame test = new Frame(0);
        test.chance = 5;
        test.word = "brave";
        test.checkWinner();
        assertTrue(test.gameOver);
        assertEquals(test.label.getText(), "You Lost! The word is: brave");
        assertEquals(test.label.getBackground(), Color.RED);
        assertEquals(test.label.getForeground(), Color.BLACK);
    }

    @Test
    public void winner() {
        Frame test = new Frame(0);
        test.word = "brave";
        boolean[] arr = new boolean[5];
        Arrays.fill(arr, true);

        test.correct = arr;
        test.checkWinner();
        assertTrue(test.gameOver);
        assertEquals(test.label.getText(), "Congratulations! You have won!");
        assertTrue(test.nextButton.isEnabled());
        assertTrue(test.saveButton.isEnabled());
        assertFalse(test.loadButton.isEnabled());
        assertTrue(test.resetButton.isEnabled());
        assertTrue(test.helpButton.isEnabled());
        assertEquals(test.label.getBackground(), Color.GREEN);
        assertEquals(test.label.getForeground(), Color.BLACK);
    }

    @Test
    public void gameNotOver() {
        Frame test = new Frame(0);
        test.word = "brave";
        boolean[] arr = new boolean[5];
        Arrays.fill(arr, true);
        arr[4] = false;

        test.correct = arr;
        test.checkWinner();
        assertFalse(test.gameOver);
        assertTrue(test.saveButton.isEnabled());
        assertTrue(test.helpButton.isEnabled());
        assertFalse(test.loadButton.isEnabled());
        assertFalse(test.nextButton.isEnabled());
        assertTrue(test.resetButton.isEnabled());
    }


}

