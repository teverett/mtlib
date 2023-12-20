package com.khubla.mtlib;

import com.khubla.mtlib.db.PropertiesFileDatabaseConfig;
import com.khubla.mtlib.util.MTLibException;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
   private static final String PROPERTIES_FILE = "/database.properties";
   protected static PropertiesFileDatabaseConfig propertiesFileDatabaseConfig = new PropertiesFileDatabaseConfig();

   @BeforeAll
   public static void loadDatabaseConfig() throws MTLibException {
      propertiesFileDatabaseConfig.load(PROPERTIES_FILE);
   }
}
