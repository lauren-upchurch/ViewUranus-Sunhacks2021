package src;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;


public class MoonPhase {
    private final LocalDateTime currentDate;
    private final int currentDay;
    private final Month currentMonth;
    private final int currentYear;
    private final LocalDate newMoon = LocalDate.of(2000, Month.JANUARY, 6);

    public MoonPhase() {
        this.currentDate = LocalDateTime.now();
        this.currentDay = currentDate.getDayOfMonth();
        this.currentMonth = currentDate.getMonth();
        this.currentYear = currentDate.getYear();
    }

    private LocalDateTime calculateNextNewMoon() {
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

    private static LocalDateTime calculateNextNewMoonFromDate(LocalDateTime day) {
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
        return day.plusDays((long) ((long) nextNewMoon + .75));
    }

    private static double getDecimal(double doubleNumber) {
        String doubleAsString = String.valueOf(doubleNumber);
        int indexOfDecimal = doubleAsString.indexOf(".");
        return Double.parseDouble(doubleAsString.substring(indexOfDecimal));
    }

    private ArrayList<LocalDateTime> starGazingRange() {
        ArrayList<LocalDateTime> dates = new ArrayList<>();
        LocalDateTime newMoon = calculateNextNewMoon();
        if(newMoon.minusDays(4).isBefore(currentDate)) {
            dates.add(currentDate);
        } else {
            dates.add(newMoon.minusDays(4));
        }
        dates.add(newMoon.plusDays(4));
        return dates;
    }
    
    public static Phase whatPhaseIsIt(LocalDateTime day) {
        LocalDateTime nextNewMoonFromDay = calculateNextNewMoonFromDate(day);
        Phase phase = Phase.NEW_MOON;
        if (day.isEqual(ChronoLocalDateTime.from(nextNewMoonFromDay))) {
            phase = Phase.NEW_MOON;
        } else if (nextNewMoonFromDay.minusDays((long) 7).equals(day)) {
            phase = Phase.THIRD_QUARTER;
        } else if (nextNewMoonFromDay.minusDays((long) 15).equals(day)) {
            phase = Phase.FULL_MOON;
        } else if (nextNewMoonFromDay.minusDays((long) 23).equals(day)) {
            phase = Phase.FIRST_QUARTER;
        } else if (day.isAfter(ChronoLocalDateTime.from(nextNewMoonFromDay.minusDays((long) 7.375)))) {
            phase = Phase.WANING_CRESCENT;
        } else if (day.isAfter(ChronoLocalDateTime.from(nextNewMoonFromDay.minusDays((long) 14.75)))) {
            phase = Phase.WANING_GIBBOUS;
        } else if (day.isAfter(ChronoLocalDateTime.from(nextNewMoonFromDay.minusDays((long) 22.5)))) {
            phase = Phase.WAXING_GIBBOUS;
        } else if (day.isAfter(ChronoLocalDateTime.from(nextNewMoonFromDay.minusDays((long) 29.5)))) {
            phase = Phase.WAXING_CRESCENT;
        }
        return phase;
    }
    public String phaseToString(Phase phase) {
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

//    public static void main(String args[]) {
//        MoonPhase next = new MoonPhase();
//        System.out.println("Next New Moon is " + next.calculateNextNewMoon().toString());
//        System.out.println("Stargazing is good between days: " + next.starGazingRange().toString());
//        System.out.println("Stargazing is good between days: " + next.starGazingRange().toString());
//        System.out.println(next.phaseToString(next.whatPhaseIsIt(next.currentDate.plusDays(33))));
//        System.out.println(next.currentDate.plusDays(33));
//   }

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
