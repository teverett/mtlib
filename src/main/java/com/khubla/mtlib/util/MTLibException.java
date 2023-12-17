package com.khubla.mtlib.util;

public class MTLibException extends Exception {
   private static final long serialVersionUID = 1L;

   public MTLibException(String message) {
      super(message);
   }

   public MTLibException(String message, Exception e) {
      super(message, e);
   }
}
