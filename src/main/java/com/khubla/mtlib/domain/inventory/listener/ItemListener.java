package com.khubla.mtlib.domain.inventory.listener;

import com.khubla.mtlib.InventoryBaseListener;
import com.khubla.mtlib.InventoryParser;

public class ItemListener extends InventoryBaseListener {
   String id;
   String[] nums;

   @Override
   public void enterItem(InventoryParser.ItemContext ctx) {
      this.id = ctx.ID().getText();
      if (null != ctx.NUM()) {
         nums = new String[ctx.NUM().size()];
         for (int i = 0; i < ctx.NUM().size(); i++) {
            nums[i] = ctx.NUM().get(i).getText();
         }
      }
   }
}
