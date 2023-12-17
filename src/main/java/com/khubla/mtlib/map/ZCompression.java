package com.khubla.mtlib.map;

import org.apache.commons.compress.utils.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterInputStream;
import java.util.zip.DeflaterOutputStream;

public class ZCompression {
   public static byte[] decompress(byte[] data) throws IOException {
      ByteArrayInputStream bais = new ByteArrayInputStream(data);
      DeflaterInputStream deflaterIn = new DeflaterInputStream(bais);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      IOUtils.copy(deflaterIn, baos);
      baos.flush();
      return baos.toByteArray();
   }

   public static byte[] compress(byte[] data) throws IOException {
      ByteArrayInputStream bais = new ByteArrayInputStream(data);
      DeflaterOutputStream deflaterOut = new DeflaterOutputStream(baos);
      IOUtils.copy(bais, deflaterOut);
      deflaterOut.flush();
      deflaterOut.close();
      return baos.toByteArray();
   }
}
