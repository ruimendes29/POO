package model;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Weather {
    private static String WEATHER_API_URL = "http://wttr.in/";
    private static String WEATHER_RESULT_FORMAT = "?format='%c+%p'";

    private Point2D.Double location;
    private String weather;
    private double precipitation;

    private Weather() {
    }

    public Weather(Point2D.Double location) {
        this.update(location);
    }

    public Point2D.Double getLocation() {
        return (Point2D.Double) location.clone();
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void update(Point2D.Double location) {
        double posX = location.getX();
        double posY = location.getY();

        try {
            URL url = new URL(Weather.WEATHER_API_URL + "~" + posX + "," + posY + Weather.WEATHER_RESULT_FORMAT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            String line = reader.readLine();
            String[] field1 = line.split(" ");
            String[] field2 = field1[1].split("m");
            this.weather = field1[0].substring(1);
            this.precipitation = Double.parseDouble(field2[0]);
        } catch (IOException | NumberFormatException e) {
            this.weather = "?";
            this.precipitation = 42;
        }
    }

    @Override
    public String toString() {
        return "Weather: " + weather + " " + precipitation + " mm";
    }
}
