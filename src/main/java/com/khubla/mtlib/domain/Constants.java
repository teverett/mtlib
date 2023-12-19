package com.khubla.mtlib.domain;

public class Constants {
   public final static int BLOCK_SIZE = 16;
   public final static int NODES_PER_BLOCK = BLOCK_SIZE * BLOCK_SIZE * BLOCK_SIZE; // 16*16*16
   public static final long MAX_EXTENT = 30927;
   public static final long MIN_EXTENT = -30912;
   public static final byte EXPECTED_SERIALIZATION_VERSION = 29;
}
