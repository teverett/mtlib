package com.khubla.mtlib.compress;

import com.khubla.mtlib.util.MTLibException;
import io.airlift.compress.zstd.ZstdInputStream;
import io.airlift.compress.zstd.ZstdOutputStream;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

// https://facebook.github.io/zstd/
// header bytes 0xFD 2F B5 28
//              0xFD 2F B5 27
// https://github.com/facebook/zstd/blob/dev/doc/zstd_compression_format.md#zstandard-frames
public class ZStdCompression {
   public static final int WRITE_SIZE = 100;

   public static byte[] decompress(byte[] data) throws MTLibException {
      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
         ZstdInputStream zstdInputStream = new ZstdInputStream(arrayInputStream);
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
         zstdOutputStream.close();
         return baos.toByteArray();
      } catch (Exception e) {
         throw new MTLibException("Exception in compress", e);
      }
   }
}
