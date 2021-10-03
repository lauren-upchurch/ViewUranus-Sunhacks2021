package ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import src.ForecastData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class WeatherPane extends Pane {

    private String weatherForecast;
    private String weatherImageString;
    private Image weatherImage;

    private WeatherPane() {};

    public WeatherPane(String weatherForecast) {
        super();
        this.weatherForecast = weatherForecast;

        weatherImageString = getWeatherImageString();
        ImageView weatherImageView = getWeatherImage(weatherImageString);

        this.getChildren().add(weatherImageView);
    }

    private String getWeatherImageString() {
        String imageString = "";

        if (weatherForecast.contains("Clear")) {
            imageString = "Clear.png";
        } else if (weatherForecast.contains("Cloudy") && weatherForecast.contains("Snow")) {
            imageString = "CloudySnow.png";
        } else if (weatherForecast.contains("Partly") && weatherForecast.contains("Cloudy")) {
            imageString = "PartlyCloudy.png";
        } else if (weatherForecast.contains("Cloudy")) {
            imageString = "Cloudy.png";
        } else if (weatherForecast.contains("Rain")) {
            imageString = "Rain.png";
        } else if (weatherForecast.contains("Snow")) {
            imageString = "Snow.png";
        } else if (weatherForecast.contains("Thunder")) {
            imageString = "Thunder.png";
        } else {
            imageString = "BrokenWeather.png";
        }

        imageString = "Uranus/images/weather/" + imageString;

        return imageString;
    }

    private ImageView getWeatherImage(String imageSource) {
        ImageView weatherImageView = new ImageView();

        try {
            Image weatherImage = new Image(new FileInputStream(imageSource));
            weatherImageView.setImage(weatherImage);
            weatherImageView.setFitHeight(40);
            weatherImageView.setPreserveRatio(true);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        return weatherImageView;
    }
}
