package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import src.LocationWeather;

import java.util.ArrayList;

public class UranusTable extends GridPane {

    private String[] rawData;
    private ArrayList<Uranusling> cleanedData = new ArrayList<>();
    private String dataSourceURL;

    private final int NUM_COLUMNS = 5;
    private final int NUM_ENTRIES_TO_SHOW = 10;

    public UranusTable() {
        super();
        getRawData();
        cleanUpData();

        initTable();
    }

    private void initTable() {
        VBox[] columnTitles = createColumnTitles();
        this.setHgap(15);
        this.setVgap(10);

        // Add column titles to the grid
        for (int i = 0; i < NUM_COLUMNS; i++) {
            this.add(columnTitles[i], i, 0);
        }

        // Insert all formatted data into the grid
        insertCleanedData();
    }

    /**
     * Helper method that creates stylized column titles for UranusTable
     * @return VBox[] An array of VBox items that contain labels
     */
    private VBox[] createColumnTitles() {
        VBox[] columnTitles = new VBox[NUM_COLUMNS];
        Label[] labels = new Label[NUM_COLUMNS * 2];

        Label dateLabel = new Label("Date");
        Label subDateLabel = new Label("year mth day");
        Label ascLabel = new Label("Ascension");
        Label subAscLabel = new Label("hour min sec");
        Label declinationLabel = new Label("Declination");
        Label subDeclinationLabel = new Label("deg min sec");
        Label observableLabel = new Label("Observable");
        Label subObservableLabel = new Label("times visible");
        Label constellationLabel = new Label ("Constellation");
        Label subConstellationLabel = new Label("region of sky");

        labels[0] = dateLabel;
        labels[1] = subDateLabel;
        labels[2] = ascLabel;
        labels[3] = subAscLabel;
        labels[4] = declinationLabel;
        labels[5] = subDeclinationLabel;
        labels[6] = observableLabel;
        labels[7] = subObservableLabel;
        labels[8] = constellationLabel;
        labels[9] = subConstellationLabel;

        StackPane newStackPane = new StackPane();
        VBox newVBox = new VBox();
        Rectangle underline = new Rectangle();
        int titlesIncrementer = 0;
        for (int i = 0; i < labels.length; i++) {
            if (i % 2 == 0) {
                newVBox = new VBox();
                newStackPane = new StackPane();
                newStackPane.setAlignment(Pos.BASELINE_CENTER);
                labels[i].setFont(Font.font("Tahoma", FontWeight.MEDIUM, 15));
                newStackPane.getChildren().add(labels[i]);
                newVBox.getChildren().add(newStackPane);
            } else {
                newStackPane = new StackPane();
                labels[i].setFont(Font.font("Tahoma", FontWeight.THIN, 11));
                newStackPane.getChildren().add(labels[i]);
                newVBox.getChildren().add(newStackPane);

                underline = new Rectangle();
                underline.widthProperty().bind(labels[i - 1].widthProperty());
                underline.setHeight(2);
                underline.setFill(Color.MIDNIGHTBLUE);
                newStackPane = new StackPane(underline);
                newVBox.getChildren().add(newStackPane);

                columnTitles[titlesIncrementer] = newVBox;
                titlesIncrementer++;
            }
        }

        return columnTitles;
    }

