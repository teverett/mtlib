package com.khubla.mtlib.compress;

import com.github.luben.zstd.ZstdInputStream;
import com.github.luben.zstd.ZstdOutputStream;
import com.khubla.mtlib.util.MTLibException;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
// https://facebook.github.io/zstd/

public class ZStdCompression {
   public static byte[] decompress(byte[] data) throws MTLibException {
      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
         ZstdInputStream zstdInputStream = new ZstdInputStream(arrayInputStream);
         zstdInputStream.setContinuous(true);
         IOUtils.copy(zstdInputStream, baos);
         baos.flush();
         return baos.toByteArray();
      } catch (Exception e) {
         throw new MTLibException("Exception in decompress", e);
      }
   }

   public static byte[] compress(byte[] data) throws MTLibException {
      try {
         ByteArrayInputStream bais = new ByteArrayInputStream(data);
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ZstdOutputStream zstdOutputStream = new ZstdOutputStream(baos);
         IOUtils.copy(bais, zstdOutputStream);
         zstdOutputStream.flush();
         baos.flush();
         return baos.toByteArray();
      } catch (Exception e) {
         throw new MTLibException("Exception in compress", e);
      }
   }
}
