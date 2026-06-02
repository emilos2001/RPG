package Totorial.RPG.Menu;

public class Answers {
    private String answer;
    private boolean correct;

    public Answers(String answer, boolean correct) {
        this.answer = answer;
        this.correct = correct;
    }

    public String getAnswer() {
        return this.answer;
    }

    public boolean isCorrect(){
        return this.correct;
    }
}
