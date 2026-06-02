package Totorial.RPG.Menu;

import java.sql.*;
import java.util.*;

public class MyJdbc {
    public Connection connection;
    public Statement statement;

    public MyJdbc() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rpg_player?tinyInt1isBit=true", "root", "root");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean joinIn(String userName, String pinGame) {
        String query = "INSERT INTO users (username, gamePin) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, userName);
            ps.setString(2, pinGame);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertQuestionAnswer(Map<String, List<String>> map, List<Boolean> isCorrect, String currentPin) {
        String query = "INSERT INTO answers(question, answer, isCorrect, gamePin) values (?, ?, ?, ?)";
        int statusPointer = 0;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                String questionText = entry.getKey();
                List<String> answerList = entry.getValue();
                for (String singleAnswer : answerList) {
                    ps.setString(1, questionText);
                    ps.setString(2, singleAnswer);
                    if (statusPointer < isCorrect.size()) {
                        ps.setBoolean(3, isCorrect.get(statusPointer));
                    } else {
                        ps.setBoolean(3, false);
                    }
                    ps.setString(4, currentPin);
                    ps.addBatch();
                    statusPointer++;
                }
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error inserting quiz data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Map<String, List<AnswerData>> getQuestionsAndAnswer(String gamePin) {
        Map<String, List<AnswerData>> map = new LinkedHashMap<>();
        String query = "SELECT question, answer, isCorrect FROM answers WHERE gamePin = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, gamePin);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String question = rs.getString("question");
                    String answer = rs.getString("answer");
                    boolean isCorrect = rs.getBoolean("isCorrect");
                    map.computeIfAbsent(question, k -> new ArrayList<>()).add(new AnswerData(answer, isCorrect));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public void insertGamePin(String gamePin) {
        String query = "INSERT INTO pincode (gamePin) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, gamePin);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean doesPinExist(String gamePin) {
        String query = "SELECT EXISTS(SELECT 1 FROM pincode WHERE gamePin = ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, gamePin);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getPinCode(String gamePin) {
        String pin = "";
        String query = "SELECT gamePin FROM pincode WHERE gamePin = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, gamePin);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    pin = rs.getString("pincode");
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return pin;
    }

    public record AnswerData(String text, boolean isCorrect) {
    }
}