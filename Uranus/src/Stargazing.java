import java.time.LocalDate;

public class Stargazing {
private ForecastData forecast;
private MoonPhase.Phase phase;
private Gaze gaze;

public Stargazing(ForecastData forecast) {
    this.forecast = forecast;
    phase = MoonPhase.whatPhaseIsIt(forecast.date);
    gaze = starGazingPotential();
}

public Gaze starGazingPotential() {
    if(forecast.forecast.equalsIgnoreCase("Clear") && phase.equals(MoonPhase.Phase.NEW_MOON)) {
        return Gaze.GREAT;
    } else {
        final boolean isNearlyNew = (phase.equals(MoonPhase.Phase.NEW_MOON)) || (phase.equals(MoonPhase.Phase.WANING_CRESCENT)) || (phase.equals(MoonPhase.Phase.WAXING_CRESCENT));
        if((forecast.forecast.equalsIgnoreCase("Clear"))&& isNearlyNew) {
            return Gaze.GOOD;
        } else if((forecast.forecast.equalsIgnoreCase("Partly Cloudy")) && isNearlyNew) {
            return Gaze.OK;
        } else {
            return Gaze.BAD;
        }
    }
}

    public String getGaze() {
    String str = "";
    switch(gaze) {
        case GREAT:
            str = "Great";
            break;
        case GOOD:
            str = "Good";
            break;
        case OK:
            str = "Ok";
            break;
        case BAD:
            str = "Bad";
            break;
    }
    return str;
}

public enum Gaze {
    GREAT,
    GOOD,
    OK,
    BAD;
}
}
