package model;

import java.util.Date;

// Represent a FlashCard with the name, Question, Answer and day, month and day it was created.
public class FlashCard {
    private int day;
    private int month;
    private int year;
    private String question;
    private String answer;
    private String name;


    // EFFECTS: Create a FlashCard that has no Question or Answer or date assigned to it
    public FlashCard() {
        this.question = "";
        this.answer = "";
    }

    // EFFECTS: Set the question for the FlashCard
    public void createQuestion(String question) {
        this.question += question;
    }

    // EFFECTS: Set the Answer to the Question for the FlashCard
    public void createAnswer(String answer) {
        this.answer += answer;
    }

    // EFFECTS: get the question of the FlashCard
    public String getQuestion() {
        return question;
    }

    // EFFECTS: get the answer of the FlashCard
    public String getAnswer() {
        return answer;
    }

    // REQUIRES: day has string length of 2, month has string length of 2 and year has string length of 4
    // MODIFIES: this
    // EFFECTS: set day, month and year the FlashCard is created
    public void setDayMonthYear(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    // EFFECTS: get the String representation of date (Date/Month/Year)
    public String getDate() {
        String date = day + "/" + month + "/" + year;
        return date;
    }

    // MODIFIES: this
    // EFFECTS: give the name to the FlashCard
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: get the name of the FlashCard
    public String getName() {
        return name;
    }
}
