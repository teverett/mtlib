package com.khubla.mtlib.domain;

import com.khubla.mtlib.compress.ZStdCompression;
import com.khubla.mtlib.util.MTLibException;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Block implements StringSerializable {
   private final NameIdMapping nameIdMapping = new NameIdMapping();
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

   @Override
   public void readFromString(String s) throws MTLibException {
      try {
         // all sorts of flapping around to get a DataInputStream
         byte[] compresseddata = s.getBytes();
         byte[] uncompressedData = ZStdCompression.decompress(compresseddata);
         // byte[] uncompressedData = compresseddata;
         ByteArrayInputStream bais = new ByteArrayInputStream(uncompressedData);
         DataInputStream dis = new DataInputStream(bais);
         // read the data
         readFromDataInputStream(dis);
      } catch (Exception e) {
         throw new MTLibException("Exception in readFromString", e);
      }
   }

   private void readFromDataInputStream(DataInputStream dis) throws MTLibException {
      try {
         this.flags = dis.readByte();
         this.m_lighting_complete = dis.readShort();
         this.content_width = dis.readByte();
         if ((content_width != 0) && (content_width != 1)) {
            throw new MTLibException("Invalid content_width: " + content_width);
         }
         this.params_width = dis.readByte();
         if (params_width != 2) {
            throw new MTLibException("Invalid params_width: " + params_width);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in readFromDataInputStream", e);
      }
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
