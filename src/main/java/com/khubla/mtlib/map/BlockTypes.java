package com.khubla.mtlib.map;

import com.khubla.mtlib.util.MTLibException;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

public class BlockTypes {
   private static final String RESOURCE_FILE = "/blocktypes.txt";
   private static BlockTypes instance;
   private final HashMap<Short, String> blockTypesById = new HashMap<Short, String>();
   private final HashMap<String, Short> blockTypesByName = new HashMap<String, Short>();

   private BlockTypes() throws MTLibException {
      load();
   }

   public static BlockTypes getInstance() throws MTLibException {
      if (null == instance) {
         instance = new BlockTypes();
      }
      return instance;
   }

   public HashMap<Short, String> getBlockTypesById() {
      return blockTypesById;
   }

   public HashMap<String, Short> getBlockTypesByName() {
      return blockTypesByName;
   }

   private void load() throws MTLibException {
      try {
         InputStream is = BlockTypes.class.getResourceAsStream(RESOURCE_FILE);
         List<String[]> data = null;
         if (null != is) {
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try (Reader reader = bufferedReader) {
               try (CSVReader csvReader = new CSVReader(reader)) {
                  data = csvReader.readAll();
               }
            }
            if (null != data) {
               for (String[] line : data) {
                  int l = parseHex((line[0]));
                  blockTypesById.put((short) l, line[1].trim());
                  blockTypesByName.put(line[1].trim(), (short) l);
               }
            }
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in load", e);
      }
   }

   int parseHex(String hex) {
      int i = hex.indexOf("x");
      return Integer.parseInt(hex.substring(i + 1).trim(), 16);
   }
}
