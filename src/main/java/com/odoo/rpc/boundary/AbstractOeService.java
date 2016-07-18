/*
 * Copyright 2016 QFast
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.odoo.rpc.boundary;

import com.odoo.rpc.entity.AbstractOeEntity;
import com.odoo.rpc.exception.OeRpcException;
import com.odoo.rpc.json.OeExecutor;
import com.odoo.rpc.util.OeBinder;
import com.odoo.rpc.util.OeConst;
import com.odoo.rpc.util.OeCriteriaBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.odoo.rpc.util.OeConst._COL_ID;
import static com.odoo.rpc.util.OeCriteriaBuilder.SortType.ASC;
import static com.odoo.rpc.util.OeCriteriaBuilder.SortType.DESC;

/**
 * AbstractOeService abstract class for boundary services of Odoo. easy use
 * findById(s), findAll, count, create, delete, update, ...etc
 *
 * @param <M> Entity type extends from {@link AbstractOeEntity}
 * @author Ahmed El-mawaziny
 * @see AbstractOeEntity
 * @since 1.0
 */
public abstract class AbstractOeService<M extends AbstractOeEntity> implements Serializable {

    private static final long serialVersionUID = -2422695985617500754L;
    protected final OeExecutor executor;
    protected final Class<M> model;

    /**
     * AbstractOeService default constructor
     *
     * @param executor Odoo Executor {@link OeExecutor}
     * @param model    model class
     */
    @SuppressWarnings("unchecked")
    public <C extends M> AbstractOeService(OeExecutor executor, Class<C> model) {
        this.executor = executor;
        this.model = (Class<M>) model;
    }

    /**
     * static helper method use same search logic by id
     *
     * @param executor Odoo executor client
     * @param m        Odoo model
     * @param e        executor service
     * @param id       Odoo model id
     * @param <OeM>    extends from {@link AbstractOeEntity}
     * @param <E>      extends from {@link AbstractOeService}
     * @return Odoo model found by id
     * @throws Exception
     * @see OeExecutor
     * @see AbstractOeService
     * @see AbstractOeEntity
     * @see OeCriteriaBuilder
     */
    public static <OeM extends AbstractOeEntity, E extends AbstractOeService> OeM findById(OeExecutor executor,
                                                                                           Class<OeM> m, Class<E> e,
                                                                                           Long id) throws Exception {
        E eInstance = e.getDeclaredConstructor(OeExecutor.class).newInstance(executor);
        OeCriteriaBuilder cb = new OeCriteriaBuilder();
        cb.column(OeConst._COL_ID).eq(id);
        Map<String, Object>[] result = executor.searchReadMap(eInstance.getName(), cb.getCriteria());
        if (result != null && result.length != 0) {
            return OeBinder.bind(result[0].toString(), m, eInstance);
        }
        throw new OeRpcException(m.getSimpleName() + " Not found with id=" + id, null);
    }

    /**
     * Get Odoo Executor
     *
     * @return OeExecutor
     */
    public OeExecutor getExecutor() {
        return executor;
    }

    /**
     * abstract method to get Odoo model name
     *
     * @return Odoo model name
     */
    public abstract String getName();

    /**
     * find Odoo Model or Object by id and return custom Entity
     *
     * @param id Id of Odoo Model or Object
     * @return custom Entity for Odoo model or object
     * @throws OeRpcException if any exception happened on the Odoo server or if no result found
     */
    public M findById(Long id, String... columns) throws OeRpcException {
        List<M> list = find(new Long[]{id}, columns);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }

