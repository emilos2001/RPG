package Totorial.RPG.Menu;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AiAPI {

    public String callApi(String prompt) {
        try {
            String sanitizedPrompt = prompt.replace("\"", "'").replace("\n", " ");
            String jsonBody = """
                              {
                                "model": "llama-3.3-70b-versatile",
                                "messages": [
                                  {
                                    "role": "user",
                                    "content": "%s"
                                  }
                                ],
                                "temperature" : 0.4
                              }
                              """.formatted(sanitizedPrompt);

            String AI_API_KEY = System.getenv("GROQ_API_KEY");
            String API_URL = "https://api.groq.com/openai/v1/chat/completions";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Bearer " + AI_API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                System.err.println("API Error: Status Code " + response.statusCode());
                System.err.println("Response: " + response.body());
                return "";
            }
            return response.body()
                    .replace("\\n", "\n")
                    .replace("\\\"", "\"")
                    .replace("\\t", "\t")
                    .replace("*", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> answers(String response) {
        String[] lines = response.split("\n");
        List<String> answers = new ArrayList<>();
        Pattern pattern = Pattern.compile("^[A-Ea-e]\\)\\s*\"?(.*?)\"?(?:\\s*\\(?\\s*-\\s*(?i)(?:true|false).*|$|\\s*\\((?i)(?:true|false).*)");

        for (String line : lines) {
            line = line.trim();
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                String clearAnswer = matcher.group(1).trim();
                answers.add(clearAnswer);
            }
        }
        return answers;
    }

    public List<String> questions(String response) {
        String[] lines = response.split("\n");
        List<String> questions = new ArrayList<>();
        for (String line : lines) {
            line = line.trim();
            if (line.matches("^\\d+\\.\\s*.*\\?$")) {
                String cleanQuestion = line.replaceAll("^\\d+\\.\\s*", "");
                questions.add(cleanQuestion);
            }
        }
        return questions;
    }
    public List<Boolean> getTrueOrFalse(String response) {
        List<Boolean> values = new ArrayList<>();
        String[] lines = response.split("\n");
        Pattern pattern = Pattern.compile("^[A-Ea-e]\\)\\s*\"?(.*?)\"?(?:\\s*[-—(]?\\s*(?i)(true|false)\\)?.*|$)");
        for (String line : lines) {
            line = line.trim();
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()){
                String booleanText = matcher.group(2);
                boolean isTrue = booleanText != null && booleanText.equalsIgnoreCase("true");
                values.add(isTrue);
            }
        }
        return values;
    }
}