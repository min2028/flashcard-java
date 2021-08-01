package ui;

import model.Divider;
import model.DividerList;
import model.FlashCard;
import model.Subject;

import java.util.Scanner;

// Flashcard Generator Application
public class FlashcardGenerator {
    private Scanner input;
    private FlashCard flashCard;

    private Subject subject;

    private Divider divider;

    private DividerList dividerList = new DividerList();

    private boolean programRunning;
    private boolean run;
    private boolean makeAnotherFlashcard;

    // EFFECTS: run the FlashcardGenerator
    public FlashcardGenerator() {
        runFlashcardGenerator();
    }

    // MODIFIES: this
    // EFFECTS: process user input and welcoming the user
    public void runFlashcardGenerator() {
        System.out.println("Ready to start a creating flashcards?");
        System.out.println("Please type in one of the option below:");
        displayOptions();
        programRunning = true;
        String cmd;
        input = new Scanner(System.in);
        while (programRunning) {
            cmd = input.next();
            cmd = cmd.toLowerCase();

            if (cmd.equals("quit")) {
                programRunning = false;
            } else {
                isReady(cmd);
                makeAnotherFlashcard = true;

                while (makeAnotherFlashcard) {
                    createAnotherFlashcard();
                }
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: process the user cmd
    public void isReady(String cmd) {
        if (cmd.equals("ready")) {
            createFlashcard();
            subjectCreator();
            dividerCreator();
            review();
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
    }

    // MODIFIES: this
    // EFFECTS: set name for the flashCard
    public void createName() {
        input = new Scanner(System.in);
        System.out.println("Please give a name.");
        String name = input.nextLine();
        flashCard.setName(name);
    }

    // MODIFIES: this
    // EFFECTS: set question for the flashCard
    public void createQuestion() {
        input = new Scanner(System.in);
        System.out.println("Please enter the question.");
        String question = input.nextLine();
        flashCard.createQuestion(question);
    }

    // MODIFIES: this
    // EFFECTS: set answer for the flashCard
    public void createAnswer() {
        input = new Scanner(System.in);
        System.out.println("Please enter the answer.");
        String answer = input.nextLine();
        flashCard.createAnswer(answer);
    }

    // MODIFIES: this
    // EFFECTS: set date for the flashCard
    public void createDate() {
        input = new Scanner(System.in);
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
        run = true;

        while (run) {
            String yesorno = input.next();
            if (yesorno.toLowerCase().equals("yes")) {
                reviewFlashcardInfo();
                run = false;
            } else if (yesorno.toLowerCase().equals("no")) {
                run = false;
            } else {
                notUnderstandCmd();
            }
        }
    }

    // EFFECTS: return the name, question, answer and date of the flashCard
    private void reviewFlashcardInfo() {
        System.out.println("Name: " + flashCard.getName());
        System.out.println("Question: " + flashCard.getQuestion());
        System.out.println("Answer: " + flashCard.getAnswer());
        System.out.println("Created on: " + flashCard.getDate() + " (Day/Month/Year)");
        System.out.println("Subject: " + subject.getSubjectName());
        System.out.println("Divider: " + divider.getDividerName());
    }

    // MODIFIES: this
    // EFFECTS: create a subject name and adding the flashcard to it.
    public void subjectCreator() {
        subject = new Subject();
        input = new Scanner(System.in);
        System.out.println("Please enter the name of the subject.");
        String subjectName = input.nextLine();
        subject.setSubjectName(subjectName);
        subject.addFlashCard(flashCard);
    }

    public void dividerCreator() {
        divider = new Divider();
        input = new Scanner(System.in);
        System.out.println("Please give the name to the divider the subject will be added to.");
        String dividerName = input.nextLine();
        divider.setDividerName(dividerName);
        divider.addSubject(subject);
        dividerList.addDivider(divider);
    }

    public void notUnderstandCmd() {
        System.out.println("Sorry. Please choose one of the options.");
        run = true;
    }

    ///////////////////////////////////////////
    public void viewDividers() {
        System.out.println("Name of the Dividers:");
        for (int i = 0; i < dividerList.dividerListSize(); i++) {
            System.out.println((i + 1) + ". " + dividerList.getDivider(i).getDividerName());
            System.out.println();
        }
    }

    public void viewSubjects() {
        System.out.println("Name of the Subjects:");
        for (int i = 0; i < dividerList.dividerListSize(); i++) {
            for (int a = 0; a < divider.dividerSize(); a++) {
                System.out.println((a + 1) + ". " + divider.getSubject(a).getSubjectName());
                System.out.println();
            }
        }
    }

    public void viewFlashcards() {
        System.out.println("Name of the Flashcards:");
        for (int i = 0; i < dividerList.dividerListSize(); i++) {
            for (int a = 0; a < divider.dividerSize(); a++) {
                for (int b = 0; b < subject.subjectSize(); b++) {
                    System.out.println((b + 1) + ". " + subject.getFlashCard(b).getName());
                    System.out.println("Question: " + subject.getFlashCard(b).getQuestion());
                    System.out.println("Answer: " + subject.getFlashCard(b).getAnswer());
                    System.out.println("Created on: " + subject.getFlashCard(b).getDate() + " (Day/Month/Year)");
                    System.out.println("Subject: " + subject.getSubjectName());
                    System.out.println("Divider: " + divider.getDividerName());
                    System.out.println();
                }
            }
        }
    }

    //////////////////////////////////////////
    public void createAnotherFlashcard() {
        flashCard = new FlashCard();
        System.out.println("Would you like to create another flashcard?");
        System.out.println("Please enter: yes or no");
        input = new Scanner(System.in);
        String yesorno = input.next();
        if (yesorno.toLowerCase().equals("yes")) {
            createFlashcard();
            displayNewOrExistingSubjectOptionAndProcess();
        }
        if (yesorno.toLowerCase().equals("no")) {
            viewDividers();
            viewSubjects();
            viewFlashcards();
            endgenerator();
        } else if (!((yesorno.toLowerCase().equals("yes") || (yesorno.toLowerCase().equals("no"))))) {
            notUnderstandCmd();
        }
    }

    public void displayNewOrExistingSubjectOptionAndProcess() {
        input = new Scanner(System.in);
        System.out.println("e - Add to existing subject");
        System.out.println("n - Create new subject");
        System.out.println("Choose e or n.");
        String existingOrNew = input.nextLine();
        if (existingOrNew.toLowerCase().equals("e")) {
            viewSubjects();
            System.out.println("Choose number of the name of the subject:");
            addtoExistingSubject();
        }
        if (existingOrNew.toLowerCase().equals("n")) {
            subjectCreator();
            System.out.println("Would you like to add the subject to a new or existing divider?");
            displayNewOrExistingDividerOptionAndProcess();
        }
    }

    public void displayNewOrExistingDividerOptionAndProcess() {
        input = new Scanner(System.in);
        System.out.println("e - Add to existing divider");
        System.out.println("n - Create new divider");
        System.out.println("Choose e or n.");
        String existingOrNew = input.nextLine();
        if (existingOrNew.toLowerCase().equals("e")) {
            viewDividers();
            System.out.println("Choose number of the name of the divider:");
            addtoExistingDivider();
        }
        if (existingOrNew.toLowerCase().equals("n")) {
            dividerCreator();
        }
    }

    public void addtoExistingSubject() {
        input = new Scanner(System.in);
        String numberofSubjectName = input.next();
        divider.getSubject(Integer.parseInt(numberofSubjectName) - 1).addFlashCard(flashCard);
        System.out.println("Flashcard added successfully");
        createAnotherFlashcard();
    }

    public void addtoExistingDivider() {
        input = new Scanner(System.in);
        String numberofDividerName = input.next();
        dividerList.getDivider(Integer.parseInt(numberofDividerName) - 1).addSubject(subject);
        System.out.println("Subject added successfully");
        createAnotherFlashcard();
    }

    public void endgenerator() {
        System.out.println("Would you like to end the generator?");
        input = new Scanner(System.in);
        String yesorno = input.next();
        if (yesorno.toLowerCase().equals("yes")) {
            programRunning = false;
            makeAnotherFlashcard = false;
        }
        if (yesorno.toLowerCase().equals("no")) {
            makeAnotherFlashcard = true;
            programRunning = true;
        } else if (!((yesorno.toLowerCase().equals("yes") || (yesorno.toLowerCase().equals("no"))))) {
            notUnderstandCmd();
        }
    }
}
