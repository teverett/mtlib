package com.khubla.mtlib.domain.inventory.listener;

import com.khubla.mtlib.InventoryBaseListener;
import com.khubla.mtlib.InventoryParser;
import com.khubla.mtlib.domain.inventory.InventoryItem;
import com.khubla.mtlib.domain.inventory.InventoryList;

public class ListItemListener extends InventoryBaseListener {
   public final InventoryList inventoryList;
   public String width;

   public ListItemListener(InventoryList inventoryList) {
      this.inventoryList = inventoryList;
   }

   @Override
   public void enterListitem(InventoryParser.ListitemContext ctx) {
      if (null != ctx.width()) {
         WidthListener widthListener = new WidthListener();
         widthListener.enterWidth(ctx.width());
         this.width = widthListener.num;
      } else if (null != ctx.item()) {
         ItemListener itemListener = new ItemListener();
         itemListener.enterItem(ctx.item());
         InventoryItem inventoryItem = new InventoryItem();
         inventoryItem.setName(itemListener.id);
         if (null != itemListener.nums) {
            if (itemListener.nums.length > 1) {
               inventoryItem.setI1(Integer.parseInt(itemListener.nums[0]));
            }
            if (itemListener.nums.length >= 2) {
               inventoryItem.setI2(Integer.parseInt(itemListener.nums[1]));
            }
         }
         inventoryList.getInventoryItems().add(inventoryItem);
      }
   }
}
