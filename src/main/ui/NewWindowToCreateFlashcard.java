package ui;

import model.Divider;
import model.DividerList;
import model.FlashCard;
import model.Subject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

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

    private JButton set;         // and a "send" button

    private JMenuBar menuBar;

    private FlashCard flashcard = new FlashCard();
    private Subject subject = new Subject();
    private Divider divider = new Divider();
    private DividerList dividerList = new DividerList();

    // Source: https://stackoverflow.com/questions/15694107/how-to-layout-multiple-panels-on-a-jframe-java
    public NewWindowToCreateFlashcard() throws FileNotFoundException {

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
        JMenuItem print = new JMenuItem("Print");
        m1.add(save);
        m1.add(load);
        m1.add(print);

        menuBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        topPanel.add(menuBar);
    }

//    protected MaskFormatter createFormatter(String s) {
//        MaskFormatter formatter = null;
//        try {
//            formatter = new MaskFormatter(s);
//        } catch (java.text.ParseException exc) {
//            System.err.println("Bad Formatter: " + exc.getMessage());
//            System.exit(-1);
//        }
//        return formatter;
//    }

    // REQUIRES: day, month and year must be digits
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("set")) {
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
            System.out.println("Set successfully");
        }
    }
}
