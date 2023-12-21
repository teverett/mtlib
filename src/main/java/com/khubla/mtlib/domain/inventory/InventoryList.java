package com.khubla.mtlib.domain.inventory;

public class InventoryList {
   private final InventoryItem[] inventoryItems;
   private String name;
   private String width;

   public InventoryList(int declaredSize) {
      this.inventoryItems = new InventoryItem[declaredSize];
   }

   public String getWidth() {
      return width;
   }

   public void setWidth(String width) {
      this.width = width;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public InventoryItem[] getInventoryItems() {
      return inventoryItems;
   }
}
