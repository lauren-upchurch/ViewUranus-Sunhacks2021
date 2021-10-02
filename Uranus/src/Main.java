

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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    private LocationWeather weather;
    private ArrayList<ForecastData> forecasts;
    private Scene welcomeScene;

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

    /**
     * Helper method that sets up the StartScreen (NOTE: button functionality is included in this).
     */
    private void setUpStartScreen(){
        SplitPane splitPane = new SplitPane();
        VBox forecastLabelBox = new VBox(); // This is just a placeholder for now

        // Create text field

        TextField locationField = new TextField("Enter your location: 'City,State'");
        locationField.setFont(Font.font("Helvetica", FontWeight.THIN, 14));
        locationField.setPrefColumnCount(40);
        locationField.setMaxWidth(250);

        // Create content labels
        Label topLabel = new Label("View Uranus");
        topLabel.setFont(Font.font("Helvetica", FontWeight.EXTRA_LIGHT, 25));

        Label subLabel = new Label("(or other stellar phenomena)");
        subLabel.setFont(Font.font("Helvetica", FontWeight.THIN, 12));

        // Create button for sending textfield data to backend
        Button checkVisibilityButton = new Button("Check visibility");
        checkVisibilityButton.setFont(Font.font("Helvetica", FontWeight.THIN, 16));
        checkVisibilityButton.setBackground(new Background(
                new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        ///////////////////////////////////////////////////////////////////////////////////////////
        // NOTE TO LAUREN AND GRANT:
        // BELOW IS WHERE THE BUTTON CLICK IS HANDLED. RIGHT NOW IT JUST PULLS THE WEATHER DATA
        // AND PRINTS IT OUT TO THE RIGHT-HAND PANEL. THIS WOULD BE A GOOD PLACE TO MAKE A CALL
        // TO SOME SORT OF CONTROLLER THAT COLLATES ALL RELEVANT DATA, THEN I CAN HAVE THE INTERFACE
        // PRESENT IT ACCORDINGLY.
        ///////////////////////////////////////////////////////////////////////////////////////////

        checkVisibilityButton.setOnMouseClicked(e -> {

            // Empty out right-hand panel and get location entry from textfield
            forecastLabelBox.getChildren().clear();
            String location = locationField.getText();

            // Gets weather information for hte given location
            getWeather(location);

            // Formatting weather condition (NOTE: This is mostly for debugging. Will update with
            // visual representation of which days are optimal for viewing Uranus
            Label locationLabel = new Label("View Uranus in " + location);
            locationLabel.setFont(Font.font("Helvetica", FontWeight.MEDIUM, 15));
            VBox.setMargin(locationLabel, new Insets(20, 0, 0, 0));
            forecastLabelBox.getChildren().add(locationLabel);

            for (int i = 0; i < 4; i++) {
                if (forecasts.get(i) != null) {
                    Label dayLabel = new Label(forecasts.get(i).toString());
                    forecastLabelBox.getChildren().add(dayLabel);
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
                new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        // Create vertical split pane for right-hand panel
        SplitPane rightPaneVerticalSplit = new SplitPane();
        rightPaneVerticalSplit.setOrientation(Orientation.VERTICAL);
        rightPaneVerticalSplit.setDividerPositions(0.3f, 0.7f);

        // Create top and bottom panes for right-hand panel
        Pane topPane = new Pane(new Label("Recommended days will go in this pane"));
        topPane.getChildren().add(forecastLabelBox);
        Pane bottomPane = new Pane(new Label("Potentially visible bodies will go in this one"));
        rightPaneVerticalSplit.getItems().addAll(topPane, bottomPane);

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
