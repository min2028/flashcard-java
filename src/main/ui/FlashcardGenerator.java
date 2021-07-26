package ui;

import model.Divider;
import model.FlashCard;
import model.Subject;

import javax.naming.Name;
import java.util.Locale;
import java.util.Scanner;

// Flashcard Generator Application
public class FlashcardGenerator {
    private Scanner input;
    private FlashCard flashCard;
    private boolean programRunning;
    private Subject subject;
    private Divider divider;

    // EFFECTS: run the FlashcardGenerator
    public FlashcardGenerator() {
        runFlashcardGenerator();
    }

    public void runFlashcardGenerator() {
        System.out.println("Ready to start a creating flashcards?");
        System.out.println("Please type in one of the option below.");
        init();
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

    public void isReady(String cmd) {
        if (cmd.equals("ready")) {
            createName();
            createQuestion();
            createAnswer();
            createDate();
        } else {
            notUnderstandCmd();
        }
    }

    public void displayOptions() {
        System.out.println("Ready");
        System.out.println("Quit");
    }

    public void init() {
        flashCard = new FlashCard();
        subject = new Subject();
        divider = new Divider();
        input = new Scanner(System.in);
    }

    public void createName() {
        System.out.println("Please give a name.");
        String name = input.next();
        flashCard.setName(name);
    }

    public void createQuestion() {
        System.out.println("Please enter the question.");
        String question = input.next();
        flashCard.createQuestion(question);
    }

    public void createAnswer() {
        System.out.println("Please enter the answer.");
        String answer = input.next();
        flashCard.createAnswer(answer);
    }

    public void createDate() {
        System.out.println("Please enter today's date in digits.");
        System.out.println("Day:");
        int day = input.nextInt();
        System.out.println("Month:");
        int month = input.nextInt();
        System.out.println("Year:");
        int year = input.nextInt();
        flashCard.setDayMonthYear(day, month, year);
        review();
    }

    public void review() {
        System.out.println("Would you like to review the information of the flashcard you just entered?");
        System.out.println("Please enter: yes or no");
        String yesorno = input.next();
        if (yesorno.toLowerCase().equals("yes")) {
            reviewFlashcardInfo();
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

    private void reviewFlashcardInfo() {
        System.out.println(flashCard.getName());
        System.out.println(flashCard.getAnswer());
        System.out.println(flashCard.getQuestion());
        System.out.println("Day/Month/Year:" + flashCard.getDate());
    }

    public void subjectCreator() {
        System.out.println("Please enter the name of the subject the newly created flashcard be put under");
        String subjectName = input.next();
        subject.setSubjectName(subjectName);
        subject.addFlashCard(flashCard);
    }

    public void dividerCreator() {
        System.out.println("Would you like to put the subject into the divider?");
        String yesorno = input.next();
        if (yesorno.toLowerCase().equals("yes")) {
            System.out.println("Please give the name to the divider.");
            String dividerName = input.next();
            divider.setDividerName(dividerName);
            System.out.println("Would you like to add the subject into the divider?");
            String addornot = input.next();
            if (addornot.toLowerCase().equals("yes")) {
                divider.addSubject(subject);
            }
            if (addornot.toLowerCase().equals("no")) {
                subjectCreator();
                anotherflashcard();
            }
        }
        if (yesorno.toLowerCase().equals("no")) {
            anotherSubject();
        }
    }

    public void anotherflashcard() {
        FlashCard flashCard = new FlashCard();  //TODO
        System.out.println("Would you like to create another flashcard?");
        System.out.println("Please enter: yes or no");
        String yesorno = input.next();
        if (yesorno.toLowerCase().equals("yes")) {
            System.out.println("wait");  //TODO
        }
        if (yesorno.toLowerCase().equals("no")) {
            System.out.println("You have created " + Integer.toString(subject.subjectSize()) + " flashcards");
        }
    }

    public void anotherSubject() {
        System.out.println("Would you like to create another subject?");
        String yesorno = input.next();
        if (yesorno.toLowerCase().equals("yes")) {
            Subject subject1 = new Subject();
            //TODO
        }
        if (yesorno.toLowerCase().equals("no")) {
            anotherflashcard();
        }
    }

    public void notUnderstandCmd() {
        System.out.println("Sorry. Please choose one of the options.");
    }



}
