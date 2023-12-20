package com.khubla.mtlib.domain.inventory.listener;

import com.khubla.mtlib.InventoryBaseListener;
import com.khubla.mtlib.InventoryParser;
import com.khubla.mtlib.domain.inventory.Inventory;

public class FileListener extends InventoryBaseListener {
   public final Inventory inventory;

   public FileListener(Inventory inventory) {
      this.inventory = inventory;
   }

   @Override
   public void enterFile(InventoryParser.FileContext ctx) {
      if (null != ctx.list()) {
         for (InventoryParser.ListContext listContext : ctx.list()) {
            ListListener listListener = new ListListener(inventory);
            listListener.enterList(listContext);
         }
      }
   }
}
