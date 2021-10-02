import java.time.LocalDateTime;
import java.util.Date;

public class ForecastData {
    LocalDateTime date;
    String forecast;


    public ForecastData(LocalDateTime d, String f) {
        this.date = d;
        this.forecast = f;
    }

}
