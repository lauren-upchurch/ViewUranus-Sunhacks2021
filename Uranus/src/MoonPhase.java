package src;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;


public class MoonPhase {
    private LocalDate currentDate;
    private int currentDay;
    private Month currentMonth;
    private int currentYear;
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
        return currentDate.plusDays((long) nextNewMoon);
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

//    public static void main(String args[]) {
//        MoonPhase next = new MoonPhase();
//        System.out.println("Next New Moon is " + next.calculateNextNewMoon().toString());
//        System.out.println("Stargazing is good between days: " + next.starGazingRange().toString());
//    }
}
