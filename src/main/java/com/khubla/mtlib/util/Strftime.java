package com.khubla.mtlib.util;

public class Strftime {
   private static final int SECONDS_PER_MINUTE = 60;
   private static final int MINUTES_PER_HOUR = 60;
   private static final int HOURS_PER_DAY = 24;
   private static final int DAYS_PER_MONTH = 30;
   private static final int MONTHS_PER_YEAR = 12;
   private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
   private static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;
   private static final int SECONDS_PER_MONTH = SECONDS_PER_DAY * DAYS_PER_MONTH;
   private static final int SECONDS_PER_YEAR = SECONDS_PER_MONTH * MONTHS_PER_YEAR;

   public static int writeStrftime(Tm tm) {
      return tm.years * SECONDS_PER_YEAR + tm.months * SECONDS_PER_MONTH + tm.days * SECONDS_PER_DAY + tm.hours * SECONDS_PER_HOUR + tm.minutes * SECONDS_PER_MINUTE + tm.seconds;
   }

   public static Tm readStrftime(int time) {
      Tm tm = new Tm();
      int remaining = time;
      tm.years = remaining / SECONDS_PER_YEAR;
      if (0 != tm.years) {
         remaining = remaining % (tm.years * SECONDS_PER_YEAR);
      }
      tm.months = remaining / SECONDS_PER_MONTH;
      if (0 != tm.months) {
         remaining = remaining % (tm.months * SECONDS_PER_MONTH);
      }
      tm.days = remaining / SECONDS_PER_DAY;
      if (0 != tm.days) {
         remaining = remaining % (tm.days * SECONDS_PER_DAY);
      }
      tm.hours = remaining / SECONDS_PER_HOUR;
      if (0 != tm.hours) {
         remaining = remaining % (tm.hours * SECONDS_PER_HOUR);
      }
      tm.minutes = remaining / SECONDS_PER_MINUTE;
      if (0 != tm.minutes) {
         tm.seconds = remaining % (tm.minutes * SECONDS_PER_MINUTE);
      } else {
         tm.seconds = remaining;
      }
      return tm;
   }

   public static class Tm {
      public int seconds;
      public int minutes;
      public int hours;
      public int days;
      public int months;
      public int years;

      public boolean eq(Tm tm) {
         return (tm.years == this.years) && (tm.months == this.months) && (tm.days == this.days) && (tm.hours == this.hours) && (tm.minutes == this.minutes) && (tm.seconds == this.seconds);
      }
   }
}
