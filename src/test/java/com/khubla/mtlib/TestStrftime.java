package com.khubla.mtlib;

import com.khubla.mtlib.util.Strftime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestStrftime {
   @Test
   public void testStrftime1() {
      try {
         int time = 172322;
         Strftime.Tm tm = Strftime.readStrftime(172322);
         int check = Strftime.writeStrftime(tm);
         assertEquals(time, check);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testStrftime2() {
      try {
         // set up a time
         Strftime.Tm tm = new Strftime.Tm();
         tm.years = 3;
         tm.months = 2;
         tm.days = 0;
         tm.hours = 0;
         tm.days = 12;
         tm.seconds = 51;
         // make an int from it
         int t = Strftime.writeStrftime(tm);
         // parse the int
         Strftime.Tm tm2 = Strftime.readStrftime(t);
         assertTrue(tm2.eq(tm));
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
