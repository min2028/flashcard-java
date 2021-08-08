package ui;

import model.Divider;
import model.DividerList;
import model.FlashCard;
import model.Subject;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Flashcard Generator Application
public class FlashcardGenerator {
    private Scanner input;
    protected FlashCard flashCard;
    private static final String JSON_STORE = "./data/dividerList.json";
    protected Subject subject = new Subject();

    protected Divider divider = new Divider();

    protected DividerList dividerList = new DividerList();

    private boolean programRunning;
    private boolean run;
    private boolean makeAnotherFlashcard;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: run the FlashcardGenerator
    public FlashcardGenerator() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runFlashcardGenerator();
    }

    // MODIFIES: this
    // EFFECTS: process user input and welcoming the user
    public void runFlashcardGenerator() {
        System.out.println("Welcome To Flashcard Generator!!!");
        System.out.println("Ready to start a creating flashcards?");
        System.out.println("Please type in one of the option below:");

        programRunning = true;
        String cmd;
        while (programRunning) {
            displayOptions();
            cmd = input.next();
            cmd = cmd.toLowerCase();

            if (cmd.equals("quit")) {
                programRunning = false;
                makeAnotherFlashcard = false;
            } else if (cmd.equals("load")) {
                loadDividerList();
                programRunning = true;
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
        } else if (cmd.equals("print")) {
            printDividers();
        } else if (cmd.equals("save")) {
            saveDividerList();
        } else {
            notUnderstandCmd();
        }
    }

    // EFFECTS: display the ready or quit options
    public void displayOptions() {
        System.out.println("Ready");
        System.out.println("Quit");
        System.out.println("Save");
        System.out.println("Load");
        System.out.println("Print");
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

    // EFFECTS: output on the console whether the user would like to review the flashcard information and execute
    //          the user input.
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

    // MODIFIES: this
    // EFFECTS: create a divider and adding subject to it and adding the divider to the dividerList
    public void dividerCreator() {
        divider = new Divider();
        input = new Scanner(System.in);
        System.out.println("Please give the name to the divider the subject will be added to.");
        String dividerName = input.nextLine();
        divider.setDividerName(dividerName);
        divider.addSubject(subject);
        dividerList.addDivider(divider);
    }

    // EFFECTS: return to user that the input was not understood
    public void notUnderstandCmd() {
        System.out.println("Sorry. Please choose one of the options.");
        run = true;
    }

    ///////////////////////////////////////////

    // EFFECTS: output the divider's name on the console
    public void viewDividers() {
        System.out.println("Name of the Dividers:");
        for (int i = 0; i < dividerList.dividerListSize(); i++) {
            System.out.println((i + 1) + ". " + dividerList.getDivider(i).getDividerName());
        }
        System.out.println();
    }

    // EFFECTS: output the subject's name on the console
    public void viewSubjects() {
        System.out.println("Name of the Subjects:");
        int i = 1;
        for (Divider d : dividerList.getDividers()) {
            for (Subject s : d.getSubjects()) {
                System.out.println(i++ + ". " + s.getSubjectName());
            }
            System.out.println();
        }
    }

    // EFFECTS: output the flashcard's name, question, answer, the subject and divider it is in on the console
    public void viewFlashcards() {
        System.out.println("Name of the Flashcards:");
        int i = 1;
        for (Divider d : dividerList.getDividers()) {
            for (Subject s : d.getSubjects()) {
                for (FlashCard f : s.getFlashcards()) {
                    System.out.println(i++ + ". " + f.getName());
                    System.out.println("Question: " + f.getQuestion());
                    System.out.println("Answer: " + f.getAnswer());
                    System.out.println("Created on: " + f.getDate() + " (Day/Month/Year)");
                    System.out.println("Subject: " + s.getSubjectName());
                    System.out.println("Divider: " + d.getDividerName());
                    System.out.println();
                }
            }
        }
    }

    //////////////////////////////////////////

    // MODIFIES: this
    // EFFECTS: prompting if the user would create another flashcard
    // or output the flashcard info and ending the generator
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

    // EFFECTS: display the option asking whether to put the flashcard in the new or exisiting divider,
    //          and process user input and executing it
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

    // EFFECTS: display the option asking whether to put the subject in the new or exisiting divider,
    //          and process user input and executing it
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

    // MODIFIES: this
    // EFFECTS: add flashcard to an existing subject
    public void addtoExistingSubject() {
        input = new Scanner(System.in);
        String numberofSubjectName = input.next();
        divider.getSubject(Integer.parseInt(numberofSubjectName) - 1).addFlashCard(flashCard);
        System.out.println("Flashcard added successfully");
        createAnotherFlashcard();
    }

    // MODIFIES: this
    // EFFECTS: add Subject to an existing divider
    public void addtoExistingDivider() {
        input = new Scanner(System.in);
        String numberofDividerName = input.next();
        dividerList.getDivider(Integer.parseInt(numberofDividerName) - 1).addSubject(subject);
        System.out.println("Subject added successfully");
        createAnotherFlashcard();
    }

    // MODIFIES: this
    // EFFECTS: ending the flashcard generator
    public void endgenerator() {
        System.out.println("Would you like to end the generator?");
        System.out.println("If you would like to save, enter no. ");
        input = new Scanner(System.in);
        String yesorno = input.next();
        if (yesorno.toLowerCase().equals("yes")) {
            programRunning = false;
            makeAnotherFlashcard = false;
        }
        if (yesorno.toLowerCase().equals("no")) {
            makeAnotherFlashcard = false;
            programRunning = true;

        } else if (!((yesorno.toLowerCase().equals("yes") || (yesorno.toLowerCase().equals("no"))))) {
            notUnderstandCmd();
        }
    }

    // EFFECTS: saves the dividerlist to file
    public void saveDividerList() {
        try {
            jsonWriter.open();
            jsonWriter.write(dividerList);
            jsonWriter.close();
            System.out.println("Saved " + "to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads dividerList from file
    public void loadDividerList() {
        try {
            dividerList = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: prints all the dividers in dividerList to the console
    private void printDividers() {
        List<Divider> dividers = dividerList.getDividers();
        int i = 1;

        for (Divider d: dividers) {
            System.out.println(i++ + ". " +  "Divider Name:");
            System.out.println(d.getDividerName());
            System.out.println();
            printSubjects(d);
        }
    }

    // EFFECTS: prints all the subjects in divider to the console
    private void printSubjects(Divider divider) {
        List<Subject> subjects = divider.getSubjects();

        System.out.println("Subject Name:");
        for (Subject s : subjects) {
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
}

