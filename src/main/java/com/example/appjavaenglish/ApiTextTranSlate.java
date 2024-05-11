package com.example.appjavaenglish;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiTextTranSlate {
    public String translate(String text) throws IOException, InterruptedException {
        String json ;
        //String text = "Hello Despite supporting older Java versions, Gson also provides a JPMS module descriptor";
        text = text.replace(" ", "%20");
//        String textFormat = "source_language=en&target_language=vi&text=" + text;
        String textFormat = "q=" + text + "&target=vi";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "application/gzip")
                .header("X-RapidAPI-Key", "d17fd45eccmsh10807ffd68a28fdp1145efjsn2e3cf5ac6367")
                .header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString(textFormat))
                .build();
        //HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
        try {
            HttpResponse<String> response1 = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response1.statusCode() == 200) {
                json = response1.body();
                JSONObject jsonObject = new JSONObject(json);
                String x = jsonObject.getJSONObject("data")
                        .getJSONArray("translations")
                        .getJSONObject(0)
                        .getString("translatedText");
                return x;
            }
            return "";
        } catch (Exception e) {
            System.out.println("Loi App.APITextTranslate");
            e.printStackTrace();
            return "";
        }
    }
}
