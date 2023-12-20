package com.khubla.mtlib.domain.inventory.listener;

import com.khubla.mtlib.InventoryBaseListener;
import com.khubla.mtlib.InventoryParser;

public class WidthListener extends InventoryBaseListener {
   String num;

   @Override
   public void enterWidth(InventoryParser.WidthContext ctx) {
      this.num = ctx.NUM().getText();
   }
}
