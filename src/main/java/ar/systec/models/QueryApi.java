package ar.systec.models;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

import io.github.cdimascio.dotenv.Dotenv;

public class QueryApi {
  public static String getData(String url) {
    // Dotenv dotenv = Dotenv.load();
    // String apiKey = dotenv.get("API_KEY");

    URI apiUrl = URI.create(url);
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(apiUrl).build();

    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return response.body();

    } catch (Exception e) {
      throw new RuntimeException("Error: " + e);
    }

  }
}
