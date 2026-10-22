package com.as.samples;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class WeatherServlet extends HttpServlet {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String city = clean(req.getParameter("city"));
        String zip = clean(req.getParameter("zip"));
        boolean useZip = !zip.isEmpty();
        String query = useZip ? zip : city;

        try {
            if (query.isEmpty()) {
                throw new IllegalArgumentException("Enter a city or a zip code.");
            }

            // 1. Geocode
            String geoUrl = "https://geocoding-api.open-meteo.com/v1/search?count=1&language=en&format=json"
                    + "&name=" + URLEncoder.encode(query, StandardCharsets.UTF_8)
                    + (useZip ? "&countryCode=US" : "");
            JsonObject geo = getJson(geoUrl);
            if (!geo.has("results")) {
                throw new IllegalArgumentException("Location not found: " + query);
            }
            JsonObject loc = geo.getAsJsonArray("results").get(0).getAsJsonObject();
            double lat = loc.get("latitude").getAsDouble();
            double lon = loc.get("longitude").getAsDouble();
            String place = loc.get("name").getAsString()
                    + (loc.has("admin1") ? ", " + loc.get("admin1").getAsString() : "");

            // 2. Weather
            String wxUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon
                    + "&current=temperature_2m,relative_humidity_2m,precipitation,weather_code,is_day"
                    + "&temperature_unit=fahrenheit";
            JsonObject cur = getJson(wxUrl).getAsJsonObject("current");

            double temp = cur.get("temperature_2m").getAsDouble();
            int humidity = cur.get("relative_humidity_2m").getAsInt();
            double precip = cur.get("precipitation").getAsDouble();
            int code = cur.get("weather_code").getAsInt();
            boolean day = cur.get("is_day").getAsInt() == 1;

            boolean rain = precip > 0 || (code >= 51 && code <= 67)
                    || (code >= 80 && code <= 82) || code >= 95;
            boolean sunny = day && code <= 1;

            req.setAttribute("city", place);
            req.setAttribute("temp", temp);
            req.setAttribute("humidity", humidity);
            req.setAttribute("rain", rain ? "Yes" : "No");
            req.setAttribute("sunny", sunny ? "Yes" : "No");

        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
        } catch (InterruptedException | RuntimeException e) {
            req.setAttribute("error", "Could not fetch weather right now.");
        }
        req.getRequestDispatcher("/jsp/weatherresult.jsp").forward(req, resp);
    }

    private JsonObject getJson(String url) throws IOException, InterruptedException {
        HttpRequest r = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<String> res = CLIENT.send(r, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() != 200) throw new IOException("HTTP " + res.statusCode());
        return JsonParser.parseString(res.body()).getAsJsonObject();
    }

    private String clean(String s) {
        return s == null ? "" : s.trim();
    }
}