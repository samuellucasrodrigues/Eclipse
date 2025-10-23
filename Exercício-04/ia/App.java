import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class App {
    public static void main(String[] args) {
        String endpoint = "endpoint";//seu endpoint da Azure
        String key = "key";//sua chave da Azure
        
        String textToAnalyze = "Tarefa concluída!!";
        
        try {
            String result = analyzeSentiment(endpoint, key, textToAnalyze);
            System.out.println("Resultado: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String analyzeSentiment(String endpoint, String key, String text) throws Exception {
        String url = endpoint + "language/:analyze-text?api-version=2023-11-15-preview";
        
        String jsonBody = String.format(
            "{\"kind\":\"SentimentAnalysis\",\"parameters\":{\"modelVersion\":\"latest\"},\"analysisInput\":{\"documents\":[{\"id\":\"1\",\"text\":\"%s\",\"language\":\"pt\"}]}}",
            text.replace("\"", "\\\"")
        );
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .header("Ocp-Apim-Subscription-Key", key)
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}