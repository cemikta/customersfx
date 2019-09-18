/*
 * Copyright (C) 2017 Cem Ikta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */

package com.cemikta.customersfx.app;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * CustomersFX Flyway DB Migrator
 *
 * @author Cem Ikta
 */
public class AppDbMigrator {

    private final Logger logger = LoggerFactory.getLogger(AppDbMigrator.class);
    private final static String DB_MIGRATOR_PROPERTIES = "db/dbmigrator.properties";
    private String databaseUrl;
    private String databaseSchema;
    private String databaseUser;
    private String databasePassword;

    public AppDbMigrator() {
        readDbProperties();
    }

    private void readDbProperties() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Properties properties = new Properties();
            properties.load(loader.getResourceAsStream(DB_MIGRATOR_PROPERTIES));
            databaseUrl = properties.getProperty("database.url");
            databaseSchema = properties.getProperty("database.schema");
            databaseUser = properties.getProperty("database.user");
            databasePassword = properties.getProperty("database.password");
        } catch (IOException e) {
            logger.error("dbmigrator.properties file error", e);
        }
    }

    public void migrate() {
        logger.info("Start the application db migrations");
        try {
            Flyway flyway = new Flyway();
            flyway.setDataSource(databaseUrl + databaseSchema, databaseUser, databasePassword);
            flyway.migrate();
            logger.info("Application db migrations applied successfully");
        } catch (FlywayException e) {
            logger.error("CustomersFX application db migrations could not be executed!", e);
        }
    }
}
