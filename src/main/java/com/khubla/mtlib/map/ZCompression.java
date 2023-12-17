package com.khubla.mtlib.map;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class ZCompression {
   public static byte[] decompress(byte[] data) throws IOException {
      byte[] readBuffer = new byte[5000];
      ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
      InflaterInputStream inputStream = new InflaterInputStream(arrayInputStream);
      int read = inputStream.read(readBuffer);
      return Arrays.copyOf(readBuffer, read);
   }

   public static byte[] compress(byte[] data) throws IOException {
      ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
      DeflaterOutputStream outputStream = new DeflaterOutputStream(arrayOutputStream);
      for (byte b : data) {
         outputStream.write(b);
      }
      outputStream.close();
      return arrayOutputStream.toByteArray();
   }
}
