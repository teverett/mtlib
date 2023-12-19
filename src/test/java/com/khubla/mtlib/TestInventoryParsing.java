package com.khubla.mtlib;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestInventoryParsing {
   @Test
   public void testInventoryParsing() {
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
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