        throw new OeRpcException(model.getSimpleName() + " Not found with id=" + id, null);
    }

    /**
     * find Odoo Models or Objects by ids and return custom Entities
     *
     * @param ids Odoo Models or Objects id
     * @return custom Entities for Odoo models or objects
     * @throws OeRpcException if any exception happened during finding
     */
    public List<M> findByIds(Long... ids) throws OeRpcException {
        return find(ids);
    }

    /**
     * Find by ids and with only array of columns not all columns (recommended for reducing bandwidth)
     *
     * @param ids     Odoo Models id
     * @param columns Odoo Model columns
     * @return custom entity with only specified columns
     * @throws OeRpcException if any exception happened during this finding
     */
    public List<M> findByIds(Long[] ids, String... columns) throws OeRpcException {
        return find(ids, columns);
    }

    /**
     * find all Odoo Models or Objects with or without array of columns (recommended to reduce bandwidth)
     *
     * @return list of custom Entities for Odoo models or Objects
     * @throws OeRpcException if any exception happened during this finding
     */
    public List<M> findAll(String... columns) throws OeRpcException {
        return find(executor.getContext(), columns);
    }

    /**
     * finding first record with/without array of columns (recommended to reduce bandwidth)
     * <p>
     * this method using order by id ascending to get lower id
     *
     * @param columns array of columns
     * @return custom entity with lower id with only specified columns
     * @throws OeRpcException if any exception happened during this finding
     * @see #findFirst(OeCriteriaBuilder, String...)
     * @see OeCriteriaBuilder
     */
    public M findFirst(String... columns) throws OeRpcException {
        return findFirst(new OeCriteriaBuilder(), columns);
    }

    /**
     * find first record by criteria builder for searching {@link OeCriteriaBuilder} (odoo domain)
     * with/without array of columns (recommended to reduce bandwidth)
     * <p>
     * this method using order by id ascending to get lower id
     *
     * @param cb      search criteria created with {@link OeCriteriaBuilder} (Odoo domain)
     * @param columns array of columns
     * @return custom entity with lower id with only specified columns
     * @throws OeRpcException if any exception happened during this finding
     * @see OeCriteriaBuilder
     * @see #find(OeCriteriaBuilder, Integer, Integer, String, String...)
     */
    public M findFirst(OeCriteriaBuilder cb, String... columns) throws OeRpcException {
        List<M> find = find(cb, 0, 1, _COL_ID + ASC.toString(), columns);
        if (find != null && !find.isEmpty()) {
            return find.get(0);
        }
        return null;
    }

    /**
     * find last record with specific columns (optional - recommended to reduce bandwidth)
     * <p>
     * this method order by id descending
     *
     * @param columns array of columns
     * @return last record in odoo model
     * @throws OeRpcException if any something went wrong
     * @see #findLast(OeCriteriaBuilder, String...)
     * @see OeCriteriaBuilder
     */
    public M findLast(String... columns) throws OeRpcException {
        return findLast(new OeCriteriaBuilder(), columns);
    }

    /**
     * find last record by criteria builder (odoo domain) {@link OeCriteriaBuilder}
     * with specific columns (optional - recommended to reduce bandwidth)
     *
     * @param cb      search criteria created with {@link OeCriteriaBuilder} (Odoo domain)
     * @param columns array of columns (optional)
     * @return last record in Odoo model
     * @throws OeRpcException if any something went wrong
     * @see OeCriteriaBuilder
     * @see #find(OeCriteriaBuilder, Integer, Integer, String, String...)
     */
    public M findLast(OeCriteriaBuilder cb, String... columns) throws OeRpcException {
        List<M> find = find(cb, 0, 1, _COL_ID + DESC.toString(), columns);
        if (find != null && !find.isEmpty()) {
            return find.get(0);
        }
        return null;
    }

    /**
     * find any record with specific columns (optional - recommended to reduce bandwidth)
     *
     * @param columns array of columns (optional)
     * @return any record in Odoo model
     * @throws OeRpcException if any something went wrong
     * @see OeCriteriaBuilder
     * @see #findAny(OeCriteriaBuilder, String...)
     */
    public M findAny(String... columns) throws OeRpcException {
        return findAny(new OeCriteriaBuilder(), columns);
    }

    /**
     * find last record by criteria builder (odoo domain) {@link OeCriteriaBuilder}
     * with specific columns (optional - recommended to reduce bandwidth)
     *
     * @param cb      search criteria created with {@link OeCriteriaBuilder}
     * @param columns array of columns (optional)
     * @return any record match this search criteria with optional columns
     * @throws OeRpcException if any something went wrong
     * @see #find(OeCriteriaBuilder, Integer, String...)
     * @see OeCriteriaBuilder
     */
    public M findAny(OeCriteriaBuilder cb, String... columns) throws OeRpcException {
        List<M> find = find(cb, 1, columns);
        if (find != null && !find.isEmpty()) {
            return find.get(0);
        }
        return null;
    }

    /**
     * find all Odoo Models or Objects with Odoo context
     *
     * @param context Odoo context
     * @return list of custom Entities for Odoo models or Objects with
     * specific Odoo context
     * @throws OeRpcException
     */
    public List<M> find(Map<String, Object> context, String... columns) throws OeRpcException {
        return find(Collections.emptyList(), null, null, null, context, columns);
    }

    /**
     * find all Odoo Models or Objects with search criteria and reading some
     * specific columns
     *
     * @param cb      search criteria created with {@link OeCriteriaBuilder}
     * @param columns one or more model columns to read
     * @return list of custom Entities for Odoo models or Objects with some
     * specific columns
     * @throws OeRpcException
     */
    public List<M> find(OeCriteriaBuilder cb, String... columns) throws OeRpcException {
        return find(cb.getCriteria(), columns);
    }

    /**
     * find all Odoo Models or Objects with search criteria, Odoo context
     * and reading some specific columns
     *
     * @param cb      search criteria created with {@link OeCriteriaBuilder}
     * @param context Odoo context
     * @param columns one or more model columns to read
     * @return list of custom Entities for Odoo models or Objects with some
     * specific columns and Odoo context
     * @throws OeRpcException
     */
    public List<M> find(OeCriteriaBuilder cb, Map<String, Object> context, String... columns)
            throws OeRpcException {
        return find(cb.getCriteria(), null, null, null, context, columns);
    }

    /**
     * find all Odoo Models or Objects with list of search criteria and
     * reading some specific columns
     *
     * @param sc      list of search criteria
     * @param columns one or more model columns
     * @return list of custom Entities for Odoo models or Objects with some
     * custom list of search criteria and one or more model columns
     * @throws OeRpcException
     */
    public List<M> find(List<Object> sc, String... columns) throws OeRpcException {
        return find(sc, (String) null, columns);
    }

    /**
     * find all Odoo Models or Objects with its id and reading some specific
     * columns
     *
     * @param ids     Odoo models or objects id
     * @param columns one or more model columns
     * @return list of custom Entities for Odoo models or Objects with its id
     * and one or more model columns
     * @throws OeRpcException
     */
    public List<M> find(Object[] ids, String... columns) throws OeRpcException {
        return find(ids, executor.getContext(), columns);
    }

    /**
     * this method to specify which instance of boundary will pass to
     * {@link OeBinder} by calling
     * {@link #find(AbstractOeService, List, Integer, Integer, String, Map, String...)}
     *
     * @param sc      list of search criteria
     * @param offset  offset number
     * @param limit   limit number
     * @param order   order by with asc/desc
     * @param context Odoo context
     * @param columns one or more model columns to read
     * @return list of custom Entities for Odoo models or Objects with list
     * of search criteria, Odoo context and one or more model columns
     * @throws OeRpcException
     */
    public abstract List<M> find(List<Object> sc, Integer offset, Integer limit, String order,
                                 Map<String, Object> context, String... columns) throws OeRpcException;

    /**
     * @param ids     Odoo models or objects id
     * @param context Odoo context
     * @param columns one or more model columns to read
     * @return list of custom Entities for Odoo models or Objects with its id, Odoo context and
     * one or more model columns
     * @throws OeRpcException
     */
    public List<M> find(Object[] ids, Map<String, Object> context, String... columns) throws OeRpcException {
        OeCriteriaBuilder cb = new OeCriteriaBuilder();
        cb.column(_COL_ID).in(ids);
        return find(cb, context, columns);
    }

    /**
     * find limited records by search criteria (Odoo domain) and with
     * columns (optional - recommended to reduce bandwidth)
     *
     * @param cb      search criteria created with {@link OeCriteriaBuilder}
     * @param limit   limit number
     * @param columns one or more model columns to read
     * @return list of custom Entities for Odoo models or Objects with its id, Odoo context and
     * one or more model columns
     * @throws OeRpcException
     * @see #find(List, Integer, String...)
     * @see OeCriteriaBuilder
     */
    public List<M> find(OeCriteriaBuilder cb, Integer limit, String... columns) throws OeRpcException {
        return find(cb.getCriteria(), limit, columns);
    }

    /**
     * find limited records by search criteria (Odoo domain) and with
     * columns (optional - recommended to reduce bandwidth)
     *
     * @param sc      search criteria
     * @param limit   limit number
     * @param columns one or more model columns to read
     * @return list of custom Entities for Odoo models or Objects with its id, Odoo context and
     * one or more model columns
     * @throws OeRpcException
     * @see #find(List, Integer, Integer, String, String...)
     */
    public List<M> find(List<Object> sc, Integer limit, String... columns) throws OeRpcException {
        return find(sc, null, limit, null, columns);
    }

    /**
     * find ordered records by search criteria (Odoo domain) and with
     * columns (optional - recommended to reduce bandwidth)
     *
     * @param cb      search criteria created with {@link OeCriteriaBuilder}
     * @param order   order column with asc/desc
     * @param columns one or more model columns to read
     * @return list of custom Entities for Odoo models or Objects with its id, Odoo context and
     * one or more model columns
     * @throws OeRpcException
     */
    public List<M> find(OeCriteriaBuilder cb, String order, String... columns) throws OeRpcException {
        return find(cb.getCriteria(), order, columns);
    }

    /**
     * find ordered records by search criteria (Odoo domain) and with
     * columns (optional - recommended to reduce bandwidth)
     *
     * @param sc      search criteria
     * @param order   order column with asc/desc
     * @param columns one or more model columns to read
     * @return list of custom Entities for Odoo models or Objects with its id, Odoo context and
     * one or more model columns
     * @throws OeRpcException
     */
    public List<M> find(List<Object> sc, String order, String... columns) throws OeRpcException {
        return find(sc, null, null, order, columns);
    }

    public List<M> findRang(Integer offset, Integer limit, String... columns) throws OeRpcException {
        return find(new OeCriteriaBuilder(), offset, limit, columns);
    }

    /**
     * find by search criteria (Odoo domain) and using offset and limit,
     * with one or more columns (optional - recommended to reduce bandwidth)
     *
     * @param cb      search criteria created with {@link OeCriteriaBuilder} (Odoo domain)
     * @param offset  offset number of records
     * @param limit   limit number of records
     * @param columns one or more model columns to read
     * @return list of custom Entities for Odoo models or Objects with one or more model columns
     * limited with limit and offset
     * @throws OeRpcException
     * @see OeCriteriaBuilder
     * @see #find(List, Integer, Integer, String...)
     */
    public List<M> find(OeCriteriaBuilder cb, Integer offset, Integer limit, String... columns)
            throws OeRpcException {
        return find(cb.getCriteria(), offset, limit, columns);
    }

    /**
     * find by search criteria (Odoo domain) and using offset and limit,
     * with one or more columns (optional - recommended to reduce bandwidth)
     *
     * @param sc      search criteria (Odoo domain)
     * @param offset  offset number of records
     * @param limit   limit number of records
     * @param columns one or more model columns to read
     * @return list of custom Entities for Odoo models or Objects with one or more model columns
     * limited with limit and offset
     * @throws OeRpcException
     * @see #find(List, Integer, Integer, String, String...)
     */
    public List<M> find(List<Object> sc, Integer offset, Integer limit, String... columns) throws OeRpcException {
        return find(sc, offset, limit, null, columns);
    }

    /**
     * find some record between limit and offset ordered and
     * with one or more columns (optional - recommended to reduce bandwidth)
     *
     * @param offset  offset number of records
     * @param limit   limit number of records
     * @param order   column name with asc/desc to order by
     * @param columns one or more model columns to read
     * @return ordered list of custom Entities for Odoo models or Objects with one or more model columns
     * limited with limit and offset
     * @throws OeRpcException
     * @see #find(OeCriteriaBuilder, Integer, Integer, String, String...)
     */
    public List<M> findRang(Integer offset, Integer limit, String order, String... columns)
            throws OeRpcException {
        return find(new OeCriteriaBuilder(), offset, limit, order, columns);
    }

    /**
     * find ordered models by search criteria (Odoo domain) and using offset and limit,
     * with one or more columns (optional - recommended to reduce bandwidth)
     *
     * @param cb      search criteria created with {@link OeCriteriaBuilder} (Odoo domain)
     * @param offset  offset number of records
     * @param limit   limit number of records
     * @param order   column name with asc/desc to order by
     * @param columns one or more model columns to read
     * @return ordered list of custom Entities for Odoo models or Objects with one or more model columns
     * limited with limit and offset
     * @throws OeRpcException
     * @see OeCriteriaBuilder
     * @see #find(List, Integer, Integer, String, String...)
     */
    public List<M> find(OeCriteriaBuilder cb, Integer offset, Integer limit, String order, String... columns)
            throws OeRpcException {
        return find(cb.getCriteria(), offset, limit, order, columns);
    }

    /**
     * find ordered models by search criteria (Odoo domain) and using offset and limit,
     * with one or more columns (optional - recommended to reduce bandwidth)
     *
     * @param sc      search criteria (Odoo domain)
     * @param offset  offset number of records
     * @param limit   limit number of records
     * @param order   column name with asc/desc to order by
     * @param columns one or more model columns to read
     * @return ordered list of custom Entities for Odoo models or Objects with one or more model columns
     * limited with limit and offset
     * @throws OeRpcException
     * @see #find(List, Integer, Integer, String, Map, String...)
     */
    public List<M> find(List<Object> sc, Integer offset, Integer limit, String order, String... columns)
            throws OeRpcException {
        return find(sc, offset, limit, order, executor.getContext(), columns);
    }

    /**
     * find Odoo models by calling {@link OeExecutor} searchReadMap method
     * give it the Odoo model name and list of search criteria
     *
     * @param <E>     Executor boundary service type
     * @param e       instance of Executor boundary service
     * @param sc      list of search criteria
     * @param offset  offset number of records
     * @param limit   limit number of records
     * @param columns one or more model columns to read
     * @param order   column name with asc/desc to order by
     * @param context Odoo context
     * @return list of custom Entities for Odoo models or Objects with list
     * of search criteria, Odoo context and one or more model columns
     * @throws OeRpcException
     * @see OeBinder#bind(String, Class, AbstractOeService)
     */
    public <E extends AbstractOeService> List<M> find(E e, List<Object> sc, Integer offset, Integer limit,
                                                      String order, Map<String, Object> context,
                                                      String... columns) throws OeRpcException {
        Map<String, Object>[] results = executor.searchReadMap(getName(), sc, offset, limit, order, context, columns);
        List<M> oeModels = new ArrayList<M>(results.length);
        for (Map<String, Object> result : results) {
            oeModels.add(OeBinder.bind(result.toString(), model, e));
        }
        return oeModels;
    }

    /**
     * Execute odoo method name
     *
     * @param method method name
     * @return result as object
     * @throws OeRpcException
     * @see #execute(String, Object[], Map)
     */
    public Object execute(String method) throws OeRpcException {
        return execute(method, new Object[]{}, Collections.<String, Object>emptyMap());
    }

    /**
     * Execute odoo method name with kwargs
     *
     * @param method odoo method name
     * @param kwargs odoo kwargs
     * @return result as object
     * @throws OeRpcException
     * @see #execute(String, Object[], Map)
     */
    public Object execute(String method, Map<String, Object> kwargs) throws OeRpcException {
        return execute(method, new Object[]{}, kwargs);
    }

    /**
     * Execute odoo method name with args
     *
     * @param method odoo method name
     * @param args   odoo method args
     * @return result as object
     * @throws OeRpcException
     * @see #execute(String, Object[], Map)
     */
    public Object execute(String method, Object[] args) throws OeRpcException {
        return execute(method, args, Collections.<String, Object>emptyMap());
    }

    /**
     * Execute odoo method name with args and kwargs
     *
     * @param method odoo method name
     * @param args   odoo method args
     * @param kwargs odoo kwargs
     * @return result as object
     * @throws OeRpcException
     */
    public Object execute(String method, Object[] args, Map<String, Object> kwargs) throws OeRpcException {
        return executor.execute(getName(), method, args, kwargs);
    }

    public Long count() throws OeRpcException {
        return count(new OeCriteriaBuilder());
    }

    /**
     * Count Odoo model with search criteria (domain name)
     *
     * @param cb search criteria created with {@link OeCriteriaBuilder} (Odoo domain)
     * @return count
     * @throws OeRpcException
     * @see #count(List)
     */
    public Long count(OeCriteriaBuilder cb) throws OeRpcException {
        return count(cb.getCriteria());
    }

    /**
     * Count Odoo model with search criteria (domain name)
     *
     * @param sc search criteria (Odoo domain)
     * @return count
     * @throws OeRpcException
     * @see OeExecutor#count(String, List)
     */
    public Long count(List<Object> sc) throws OeRpcException {
        return executor.count(getName(), sc);
    }

    /**
     * Create Odoo model record
     *
     * @param values record values
     * @return generated odoo id
     * @throws OeRpcException
     * @see OeExecutor#create(String, Map)
     */
    public Long create(Map<String, Object> values) throws OeRpcException {
        return executor.create(getName(), values);
    }

    /**
     * Update Odoo model record
     *
     * @param id     record id
     * @param values new values to update
     * @return true if record updated
     * @throws OeRpcException
     * @see OeExecutor#write(String, Object, Map)
     */
    public Boolean write(Object id, Map<String, Object> values) throws OeRpcException {
        return executor.write(getName(), id, values);
    }

    /**
     * Update Odoo model record
     *
     * @param id     record id
     * @param values new values to update
     * @return true if record updated
     * @throws OeRpcException
     * @see #write(Object, Map)
     */
    public Boolean update(Object id, Map<String, Object> values) throws OeRpcException {
        return write(id, values);
    }

    /**
     * Delete record using unlike in odoo model
     *
     * @param ids ids to be removed
     * @return true if the record(s) removed
     * @throws OeRpcException
     * @see OeExecutor#unlike(String, Long...)
     */
    public Boolean unlike(Long... ids) throws OeRpcException {
        return executor.unlike(getName(), ids);
    }

    /**
     * Delete record using unlike in odoo model
     *
     * @param ids ids to be removed
     * @return true if the record(s) removed
     * @throws OeRpcException
     * @see #unlike(Long...)
     */
    public Boolean delete(Long... ids) throws OeRpcException {
        return unlike(ids);
    }
}
