package com.khubla.mtlib;

import com.khubla.mtlib.util.StringPersistence;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStringPersistence {
   private static final String TEST_STRING = "And you ate an apple, and I ate a pear,\n" + "From a dozen of each we had bought somewhere;";

   @Test
   public void testStringSZ16() {
      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         DataOutputStream dos = new DataOutputStream(baos);
         StringPersistence.write16(dos, TEST_STRING);
         dos.flush();
         byte[] data = baos.toByteArray();
         ByteArrayInputStream bais = new ByteArrayInputStream(data);
         DataInputStream dis = new DataInputStream(bais);
         String s = StringPersistence.read16(dis);
         assertEquals(s.length(), TEST_STRING.length());
         assertEquals(0, TEST_STRING.compareTo(s));
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testStringSZ32() {
      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         DataOutputStream dos = new DataOutputStream(baos);
         StringPersistence.write32(dos, TEST_STRING);
         dos.flush();
         byte[] data = baos.toByteArray();
         ByteArrayInputStream bais = new ByteArrayInputStream(data);
         DataInputStream dis = new DataInputStream(bais);
         String s = StringPersistence.read32(dis);
         assertEquals(s.length(), TEST_STRING.length());
         assertEquals(0, TEST_STRING.compareTo(s));
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
