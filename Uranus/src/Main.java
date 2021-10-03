package src;

/**
 * Main class that launches the app and hosts the core GUI elements.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import ui.DayLabel;
import ui.UranusTable;
import ui.WeatherPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main extends Application {

    private LocationWeather weather;
    private ArrayList<ForecastData> forecasts;
    private MoonPhase moonPhaseCalculator = new MoonPhase();
    private Scene welcomeScene;
    private GridPane forecastGrid = new GridPane();

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        setUpStartScreen();

        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }

    /**
     * Helper method that instantiates LocationWeather object and populates forecasts ArrayList.
     * @param city String of the city that the user is searching for
     */
    private void getWeather(String city) {
        weather = new LocationWeather(city);
        forecasts = weather.getForecasts();
    }

    private Pane getMoonPhasePane(MoonPhase.Phase phase){
        String imageString;

        switch (phase) {
            case NEW_MOON:
                imageString = "NewMoon.png";
                break;
            case THIRD_QUARTER:
                imageString = "ThirdQuarter.png";
                break;
            case FULL_MOON:
                imageString = "FullMoon.png";
                break;
            case FIRST_QUARTER:
                imageString = "FirstQuarter.png";
                break;
            case WANING_CRESCENT:
                imageString = "WaningCrescent.png";
                break;
            case WANING_GIBBOUS:
                imageString = "WaningGibbous.png";
                break;
            case WAXING_GIBBOUS:
                imageString = "WaxingGibbous.png";
                break;
            case WAXING_CRESCENT:
                imageString = "WaxingCrescent.png";
                break;
            default:
                imageString = "Oops.png";
        }

        imageString = "Uranus/images/moon_phases/" + imageString;
        ImageView moonPhaseImage = getMoonPhaseImage(imageString);

        return new Pane(moonPhaseImage);
    }

    private ImageView getMoonPhaseImage(String imageSource) {
        ImageView moonImageView = new ImageView();

        try {
            Image moonPhaseImage = new Image(new FileInputStream(imageSource));
            moonImageView.setImage(moonPhaseImage);
            moonImageView.setFitHeight(40);
            moonImageView.setPreserveRatio(true);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        return moonImageView;
    }

    /**
     * Helper method for setting up lefthand pane.
     * @return Pane object that contains all leftpane sections
     */
    private VBox setUpLeftPane() {
        String logoImageString = "Uranus/images/logo/view-uranus-logo2.png";
        ImageView logoImageView = new ImageView();
        logoImageView.setStyle("-fx-alignment: CENTER;");

        try {
            Image logo = new Image(new FileInputStream(logoImageString));
            logoImageView.setImage(logo);
            logoImageView.setFitHeight(140);
            logoImageView.setPreserveRatio(true);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        StackPane logoPane = new StackPane(logoImageView);
        logoPane.setAlignment(Pos.BASELINE_CENTER);

        // Create text field
        TextField locationField = new TextField("City, State");
        locationField.setFont(Font.font("Helvetica", FontWeight.THIN, 14));
        locationField.setPrefColumnCount(40);
        locationField.setMaxWidth(250);

        // Create button for sending textfield data to backend
        Button checkVisibilityButton = new Button("Check visibility");
        checkVisibilityButton.setFont(Font.font("Helvetica", FontWeight.THIN, 16));
        checkVisibilityButton.setBackground(new Background(
                new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        // Setting button functionality
        checkVisibilityButton.setOnMouseClicked(e -> {
            // Empty  right-hand panel and get location entry from textfield
            forecastGrid.getChildren().clear();
            String location = locationField.getText();

            // Gets weather information for hte given location
            getWeather(location);

            Label locationLabel = new Label("Visibility forecast for " + location);
            locationLabel.setFont(Font.font("Helvetica", FontWeight.MEDIUM, 15));
            locationLabel.setAlignment(Pos.CENTER);

            for (int i = 0; i < 5; i++) {
                if (forecasts.get(i) != null) {
                    LocalDateTime date = forecasts.get(i).date;

                    Stargazing stargazer = new Stargazing(forecasts.get(i));
                    Stargazing.Gaze visibility = stargazer.starGazingPotential();

                    DayLabel dayLabel = new DayLabel(forecasts.get(i).date, visibility);

                    MoonPhase.Phase moonPhase = MoonPhase.whatPhaseIsIt(date);
                    String phaseString = moonPhaseCalculator.phaseToString(moonPhase);
                    Pane moonPhasePane = getMoonPhasePane(moonPhase);

                    WeatherPane weatherPane = new WeatherPane(forecasts.get(i).forecast);

                    forecastGrid.add(moonPhasePane, i,0);
                    forecastGrid.add(dayLabel, i, 1);
                    forecastGrid.add(weatherPane, i, 2);
                }
            }
        });

        // Create VBox and layout parameters for storing GUI elements in left-hand panel
        VBox leftVBox = new VBox(5, logoPane, locationField, checkVisibilityButton);
        VBox.setMargin(logoPane, new Insets(20,0,0,0));
        VBox.setMargin(checkVisibilityButton, new Insets(15, 0, 0, 0));
        VBox.setVgrow(locationField, Priority.NEVER);
        leftVBox.setAlignment(Pos.BASELINE_CENTER);

        leftVBox.setBackground(new Background(
                new BackgroundFill(Color.rgb(23, 59, 95), CornerRadii.EMPTY, Insets.EMPTY)));

        return leftVBox;
    }

    /**
     * Helper method that sets up the StartScreen (NOTE: button functionality is included in this).
     */
    private void setUpStartScreen(){
        SplitPane splitPane = new SplitPane();

        // Create forecast grid for weather/moon info in top-right corner
        forecastGrid.setAlignment(Pos.CENTER);
        forecastGrid.setVgap(20);
        forecastGrid.setHgap(20);

        // Set up left-hand pane
        VBox leftPane = setUpLeftPane();

        // Set up right-hand pane
        SplitPane rightPane = new SplitPane();
        rightPane.setOrientation(Orientation.VERTICAL);
        rightPane.setDividerPositions(0.45f, 0.55f);

        Label uranusLabel = new Label("Ephemeris for Uranus");
        uranusLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
        uranusLabel.setTextFill(Color.MIDNIGHTBLUE);
        uranusLabel.setAlignment(Pos.CENTER);

        UranusTable uranusData = new UranusTable();
        uranusData.setAlignment(Pos.CENTER);

        Label creditLabel = new Label("Ephemeris data courtesy of Dominic Ford at http://in-the-sky.org");
        creditLabel.setFont(Font.font("Helvetica", FontWeight.THIN, 10));
        creditLabel.setTextFill(Color.DIMGREY);

        BorderPane uranusBorderPane = new BorderPane();
        BorderPane.setAlignment(uranusLabel, Pos.BOTTOM_CENTER);
        BorderPane.setMargin(uranusLabel, new Insets(20, 0, 0, 0));
        BorderPane.setAlignment(creditLabel, Pos.BOTTOM_CENTER);
        BorderPane.setMargin(creditLabel, new Insets(0,0,15,0));

        uranusBorderPane.setTop(uranusLabel);
        uranusBorderPane.setCenter(uranusData);
        uranusBorderPane.setBottom(creditLabel);

        rightPane.getItems().addAll(forecastGrid, uranusBorderPane);

        // Adding nodes to right-hand panel
        splitPane.getItems().addAll(leftPane, rightPane);
        splitPane.setDividerPositions(0.4f, 0.6f);

        // Setting up the scene
        welcomeScene = new Scene(splitPane, 850, 800);
        welcomeScene.getStylesheets().add("src/viewUranusStyles.css");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
