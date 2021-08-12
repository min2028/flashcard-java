package ui;

import model.Divider;
import model.DividerList;
import model.FlashCard;
import model.Subject;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

// Represent a new window where the flashcard information will be inputted
public class NewWindowToCreateFlashcard extends JFrame implements ActionListener {

    private JSplitPane splitPane = new JSplitPane();  // split the window in top and bottom
    private JPanel topPanel = new JPanel();       // container panel for the top
    private JPanel bottomPanel = new JPanel();    // container panel for the bottom
    private JScrollPane scrollPaneQuestion = new JScrollPane(); // makes the text scrollable
    private JScrollPane scrollPaneAnswer = new JScrollPane(); // makes the text scrollable
    private JTextArea textAreaQuestion = new JTextArea("Question: ");     // the text
    private JTextArea textAreaAnswer = new JTextArea("Answer: ");     // the text
    private JPanel inputPanel = new JPanel();      // under the text a container for all the input elements

    private JFormattedTextField day = new JFormattedTextField("00");
    private JFormattedTextField month = new JFormattedTextField("00");
    private JFormattedTextField year = new JFormattedTextField("0000");

    private final JPanel compartmentPanel = new JPanel();
    private final JTextField nameTextField = new JTextField();
    private final JLabel namelabel = new JLabel("Name: ");

    private final JTextField subjectTextField = new JTextField();
    private final JLabel subjectlabel = new JLabel("Subject Name: ");

    private final JTextField dividerTextField = new JTextField();
    private final JLabel dividerlabel = new JLabel("Divider Name: ");

    private final JLabel daylabel = new JLabel("Day: ");
    private final JLabel monthlabel = new JLabel("Month: ");
    private final JLabel yearlabel = new JLabel("Year: ");

    private JTextArea textArea = new JTextArea();
    private JTextArea reviewArea = new JTextArea();

    private JButton set;
    private JButton review;

    private JMenuBar menuBar;

    private FlashCard flashcard;
    private Subject subject;
    private Divider divider;
    private DividerList dividerList = new DividerList();

    private static final String JSON_STORE = "./data/dividerListForGUI.json";
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    private Clip clip;

    private int initialSet = 0;

    // EFFECTS: Create a new window where the flashcard information input area and menu bar will be created
    // Source: https://stackoverflow.com/questions/15694107/how-to-layout-multiple-panels-on-a-jframe-java
    public NewWindowToCreateFlashcard() throws FileNotFoundException {

        super("Flashcard Generator");

        playSound("/Users/sittpaing/Downloads/jazz.wav");

        setPreferredSize(new Dimension(940, 636));

        getContentPane().setLayout(new GridLayout());

        getContentPane().add(splitPane);

        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(300);
        splitPane.setTopComponent(topPanel);
        splitPane.setBottomComponent(bottomPanel);

        designTopPanel();

        designBottomPanel();

        pack();
    }

    // MODIFIES: this
    // EFFECTS: design the Top panel with JTextField for the flashcard, subject and divider name input and
    //          JTextArea for flashcard question input
    public void designTopPanel() {
        addMenuBar();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(compartmentPanel);

        compartmentPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        compartmentPanel.setLayout(new BoxLayout(compartmentPanel, BoxLayout.X_AXIS));
        compartmentPanel.add(namelabel);
        compartmentPanel.add(nameTextField);
        compartmentPanel.add(subjectlabel);
        compartmentPanel.add(subjectTextField);
        compartmentPanel.add(dividerlabel);
        compartmentPanel.add(dividerTextField);

        topPanel.add(scrollPaneQuestion);
        scrollPaneQuestion.setViewportView(textAreaQuestion);
    }

    // MODIFIES: this
    // EFFECTS: design the bottom panel with the JTextArea for answer input and new input panel
    public void designBottomPanel() {
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        bottomPanel.add(scrollPaneAnswer);
        scrollPaneAnswer.setViewportView(textAreaAnswer);
        bottomPanel.add(inputPanel);

        designInputPanel();
    }

