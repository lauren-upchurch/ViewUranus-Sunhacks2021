package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Displays day and date in customized label object.
 */

public class DayLabel extends Pane {

    private String dayName;
    private LocalDateTime date;
    private VBox vBoxLayout = new VBox();
    private DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEE");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd");

    private DayLabel(){};

    public DayLabel(LocalDateTime date) {
        super();
        this.date = date;

        initPane();
    }

    private void initPane() {
        String dayString = this.date.format(dayFormatter);
        String dateString = this.date.format(dateFormatter);

        Label dayLabel = new Label(dayString);
        Label dateLabel = new Label(dateString);
        dayLabel.setFont(Font.font("Tahoma", FontWeight.MEDIUM, 30));
        dateLabel.setFont(Font.font("Tahoma", FontWeight.THIN, 15));

        vBoxLayout.getChildren().addAll(dayLabel, dateLabel);
        vBoxLayout.setSpacing(3);
        vBoxLayout.setAlignment(Pos.BASELINE_CENTER);
        vBoxLayout.setVisible(true);

        this.getChildren().add(vBoxLayout);
    }

//    public static void main(String[] args) {
//        LocalDateTime timeNow = LocalDateTime.now();
//        DayLabel testLabel = new DayLabel(timeNow);
//    }

}