    private void insertCleanedData() {

        Uranusling uranusling = new Uranusling();
        Label dataLabel = new Label();
        StackPane stackPane = new StackPane();

        for (int i = 0; i < NUM_ENTRIES_TO_SHOW; i++) {
            uranusling = this.cleanedData.get(i);

            for (int j = 0; j < NUM_COLUMNS; j++) {
                String dataToStore = "";
                switch (j) {
                    case 0:
                        dataToStore = uranusling.date;
                        break;
                    case 1:
                        dataToStore = uranusling.ascension;
                        break;
                    case 2:
                        dataToStore = uranusling.declination;
                        break;
                    case 3:
                        dataToStore = uranusling.observable;
                        break;
                    case 4:
                        dataToStore = uranusling.constellation;
                        break;
                    default:
                        dataToStore = "";
                }

                dataLabel = new Label(dataToStore);
                dataLabel.setFont(Font.font("Tahoma", FontWeight.THIN, 12));
                stackPane = new StackPane(dataLabel);
                stackPane.setAlignment(Pos.BASELINE_CENTER);

                if (i % 2 == 0) {
                    stackPane.setBackground(new Background(
                            new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                }

                this.add(stackPane, j, i + 1);
            }
        }
    }


    /**
     * Retrieves raw data from online source.
     */
    private void getRawData() {
        String url = "https://in-the-sky.org/ephemeris.php?startday=2&startmonth=10&startyear=2021&ird=1&irs=1&ima=1&iph" +
                "=0&ias=0&iss=0&iob=1&ide=0&ids=0&interval=1&tz=0&format=txt&rows=25&objtype=1&objpl=Uranus&objtxt=Uranus";
        String result = LocationWeather.fetchURL(url);
        String[] input = result.split("\\|");

        int count = 0;
        for (String i : input) {
            i = i.trim();
//            System.out.println(count + " " + i);
            count++;
        }

        rawData = input;
    }

    /**
     * Converts raw data into objects and then saves into ArrayList (cleanedData)
     */
    private void cleanUpData() {
        int counter = 0;
        Uranusling newUranusling = new Uranusling();
        for (int i = 24; i < rawData.length - 6; i++) {
            if (counter == 10) {
                counter = 0; // Reset counter for each Uranusling
                cleanedData.add(newUranusling);
            }

            if (counter == 0) newUranusling = new Uranusling();

            switch (counter) {
                case 0:
                    newUranusling.date = rawData[i];
                    break;
                case 1:
                    newUranusling.moonAge = rawData[i];
                    break;
                case 2:
                    newUranusling.ascension = rawData[i];
                    break;
                case 3:
                    newUranusling.declination = rawData[i];
                    break;
                case 4:
                    newUranusling.rise = rawData[i];
                    break;
                case 5:
                    newUranusling.culm = rawData[i];
                    break;
                case 6:
                    newUranusling.set = rawData[i];
                    break;
                case 7:
                    newUranusling.mag = rawData[i];
                    break;
                case 8:
                    newUranusling.observable = rawData[i];
                    break;
                case 9:
                    newUranusling.constellation = rawData[i];
                    break;
                default:
                    break;
            }

            counter++;
        }
    }

    /**
     * Private class representing Uranus' trajectory on one single day
     */
    private class Uranusling {

        private String date;
        private String moonAge;
        private String ascension;
        private String declination;
        private String rise;
        private String culm;
        private String set;
        private String mag;
        private String observable;
        private String constellation;

        private ArrayList<String> fullData;
        private ArrayList<String> clippedData;

        public Uranusling() {
            this.date = "";
            this.moonAge = "";
            this.ascension = "";
            this.declination = "";
            this.rise = "";
            this.culm = "";
            this.set = "";
            this.mag = "";
            this.observable = "";
            this.constellation = "";

            // Clipping off the bits of the data we don't want
            this.clippedData = new ArrayList<>();

            clippedData.add(this.date);
            clippedData.add(this.ascension);
            clippedData.add(this.declination);
            clippedData.add(observable);
            clippedData.add(constellation);
        }
    }



//    public TableView getTable() {
//        String url = "https://in-the-sky.org/ephemeris.php?startday=2&startmonth=10&startyear=2021&ird=1&irs=1&ima=1&iph" +
//                "=0&ias=0&iss=0&iob=1&ide=0&ids=0&interval=1&tz=0&format=txt&rows=25&objtype=1&objpl=Uranus&objtxt=Uranus";
//        String result = LocationWeather.fetchURL(url);
//        String[] input = result.split("\\|");
//        int count = 0;
//        for (String i : input) {
//            i = i.trim();
//            //System.out.println(count + " " + i);
//            count++;
//        }

//
//        ArrayList<String> colNames = new ArrayList<String>();
//        colNames.add("DATE (YYYY MM DD");
//        colNames.add("RIGHT ASCENSION");
//        colNames.add("DECLINATION");
//        colNames.add("OBSERVABLE");
//        colNames.add("CONSTELLATION");
//        /*
//        TableView<TableData> table = new TableView();
//
//        TableColumn<TableData, String> col1 = new TableColumn(input[3] + "\n" + "Year - Month - Day");
//        col1.setPrefWidth(150);
//        TableColumn<TableData, String> col3 = new TableColumn(input[5] + "\n" + "Hours - Min - Sec");
//        col3.setPrefWidth(150);
//        TableColumn<TableData, String> col4 = new TableColumn(input[6] + "\n" + "Deg - Min - Sec");
//        col4.setPrefWidth(150);
//        TableColumn<TableData, String> col9 = new TableColumn(input[11]);
//        col9.setPrefWidth(150);
//        TableColumn<TableData, String> col10 = new TableColumn(input[12]);
//        col10.setPrefWidth(150);
//
//        table.getColumns().addAll(col1,col3,col4,col9,col10);
//
//         */
//        ArrayList<TableData> tableData = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            int idx1, idx2, idx3,idx4,idx5;
//            idx1=24;
//            idx2=26;
//            idx3=27;
//            idx4=32;
//            idx5=33;
//            tableData.add(new TableData(input[idx1],input[idx2],input[idx3],input[idx4],input[idx5]));
//            idx1 +=10;
//            idx2 +=10;
//            idx3 +=10;
//            idx4 +=10;
//            idx5 +=10;
//        }
//
//        //ObservableList<TableData> tabList = FXCollections.observableArrayList(
//        //        tableData.get(0), tableData.get(1), tableData.get(2), tableData.get(3),
//        //        tableData.get(4), tableData.get(5), tableData.get(6), tableData.get(7)
//        //);
//        //table.getItems().addAll(tabList);
//
//        /*
//        ObservableList<String> list = FXCollections.observableArrayList();
//
//        ObservableList<String> data = FXCollections.observableArrayList(
//                new FileData("file1", "D:\\myFiles\\file1.txt", "25 MB", "12/01/2017"),
//                new FileData("file2", "D:\\myFiles\\file2.txt", "30 MB", "01/11/2019"),
//                new FileData("file3", "D:\\myFiles\\file3.txt", "50 MB", "12/04/2017"),
//                new FileData("file4", "D:\\myFiles\\file4.txt", "75 MB", "25/09/2018")
//        );
//        */
//        return table;
//    }
//    class TableData {
//        String date;
//        String asc;
//        String desc;
//        String obs;
//        String constell;
//
//        public TableData(String date, String asc, String desc, String obs, String constell) {
//            this.date = date;
//            this.asc = asc;
//            this.desc = desc;
//            this.obs = obs;
//            this.constell = constell;
//        }
//    }
}
