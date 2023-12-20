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
      this.id = ctx.ID().getText();
      InventoryList inventoryList = new InventoryList();
      inventoryList.setName(id);
      if (null != ctx.NUM()) {
         inventoryList.setDeclaredSize(Integer.parseInt(ctx.NUM().getText()));
      }
      if (null != ctx.listitem()) {
         for (InventoryParser.ListitemContext listitemContext : ctx.listitem()) {
            ListItemListener listItemListener = new ListItemListener(inventoryList);
            listItemListener.enterListitem(listitemContext);
         }
         this.inventory.getInventoryLists().add(inventoryList);
      }
   }
}