    // MODIFIES: this
    // EFFECTS: Design the input panel with the date, month and year JTextField and Set and Review JButton.
    public void designInputPanel() {
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));

        inputPanel.add(daylabel);
        inputPanel.add(day);
        inputPanel.add(monthlabel);
        inputPanel.add(month);
        inputPanel.add(yearlabel);
        inputPanel.add(year);

        set = new JButton("Set");
        set.setActionCommand("set");
        set.addActionListener(this);

        review = new JButton("Review");
        review.setActionCommand("review");
        review.addActionListener(this);
        inputPanel.add(set);
        inputPanel.add(review);

    }

    // MODIFIES: this
    // EFFECTS: Create a menu Bar and add functionality the menu items in the menu Bar
    private void addMenuBar() {
        menuBar = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        menuBar.add(m1);
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        m1.add(save);
        m1.add(load);

        save.setActionCommand("save");
        save.addActionListener(this);

        load.setActionCommand("load");
        load.addActionListener(this);

        menuBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        topPanel.add(menuBar);
    }

    // REQUIRES: set must be clicked before review
    // MODIFIES: this
    // EFFECTS: process the clicks to JButton, when Save is clicked, save the input to the file.
    //          When load is clicked, load the flashcard info from the file and print it on a new window panel.
    //          When review is clicked, display the flashcard info that was just set, on a new window panel.
    //          When set is clicked, set and categorize the input information for the flashcard, subject and divider
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("save")) {
            saveFlashcardSubjectDivider();
        }

        if (e.getActionCommand().equals("load")) {
            loadAndPrint();
        }

        if (e.getActionCommand().equals("review")) {
            review();
        }

        if (e.getActionCommand().equals("set")) {
            if (initialSet == 0) {
                setInitialFlashcardinfo();
                initialSet++;
            } else {
                try {
                    categorizeFlashcard();
                } catch (Exception exception) {
                    System.out.println();
                }
            }
        }
    }

    // EFFECTS: saves the dividerList to file
    private void saveFlashcardSubjectDivider() {
        try {
            jsonWriter.open();
            jsonWriter.write(dividerList);
            jsonWriter.close();
        } catch (FileNotFoundException exception) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads dividerList from file and prints all the information of the dividers, subjects and flashcards
    //          into the JTextArea of the new window Panel
    public void loadAndPrint() {
        try {
            dividerList = jsonReader.read();
            redirectSystemStreams(textArea);
            printDividers();
            JFrame printWindow = new JFrame("Flashcard Information");
            printWindow.setSize(600, 600);
            textArea.setMaximumSize(new Dimension(500, 500));
            JScrollPane scrollPanePrint = new JScrollPane(textArea);
            printWindow.getContentPane().add(scrollPanePrint);
            printWindow.setVisible(true);
        } catch (IOException exception) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // REQUIRES: flashcard information must be set first
    // MODIFIES: this
    // EFFECTS: output all the information of the flashcard that would be displayed on the JTextArea of the
    //          new window panel
    public void review() {
        redirectSystemStreams(reviewArea);
        System.out.println("Name: " + flashcard.getName());
        System.out.println("Question: " + flashcard.getQuestion());
        System.out.println("Answer: " + flashcard.getAnswer());
        System.out.println("Created on: " + flashcard.getDate() + " (Day/Month/Year)");
        System.out.println("Subject: " + subject.getSubjectName());
        System.out.println("Divider: " + divider.getDividerName());
        System.out.println("///////////////////////");
        System.out.println();
        JFrame reviewWindow = new JFrame("Flashcard Review");
        reviewWindow.setSize(600, 600);
        reviewArea.setMaximumSize(new Dimension(500, 500));
        JScrollPane scrollPaneReview = new JScrollPane(reviewArea);
        reviewWindow.getContentPane().add(scrollPaneReview);
        reviewWindow.setVisible(true);
    }

    // REQUIRES: day, month and year must be integers
    // MODIFIES: this
    // EFFECTS: set the question, answer, date and name of the flashcard and also creating and setting the name for
    //          new subject and divider
    public void setInitialFlashcardinfo() {
        divider = new Divider();
        subject = new Subject();
        flashcard = new FlashCard();
        flashcard.setDayMonthYear(Integer.parseInt(day.getText()),
                Integer.parseInt(month.getText()), Integer.parseInt(year.getText()));
        flashcard.setName(nameTextField.getText());
        flashcard.createQuestion(textAreaQuestion.getText());
        flashcard.createAnswer(textAreaAnswer.getText());
        subject.setSubjectName(subjectTextField.getText());
        subject.add(flashcard);
        divider.setDividerName(dividerTextField.getText());
        divider.add(subject);
        dividerList.add(divider);
    }

    // REQUIRES: day, month and year must be integers
    // MODIFIES: this
    // EFFECTS: adding the flashcard into new and existing subject and divider
    public void categorizeFlashcard() throws Exception {
        flashcard = new FlashCard();
        flashcard.setDayMonthYear(Integer.parseInt(day.getText()),
                Integer.parseInt(month.getText()), Integer.parseInt(year.getText()));
        flashcard.setName(nameTextField.getText());
        flashcard.createQuestion(textAreaQuestion.getText());
        flashcard.createAnswer(textAreaAnswer.getText());
        sameDividerAndSubject();
        sameDividerdifferentSubject();
        differentDividerSameSubject();
        differentDividerAndSubject();
    }

    // MODIFIES: this
    // EFFECTS: Adding the new flashcard to the existing subject and divider if the name of the subject and divider
    //          input for this flashcard is already in the dividerList
    public void sameDividerAndSubject() {
        for (Divider d : dividerList.getList()) {
            for (Subject s : d.getList()) {
                if (d.getDividerName().equals(dividerTextField.getText())
                        && s.getSubjectName().equals(subjectTextField.getText())) {
                    subject = s;
                    subject.add(flashcard);
                    divider = d;
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Adding the new flashcard to a newly created subject in the existing divider if the name of the
    //          divider inputted is the same as the divider in the dividerList but the name of the subject is new.
    public void sameDividerdifferentSubject() {
        for (Divider d : dividerList.getList()) {
            for (Subject s : d.getList()) {
                if (d.getDividerName().equals(dividerTextField.getText())
                        && !(s.getSubjectName().equals(subjectTextField.getText()))) {
                    subject = new Subject();
                    subject.setSubjectName(subjectTextField.getText());
                    subject.add(flashcard);
                    divider = d;
                    divider.add(subject);
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Adding the new flashcard to a newly created subject and divider if the name of the divider inputted
    //          is new but the subject name is the same
    public void differentDividerSameSubject() {
        for (Divider d : dividerList.getList()) {
            for (Subject s : d.getList()) {
                if (!(d.getDividerName().equals(dividerTextField.getText()))
                        && s.getSubjectName().equals(subjectTextField.getText())) {
                    subject = new Subject();
                    subject.setSubjectName(subjectTextField.getText());
                    subject.add(flashcard);
                    divider = new Divider();
                    divider.setDividerName(dividerTextField.getText());
                    divider.add(subject);
                    dividerList.add(divider);
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Adding the new flashcard to a newly created subject and divider if the name of the divider and
    // subject inputted is new.
    public void differentDividerAndSubject() {
        for (Divider d : dividerList.getList()) {
            for (Subject s : d.getList()) {
                if (!(d.getDividerName().equals(dividerTextField.getText()))
                        && !(s.getSubjectName().equals(subjectTextField.getText()))) {
                    subject = new Subject();
                    subject.setSubjectName(subjectTextField.getText());
                    subject.add(flashcard);
                    divider = new Divider();
                    divider.setDividerName(dividerTextField.getText());
                    divider.add(subject);
                    dividerList.add(divider);
                }
            }
        }
    }

    // EFFECTS: prints all the dividers in dividerList to the console
    private void printDividers() {
        List<Divider> dividers = dividerList.getList();
        int i = 1;

        for (Divider d : dividers) {
            System.out.println(i++ + ". " + "Divider Name:");
            System.out.println(d.getDividerName());
            System.out.println();
            printSubjects(d);
        }
    }


    // EFFECTS: prints all the subjects in divider to the console
    private void printSubjects(Divider divider) {
        java.util.List<Subject> subjects = divider.getList();
        int i = 1;

        for (Subject s : subjects) {
            System.out.println(i++ + ". " + "Subject Name:");
            System.out.println(s.getSubjectName());
            System.out.println();
            printFlashcards(s);
        }
    }

    // EFFECTS: prints all the flashcards in Subject to the console
    private void printFlashcards(Subject subject) {
        List<FlashCard> flashcards = subject.getList();

        System.out.println("Flashcard Info: ");
        for (FlashCard f : flashcards) {
            System.out.println("Flashcard Name: " + f.getName());
            System.out.println();
            System.out.println("Flashcard Question: " + f.getQuestion());
            System.out.println();
            System.out.println("Flashcard Answer: " + f.getAnswer());
            System.out.println();
            System.out.println("Flashcard Date: " + f.getDate());
            System.out.println("/////////////////////////////");
            System.out.println();
        }
    }

    // Source: http://unserializableone.blogspot.com/2009/01/redirecting-systemout-and-systemerr-to.html
    // MODIFIES: this
    // EFFECTS: update the JTextArea with the output printed on the console
    private void updateTextArea(final String text, JTextArea jtextArea) {
        SwingUtilities.invokeLater(() -> jtextArea.append(text));
    }

    // EFFECTS: redirect the output that would be printed on the console to the designated JTextArea
    private void redirectSystemStreams(JTextArea jtextArea) {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                updateTextArea(String.valueOf((char) b), jtextArea);
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextArea(new String(b, off, len), jtextArea);
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }

    // EFFECTS: play the soundtrack given the path to the sound file
    public void playSound(String soundPath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundPath));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error with playing sound.");
            e.printStackTrace();
        }
    }
}
