package model;


import org.json.JSONObject;
import persistence.Writable;

// Represent a FlashCard with the name, Question, Answer and day, month and day it was created.
public class FlashCard extends Writable {
    private int day;
    private int month;
    private int year;
    private String question;
    private String answer;
    private String name;


    // EFFECTS: Create a FlashCard that has no Question or Answer or date assigned to it
    public FlashCard() {
    }

    public void createQuestion(String question) {
        this.question = question;
    }

    public void createAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    // MODIFIES: this
    // EFFECTS: set day, month and year the FlashCard is created
    public void setDayMonthYear(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    // EFFECTS: return day, month or year and return 0 if invalid input
    public int getDayMonthYear(String daymonthyear) {
        if (daymonthyear.toLowerCase().equals("day")) {
            return day;
        }
        if (daymonthyear.toLowerCase().equals("month")) {
            return month;
        }
        if (daymonthyear.toLowerCase().equals("year")) {
            return year;
        }
        return 0;
    }

    // EFFECTS: get the String representation of date (Date/Month/Year)
    public String getDate() {
        String date = day + "/" + month + "/" + year;
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("day", day);
        json.put("month", month);
        json.put("year", month);
        json.put("flashcard name", name);
        json.put("question", question);
        json.put("answer", answer);
        return json;
    }
}
