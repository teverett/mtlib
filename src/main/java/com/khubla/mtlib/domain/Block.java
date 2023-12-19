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
   private int timestamp;
   private byte name_id_mapping_version;
   private short num_name_id_mappings;
   private NodeData nodeData;
   private byte metadata_version;
   private short metadata_count;
   private MetadataList metadataList;
   private byte version;
   private NodeTimers nodeTimers;

   @Override
   public void read(byte[] b) throws MTLibException {
      try {
         // all sorts of flapping around to get a DataInputStream
         this.version = b[0];
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
         nameIdMapping.read(dis, version);
         this.content_width = dis.readByte();
         if ((content_width != 1) && (content_width != 2)) {
            throw new MTLibException("Invalid content_width: " + content_width);
         }
         if (2 != content_width) {
            throw new MTLibException("Invalid content_width: " + content_width);
         }
         this.params_width = dis.readByte();
         if (params_width != 2) {
            throw new MTLibException("Invalid params_width: " + params_width);
         }
         this.nodeData = new NodeData();
         this.nodeData.read(dis, version);
         this.metadata_version = dis.readByte();
         if (0 != metadata_version) {
            if (2 != metadata_version) {
               throw new MTLibException("Invalid metadata_version: " + metadata_version);
            }
            this.metadata_count = dis.readShort();
            this.metadataList = new MetadataList(metadata_count);
            this.metadataList.read(dis, version);
         }
         nodeTimers = new NodeTimers();
         nodeTimers.read(dis, version);
      } catch (Exception e) {
         throw new MTLibException("Exception in readFromDataInputStream", e);
      }
   }

   public NameIdMapping getNameIdMapping() {
      return nameIdMapping;
   }

   public void setNameIdMapping(NameIdMapping nameIdMapping) {
      this.nameIdMapping = nameIdMapping;
   }

   public byte getFlags() {
      return flags;
   }

   public void setFlags(byte flags) {
      this.flags = flags;
   }

   public short getM_lighting_complete() {
      return m_lighting_complete;
   }

   public void setM_lighting_complete(short m_lighting_complete) {
      this.m_lighting_complete = m_lighting_complete;
   }

   public byte getContent_width() {
      return content_width;
   }

   public void setContent_width(byte content_width) {
      this.content_width = content_width;
   }

   public byte getParams_width() {
      return params_width;
   }

   public void setParams_width(byte params_width) {
      this.params_width = params_width;
   }

   public int getTimestamp() {
      return timestamp;
   }

   public void setTimestamp(int timestamp) {
      this.timestamp = timestamp;
   }

   public byte getName_id_mapping_version() {
      return name_id_mapping_version;
   }

   public void setName_id_mapping_version(byte name_id_mapping_version) {
      this.name_id_mapping_version = name_id_mapping_version;
   }

   public short getNum_name_id_mappings() {
      return num_name_id_mappings;
   }

   public void setNum_name_id_mappings(short num_name_id_mappings) {
      this.num_name_id_mappings = num_name_id_mappings;
   }

   public NodeData getNodeData() {
      return nodeData;
   }

   public void setNodeData(NodeData nodeData) {
      this.nodeData = nodeData;
   }

   public byte getMetadata_version() {
      return metadata_version;
   }

   public void setMetadata_version(byte metadata_version) {
      this.metadata_version = metadata_version;
   }

   public short getMetadata_count() {
      return metadata_count;
   }

   public void setMetadata_count(short metadata_count) {
      this.metadata_count = metadata_count;
   }

   public MetadataList getMetadataList() {
      return metadataList;
   }

   public void setMetadataList(MetadataList metadataList) {
      this.metadataList = metadataList;
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
