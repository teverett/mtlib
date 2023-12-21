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
   private NodeData nodeData;
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

   /*
    * public for unit testing purposes.  data is the original data chunk from Redis
    */
   public byte[] getUncompressedData(byte[] data) throws MTLibException {
      this.version = data[0];
      if (version == EXPECTED_SERIALIZATION_VERSION) {
         byte[] b = ArrayUtils.remove(data, 0);
         return ZStdCompression.decompress(b);
      } else {
         throw new MTLibException("Unexpected serialization version: " + version);
      }
   }

   @Override
   public void read(byte[] b) throws MTLibException {
      try {
         byte[] uncompressedData = getUncompressedData(b);
         this.dataSize = uncompressedData.length;
         ByteArrayInputStream bais = new ByteArrayInputStream(uncompressedData);
         DataInputStream dis = new DataInputStream(bais);
         // read the data
         readFromDataInputStream(dis);
      } catch (Exception e) {
         throw new MTLibException("Exception in readFromString", e);
      }
   }

   /**
    * read the uncompressed data.  public access for unit testing
    */
   public void readFromDataInputStream(DataInputStream dis) throws MTLibException {
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
         nameIdMapping = new NameIdMapping();
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
         this.metadataList = new MetadataList();
         this.metadataList.read(dis, version);
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

   public NodeData getNodeData() {
      return nodeData;
   }

   public void setNodeData(NodeData nodeData) {
      this.nodeData = nodeData;
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
         /**
          * this is the stream we will return
          */
         ByteArrayOutputStream ret = new ByteArrayOutputStream();
         DataOutputStream retdos = new DataOutputStream(ret);
         retdos.write(EXPECTED_SERIALIZATION_VERSION);
         /**
          * this is the stream we will compress
          */
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         DataOutputStream dos = new DataOutputStream(baos);
         writeToDataOutputStream(dos);
         dos.flush();
         /**
          * compress
          */
         byte[] compressedData = ZStdCompression.compress(baos.toByteArray());
         /*
          * add on the returned stream
          */
         retdos.write(compressedData, 0, compressedData.length);
         retdos.flush();
         return ret.toByteArray();
      } catch (Exception e) {
         throw new MTLibException("Exception in write", e);
      }
   }

   /*
    * write data to stream. public access for unit testing
    */
   public void writeToDataOutputStream(DataOutputStream dos) throws MTLibException {
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
          * name id mapping
          */
         this.nameIdMapping.write(dos, version);
         /*
          * content width
          */
         dos.writeByte(this.content_width);
         /**
          * params_width
          */
         dos.writeByte(this.params_width);
         /*
          * node data
          */
         this.nodeData.write(dos, version);
         /*
          * metadata
          */
         this.metadataList.write(dos, version);
         /*
          * static objects
          */
         this.staticObjects.write(dos, version);
         /*
          * node timers
          */
         this.nodeTimers.write(dos, version);
      } catch (Exception e) {
         throw new MTLibException("Exception in writeToDataOutputStream", e);
      }
   }

   void validateBlockRelativeCoords(Coord relativeCoord) throws MTLibException {
      if ((Math.abs(relativeCoord.getX()) < 0) || (Math.abs(relativeCoord.getX()) > +BLOCK_SIZE - 1)) {
         throw new MTLibException("Invalid x:" + relativeCoord.getX());
      }
      if ((Math.abs(relativeCoord.getY()) < 0) || (Math.abs(relativeCoord.getY()) > +BLOCK_SIZE - 1)) {
         throw new MTLibException("Invalid y:" + relativeCoord.getY());
      }
      if ((Math.abs(relativeCoord.getZ()) < 0) || (Math.abs(relativeCoord.getZ()) > +BLOCK_SIZE - 1)) {
         throw new MTLibException("Invalid z:" + relativeCoord.getZ());
      }
   }

   /*
    * Coord is relative to the block, not the world
    */
   public Node getNode(Coord relativeCoord) throws MTLibException {
      validateBlockRelativeCoords(relativeCoord);
      if (null != this.nodeData) {
         return this.nodeData.getNode((short) relativeCoord.getX(), (short) relativeCoord.getY(), (short) relativeCoord.getZ());
      }
      return null;
   }

   /*
    * Coord is relative to the block, not the world
    */
   public void setNode(Coord relativeCoord, Node node) throws MTLibException {
      validateBlockRelativeCoords(relativeCoord);
      if ((null != this.nodeData) && (null != node)) {
         this.nodeData.setNode((short) relativeCoord.getX(), (short) relativeCoord.getY(), (short) relativeCoord.getZ(), node);
      }
   }
}
