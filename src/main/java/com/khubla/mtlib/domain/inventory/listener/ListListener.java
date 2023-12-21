package com.khubla.mtlib.domain.inventory.listener;

import com.khubla.mtlib.InventoryBaseListener;
import com.khubla.mtlib.InventoryParser;
import com.khubla.mtlib.domain.inventory.Inventory;
import com.khubla.mtlib.domain.inventory.InventoryList;

public class ListListener extends InventoryBaseListener {
   public final Inventory inventory;
   public String id;

   public ListListener(Inventory inventory) {
      this.inventory = inventory;
   }

   @Override
   public void enterList(InventoryParser.ListContext ctx) {
      if (null != ctx.NUM()) {
         this.id = ctx.ID().getText();
         InventoryList inventoryList = new InventoryList(Integer.parseInt(ctx.NUM().getText()));
         inventoryList.setName(id);
         if (null != ctx.listitem()) {
            int i = 0;
            for (InventoryParser.ListitemContext listitemContext : ctx.listitem()) {
               ListItemListener listItemListener = new ListItemListener();
               listItemListener.enterListitem(listitemContext);
               if (null != listItemListener.width) {
                  inventoryList.setWidth(listItemListener.width);
               } else {
                  if (null != listItemListener.inventoryItem) {
                     inventoryList.getInventoryItems()[i] = listItemListener.inventoryItem;
                  }
                  i = i + 1;
               }
            }
            this.inventory.getInventoryLists().add(inventoryList);
         }
      }
   }
}
