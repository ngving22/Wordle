package org.cis1200.Wordle;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Objects;

public class Frame extends JFrame implements ActionListener, KeyListener, Serializable {
    JButton[][] tiles = new JButton[6][5];
    JButton resetButton;
    JButton nextButton;

    JButton loadButton;

    JButton helpButton;
    JButton saveButton;

    JLabel label;
    JLabel score;

    JPanel buttonsPanel = new JPanel();
    JPanel lowerButtons = new JPanel();

    boolean canContinue;

    boolean[] correct;
    boolean[] checked;

    boolean gameOver;

    int chance;

    int points;
    int letter;

    String word;

    public static final int BOARD_WIDTH = 600;

    public static final int BOARD_HEIGHT = 600;

    // Game Constructor
    public Frame(int points) {
        this.points = points;
        word = new Words().getWord();

        correct = new boolean[5];
        checked = new boolean[5];

        chance = 0;
        letter = 0;
        canContinue = true;

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
        this.setSize(BOARD_WIDTH, BOARD_HEIGHT);

        label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Helvetica Neue",Font.BOLD,20));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setText("WORDLE");
        label.setBackground(Color.BLACK);

        score = new JLabel();
        score.setHorizontalAlignment(JLabel.CENTER);
        score.setFont(new Font("Helvetica Neue",Font.BOLD,15));
        score.setOpaque(true);
        score.setText("Score: " + points + " "); //Space after to make it look clear
        score.setBackground(Color.MAGENTA);

        resetButton = new JButton();
        resetButton.setForeground(Color.RED);
        resetButton.setText("Reset");
        resetButton.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        resetButton.setBackground(Color.BLACK);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        nextButton = new JButton();
        nextButton.setForeground(Color.BLUE);
        nextButton.setText("Next");
        nextButton.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        nextButton.setBackground(Color.BLACK);
        nextButton.setFocusable(false);
        nextButton.addActionListener(this);
        nextButton.setEnabled(false);

        saveButton = new JButton();
        saveButton.setForeground(Color.GREEN);
        saveButton.setText("Save");
        saveButton.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        saveButton.setBackground(Color.BLACK);
        saveButton.setFocusable(false);
        saveButton.addActionListener(this);
        saveButton.setEnabled(true);

        loadButton = new JButton();
        loadButton.setForeground(Color.BLUE);
        loadButton.setText("Load");
        loadButton.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        loadButton.setBackground(Color.BLACK);
        loadButton.setFocusable(false);
        loadButton.addActionListener(this);
        loadButton.setEnabled(false);

        helpButton = new JButton();
        helpButton.setForeground(Color.BLACK);
        helpButton.setText("Help");
        helpButton.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        helpButton.setBackground(Color.BLACK);
        helpButton.setFocusable(false);
        helpButton.addActionListener(this);
        helpButton.setEnabled(true);


        buttonsPanel.setVisible(true);
        buttonsPanel.setLayout(new GridLayout(6,5));

        lowerButtons.setVisible(true);
        lowerButtons.setLayout(new GridLayout(1,3));
        lowerButtons.add(resetButton);
        lowerButtons.add(nextButton);
        lowerButtons.add(saveButton);
        lowerButtons.add(loadButton);
        lowerButtons.add(helpButton);


        loadButton.setEnabled(Files.exists(Paths.get("saveGame.txt")));


        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = new JButton();
                tiles[i][j].setEnabled(false);
                tiles[i][j].setBackground(Color.WHITE);
                tiles[i][j].setFont(new Font("Helvetica Neue", Font.BOLD, 20));
                buttonsPanel.add(tiles[i][j]);
            }
        }

        this.add(label, BorderLayout.NORTH);
        this.add(score, BorderLayout.WEST);
        this.add(lowerButtons, BorderLayout.SOUTH);
        this.getContentPane().setBackground(Color.WHITE);
        this.add(buttonsPanel);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setTitle("Wordle");
        this.repaint();
        this.revalidate();
        this.setLocationRelativeTo(null);
    }


    // Deals with user keyboard interaction
    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            int code = e.getKeyCode();
            if (code >= 65 && code <= 90) {
                if (canContinue) {
                    if (letter < 5) {
                        tiles[chance][letter].setText(String.valueOf((char)code));
                        letter++;
                    } else {
                        canContinue = false;
                    }
                }
            } else if (code == 10) {
                if (chance < 6) {
                    if (letter == 5) {
                        StringBuilder wordTyped = new StringBuilder();
                        for (int i = 0; i < 5; i++) {
                            wordTyped.append(tiles[chance][i].getText());
                        }

                        boolean wordExists = new Words().wordExists(wordTyped.
                                                 toString().toLowerCase());
                        if (wordExists) {
                            checkWord(wordTyped.toString().toLowerCase(), this.tiles);
                            checkWinner();
                            chance += 1;
                            letter = 0;
                            canContinue = true;
                        } else {
                            label.setForeground(Color.RED);
                            label.setText("Word Doesn't Exist!");
                        }
                    } else {
                        label.setForeground(Color.RED);
                        label.setText("Need More Letters!");
                    }
                }
            } else if (code == 8) {
                if (letter > 0) {
                    canContinue = true;
                    letter--;
                    tiles[chance][letter].setText("");
                }
            }
        }
    }
    // Deals when different buttons are interacted with
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            File file;
            try {
                file = Paths.get("saveGame.txt").toFile();
                file.delete();
            } catch (InvalidPathException exc) {
                new File("saveGame.txt");
            }
            this.dispose();
            new Frame(0);
            loadButton.setEnabled(false);
        }
        if (e.getSource() == nextButton) {
            this.dispose();
            new Frame(points += 1);
        }
        if (e.getSource() == saveButton) {
            saveGameState();
            this.dispose();
        }
        if (e.getSource() == loadButton) {
            loadGame();
        }
        if (e.getSource() == helpButton) {
            new Instructions();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // Checks the mystery word vs the user's guess
    public void checkWord(String toBeChecked, JButton[][] buttons) {
        // Frequency array keeps track of number of times letter of mystery word matches typed word
        int[] frequency = new int[5];
        for (int i = 0; i < 5; i++) {
            checked[i] = false; // Clearing checked for new word;
            correct[i] = false;
            frequency[i] = 0; //Making frequency empty
        }
        // Using Collections to keep track of keep track of mystery word and guess
        ArrayList<Character> chars = new ArrayList<>();
        ArrayList<Character> mystery = new ArrayList<>();


        for (int i = 0; i < word.length(); i++) {
            mystery.add(word.charAt(i));
            System.out.print(word.charAt(i));
        }
        for (int i = 0; i < toBeChecked.length(); i++) {
            chars.add(toBeChecked.charAt(i));
        }
        // Tile turns green if letters match up
        for (int i = 0; i < 5; i++) {
            boolean sameLetter = (buttons[chance][i].getText().toLowerCase()).
                    equals(mystery.get(i).toString());
            if (sameLetter) {
                buttons[chance][i].setBackground(Color.GREEN);
                buttons[chance][i].setOpaque(true);
                checked[i] = true;
                correct[i] = true;
                frequency[i] = 1;
            }
        }
        // Tile turns yellow if guess contains letter in mystery word, but not in right position
        for (int i = 0; i < chars.size(); i++) {
            for (int j = 0; j < mystery.size(); j++) {
                if (mystery.get(j) == chars.get(i)) {
                    //If the letter of mystery word isn't matched with another at the same position
                    if (frequency[j] < 1) {
                        if (!checked[i]) {
                            buttons[chance][i].setBackground(Color.YELLOW);
                            buttons[chance][i].setOpaque(true);
                            checked[i] = true;
                            frequency[j] = 1;
                            break;
                        }
                    }
                }
            }
        }
        // Tile turns grey if letter in guess not in word
        for (int i = 0; i < 5; i++) {
            if (!checked[i]) {
                buttons[chance][i].setBackground(Color.darkGray);
                buttons[chance][i].setOpaque(true);
            }
        }


    }
    // Checks if the user has won
    public void checkWinner() {
        saveButton.setEnabled(true);
        if (chance == 5) {
            label.setBackground(Color.RED);
            label.setForeground(Color.BLACK);
            label.setText("You Lost! The word is: " + word);
            gameOver = true;

        }

        int count = 0;
        for (boolean b : correct) {
            if (b) {
                count++;
            }
            if (count == 5) {
                label.setBackground(Color.GREEN);
                label.setForeground(Color.BLACK);
                label.setText("Congratulations! You have won!");

                gameOver = true;
                nextButton.setEnabled(true);
            }
        }
    }

    // Method for saving game data to a file
    public void saveGameState() {
        File file;

        try {
            file = Paths.get("saveGame.txt").toFile();
        } catch (InvalidPathException e) {
            file = new File("saveGame.txt");
        }

        BufferedWriter bw = null;
        try {

            bw = new BufferedWriter((new FileWriter(file)));
            // Creates writable file for game state to be saved to

            for (JButton[] button : tiles) {
                for (int col = 0; col < tiles[0].length; col++) {
                    JButton b = button[col];
                    if (!Objects.equals(b.getText(), "")) {
                        bw.write(b.getText());
                    } else {
                        bw.write("$");
                    }
                }
                bw.newLine();
            }

            for (boolean b : correct) {
                bw.write(String.valueOf(b));
                bw.write(" ");
            }
            bw.newLine();
            bw.write(String.valueOf(this.points));
            bw.newLine();
            bw.write(this.label.getText());
            bw.newLine();
            bw.write(String.valueOf(this.chance));
            bw.newLine();
            bw.write(String.valueOf(this.letter));
            bw.newLine();
            bw.write(this.word);

            bw.newLine();
            for (JButton[] button : tiles) {
                for (int col = 0; col < tiles[0].length; col++) {
                    JButton b = button[col];
                    if (b.getBackground().equals(Color.GREEN)) {
                        bw.write("green ");
                    } else if (b.getBackground().equals(Color.YELLOW)) {
                        bw.write("yellow ");
                    } else if (b.getBackground().equals(Color.darkGray)) {
                        bw.write("darkgray ");
                    } else if (b.getBackground().equals(Color.RED)) {
                        bw.write("red ");
                    } else {
                        bw.write("white ");
                    }
                }
                bw.newLine();
            }
            bw.write(String.valueOf(canContinue));
            bw.newLine();
            bw.write(String.valueOf(nextButton.isEnabled()));
            bw.newLine();
            bw.write(String.valueOf(saveButton.isEnabled()));
            bw.newLine();
            bw.write(String.valueOf(gameOver));


        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "An error occurred while saving the game.",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (Exception a) {
                System.out.println("Can't close BufferedWriter " + a);
            }
        }



        JOptionPane.showConfirmDialog(this,
                "Save game state successfully.",
                "Wordle",
                JOptionPane.DEFAULT_OPTION);

    }

    // Method of prepping saved file to be loaded
    public void readFile(String fileName) {

        BufferedReader br = null;
        try {
            String line;
            File file = new File(fileName);
            br = new BufferedReader(new FileReader(file));

            if (fileName.equals("saveGame.txt")) {
                for (int i = 0; i < 6; i++) {
                    if ((line = br.readLine()) != null) {
                        if (!line.equals("$$$$$")) {
                            String[] value = line.split("");
                            for (int j = 0; j < value.length; j++) {
                                this.tiles[i][j].setText(value[j]);
                            }
                        }

                    }
                }
                if ((line = br.readLine()) != null) {
                    for (int i = 0; i < 5; i++) {
                        String[] value = line.split(" ");
                        for (int j = 0; j < value.length; j++) {
                            this.correct[j] = Boolean.parseBoolean(value[j]);
                        }
                    }
                }
                if ((line = br.readLine()) != null) {
                    this.points = Integer.parseInt(line);
                    this.score.setText("Score: " + points + " ");
                }
                if ((line = br.readLine()) != null) {
                    this.label.setText(line);
                }
                if ((line = br.readLine()) != null) {
                    this.chance = Integer.parseInt(line);
                }
                if ((line = br.readLine()) != null) {
                    this.letter = Integer.parseInt((line));
                }
                if ((line = br.readLine()) != null) {
                    this.word = line;
                }
                for (int i = 0; i < 6; i++) {
                    if ((line = br.readLine()) != null) {
                        String[] value = line.split(" ");
                        for (int j = 0; j < value.length; j++) {
                            String s = value[j];
                            if (s.equals("green")) {
                                this.tiles[i][j].setBackground(Color.GREEN);
                                this.tiles[i][j].setOpaque(true);
                            } else if (s.equals("darkgray")) {
                                this.tiles[i][j].setBackground(Color.darkGray);
                                this.tiles[i][j].setOpaque(true);
                            } else if (s.equals("red")) {
                                this.tiles[i][j].setBackground(Color.RED);
                                this.tiles[i][j].setOpaque(true);
                            } else if (s.equals("yellow")) {
                                this.tiles[i][j].setBackground(Color.YELLOW);
                                this.tiles[i][j].setOpaque(true);
                            } else {
                                this.tiles[i][j].setBackground(Color.WHITE);
                                this.tiles[i][j].setOpaque(true);
                            }
                        }
                    }

                }
                if ((line = br.readLine()) != null) {
                    this.canContinue = Boolean.parseBoolean(line);
                }
                if ((line = br.readLine()) != null) {
                    this.nextButton.setEnabled(line.equals("true"));
                }

                if ((line = br.readLine()) != null) {
                    this.saveButton.setEnabled(line.equals("true"));
                }
                if ((line = br.readLine()) != null) {
                    this.gameOver = Boolean.parseBoolean(line);
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found Error: " + e);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "An error occurred while loading the game.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception ex) {
                System.out.println("Can't close BufferedReader: " + ex);
            }
        }
    }


    // Method for loading game data from a file
    public void loadGame() {
        readFile("saveGame.txt");
    }


}

