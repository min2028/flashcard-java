package ui;

import model.Divider;
import model.FlashCard;
import model.Subject;

import javax.naming.Name;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

// Flashcard Generator Application
public class FlashcardGenerator {
    private Scanner input;
    private FlashCard flashCard;
    private boolean programRunning;
    private Subject subject;
    private Divider divider;
    private int track = 0;

    // EFFECTS: run the FlashcardGenerator
    public FlashcardGenerator() {
        runFlashcardGenerator();
    }

    // MODIFIES: this
    // EFFECTS: process user input and welcoming the user
    public void runFlashcardGenerator() {
        System.out.println("Ready to start a creating flashcards?");
        System.out.println("Please type in one of the option below.");
        input = new Scanner(System.in);
        programRunning = true;
        String cmd;

        while (programRunning) {
            displayOptions();
            cmd = input.next();
            cmd = cmd.toLowerCase();


            if (cmd.equals("quit")) {
                programRunning = false;
            } else {
                isReady(cmd);
            }
        }
        System.out.println("Flashcard generator quitted.");
    }

    // MODIFIES: this
    // EFFECTS: process the user cmd
    public void isReady(String cmd) {
        if (cmd.equals("ready")) {
            createFlashcard();
        } else {
            notUnderstandCmd();
        }
    }

    // EFFECTS: display the ready or quit options
    public void displayOptions() {
        System.out.println("Ready");
        System.out.println("Quit");
    }

    //////////////////////////////////////
    // MODIFIES: this
    // EFFECTS: Create a flashCard
    public void createFlashcard() {
        flashCard = new FlashCard();
        createName();
        createQuestion();
        createAnswer();
        createDate();
        review();

    }

    // MODIFIES: this
    // EFFECTS: set name for the flashCard
    public void createName() {
        System.out.println("Please give a name.");
        String name = input.next();
        flashCard.setName(name);
    }

    // MODIFIES: this
    // EFFECTS: set question for the flashCard
    public void createQuestion() {
        System.out.println("Please enter the question.");
        String question = input.next();
        flashCard.createQuestion(question);
    }

    // MODIFIES: this
    // EFFECTS: set answer for the flashCard
    public void createAnswer() {
        System.out.println("Please enter the answer.");
        String answer = input.next();
        flashCard.createAnswer(answer);
    }

    // MODIFIES: this
    // EFFECTS: set date for the flashCard
    public void createDate() {
        System.out.println("Please enter today's date in digits.");
        System.out.println("Day:");
        int day = input.nextInt();
        System.out.println("Month:");
        int month = input.nextInt();
        System.out.println("Year:");
        int year = input.nextInt();
        flashCard.setDayMonthYear(day, month, year);
    }

    //////////////////////////////////////

    public void review() {
        System.out.println("Would you like to review the information of the flashcard you just entered?");
        System.out.println("Please enter: yes or no");
        String yesorno = input.next();
        if (yesorno.toLowerCase().equals("yes")) {
            reviewFlashcardInfo();
            if (track > 0) {    // Change later
                anotherSubject();
            }
            subjectCreator();
            anotherflashcard();
        }
        if (yesorno.toLowerCase().equals("no")) {
            subjectCreator();
            anotherflashcard();
        } else {
            notUnderstandCmd();
        }
    }

    // EFFECTS: return the name, question, answer and date of the flashCard
    private void reviewFlashcardInfo() {
        System.out.println(flashCard.getName());
        System.out.println(flashCard.getAnswer());
        System.out.println(flashCard.getQuestion());
        System.out.println("Day/Month/Year:" + flashCard.getDate());
    }

    // MODIFIES: this
    // EFFECTS: create a subject name and adding the flashcard to it.
    public void subjectCreator() {
        subject = new Subject();
        System.out.println("Please enter the name of the subject for the newly created flashcard to be put under");
        String subjectName = input.next();
        subject.setSubjectName(subjectName);
        subject.addFlashCard(flashCard);
        track++;
    }

    public void dividerCreator() {
        System.out.println("Please give the name to the divider the subject will be added to.");
        String dividerName = input.next();
        divider.setDividerName(dividerName);
        divider.addSubject(subject);
    }

    public void anotherflashcard() {
        System.out.println("Would you like to create another flashcard?");
        System.out.println("Please enter: yes or no");
        String yesorno = input.next();
        if (yesorno.toLowerCase().equals("yes")) {
            createFlashcard();
        }
        if (yesorno.toLowerCase().equals("no")) {
            System.out.println("You have created " + Integer.toString(subject.subjectSize()) + " flashcards");
        } else {
            notUnderstandCmd();
        }
    }

    public void anotherSubject() {
        System.out.println("Would you like to create another subject to add the flashCard to?");
        String yesorno = input.next();
        if (yesorno.toLowerCase().equals("yes")) {
            subjectCreator();
        }
        if (yesorno.toLowerCase().equals("no")) {
            System.out.println("Here is the list of existing subject:");
            // input
        } else {
            notUnderstandCmd();
        }
    }

    public void anotherDivider() {
        System.out.println("Would you like to create another divider to add the subject to?");
        String yesorno = input.next();
        if (yesorno.toLowerCase().equals("yes")) {
            dividerCreator();
        }
        if (yesorno.toLowerCase().equals("no")) {
            System.out.println("Here is the list of existing divider:");
            // Input
        } else {
            notUnderstandCmd();
        }
    }

    public void notUnderstandCmd() {
        System.out.println("Sorry. Please choose one of the options.");
    }



}
