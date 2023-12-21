package com.khubla.mtlib.domain.inventory;

import com.khubla.mtlib.InventoryLexer;
import com.khubla.mtlib.InventoryParser;
import com.khubla.mtlib.domain.StreamPersistable;
import com.khubla.mtlib.domain.inventory.listener.FileListener;
import com.khubla.mtlib.util.MTLibException;
import com.khubla.mtlib.util.StringPersistence;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements StreamPersistable {
   private final static String MARKER = "EndInventory\n";
   private final List<InventoryList> inventoryLists = new ArrayList<InventoryList>();
   private String inventoryString;

   public List<InventoryList> getInventoryLists() {
      return inventoryLists;
   }

   public String getInventoryString() {
      return inventoryString;
   }

   public void setInventoryString(String inventoryString) {
      this.inventoryString = inventoryString;
   }

   @Override
   public void read(DataInputStream dis, byte version) throws MTLibException {
      try {
         this.inventoryString = StringPersistence.readToMarker(dis, MARKER);
         if (!this.inventoryString.isEmpty()) {
            InventoryParser.FileContext fileContext = parseInventory(this.inventoryString);
            FileListener fileListener = new FileListener(this);
            fileContext.enterRule(fileListener);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }

   private void writeString(DataOutputStream dos, String str) throws IOException {
      dos.write(str.getBytes(), 0, str.length());
   }

   @Override
   public void write(DataOutputStream dos, byte version) throws MTLibException {
      try {
         for (InventoryList inventoryList : this.inventoryLists) {
            writeString(dos, "List ");
            writeString(dos, inventoryList.getName() + " ");
            // TODO this might have to change, its the number of slots
            writeString(dos, Integer.toString(inventoryList.getInventoryItems().length));
            writeString(dos, "\n");
            if (null != inventoryList.getWidth()) {
               writeString(dos, "Width ");
               writeString(dos, inventoryList.getWidth());
               writeString(dos, "\n");
            }
            for (int i = 0; i < inventoryList.getInventoryItems().length; i++) {
               InventoryItem inventoryItem = inventoryList.getInventoryItems()[i];
               if (null != inventoryItem) {
                  writeString(dos, "Item ");
                  writeString(dos, inventoryItem.getName());
                  if (null != inventoryItem.getI1()) {
                     writeString(dos, " " + inventoryItem.getI1().toString());
                  }
                  if (null != inventoryItem.getI2()) {
                     writeString(dos, " " + inventoryItem.getI2().toString());
                  }
                  writeString(dos, "\n");
               } else {
                  writeString(dos, "Empty\n");
               }
            }
            writeString(dos, "EndInventoryList\n");
         }
         writeString(dos, "EndInventory\n");
      } catch (Exception e) {
         throw new MTLibException("Exception in write", e);
      }
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
