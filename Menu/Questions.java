package Totorial.RPG.Menu;

import java.util.List;
public class Questions {
    private final String question;
    private final List<String> answer;

    public Questions(String question, List<String> answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return this.question;
    }

    public List<String> getAnswer() {
        return this.answer;
    }

}