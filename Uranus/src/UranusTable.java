package src;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class UranusTable {

    public TableView getTable() {
        String url = "https://in-the-sky.org/ephemeris.php?startday=2&startmonth=10&startyear=2021&ird=1&irs=1&ima=1&iph" +
                "=0&ias=0&iss=0&iob=1&ide=0&ids=0&interval=1&tz=0&format=txt&rows=25&objtype=1&objpl=Uranus&objtxt=Uranus";
        String result = LocationWeather.fetchURL(url);
        String[] input = result.split("\\|");
        int count = 0;
        for (String i : input) {
            i = i.trim();
            //System.out.println(count + " " + i);
            count++;
        }
        TableView table = new TableView();

        TableColumn col1 = new TableColumn(input[3] + "\n" + "Year - Month - Day");
        col1.setPrefWidth(150);
        TableColumn col3 = new TableColumn(input[5] + "\n" + "Hours - Min - Sec");
        col3.setPrefWidth(150);
        TableColumn col4 = new TableColumn(input[6] + "\n" + "Deg - Min - Sec");
        col4.setPrefWidth(150);
        TableColumn col9 = new TableColumn(input[11]);
        col9.setPrefWidth(150);
        TableColumn col10 = new TableColumn(input[12]);
        col10.setPrefWidth(150);

        table.getColumns().addAll(col1,col3,col4,col9,col10);
        List<TableData> tabList = new ArrayList<TableData>();

        for (int i = 0; i < 10; i++) {
            int idx1, idx2, idx3,idx4,idx5;
            idx1=24;
            idx2=26;
            idx3=27;
            idx4=32;
            idx5=33;
            TableData data = new TableData(input[idx1],input[idx2],input[idx3],input[idx4],input[idx5]);
            tabList.add(data);
            idx1 +=10;
            idx2 +=10;
            idx3 +=10;
            idx4 +=10;
            idx5 +=10;
        }
        

        table.getItems().add(tabList);

        /*
        ObservableList<String> list = FXCollections.observableArrayList();

        ObservableList<String> data = FXCollections.observableArrayList(
                new FileData("file1", "D:\\myFiles\\file1.txt", "25 MB", "12/01/2017"),
                new FileData("file2", "D:\\myFiles\\file2.txt", "30 MB", "01/11/2019"),
                new FileData("file3", "D:\\myFiles\\file3.txt", "50 MB", "12/04/2017"),
                new FileData("file4", "D:\\myFiles\\file4.txt", "75 MB", "25/09/2018")
        );
        */
        return table;
    }
    class TableData {
        String date;
        String asc;
        String desc;
        String obs;
        String constell;

        public TableData(String date, String asc, String desc, String obs, String constell) {
            this.date = date;
            this.asc = asc;
            this.desc = desc;
            this.obs = obs;
            this.constell = constell;
        }
    }

}
