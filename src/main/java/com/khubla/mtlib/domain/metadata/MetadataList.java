package com.khubla.mtlib.domain.metadata;

import com.khubla.mtlib.domain.StreamPersistable;
import com.khubla.mtlib.util.MTLibException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MetadataList implements StreamPersistable {
   private final List<Metadata> metadataList = new ArrayList<Metadata>();

   public MetadataList() {
   }

   public List<Metadata> getMetadataList() {
      return metadataList;
   }

   @Override
   public void read(DataInputStream dis, byte version) throws MTLibException {
      try {
         byte metadata_version = dis.readByte();
         if (0 != metadata_version) {
            if (2 != metadata_version) {
               throw new MTLibException("Invalid metadata_version: " + metadata_version);
            }
            short metadata_count = dis.readShort();
            if (metadata_count > 0) {
               for (int i = 0; i < metadata_count; i++) {
                  Metadata metadata = new Metadata();
                  metadata.read(dis, version);
                  metadataList.add(metadata);
               }
            }
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }

   @Override
   public void write(DataOutputStream dos, byte version) throws MTLibException {
      try {
         for (int i = 0; i < this.getMetadataList().size(); i++) {
            Metadata metadata = this.metadataList.get(i);
            metadata.write(dos, version);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in write", e);
      }
   }
}
