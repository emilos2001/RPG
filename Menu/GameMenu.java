package Totorial.RPG.Menu;

import Totorial.RPG.GamePanel;
import Totorial.RPG.Keys;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameMenu extends JPanel {
    private static final Font font = new Font("Sans-serif", Font.BOLD, 15);
    public Map<String, List<String>> map = new HashMap<>();
    public List<Questions> questions = new ArrayList<>();
    public List<String> answers = new ArrayList<>();
    List<JCheckBox> isCorrectStatus = new ArrayList<>();
    public List<Boolean> boolList = new ArrayList<>();

    public JFrame frame;
    public JPanel panel;
    public JLabel savedAnswer = new JLabel();
    public JPanel answerPanel = new JPanel();
    public JTextArea nameFieldForJoin = fields(190, 145, 250, 30);
    public JTextArea pinFieldForJoin = fields(190, 225, 250, 30);
    public JTextArea questionField = fields(10, 56, 645, 85);
    public JTextArea answerField = fields(10, 650, 645, 50);
    public JButton closeButton;
    public int nrAnswers = 0;
    public JLabel nrCharInAnswerLabel = new JLabel("0/300");
    public JLabel nrCharInQuestionLabel = new JLabel("0/300");
    public int nrOfQuestions = 0;
    public MyJdbc myJdbc = new MyJdbc();
    GamePanel gp = new GamePanel();
    Keys keys = new Keys(gp);
    AiAPI ai = new AiAPI();
    JTextArea textArea;
    public JTextArea joinTextMenu = texts(250, 50, 300, 30, "JOIN IN A GAME");
    public JTextArea textName = texts(250, 115, 300, 30, "USERNAME");
    public JTextArea textPin = texts(285, 195, 300, 30, "PIN");
    //CRETE YOUR OWN GAME
    public JTextArea createMenuText = texts(20, 5, 350, 30, "CREATE YOUR OWN GAME QUESTIONS");
    public JTextArea writeQuestion = texts(20, 35, 300, 20, "WRITE YOUR QUESTION : ");
    public JTextArea nrQuestions = texts(220, 35, 150, 20, nrOfQuestions + " / 32");

    public GameMenu(JFrame parentFrame, int width, int height) {
        frame = new JFrame();
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(20, 82, 20));
        panel.setBounds(10, 10, width - 20, height - 20);
        frame.add(panel);
        frame.setLayout(null);
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setSize(width, height);
        frame.getContentPane().setBackground(new Color(26, 26, 0));
        frame.setLocation(590, 100);
        parentFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                frame.setLocation(
                        parentFrame.getX() + parentFrame.getWidth(),
                        parentFrame.getY()
                );
            }
        });
        frame.setVisible(true);
    }

    private JTextArea fields(int x, int y, int width, int height) {
        JTextArea textField = new JTextArea(100, 100);
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        textField.setBounds(x, y, width, height);
        textField.setFont(font);
        textField.setBorder(new LineBorder(new Color(26, 26, 0), 3));
        textField.setEditable(true);
        textField.setBackground(new Color(102, 255, 153));
        textField.setForeground(Color.BLACK);
        return textField;
    }

    private void box() {
        JCheckBox checkBox = new JCheckBox();
        checkBox.setBorder(null);
        checkBox.setFont(font);
        checkBox.setText("True");
        checkBox.setLayout(null);
        checkBox.setFocusable(false);
        checkBox.setBounds(560, 225, 80, 30);
        checkBox.setBackground(new Color(20, 82, 20));
        checkBox.setForeground(Color.white);
        checkBox.setSelected(false);
        isCorrectStatus.add(checkBox);
        answerPanel.add(checkBox);
    }

    public void addAnswers(JButton button) {
        button.addActionListener(a -> {
            if (answerField.getText().isEmpty()) {
                createMenuText.setText("Answer field cannot be empty");
                answerField.setBackground(keys.warningColor);
                return;
            }
            if (nrAnswers < 5) {
                box();
                nrAnswers++;
                savedAnswer = new JLabel(nrAnswers + ": " + answerField.getText());
                savedAnswer.setFont(new Font("Sans-serif", Font.PLAIN, 25));
                savedAnswer.setForeground(Color.white);
                answers.add(savedAnswer.getText());
                answerPanel.add(savedAnswer);
                answerPanel.setBounds(10, 150, 715, 475);
                answerPanel.setBackground(new Color(20, 82, 20));
                answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
                createMenuText.setText("Answer added (" + nrAnswers + "/5)");
                answerPanel.setVisible(true);
                answerField.setText("");
                answerPanel.repaint();
                answerPanel.revalidate();
            } else {
                createMenuText.setText("Maximum 5 answers reached!");
                answerField.setBackground(keys.warningColor);
            }
        });
    }

    public void addAIQuestionsAndAnswers(JButton button, JTextArea theme, JFrame frame) {
        button.addActionListener(a -> {
            if (theme.getText().isEmpty()) {
                return;
            }
            String themeText = theme.getText();
            String prompt = "pune 10 intrebari legate de " + themeText + ", " +
                    "si 5 raspunsuri din care unul sa fie corect, " +
                    "pune true sau false in drepul fiecarui raspuns";
            try {
                String callApi = ai.callApi(prompt);
                List<String> aiQuestions = ai.questions(callApi);
                List<String> aiAnswers = ai.answers(callApi);
                List<Boolean> values = ai.getTrueOrFalse(callApi);
                int answersPerQuestion = 5;
                for (boolean value : values) {
                    System.out.println(value);
                }
                for (int i = 0; i < aiQuestions.size(); i++) {
                    String singleQuestion = aiQuestions.get(i);
                    int fromIndex = i * answersPerQuestion;
                    int toIndex = Math.min(fromIndex + answersPerQuestion, aiAnswers.size());
                    if (fromIndex >= aiAnswers.size()) {
                        System.out.println("Not enough answers provided by the API for question: " + singleQuestion);
                        break;
                    }
                    List<String> specificAnswers = new ArrayList<>(aiAnswers.subList(fromIndex, toIndex));
                    Questions questionObj = new Questions(singleQuestion, specificAnswers);
                    questions.add(questionObj);
                    map.put(singleQuestion, specificAnswers);
                }
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    System.out.println(entry.getKey());
                    for (String answer : entry.getValue()) {
                        System.out.println(answer);
                    }
                }
                for (boolean value : values) {
                    System.out.println(value);
                    boolList.add(value);
                }
                System.out.println("size: " + boolList.size());
                myJdbc.insertGamePin(gp.menu.currentPin);
                myJdbc.insertQuestionAnswer(map, boolList, gp.menu.currentPin);
                map.clear();
                questions.clear();
                boolList.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
            frame.dispose();
        });
    }


    public void addQuestionAndAnswers(JButton button) {
        button.addActionListener(q -> {
            String question = questionField.getText();
            if (question.isEmpty()) {
                createMenuText.setText("Question and Answer fields cannot be empty");
                questionField.setBackground(keys.warningColor);
            } else {
                answerField.setText("");
                questionField.setText("");
                for (JCheckBox cb : isCorrectStatus) {
                    boolList.add(cb.isSelected());
                }
                questions.add(new Questions(question, new ArrayList<>(answers)));
                answers.clear();
                createMenuText.setText("CREATE YOUR OWN GAME QUESTIONS");
                nrOfQuestions += 1;
                nrQuestions.setText(nrOfQuestions + "/35");
                panel.revalidate();
                panel.repaint();
                createMenuText.setText("CREATE YOUR OWN GAME QUESTIONS");
                answerPanel.removeAll();
                answerPanel.revalidate();
                answerPanel.repaint();
                nrAnswers = 0;
                if (nrOfQuestions < 35) {
                    createMenuText.setText("Question added! Create question #" + (nrOfQuestions + 1));
                    saveQuiz();
                    keys.additionalWindow("Game was Created!", gp.menu.currentPin, false);
                } else if (nrOfQuestions == 35) {
                    createMenuText.setText("Maximum questions reached.");
                    frame.dispose();
                    saveQuiz();
                    keys.additionalWindow("Game was Created!", gp.menu.currentPin, false);
                }
            }
        });
    }


    private void saveQuiz() {
        myJdbc.insertGamePin(gp.menu.currentPin);
        for (Questions q : questions) {
            map.computeIfAbsent(q.getQuestion(), k -> new ArrayList<>()).
                    addAll(q.getAnswer());
        }
        myJdbc.insertQuestionAnswer(map, boolList, gp.menu.currentPin);
        System.out.println("size" + map.size());
        map.clear();
        isCorrectStatus.clear();
        questions.clear();
    }

    public void joinGame(JButton button) {
        button.addActionListener(a -> {
            String nameField = nameFieldForJoin.getText();
            String pinField = pinFieldForJoin.getText();
            if (nameField.isEmpty()) {
                nameFieldForJoin.setBackground(keys.warningColor);
                joinTextMenu.setText("Name field cannot be empty");
                return;
            } else if (pinField.isEmpty()) {
                joinTextMenu.setText("Pin field cannot empty");
                pinFieldForJoin.setBackground(keys.warningColor);
                return;
            }
            System.out.println(keys.menuBtn);
            if (myJdbc.doesPinExist(pinField)) {
                if (myJdbc.joinIn(nameField, pinField)) {
                    joinTextMenu.setText("Welcome " + nameField);
                    myJdbc.joinIn(nameField, pinField);
                    Keys.verifyChanges = true;
                }
                frame.dispose();
            } else {
                joinTextMenu.setText("This Pin does not exist!");
            }
        });

    }

    public JButton closeWindow(int x) {
        closeButton = new JButton("X");
        closeButton.addActionListener(e -> frame.setVisible(false));
        closeButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");
        closeButton.getActionMap().put("close", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeButton.doClick();
                frame.dispose();
            }
        });
        closeButton.setBounds(x, 0, 40, 35);
        closeButton.setForeground(Color.white);
        closeButton.setFocusable(false);
        closeButton.setBorder(null);
        closeButton.setFont(font);
        closeButton.setBackground(new Color(26, 26, 0));
        return closeButton;
    }

    public JTextArea texts(int x, int y, int width, int height, String text) {
        textArea = new JTextArea();
        textArea.setText(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setBounds(x, y, width, height);
        textArea.setFont(font);
        textArea.setBackground(new Color(20, 82, 20));
        textArea.setForeground(Color.white);
        return textArea;
    }


    public JButton buttons(int x, int y, int width, int height, String text) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setForeground(Color.white);
        button.setFocusable(false);
        button.setFont(font);
        button.setBorder(null);
        button.setBackground(new Color(136, 204, 0));
        panel.add(button);
        return button;
    }
}