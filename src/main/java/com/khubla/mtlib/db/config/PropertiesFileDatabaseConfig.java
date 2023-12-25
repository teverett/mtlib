package com.khubla.mtlib.db.config;

import com.khubla.mtlib.util.MTLibException;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileDatabaseConfig extends BaseDatabaseConfig {
   public void load(String propertiesFile) throws MTLibException {
      try {
         InputStream is = PropertiesFileDatabaseConfig.class.getResourceAsStream(propertiesFile);
         if (null != is) {
            Properties properties = new Properties();
            properties.load(is);
            this.setHash(properties.getProperty("hash"));
            this.setPassword(properties.getProperty("password"));
            this.setPort(Integer.parseInt(properties.getProperty("port")));
            this.setHostname(properties.getProperty("hostname"));
         } else {
            throw new MTLibException("Unable to load properties file: " + propertiesFile);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in load ", e);
      }
   }
}
