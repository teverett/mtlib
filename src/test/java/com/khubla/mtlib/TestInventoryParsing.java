package com.khubla.mtlib;

import com.khubla.mtlib.domain.inventory.Inventory;
import com.khubla.mtlib.domain.inventory.listener.FileListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestInventoryParsing {
   @Test
   public void testInventoryParsing1() {
      try {
         /*
          * get the input file as an InputStream
          */
         InputStream inputStream = TestInventoryParsing.class.getResourceAsStream("/inventory1.txt");
         assertNotNull(inputStream);
         Lexer lexer = new InventoryLexer(CharStreams.fromStream(inputStream));
         TokenStream tokenStream = new CommonTokenStream(lexer);
         InventoryParser parser = new InventoryParser(tokenStream);
         InventoryParser.FileContext fileContext = parser.file();
         assertNotNull(fileContext);
         Inventory inventory = new Inventory();
         FileListener fileListener = new FileListener(inventory);
         fileContext.enterRule(fileListener);
         assertNotNull(inventory.getInventoryLists());
         assertEquals(2, inventory.getInventoryLists().size());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testInventoryParsing2() {
      try {
         /*
          * get the input file as an InputStream
          */
         InputStream inputStream = TestInventoryParsing.class.getResourceAsStream("/inventory2.txt");
         assertNotNull(inputStream);
         Lexer lexer = new InventoryLexer(CharStreams.fromStream(inputStream));
         TokenStream tokenStream = new CommonTokenStream(lexer);
         InventoryParser parser = new InventoryParser(tokenStream);
         InventoryParser.FileContext fileContext = parser.file();
         assertNotNull(fileContext);
         Inventory inventory = new Inventory();
         FileListener fileListener = new FileListener(inventory);
         fileContext.enterRule(fileListener);
         assertNotNull(inventory.getInventoryLists());
         assertEquals(1, inventory.getInventoryLists().size());
         assertEquals(32, inventory.getInventoryLists().get(0).getDeclaredSize());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
