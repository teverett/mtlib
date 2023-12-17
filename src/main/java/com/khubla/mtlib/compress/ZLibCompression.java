package com.khubla.mtlib.compress;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class ZLibCompression {
   public static byte[] decompress(byte[] data) throws IOException {
      ByteArrayInputStream bais = new ByteArrayInputStream(data);
      InflaterInputStream inflateris = new InflaterInputStream(bais);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      IOUtils.copy(inflateris, baos);
      baos.flush();
      return baos.toByteArray();
   }

   public static byte[] compress(byte[] data) throws IOException {
      ByteArrayInputStream bais = new ByteArrayInputStream(data);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DeflaterOutputStream deflateros = new DeflaterOutputStream(baos);
      IOUtils.copy(bais, deflateros);
      deflateros.flush();
      deflateros.close();
      return baos.toByteArray();
   }
}
