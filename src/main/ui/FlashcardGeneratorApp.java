package ui;

import javax.swing.*;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;

// Represents a flashcard generator GUI
public class FlashcardGeneratorApp extends JFrame implements ActionListener {

    private JButton ready;
    private JButton quit;
    private JMenuBar menuBar;

    private JLabel background;
    private JPanel optionsPanel;

    private Dimension optionsDimension = new Dimension(300, 100);

    // EFFECTS: Create a flashcard generator window with background
    public FlashcardGeneratorApp() {
        super("Flashcard Generator");
        setSize(940, 636);
        JPanel backgroundpanel = new JPanel();
        background = new JLabel("");
        background.setIcon(new ImageIcon("/Users/sittpaing/Downloads/flashcardIcon2.jpeg"));

        options();
        backgroundpanel.add(background);
        this.getContentPane().add(BorderLayout.CENTER, backgroundpanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(true);
        centreOnScreen();
        setVisible(true);
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    // MODIFIES: this
    // EFFECTS: add the functions to the Ready and Quit JButton and display on the optionsPanel
    private void options() {
        optionsPanel = new JPanel();

        ready = new JButton("Ready");
        ready.setPreferredSize(optionsDimension);
        ready.setActionCommand("ready");
        ready.addActionListener(this);
        optionsPanel.add(ready);

        quit = new JButton("Quit");
        quit.setPreferredSize(optionsDimension);
        quit.setActionCommand("quit");
        quit.addActionListener(this);
        optionsPanel.add(quit);

        this.getContentPane().add(BorderLayout.SOUTH, optionsPanel);

    }

    // EFFECTS: processes the action on the JButtons, if clicked on Ready,
    //          create a new Window to generate flashcard
    //          If clicked on quit, the flashcard generator quits
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ready")) {
            try {
                new NewWindowToCreateFlashcard().setVisible(true);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

        // Source: https://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
        if (e.getActionCommand().equals("quit")) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }


    public static void main(String[] args) throws IOException {
        new FlashcardGeneratorApp();
        try {
            new FlashcardGenerator();
            System.out.println("Flashcard generator quitted.");
        } catch (FileNotFoundException e) {
            System.out.println("file not found: Choose another file");
        }
    }
}
