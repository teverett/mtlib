package com.khubla.mtlib.domain;

import com.khubla.mtlib.compress.ZStdCompression;
import com.khubla.mtlib.util.MTLibException;
import org.apache.commons.lang3.ArrayUtils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

// https://github.com/minetest/minetest/blob/5d3e83017679317c27fe02b7087effd9d67f79cc/src/map.cpp#L1799
// https://github.com/minetest/minetest/blob/master/doc/world_format.md
public class Block implements BytePersistable {
   private static final byte EXPECTED_SERIALIZATION_VERSION = 29;
   private NameIdMapping nameIdMapping;
   private byte flags;
   private short m_lighting_complete;
   private byte content_width;
   private byte params_width;
   private byte[] bulk_data;
   private byte[] metadata;
   private byte[] objects;
   private int timestamp;
   private byte[] nimap;
   private byte[] node_timers;
   private byte name_id_mapping_version;
   private short num_name_id_mappings;

   @Override
   public void read(byte[] b) throws MTLibException {
      try {
         // all sorts of flapping around to get a DataInputStream
         byte version = b[0];
         if (version == EXPECTED_SERIALIZATION_VERSION) {
            b = ArrayUtils.remove(b, 0);
            byte[] uncompressedData = ZStdCompression.decompress(b);
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

   /**
    * read the uncompressed data
    */
   private void readFromDataInputStream(DataInputStream dis) throws MTLibException {
      try {
         this.flags = dis.readByte();
         this.m_lighting_complete = dis.readShort();
         this.timestamp = dis.readInt();
         this.name_id_mapping_version = dis.readByte();
         if (0 != name_id_mapping_version) {
            throw new MTLibException("Unexpected name_id_mapping_version: " + name_id_mapping_version);
         }
         this.num_name_id_mappings = dis.readShort();
         nameIdMapping = new NameIdMapping(num_name_id_mappings);
         nameIdMapping.read(dis);
         this.content_width = dis.readByte();
         if ((content_width != 1) && (content_width != 2)) {
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
   public byte[] write() {
      return null;
   }
}
