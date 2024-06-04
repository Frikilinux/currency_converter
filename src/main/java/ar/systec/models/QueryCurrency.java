package ar.systec.models;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

import io.github.cdimascio.dotenv.Dotenv;

public class QueryCurrency {
  public ConversionDTO getCurrency(String base_currency, String target_currency, double amount) {
    Dotenv dotenv = Dotenv.load();
    String apiKey = dotenv.get("API_KEY");

    URI apiUrl = URI.create("https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + base_currency + "/"
        + target_currency + "/" + amount);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(apiUrl).build();

    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return new Gson().fromJson(response.body(), ConversionDTO.class);

    } catch (Exception e) {
      throw new RuntimeException("Error: " + e);
    }

  }
}
