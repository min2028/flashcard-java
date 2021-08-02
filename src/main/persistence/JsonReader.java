package persistence;

import model.Divider;
import model.DividerList;
import model.FlashCard;
import model.Subject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Modelled from JsonSerializationDemo

// Represents a reader that reads DividerList from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads DividerList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public DividerList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDividerList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses DividerList from JSON object and returns it
    private DividerList parseDividerList(JSONObject jsonObject) {
        DividerList dividerList = new DividerList();
        JSONArray jsonArray = jsonObject.getJSONArray("dividers");
        for (Object json : jsonArray) {
            JSONObject nextDivider = (JSONObject) json;
            addDivider(dividerList, nextDivider);
        }
        return dividerList;
    }

    // MODIFIES: dividerList
    // EFFECTS: parses Divider from JSON object and adds it to DividerList
    private void addDivider(DividerList dividerList, JSONObject jsonObject) {
        String dividerName = jsonObject.getString("divider name");
        Divider divider = new Divider();
        divider.setDividerName(dividerName);
        dividerList.addDivider(divider);
        JSONArray jsonArray = jsonObject.getJSONArray("subjects");
        for (Object json: jsonArray) {
            JSONObject nextSubject = (JSONObject) json;
            addSubject(divider, nextSubject);
        }
    }

    // MODIFIES: divider
    // EFFECTS: parses Subject from JSON object and adds it to Divider
    private void addSubject(Divider divider, JSONObject jsonObject) {
        String subjectName = jsonObject.getString("subject name");
        Subject subject = new Subject();
        subject.setSubjectName(subjectName);
        divider.addSubject(subject);
        JSONArray jsonArray = jsonObject.getJSONArray("flashcards");
        for (Object json: jsonArray) {
            JSONObject nextFlashcard = (JSONObject) json;
            addFlashcard(subject, nextFlashcard);
        }
    }

    private void addFlashcard(Subject subject, JSONObject jsonObject) {
        String flashcardName = jsonObject.getString("flashcard name");
        String question = jsonObject.getString("question");
        String answer = jsonObject.getString("answer");
        int day = jsonObject.getInt("day");
        int month = jsonObject.getInt("month");
        int year = jsonObject.getInt("year");
        FlashCard flashCard = new FlashCard();
        flashCard.setName(flashcardName);
        flashCard.setDayMonthYear(day, month, year);
        flashCard.createQuestion(question);
        flashCard.createAnswer(answer);
        subject.addFlashCard(flashCard);
    }
}
