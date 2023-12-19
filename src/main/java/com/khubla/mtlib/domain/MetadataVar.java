package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;
import com.khubla.mtlib.util.StringSZ;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class MetadataVar implements StreamPersistable {
   private String key;
   private String value;
   private byte is_private;

   public String getKey() {
      return key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public byte getIs_private() {
      return is_private;
   }

   public void setIs_private(byte is_private) {
      this.is_private = is_private;
   }

   @Override
   public void read(DataInputStream dis) throws MTLibException {
      try {
         this.key = StringSZ.read16(dis);
         this.value = StringSZ.read32(dis);
         this.is_private = dis.readByte();
         if ((0 != is_private) && (1 != is_private)) {
            throw new MTLibException("Invalid is_private: " + is_private);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }

   @Override
   public void write(DataOutputStream dos) throws MTLibException {
      try {
         StringSZ.write16(dos, this.key);
         StringSZ.write32(dos, this.value);
         dos.writeByte(this.is_private);
      } catch (Exception e) {
         throw new MTLibException("Exception in write", e);
      }
   }
}
