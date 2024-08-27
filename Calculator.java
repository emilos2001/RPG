package Totorial.RPG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JPanel implements ActionListener {

    JFrame jFrame;
    JTextField jTextField;
    JButton[] numbersButton;
    JButton[] functionButton;
    JButton button1, button2, button3, button4, button5, button6,
            button7, button8, button9, button0;
    JButton decButton, equButton, delButton, clrButton, powerButton, sqrtButton,
            addButton, subButton, mulButton, divButton, paranButtonLeft, paranButtonRight, procentButton;
    JButton closeButton;
    Font font;
    JPanel jPanel;
    double num1 = 0, num2 = 0, result = 0;
    char operator;

    public Calculator() {
        int width = 330;
        int height = 470;
        jFrame = new JFrame();
        jFrame.setAlwaysOnTop(true);
        jFrame.setUndecorated(true);
        jFrame.setSize(width, height);
        jFrame.setResizable(false);
        font = new Font(null, Font.ITALIC, 20);
        jTextField = new JTextField();
        jTextField.setBounds(25, 60, 275, 30);
        jTextField.setFont(font);
        jTextField.setBorder(null);
        jTextField.setEditable(false);
        jTextField.setBackground(new Color(20, 52, 20));
        jTextField.setForeground(Color.white);
        jFrame.add(jTextField);
        jPanel = new JPanel();
        jPanel.setBounds(25, 170, 280, 270);
        jPanel.setBackground(new Color(26, 26, 0));
        jPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        Dimension dimension = new Dimension(65, 60);
        button0 = new JButton("0");
        button1 = new JButton("1");
        button2 = new JButton("2");
        button3 = new JButton("3");
        button4 = new JButton("4");
        button5 = new JButton("5");
        button6 = new JButton("6");
        button7 = new JButton("7");
        button8 = new JButton("8");
        button9 = new JButton("9");
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        powerButton = new JButton("^");
        sqrtButton = new JButton("\u221A");
        delButton = new JButton("<");
        clrButton = new JButton("C");
        paranButtonLeft = new JButton("(");
        paranButtonRight = new JButton(")");
        procentButton = new JButton("%");
        functionButton = new JButton[9];
        functionButton[0] = addButton;
        functionButton[1] = subButton;
        functionButton[2] = mulButton;
        functionButton[3] = subButton;
        functionButton[4] = divButton;
        functionButton[5] = equButton;
        functionButton[6] = clrButton;
        functionButton[7] = decButton;
        functionButton[8] = delButton;
        numbersButton = new JButton[10];
        for (JButton jButton : functionButton) {
            jButton.addActionListener(this);
            jButton.setBackground(new Color(20, 52, 20));
            jButton.setFont(font);
            jButton.setBorder(null);
            jButton.setFocusable(false);
            jButton.setForeground(Color.white);
            jButton.setPreferredSize(dimension);
        }
        for (int i = 0; i < 10; i++) {
            numbersButton[i] = new JButton(String.valueOf(i));
            numbersButton[i].addActionListener(this);
            numbersButton[i].setFont(font);
            numbersButton[i].setBorder(null);
            numbersButton[i].setFocusable(false);
            numbersButton[i].setBackground(new Color(20, 52, 20));
            numbersButton[i].setForeground(Color.white);
            numbersButton[i].setPreferredSize(dimension);
        }
        jPanel.add(mulButton);
        jPanel.add(numbersButton[9]);
        jPanel.add(numbersButton[8]);
        jPanel.add(numbersButton[7]);
        jPanel.add(subButton);
        jPanel.add(numbersButton[6]);
        jPanel.add(numbersButton[5]);
        jPanel.add(numbersButton[4]);
        jPanel.add(addButton);
        jPanel.add(numbersButton[3]);
        jPanel.add(numbersButton[2]);
        jPanel.add(numbersButton[1]);
        jPanel.add(equButton);
        jPanel.add(delButton);
        jPanel.add(numbersButton[0]);
        jPanel.add(decButton);
        jFrame.add(jPanel);
        sqrtButton.addActionListener(this);
        sqrtButton.setBounds(25, 100, 70, 25);
        sqrtButton.setBackground(new Color(20, 52, 20));
        sqrtButton.setFocusable(false);
        sqrtButton.setFont(font);
        sqrtButton.setBorder(null);
        sqrtButton.setForeground(Color.white);
        jFrame.add(sqrtButton);
        sqrtButton.addActionListener(this);
        powerButton.setBounds(235, 100, 65, 25);
        powerButton.addActionListener(this);
        powerButton.setBackground(new Color(20, 52, 20));
        powerButton.setFocusable(false);
        powerButton.setBorder(null);
        powerButton.setFont(font);
        powerButton.setForeground(Color.white);
        jFrame.add(powerButton);
        clrButton.addActionListener(this);
        clrButton.setBounds(25, 130, 85, 30);
        clrButton.setBackground(new Color(20, 52, 20));
        clrButton.setFocusable(false);
        clrButton.setFont(font);
        clrButton.setBorder(null);
        clrButton.setForeground(Color.white);
        jFrame.add(clrButton);
        paranButtonLeft.addActionListener(this);
        paranButtonLeft.setBounds(120, 100, 40, 25);
        paranButtonLeft.setForeground(Color.white);
        paranButtonLeft.setFocusable(false);
        paranButtonLeft.setBorder(null);
        paranButtonLeft.setFont(font);
        paranButtonLeft.setBackground(new Color(20, 52, 20));
        jFrame.add(paranButtonLeft);
        paranButtonRight.addActionListener(this);
        paranButtonRight.setBounds(170, 100, 40, 25);
        paranButtonRight.setForeground(Color.white);
        paranButtonRight.setFocusable(false);
        paranButtonRight.setBorder(null);
        paranButtonRight.setFont(font);
        paranButtonRight.setBackground(new Color(20, 52, 20));
        jFrame.add(paranButtonRight);
        procentButton.addActionListener(this);
        procentButton.setBounds(120, 130, 90, 30);
        procentButton.setForeground(Color.white);
        procentButton.setFocusable(false);
        procentButton.setFont(font);
        procentButton.setBorder(null);
        procentButton.setBackground(new Color(20, 52, 20));
        jFrame.add(procentButton);
        divButton.addActionListener(this);
        divButton.setBounds(220, 130, 80, 30);
        divButton.setForeground(Color.white);
        divButton.setBorder(null);
        divButton.setBackground(new Color(20, 52, 20));
        closeButton = new JButton("X");
        closeButton.addActionListener(this);
        closeButton.setBounds(265, 10, 60, 35);
        closeButton.setForeground(Color.white);
        closeButton.setFocusable(false);
        closeButton.setFont(font);
        closeButton.setBorder(null);
        closeButton.setBackground(new Color(20, 52, 20));
        jFrame.add(closeButton);
        jFrame.add(divButton);
        jFrame.setLayout(null);
        jFrame.getContentPane().setBackground(new Color(26, 26, 0));
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            for (int i = 0; i < 10; i++) {
                if (e.getSource() == numbersButton[i]) {
                    jTextField.setText(jTextField.getText().concat(String.valueOf(i)));
                }
            }
            if (e.getSource() == addButton) {
                num1 = Double.parseDouble(jTextField.getText());
                operator = '+';
                jTextField.setText("");
            }
            if (e.getSource() == subButton) {
                num1 = Double.parseDouble(jTextField.getText());
                operator = '-';
                jTextField.setText("");
            }
            if (e.getSource() == mulButton) {
                num1 = Double.parseDouble(jTextField.getText());
                operator = '*';
                jTextField.setText("");
            }
            if (e.getSource() == divButton) {
                num1 = Double.parseDouble(jTextField.getText());
                operator = '/';
                jTextField.setText("");
            }
            if (e.getSource() == powerButton) {
                num1 = Double.parseDouble(jTextField.getText());
                operator = '^';
                jTextField.setText("");
            }
            if (e.getSource() == decButton) {
                jTextField.setText(jTextField.getText().concat("."));
            }
            if (e.getSource() == equButton) {
                num2 = Double.parseDouble(jTextField.getText());
                switch (operator) {
                    case '+' -> result = num1 + num2;
                    case '-' -> result = num1 - num2;
                    case '*' -> result = num1 * num2;
                    case '/' -> result = num1 / num2;
                    case '^' -> result = Math.pow(num1, num2);
                }
                jTextField.setText(String.valueOf(result));
                num1 = result;
            }
            if (e.getSource() == paranButtonLeft) {
                jTextField.setText(jTextField.getText().concat("("));
            }
            if (e.getSource() == paranButtonRight) {
                jTextField.setText(jTextField.getText().concat(")"));
            }
            if (e.getSource() == clrButton) {
                jTextField.setText("");
            }
            if (e.getSource() == delButton) {
                if (jTextField.getText().length() != 0) {
                    String text = jTextField.getText().substring(0, jTextField.getText().length() - 1);
                    jTextField.setText(text);
                }

            }
            if (e.getSource() == closeButton) {
                jFrame.setVisible(false);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}