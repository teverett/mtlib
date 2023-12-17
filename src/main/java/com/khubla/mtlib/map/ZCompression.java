package com.khubla.mtlib.map;

import org.apache.commons.compress.compressors.zstandard.ZstdCompressorInputStream;
import org.apache.commons.compress.compressors.zstandard.ZstdCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ZCompression {
   public static byte[] decompress(byte[] data) throws IOException {
      ByteArrayInputStream bais = new ByteArrayInputStream(data);
      ZstdCompressorInputStream zsIn = new ZstdCompressorInputStream(bais);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      IOUtils.copy(zsIn, baos);
      return baos.toByteArray();
   }

   public static byte[] compress(byte[] data) throws IOException {
      ByteArrayInputStream bais = new ByteArrayInputStream(data);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ZstdCompressorOutputStream zsOut = new ZstdCompressorOutputStream(baos);
      IOUtils.copy(bais, zsOut);
      return baos.toByteArray();
   }
}
