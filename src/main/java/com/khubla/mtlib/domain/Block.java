package com.khubla.mtlib.domain;

import com.github.luben.zstd.Zstd;
import com.khubla.mtlib.compress.ZStdCompression;
import com.khubla.mtlib.util.HexDump;
import com.khubla.mtlib.util.MTLibException;
import org.apache.commons.lang3.ArrayUtils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.nio.ByteBuffer;

public class Block implements StringSerializable {
   private static final byte EXPECTED_SERIALIZATION_VERSION = 29;
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
         byte version = compresseddata[0];
         if (version == EXPECTED_SERIALIZATION_VERSION) {
            HexDump.dump(compresseddata, 128);
            compresseddata = ArrayUtils.remove(compresseddata, 0);
            compresseddata = ArrayUtils.insert(0, compresseddata, (byte) 0xFD);
            compresseddata = ArrayUtils.insert(0, compresseddata, (byte) 0x2F);
            compresseddata = ArrayUtils.insert(0, compresseddata, (byte) 0xB5);
            compresseddata = ArrayUtils.insert(0, compresseddata, (byte) 0x28);
            ByteBuffer in = ByteBuffer.wrap(compresseddata);
            byte[] outbytes = new byte[10000];
            ByteBuffer out = ByteBuffer.wrap(outbytes);
            Zstd.decompress(outbytes, compresseddata);
            byte[] uncompressedData = ZStdCompression.decompress(compresseddata);
            //       byte[] uncompressedData = compresseddata;
            ByteArrayInputStream bais = new ByteArrayInputStream(uncompressedData);
            DataInputStream dis = new DataInputStream(bais);
            // read the data
            readFromDataInputStream(dis);
         } else {
            throw new MTLibException("Unexpected serialization version: " + version);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in readFromString", e);
      }
   }

   private void readFromDataInputStream(DataInputStream dis) throws MTLibException {
      try {
         // https://github.com/minetest/minetest/blob/5d3e83017679317c27fe02b7087effd9d67f79cc/src/map.cpp#L1799
         this.flags = dis.readByte();
         this.m_lighting_complete = dis.readShort();
         this.timestamp = dis.readLong();
         nameIdMapping.read(dis);
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
