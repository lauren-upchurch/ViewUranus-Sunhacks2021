import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;


public class MoonPhase {
    private final LocalDate currentDate;
    private final int currentDay;
    private final Month currentMonth;
    private final int currentYear;
    private final LocalDate newMoon = LocalDate.of(2000, Month.JANUARY, 6);

    public MoonPhase() {
        this.currentDate = LocalDate.now();
        this.currentDay = currentDate.getDayOfMonth();
        this.currentMonth = currentDate.getMonth();
        this.currentYear = currentDate.getYear();
    }

    private LocalDate calculateNextNewMoon() {
        int month =  currentMonth.getValue();
        int year = currentYear;
        if(month <= 2) {
            month = month + 12;
            year--;
        }
        int a = year / 100;
        int b = a / 4;
        int c = 2 - a + b;
        int d = currentDay;
        int e = (int) (365.25 * (year + 4716));
        int f = (int) (30.60001 * (month + 1));
        double j = c + d + e + f - 1524.5; // Julian day
        double sinceNewMoon  = j - 2451549.5;
        double newMoons = sinceNewMoon / 29.53;
        //double lastNewMoon = Math.ceil(getDecimal(newMoons));
        double lastNewMoon = getDecimal(newMoons) * 29.53;
        double nextNewMoon = 29.5 - lastNewMoon;
        return currentDate.plusDays((long) nextNewMoon + 1);
    }

    private LocalDate calculateNextNewMoonFromDate(LocalDate day) {
        int month =  day.getMonth().getValue();
        int year = day.getYear();
        if(month <= 2) {
            month = month + 12;
            year--;
        }
        int a = year / 100;
        int b = a / 4;
        int c = 2 - a + b;
        int d = day.getDayOfMonth();
        int e = (int) (365.25 * (year + 4716));
        int f = (int) (30.60001 * (month + 1));
        double j = c + d + e + f - 1524.5; // Julian day
        double sinceNewMoon  = j - 2451549.5;
        double newMoons = sinceNewMoon / 29.53;
        //double lastNewMoon = Math.ceil(getDecimal(newMoons));
        double lastNewMoon = getDecimal(newMoons) * 29.53;
        double nextNewMoon = 29.5 - lastNewMoon;
        return day.plusDays((long) nextNewMoon + 1);
    }

    private double getDecimal(double doubleNumber) {
        String doubleAsString = String.valueOf(doubleNumber);
        int indexOfDecimal = doubleAsString.indexOf(".");
        return Double.parseDouble(doubleAsString.substring(indexOfDecimal));
    }

    private ArrayList<LocalDate> starGazingRange() {
        ArrayList<LocalDate> dates = new ArrayList<>();
        LocalDate newMoon = calculateNextNewMoon();
        if(newMoon.minusDays(4).isBefore(currentDate)) {
            dates.add(currentDate);
        } else {
            dates.add(newMoon.minusDays(4));
        }
        dates.add(newMoon.plusDays(4));
        return dates;
    }
    
    private Phase whatPhaseIsIt(LocalDate day) {
        LocalDate nextNewMoonFromDay = calculateNextNewMoonFromDate(day);
        Phase phase = Phase.NEW_MOON;
        if (day.isEqual(nextNewMoonFromDay)) {
            phase = Phase.NEW_MOON;
        } else if (nextNewMoonFromDay.minusDays((long) 7.5).equals(day)) {
            phase = Phase.THIRD_QUARTER;
        } else if (nextNewMoonFromDay.minusDays(15).equals(day)) {
            phase = Phase.FULL_MOON;
        } else if (nextNewMoonFromDay.minusDays((long) 22.5).equals(day)) {
            phase = Phase.FIRST_QUARTER;
        } else if (day.isAfter(nextNewMoonFromDay.minusDays((long) 7.5))) {
            phase = Phase.WANING_CRESCENT;
        } else if (day.isAfter(nextNewMoonFromDay.minusDays(15))) {
            phase = Phase.WANING_GIBBOUS;
        } else if (day.isAfter(nextNewMoonFromDay.minusDays((long) 22.5))) {
            phase = Phase.WAXING_GIBBOUS;
        } else if (day.isAfter(nextNewMoonFromDay.minusDays((long) 29.5))) {
            phase = Phase.WAXING_CRESCENT;
        }
        return phase;
    }
    private String phaseToString(Phase phase) {
        String str = "";
        switch (phase) {
            case NEW_MOON:
                str = "New Moon";
                break;
            case THIRD_QUARTER:
                str = "Third Quarter";
                break;
            case FULL_MOON:
                str = "Full Moon";
                break;
            case FIRST_QUARTER:
                str = "First Quarter";
                break;
            case WANING_CRESCENT:
                str = "Waning Crescent";
                break;
            case WANING_GIBBOUS:
                str = "Waning Gibbous";
                break;
            case WAXING_GIBBOUS:
                str = "Waxing Gibbous";
                break;
            case WAXING_CRESCENT:
                str = "Waxing Crescent";
                break;
            default:
                str = "NOT WORKING";
        }
        return str;
    }
/*
    public static void main(String args[]) {
        MoonPhase next = new MoonPhase();
        System.out.println("Next New Moon is " + next.calculateNextNewMoon().toString());
        System.out.println("Stargazing is good between days: " + next.starGazingRange().toString());
        System.out.println("Stargazing is good between days: " + next.starGazingRange().toString());
        System.out.println(next.phaseToString(next.whatPhaseIsIt(next.currentDate.plusDays(10))));
   }
*/
    public enum Phase {
        NEW_MOON,
        WAXING_CRESCENT,
        FIRST_QUARTER,
        WAXING_GIBBOUS,
        FULL_MOON,
        WANING_GIBBOUS,
        THIRD_QUARTER,
        WANING_CRESCENT
    }
}
