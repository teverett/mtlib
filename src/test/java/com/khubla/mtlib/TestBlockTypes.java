package com.khubla.mtlib;

import com.khubla.mtlib.worldmap.BlockTypes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestBlockTypes {
   @Test
   public void restBlockTypes() {
      try {
         BlockTypes blockTypes = BlockTypes.getInstance();
         assertNotNull(blockTypes);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}