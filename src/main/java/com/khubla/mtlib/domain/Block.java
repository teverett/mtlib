package com.khubla.mtlib.domain;

import com.khubla.mtlib.compress.ZStdCompression;
import com.khubla.mtlib.domain.metadata.MetadataList;
import com.khubla.mtlib.domain.staticobject.StaticObjects;
import com.khubla.mtlib.util.MTLibException;
import com.khubla.mtlib.worldmap.Node;
import org.apache.commons.lang3.ArrayUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import static com.khubla.mtlib.domain.Constants.BLOCK_SIZE;
import static com.khubla.mtlib.domain.Constants.EXPECTED_SERIALIZATION_VERSION;

// https://github.com/minetest/minetest/blob/5d3e83017679317c27fe02b7087effd9d67f79cc/src/map.cpp#L1799
// https://github.com/minetest/minetest/blob/master/doc/world_format.md
public class Block implements BytePersistable {
   private final String key;
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
   private StaticObjects staticObjects;
   private long dataSize;

   public Block(String key) {
      this.key = key;
   }

   public String getKey() {
      return key;
   }

   public StaticObjects getStaticObjects() {
      return staticObjects;
   }

   public void setStaticObjects(StaticObjects staticObjects) {
      this.staticObjects = staticObjects;
   }

   public byte getVersion() {
      return version;
   }

   public void setVersion(byte version) {
      this.version = version;
   }

   public NodeTimers getNodeTimers() {
      return nodeTimers;
   }

   public void setNodeTimers(NodeTimers nodeTimers) {
      this.nodeTimers = nodeTimers;
   }

   @Override
   public void read(byte[] b) throws MTLibException {
      try {
         // all sorts of flapping around to get a DataInputStream
         this.version = b[0];
         if (version == EXPECTED_SERIALIZATION_VERSION) {
            b = ArrayUtils.remove(b, 0);
            byte[] uncompressedData = ZStdCompression.decompress(b);
            this.dataSize = uncompressedData.length;
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
         /*
          * flags
          */
         this.flags = dis.readByte();
         /*
          * lighting
          */
         this.m_lighting_complete = dis.readShort();
         /*
          * timestamp
          */
         this.timestamp = dis.readInt();
         /*
          * name-id-mapping
          */
         this.name_id_mapping_version = dis.readByte();
         if (0 != name_id_mapping_version) {
            throw new MTLibException("Unexpected name_id_mapping_version: " + name_id_mapping_version);
         }
         this.num_name_id_mappings = dis.readShort();
         nameIdMapping = new NameIdMapping(num_name_id_mappings);
         nameIdMapping.read(dis, version);
         /*
          * content width
          */
         this.content_width = dis.readByte();
         if ((content_width != 1) && (content_width != 2)) {
            throw new MTLibException("Invalid content_width: " + content_width);
         }
         if (2 != content_width) {
            throw new MTLibException("Invalid content_width: " + content_width);
         }
         /*
          * params width
          */
         this.params_width = dis.readByte();
         if (params_width != 2) {
            throw new MTLibException("Invalid params_width: " + params_width);
         }
         /*
          * node data
          */
         this.nodeData = new NodeData();
         this.nodeData.read(dis, version);
         /*
          * metadata
          */
         this.metadata_version = dis.readByte();
         if (0 != metadata_version) {
            if (2 != metadata_version) {
               throw new MTLibException("Invalid metadata_version: " + metadata_version);
            }
            this.metadata_count = dis.readShort();
            if (metadata_count > 0) {
               this.metadataList = new MetadataList(metadata_count);
               this.metadataList.read(dis, version);
            }
         }
         /*
          * static objects
          */
         staticObjects = new StaticObjects();
         staticObjects.read(dis, version);
         /*
          * node timers
          */
         nodeTimers = new NodeTimers();
         nodeTimers.read(dis, version);
         /*
          * done
          */
         if (0 != dis.available()) {
            throw new MTLibException("Exception in readFromDataInputStream; stream is not empty");
         }
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
   public byte[] write() throws MTLibException {
      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         DataOutputStream dos = new DataOutputStream(baos);
         writeToDataOutputStream(dos);
         return null;
      } catch (Exception e) {
         throw new MTLibException("Exception in write", e);
      }
   }

   private void writeToDataOutputStream(DataOutputStream dos) throws MTLibException {
      try {
         /*
          * flags
          */
         dos.writeByte(this.flags);
         /*
          * lighting
          */
         dos.writeShort(this.m_lighting_complete);
         /*
          * timestamp
          */
         dos.writeInt(this.timestamp);
         /*
          * name-id-mapping version
          */
         dos.writeByte(0);
      } catch (Exception e) {
         throw new MTLibException("Exception in writeToDataOutputStream", e);
      }
   }

   void validateBlockRelativeCoords(short x, short y, short z) throws MTLibException {
      if ((x < 0) || (x > +BLOCK_SIZE - 1)) {
         throw new MTLibException("Invalid x:" + x);
      }
      if ((y < 0) || (y > +BLOCK_SIZE - 1)) {
         throw new MTLibException("Invalid y:" + y);
      }
      if ((z < 0) || (z > +BLOCK_SIZE - 1)) {
         throw new MTLibException("Invalid z:" + z);
      }
   }

   /*
    * Coord is relative to the block, not the world
    */
   public Node getNode(short x, short y, short z) throws MTLibException {
      validateBlockRelativeCoords(x, y, z);
      if (null != this.nodeData) {
         return this.nodeData.getNode(x, y, z);
      }
      return null;
   }

   /*
    * Coord is relative to the block, not the world
    */
   public void setNode(short x, short y, short z, Node node) throws MTLibException {
      validateBlockRelativeCoords(x, y, z);
      if ((null != this.nodeData) && (null != node)) {
         this.nodeData.setNode(x, y, z, node);
      }
   }
}
