package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import src.Stargazing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Displays day and date in customized label object.
 */

public class DayLabel extends Pane {

    private String dayName;
    private LocalDateTime date;
    private Stargazing.Gaze visibility;
    private VBox vBoxLayout = new VBox();
    private DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEE");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd");

    private DayLabel(){};
//
//    public DayLabel(LocalDateTime date) {
//        super();
//        this.date = date;
//
//        initPane();
//    }

    public DayLabel(LocalDateTime date, Stargazing.Gaze visibility) {
        super();
        this.date = date;
        this.visibility = visibility;

        initPane();
    }

    private void initPane() {
        String dayString = this.date.format(dayFormatter);
        String dateString = this.date.format(dateFormatter);

        Label dayLabel = new Label(dayString);
        Label dateLabel = new Label(dateString);
        dayLabel.setFont(Font.font("Tahoma", FontWeight.MEDIUM, 30));
        dateLabel.setFont(Font.font("Tahoma", FontWeight.THIN, 15));

        Rectangle visibilityRating = new Rectangle();
        visibilityRating.setHeight(10);
        visibilityRating.widthProperty().bind(dayLabel.widthProperty());
        setBackGroundColor(visibilityRating);

        vBoxLayout.getChildren().addAll(dayLabel, dateLabel, visibilityRating);
        vBoxLayout.setSpacing(1);
        VBox.setMargin(visibilityRating, new Insets(4, 0, 2, 0));
        vBoxLayout.setAlignment(Pos.BASELINE_CENTER);
        vBoxLayout.setVisible(true);

        this.getChildren().add(vBoxLayout);
    }

    /**
     * Helper method for setting background color based on visibility
     */
    private void setBackGroundColor(Rectangle rectangle) {
        switch (visibility) {
            case BAD:
                rectangle.setFill(Color.RED);
                break;
            case GOOD:
                rectangle.setFill(Color.GREENYELLOW);
                break;
            case OK:
                rectangle.setFill(Color.YELLOW);
                break;
            case GREAT:
                rectangle.setFill(Color.DARKGREEN);
                break;
            default:
                rectangle.setFill(null);
                break;
        }
    }

//    public static void main(String[] args) {
//        LocalDateTime timeNow = LocalDateTime.now();
//        DayLabel testLabel = new DayLabel(timeNow);
//    }

}
