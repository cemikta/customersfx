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

package com.cemikta.customersfx.service;

import com.cemikta.customersfx.model.BaseEntity;
import com.cemikta.customersfx.util.JpaUtil;
import org.hibernate.internal.SessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Abstract service.
 *
 * @param <T> entity
 *
 * @author Cem Ikta
 */
public abstract class AbstractService<T extends BaseEntity> {

    private final Logger logger = LoggerFactory.getLogger(AbstractService.class);
    private EntityManager entityManager;
    private final Class<T> entityClass;
    private Connection connection;

    /**
     * Create default service
     *
     * @param entityClass entity class
     */
    public AbstractService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Gets entity manager from JpaUtil.
     *
     * @return entity manager
     */
    public EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = JpaUtil.getEntityManager();
        }
        return entityManager;
    }

    /**
     * Gets {@link Connection} for reporting
     *
     * @return sql connection
     */
    public Connection getConnection() {
        logger.info("get sql.connection for reporting");
        if (connection == null) {
            EntityManager entityManager = getEntityManager();
            connection = entityManager.unwrap(SessionImpl.class).connection();
        }
        return connection;
    }

    /**
     * Gets entity class.
     *
     * @return entity class
     */
    public Class<T> getEntityClass() {
        return entityClass;
    }

    /**
     * Create entity.
     *
     * @param entity entity model.
     * @return created entity
     */
    public T create(T entity) {
        logger.info("create {}", entity);
        getEntityManager().getTransaction().begin();
        getEntityManager().persist(entity);
        getEntityManager().getTransaction().commit();

        return entity;
    }

    /**
     * Update entity.
     *
     * @param entity entity model
     * @return updated entity
     */
    public T update(T entity) {
        logger.info("update {}", entity);
        getEntityManager().getTransaction().begin();
        entity = getEntityManager().merge(entity);
        getEntityManager().getTransaction().commit();

        return entity;
    }

    /**
     * Remove entity.
     *
     * @param entity entity model
     */
    public void remove(T entity) {
        logger.info("remove {}", entity);
        getEntityManager().getTransaction().begin();
        getEntityManager().remove(getEntityManager().merge(entity));
        getEntityManager().getTransaction().commit();
    }

    /**
     * Find entity.
     *
     * @param id entity id
     * @return entity
     */
    public T find(Long id) {
        logger.info("find {}", id);
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Find one entity with named query and parameters.
     *
     * @param namedQueryName named query name from entity
     * @param parameters parameters map for named query
     * @return entity with single result
     */
    @SuppressWarnings("unchecked")
    public T findOneWithNamedQuery(String namedQueryName, Map<String, Object> parameters) {
        logger.info("find one with named query {} with params {}", namedQueryName, parameters);
        Set<Entry<String, Object>> params = parameters.entrySet();
        Query query = getEntityManager().createNamedQuery(namedQueryName);

        for (Entry<String, Object> entry : params) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        try {
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            logger.info("No result found with named query {} and params {}", namedQueryName, parameters, e);
            return null;
        }
    }

    /**
     * Gets entity list with named query.
     *
     * @param namedQueryName named query name from entity
     * @return result list
     */
    @SuppressWarnings("unchecked")
    public List<T> getListWithNamedQuery(String namedQueryName) {
        logger.info("get list with named query {}", namedQueryName);
        return getEntityManager().createNamedQuery(namedQueryName).getResultList();
    }

    /**
     * Gets entity list with named query.
     *
     * @param namedQueryName named query name from entity
     * @param resultLimit limit for result list
     * @return result list
     */
    @SuppressWarnings("unchecked")
    public List<T> getListWithNamedQuery(String namedQueryName, int resultLimit) {
        logger.info("get list with named query {} and result limit {}", namedQueryName, resultLimit);
        return getEntityManager().createNamedQuery(namedQueryName)
                .setMaxResults(resultLimit)
                .getResultList();
    }

    /**
     * Gets entity list with named query.
     *
     * @param namedQueryName named query name from entity
     * @param parameters parameters map for named query
     * @return result list
     */
    public List<T> getListWithNamedQuery(String namedQueryName, Map<String, Object> parameters) {
        logger.info("get list with named query {} parameters {} and without limit", namedQueryName, parameters);
        return getListWithNamedQuery(namedQueryName, parameters, 0);
    }

    /**
     * Gets entity list with named query.
     *
     * @param namedQueryName named query name from entity
     * @param parameters parameters map for named query
     * @param resultLimit limit for result list
     * @return result list
     */
    @SuppressWarnings("unchecked")
    public List<T> getListWithNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
        logger.info("get list with named query {} parameters {} and limit {}",
                namedQueryName, parameters, resultLimit);
        Set<Entry<String, Object>> params = parameters.entrySet();
        Query query = getEntityManager().createNamedQuery(namedQueryName);

        if (resultLimit > 0) {
            query.setMaxResults(resultLimit);
        }
        for (Entry<String, Object> entry : params) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    /**
     * Gets entity list with named query.
     *
     * @param namedQueryName named query name from entity
     * @param parameters parameters map for named query
     * @param start start position
     * @param end end position
     * @return result list
     */
    @SuppressWarnings("unchecked")
    public List<T> getListWithNamedQuery(String namedQueryName, Map<String, Object> parameters, int start, int end) {
        logger.info("get list with named query {} parameters {} start {} end {}",
                namedQueryName, parameters, start, end);
        Set<Entry<String, Object>> params = parameters.entrySet();
        Query query = getEntityManager().createNamedQuery(namedQueryName);

        for (Entry<String, Object> entry : params) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        query.setMaxResults(end - start);
        query.setFirstResult(start);
        return query.getResultList();
    }

    /**
     * Gets entity list with named query.
     *
     * @param namedQueryName named query name from entity
     * @param start start position
     * @param end end position
     * @return result list
     */
    @SuppressWarnings("unchecked")
    public List<T> getListWithNamedQuery(String namedQueryName, int start, int end) {
        logger.info("get list with named query {} start {} end {}", namedQueryName, start, end);
        return getEntityManager().createNamedQuery(namedQueryName)
                .setMaxResults(end - start)
                .setFirstResult(start)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> getUnmappedResultList(String query) {
        logger.info("get unmapped list with query {}", query);
        return getEntityManager().createQuery(query).getResultList();
    }

    /**
     * Gets entity list with native sql query.
     *
     * @param sql native sql
     * @return result list
     */
    @SuppressWarnings("unchecked")
    public List<T> getListByNativeQuery(String sql) {
        logger.info("get list with native sql query {}", sql);
        return getEntityManager().createNativeQuery(sql, entityClass).getResultList();
    }

    /**
     * Gets entity list with native sql query.
     *
     * @param sql native sql query
     * @param resultLimit limit for result list
     * @return result list
     */
    @SuppressWarnings("unchecked")
    public List<T> getListByNativeQuery(String sql, int resultLimit) {
        logger.info("get list with native sql query {} and limit", sql, resultLimit);
        return getEntityManager().createNativeQuery(sql, entityClass)
                .setMaxResults(resultLimit)
                .getResultList();
    }

    /**
     * Gets entity list with native sql query.
     *
     * @param sql native sql query
     * @param start start position
     * @param end end position
     * @return result list
     */
    @SuppressWarnings("unchecked")
    public List<T> getListByNativeQuery(String sql, int start, int end) {
        logger.info("get list with native sql query {} start {} end {}", sql, start, end);
        return getEntityManager().createNativeQuery(sql, entityClass)
                .setMaxResults(end - start)
                .setFirstResult(start)
                .getResultList();
    }

}
