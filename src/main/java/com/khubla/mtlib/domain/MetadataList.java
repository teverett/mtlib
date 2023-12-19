package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MetadataList implements StreamPersistable {
   private final short count;
   private final List<Metadata> lst = new ArrayList<Metadata>();

   public MetadataList(short count) {
      this.count = count;
   }

   public short getCount() {
      return count;
   }

   public List<Metadata> getLst() {
      return lst;
   }

   @Override
   public void read(DataInputStream dis, byte version) throws MTLibException {
      try {
         for (int i = 0; i < count; i++) {
            Metadata metadata = new Metadata();
            metadata.read(dis, version);
            lst.add(metadata);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }

   @Override
   public void write(DataOutputStream dos, byte version) throws MTLibException {
      try {
         for (int i = 0; i < count; i++) {
            Metadata metadata = this.lst.get(i);
            metadata.write(dos, version);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in write", e);
      }
   }
}
