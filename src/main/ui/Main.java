package ui;

import model.FlashCard;

import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Welcome To Flashcard Generator!!!");
            new FlashcardGenerator();
            System.out.println("Flashcard generator quitted.");
        } catch (FileNotFoundException e) {
            System.out.println("file not found: Choose another file");
        }
    }
}
