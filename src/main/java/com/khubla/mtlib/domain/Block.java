package com.khubla.mtlib.domain;

import com.khubla.mtlib.map.ZCompression;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class Block implements StringSerializable {
   private byte flags;
   private short m_lighting_complete;
   private byte content_width;
   private byte params_width;
   private byte[] bulk_data;
   private byte[] metadata;
   private byte[] objects;
   private long timestamp;
   private byte[] nimap;
   private byte[] node_timers;
   private final NameIdMapping nameIdMapping = new NameIdMapping();

   @Override
   public void readFromString(String s) throws IOException {
      // all sorts of flapping around to get a DataInputStream
      byte[] compresseddata = s.getBytes();
      byte[] uncompressedData = ZCompression.decompress(compresseddata);
      ByteArrayInputStream bais = new ByteArrayInputStream(uncompressedData);
      DataInputStream dos = new DataInputStream(bais);
      // read the data
      this.flags = dos.readByte();
      m_lighting_complete = dos.readShort();
   }

   public boolean isGenerated() {
      return (flags & 0x08) == 0;
   }

   public boolean isUnderground() {
      return (flags & 0x01) != 0;
   }

   public boolean isDayNightDiffers() {
      return (flags & 0x02) != 0;
   }

   @Override
   public String writeToString() {
      return null;
   }
}
