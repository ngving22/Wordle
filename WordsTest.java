package org.cis1200.Wordle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordsTest {

    @Test
    public void invalidFiveLetterWord() {
        assertFalse(new Words().wordExists("#$##$"));
    }

    @Test
    public void invalidWordTooLong() {
        assertFalse(new Words().wordExists("temper"));
    }

    @Test
    public void invalidWordTooShort() {
        assertFalse(new Words().wordExists("many"));
    }

    @Test
    public void validWord() {
        assertTrue(new Words().wordExists("point"));
    }

    @Test
    public void getWordSimple() {
        String s = new Words().getWord();
        assertTrue(new Words().wordExists(s));
    }
}
