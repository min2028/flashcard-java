package ui;

import model.Divider;
import model.DividerList;
import model.FlashCard;
import model.Subject;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class NewWindowToCreateFlashcard extends JFrame implements ActionListener {

    private JSplitPane splitPane = new JSplitPane();  // split the window in top and bottom
    private JPanel topPanel = new JPanel();       // container panel for the top
    private JPanel bottomPanel = new JPanel();    // container panel for the bottom
    private JScrollPane scrollPaneQuestion = new JScrollPane(); // makes the text scrollable
    private JScrollPane scrollPaneAnswer = new JScrollPane(); // makes the text scrollable
    private JTextArea textAreaQuestion = new JTextArea("Question: ");     // the text
    private JTextArea textAreaAnswer = new JTextArea("Answer: ");     // the text
    private JPanel inputPanel = new JPanel();      // under the text a container for all the input elements

    private JFormattedTextField day = new JFormattedTextField("00");   // a textField for the text the user inputs
    private JFormattedTextField month = new JFormattedTextField("00");   // a textField for the text the user inputs
    private JFormattedTextField year = new JFormattedTextField("0000");   // a textField for the text the user inputs

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

    private JButton set;

    private JMenuBar menuBar;

    private FlashCard flashcard;
    private Subject subject;
    private Divider divider;
    private DividerList dividerList = new DividerList();

    private static final String JSON_STORE = "./data/dividerListForGUI.json";
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    private int initialSet = 0;

    // Source: https://stackoverflow.com/questions/15694107/how-to-layout-multiple-panels-on-a-jframe-java
    public NewWindowToCreateFlashcard() throws FileNotFoundException {

        super("Flashcard Generator");

        new SoundTrack("/Users/sittpaing/Downloads/jazz.wav");

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

    public void designBottomPanel() {
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        bottomPanel.add(scrollPaneAnswer);
        scrollPaneAnswer.setViewportView(textAreaAnswer);
        bottomPanel.add(inputPanel);

        designInputPanel();
    }

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
        inputPanel.add(set);

    }


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

    // REQUIRES: day, month and year must be digits
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("save")) {
            try {
                jsonWriter.open();
                jsonWriter.write(dividerList);
                jsonWriter.close();
            } catch (FileNotFoundException exception) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }

        if (e.getActionCommand().equals("load")) {
            loadAndPrint();
        }


        if (e.getActionCommand().equals("set")) {
            if (initialSet == 0) {
                setInitialFlashcardinfo();
                initialSet++;
            } else {
                categorizeFlashcard();
            }
        }
    }

    public void loadAndPrint() {
        try {
            dividerList = jsonReader.read();
            redirectSystemStreams();
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
        subject.addFlashCard(flashcard);
        divider.setDividerName(dividerTextField.getText());
        divider.addSubject(subject);
        dividerList.addDivider(divider);
    }

    public void categorizeFlashcard() {
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

    public void sameDividerAndSubject() {
        for (Divider d : dividerList.getDividers()) {
            for (Subject s : d.getSubjects()) {
                if (d.getDividerName().equals(dividerTextField.getText())
                        && s.getSubjectName().equals(subjectTextField.getText())) {
                    s.addFlashCard(flashcard);
                }
            }
        }
    }

    public void sameDividerdifferentSubject() {
        for (Divider d : dividerList.getDividers()) {
            for (Subject s : d.getSubjects()) {
                if (d.getDividerName().equals(dividerTextField.getText())
                        && !(s.getSubjectName().equals(subjectTextField.getText()))) {
                    subject = new Subject();
                    subject.setSubjectName(subjectTextField.getText());
                    subject.addFlashCard(flashcard);
                    d.addSubject(subject);
                }
            }
        }
    }

    public void differentDividerSameSubject() {
        for (Divider d : dividerList.getDividers()) {
            for (Subject s : d.getSubjects()) {
                if (!(d.getDividerName().equals(dividerTextField.getText()))
                        && s.getSubjectName().equals(subjectTextField.getText())) {
                    subject = new Subject();
                    subject.setSubjectName(subjectTextField.getText());
                    subject.addFlashCard(flashcard);
                    divider = new Divider();
                    divider.setDividerName(dividerTextField.getText());
                    divider.addSubject(subject);
                    dividerList.addDivider(divider);
                }
            }
        }
    }

    public void differentDividerAndSubject() {
        for (Divider d : dividerList.getDividers()) {
            for (Subject s : d.getSubjects()) {
                if (!(d.getDividerName().equals(dividerTextField.getText()))
                        && !(s.getSubjectName().equals(subjectTextField.getText()))) {
                    subject = new Subject();
                    subject.setSubjectName(subjectTextField.getText());
                    subject.addFlashCard(flashcard);
                    divider = new Divider();
                    divider.setDividerName(dividerTextField.getText());
                    divider.addSubject(subject);
                    dividerList.addDivider(divider);
                }
            }
        }
    }

    // EFFECTS: prints all the dividers in dividerList to the console
    private void printDividers() {
        List<Divider> dividers = dividerList.getDividers();
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
        java.util.List<Subject> subjects = divider.getSubjects();
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
        List<FlashCard> flashcards = subject.getFlashcards();

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
    private void updateTextArea(final String text) {
        SwingUtilities.invokeLater(() -> textArea.append(text));
    }

    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                updateTextArea(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextArea(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }
}
