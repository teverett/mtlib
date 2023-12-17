package com.khubla.mtlib.map;

import com.khubla.mtlib.util.MTLibException;
import io.airlift.compress.zstd.ZstdCompressor;
import io.airlift.compress.zstd.ZstdDecompressor;

import java.nio.ByteBuffer;
// https://facebook.github.io/zstd/
// https://github.com/airlift/aircompressor

public class ZStdCompression {
   public static byte[] decompress(byte[] data) throws MTLibException {
      try {
         ZstdDecompressor zstdDecompressor = new ZstdDecompressor();
         ByteBuffer bb = ByteBuffer.wrap(data);
         ByteBuffer output = ByteBuffer.allocate(data.length * 2);
         zstdDecompressor.decompress(bb, output);
         return output.array();
      } catch (Exception e) {
         throw new MTLibException("Exception in compress", e);
      }
   }

   public static byte[] compress(byte[] data) throws MTLibException {
      try {
         ZstdCompressor zstdCompressor = new ZstdCompressor();
         ByteBuffer bb = ByteBuffer.wrap(data);
         ByteBuffer output = ByteBuffer.allocate(data.length * 2);
         zstdCompressor.compress(bb, output);
         return output.array();
      } catch (Exception e) {
         throw new MTLibException("Exception in compress", e);
      }
   }
}
