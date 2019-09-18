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

package com.cemikta.customersfx.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * JPA util
 *
 * @see EntityManagerFactory
 * @see EntityManager
 *
 * @author Cem Ikta
 */
public class JpaUtil {

    private final static Logger logger = LoggerFactory.getLogger(JpaUtil.class);
    private final static String PERSISTENCE_UNIT = "customersFxPU";
    private static EntityManagerFactory emf = null;

    private JpaUtil() {}

    /**
     * Create entity manager factory as singleton instance.
     *
     * @return entity manager factory from persistence unit
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        logger.debug("get entity manager factory for persistence unit {}", PERSISTENCE_UNIT);
        if (emf == null) {
            synchronized (EntityManagerFactory.class) {
                if (emf == null) {
                    emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
                }
            }
        }
        return emf;
    }

    /**
     * Close the entity manager factory.
     */
    public static void closeEntityManagerFactory() {
        logger.debug("close entity manager factory for persistence unit {}", PERSISTENCE_UNIT);
        if (emf != null) {
            emf.close();
        }
    }

    /**
     * Gets new entity manager instance.
     *
     * @return new entity manager instance
     */
    public static EntityManager getEntityManager() {
        logger.debug("get entity manager for persistence unit {}", PERSISTENCE_UNIT);
        return getEntityManagerFactory().createEntityManager();
    }

}
