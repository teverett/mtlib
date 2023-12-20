package com.khubla.mtlib.domain.inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryList {
   private final List<InventoryItem> InventoryItems = new ArrayList<InventoryItem>();
   private String name;
   private int declaredSize;

   public int getDeclaredSize() {
      return declaredSize;
   }

   public void setDeclaredSize(int declaredSize) {
      this.declaredSize = declaredSize;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List<InventoryItem> getInventoryItems() {
      return InventoryItems;
   }
}
