package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;

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
         short key_len = dis.readShort();
         byte[] k = new byte[key_len];
         dis.read(k, 0, key_len);
         this.key = new String(k);
         int val_len = dis.readInt();
         byte[] v = new byte[val_len];
         dis.read(v, 0, val_len);
         this.value = new String(v);
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
   }
}
