import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.time.LocalDate;
import java.time.Month;
//import java.util.TimeZone;


public class MoonPhase {
    private LocalDate currentDate;
    private int currentDay;
    private Month currentMonth;
    private int currentYear;
    private final LocalDate newMoon = LocalDate.of(2000, Month.JANUARY, 6);
    private

    public MoonPhase() {
        this.currentDate = LocalDate.now();
        this.currentDay = currentdate.getDayOfMonth();
        this.currentMonth = currentdate.getMonth();
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
        int e = 365.25 * (year + 4716);
        int f = 30.60001 * (month + 1);
        double j = c + d + e + f - 1524.5; // Julian day
        double sinceNewMoon  = j - 2451549.5;
        double newMoons = sinceNewMoon / 29.53;
        //double lastNewMoon = Math.ceil(getDecimal(newMoons));
        double lastNewMoon = getDecimal(newMoons) * 29.53;
        
    }

    private double getDecimal(double doubleNumber) {
        String doubleAsString = String.valueOf(doubleNumber);
        int indexOfDecimal = doubleAsString.indexOf(".");
        System.out.println("Integer Part: " + doubleAsString.substring(0, indexOfDecimal));
        System.out.println("Decimal Part: " + doubleAsString.substring(indexOfDecimal));
        return (double) doubleAsString.substring(indexOfDecimal));
    }

}