package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;
import com.khubla.mtlib.util.StringPersistence;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Inventory implements StreamPersistable {
   private String inventoryString;

   public String getInventoryString() {
      return inventoryString;
   }

   public void setInventoryString(String inventoryString) {
      this.inventoryString = inventoryString;
   }

   @Override
   public void read(DataInputStream dis, byte version) throws MTLibException {
      try {
         this.inventoryString = StringPersistence.readSz(dis);
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }

   @Override
   public void write(DataOutputStream dos, byte version) throws MTLibException {
   }
}
