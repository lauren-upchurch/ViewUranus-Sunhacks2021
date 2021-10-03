package src;

/**
 * Main class that launches the app and hosts the core GUI elements.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.DayLabel;
import ui.WeatherPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main extends Application {

    private LocationWeather weather;
    private ArrayList<ForecastData> forecasts;
    private Scene welcomeScene;
    private MoonPhase moonPhaseCalculator = new MoonPhase();

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
     * Helper method that sets up the StartScreen (NOTE: button functionality is included in this).
     */
    private void setUpStartScreen(){
        SplitPane splitPane = new SplitPane();
        GridPane forecastGrid = new GridPane();
        forecastGrid.setAlignment(Pos.CENTER);
        forecastGrid.setVgap(20);
        forecastGrid.setHgap(20);

        // Create text field
        TextField locationField = new TextField("City, State");
        locationField.setFont(Font.font("Helvetica", FontWeight.THIN, 14));
        locationField.setPrefColumnCount(40);
        locationField.setMaxWidth(250);

        // Create content labels
        Label topLabel = new Label("View Uranus");
        topLabel.setFont(Font.font("Helvetica", FontWeight.EXTRA_LIGHT, 25));
        topLabel.setTextFill(Color.WHITESMOKE);

        Label subLabel = new Label("(or other stellar phenomena)");
        subLabel.setFont(Font.font("Helvetica", FontWeight.THIN, 12));
        subLabel.setTextFill(Color.WHITESMOKE);

        // Create button for sending textfield data to backend
        Button checkVisibilityButton = new Button("Check visibility");
        checkVisibilityButton.setFont(Font.font("Helvetica", FontWeight.THIN, 16));
        checkVisibilityButton.setBackground(new Background(
                new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

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
                    DayLabel dayLabel = new DayLabel(forecasts.get(i).date);

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
        VBox leftPane = new VBox(5, topLabel, subLabel, locationField, checkVisibilityButton);
        VBox.setMargin(topLabel, new Insets(30,0,0,0));
        VBox.setMargin(checkVisibilityButton, new Insets(15, 0, 0, 0));
        VBox.setVgrow(locationField, Priority.NEVER);
        leftPane.setAlignment(Pos.BASELINE_CENTER);
        leftPane.setBackground(new Background(
                new BackgroundFill(Color.rgb(23, 59, 95), CornerRadii.EMPTY, Insets.EMPTY)));

        // Create vertical split pane for right-hand panel
        SplitPane rightPaneVerticalSplit = new SplitPane();
        rightPaneVerticalSplit.setOrientation(Orientation.VERTICAL);
        rightPaneVerticalSplit.setDividerPositions(0.3f, 0.7f);

        // Create top and bottom panes for right-hand panel
//        Pane topPane = new Pane();
//        topPane.getChildren().add(forecastGrid);
        Pane bottomPane = new Pane(new Label("Potentially visible bodies will go in this one"));
        rightPaneVerticalSplit.getItems().addAll(forecastGrid, bottomPane);

        // Adding nodes to right-hand panel
        splitPane.getItems().addAll(leftPane, rightPaneVerticalSplit);
        splitPane.setDividerPositions(0.4f, 0.6f);

        // Setting up the scene
        welcomeScene = new Scene(splitPane, 800, 800);
        welcomeScene.getStylesheets().add("src/viewUranusStyles.css");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
