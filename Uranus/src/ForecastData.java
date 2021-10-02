import java.time.LocalDateTime;

public class ForecastData {
    LocalDateTime date;
    String forecast;
    String dayName;
    String latitude; // do we need these here, maybe we save the latitude and longitude that gets fed into the weather class
    String longitude;


    public ForecastData(LocalDateTime d, String dName, String f) {
        this.date = d;
        this.forecast = f;
        this.dayName = dName;
    }

    @Override
    public String toString() {
        String string = date.toString().substring(0,10) + "\t" + dayName + ":\t" + forecast;
        return string;
    }

}
