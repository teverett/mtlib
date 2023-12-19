package com.khubla.mtlib.domain.inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryList {
   private final List<InventoryItem> InventoryItems = new ArrayList<InventoryItem>();

   public List<InventoryItem> getInventoryItems() {
      return InventoryItems;
   }
}
