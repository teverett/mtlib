package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Metadata implements StreamPersistable {
   private final List<MetadataVar> metadataVars = new ArrayList<MetadataVar>();
   private short position;
   private int num_vars;

   public List<MetadataVar> getMetadataVars() {
      return metadataVars;
   }

   public short getPosition() {
      return position;
   }

   public void setPosition(short position) {
      this.position = position;
   }

   public int getNum_vars() {
      return num_vars;
   }

   public void setNum_vars(int num_vars) {
      this.num_vars = num_vars;
   }

   @Override
   public void read(DataInputStream dis) throws MTLibException {
      try {
         this.position = dis.readShort();
         this.num_vars = dis.readInt();
         for (int i = 0; i < num_vars; i++) {
            MetadataVar metadataVar = new MetadataVar();
            metadataVar.read(dis);
            metadataVars.add(metadataVar);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }

   @Override
   public void write(DataOutputStream dos) throws MTLibException {
      try {
         dos.writeShort(this.position);
         dos.writeInt(this.num_vars);
         for (int i = 0; i < num_vars; i++) {
            MetadataVar metadataVar = this.metadataVars.get(i);
            metadataVar.write(dos);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in write", e);
      }
   }
}
