package com.example.appjavaenglish;
//
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//public class ApiTextTranSlate {
//    public String translate(String text) throws IOException, InterruptedException {
//        String json ;
//        //String text = "Hello Despite supporting older Java versions, Gson also provides a JPMS module descriptor";
//        text = text.replace(" ", "%20");
////        String textFormat = "source_language=en&target_language=vi&text=" + text;
//        String textFormat = "q=" + text + "&target=vi";
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
//                .header("content-type", "application/x-www-form-urlencoded")
//                .header("Accept-Encoding", "application/gzip")
//                .header("X-RapidAPI-Key", "d17fd45eccmsh10807ffd68a28fdp1145efjsn2e3cf5ac6367")
//                .header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
//                .method("POST", HttpRequest.BodyPublishers.ofString(textFormat))
//                .build();
//        //HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
////        System.out.println(response.body());
//        try {
//            HttpResponse<String> response1 = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//            if (response1.statusCode() == 200) {
//                json = response1.body();
//                JSONObject jsonObject = new JSONObject(json);
//                String x = jsonObject.getJSONObject("data")
//                        .getJSONArray("translations")
//                        .getJSONObject(0)
//                        .getString("translatedText");
//                return x;
//            }
//            return "";
//        } catch (Exception e) {
//            System.out.println("Loi App.APITextTranslate");
//            e.printStackTrace();
//            return "";
//        }
//    }
//}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ApiTextTranSlate {

    public static String googleTranslate(String langFrom, String langTo, String text) throws IOException {
        String urlScript = "https://script.google.com/macros/s/AKfycbw1qSfs1Hvfnoi3FzGuoDWijwQW69eGcMM_iGDF7p5vu1oN_CaFqIDFmCGzBuuGCk_N/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlScript);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public static void main(String[] args) throws IOException {
        String text = "simp";
        System.out.println("Translated text: \n" + googleTranslate("", "ja", text));
    }

}
