package com.khubla.mtlib.domain.inventory;

import com.khubla.mtlib.InventoryLexer;
import com.khubla.mtlib.InventoryParser;
import com.khubla.mtlib.domain.StreamPersistable;
import com.khubla.mtlib.util.MTLibException;
import com.khubla.mtlib.util.StringPersistence;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements StreamPersistable {
   private final List<InventoryList> inventoryLists = new ArrayList<InventoryList>();
   private String inventoryString;

   public String getInventoryString() {
      return inventoryString;
   }

   public void setInventoryString(String inventoryString) {
      this.inventoryString = inventoryString;
   }

   @Override
   public void read(DataInputStream dis, byte version) throws MTLibException {
      try {
         this.inventoryString = StringPersistence.readSz(dis);
         if (!this.inventoryString.isEmpty()) {
            InventoryParser.FileContext fileContext = parseInventory(this.inventoryString);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }

   @Override
   public void write(DataOutputStream dos, byte version) throws MTLibException {
   }

   private InventoryParser.FileContext parseInventory(String inventoryString) throws MTLibException {
      try {
         InputStream inputStream = new ByteArrayInputStream(inventoryString.getBytes());
         Lexer lexer = new InventoryLexer(CharStreams.fromStream(inputStream));
         TokenStream tokenStream = new CommonTokenStream(lexer);
         InventoryParser parser = new InventoryParser(tokenStream);
         return parser.file();
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }
}
