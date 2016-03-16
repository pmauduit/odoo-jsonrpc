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

package org.qfast.openerp.rpc.boundary;

import com.google.gson.JsonArray;
import org.qfast.openerp.rpc.OeConst;
import org.qfast.openerp.rpc.entity.AbstractOeEntity;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.json.OeExecutor;
import org.qfast.openerp.rpc.util.OeBinder;
import org.qfast.openerp.rpc.util.OeCriteriaBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.qfast.openerp.rpc.OeConst.SortType.ASC;
import static org.qfast.openerp.rpc.OeConst.SortType.DESC;
import static org.qfast.openerp.rpc.OeConst._COL_ID;

/**
 * AbstractOeService abstract class for boundary services of OpenERP. easy use
 * findById(s) findAll ...etc
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
     * @param executor OpenERP Executor {@link OeExecutor}
     * @param model    model class
     */
    @SuppressWarnings("unchecked")
    public <C extends M> AbstractOeService(OeExecutor executor, Class<C> model) {
        this.executor = executor;
        this.model = (Class<M>) model;
    }

    public static <OeM extends AbstractOeEntity, E extends AbstractOeService> OeM findById(OeExecutor executor,
                                                                                           Class<OeM> m, Class<E> e,
                                                                                           Long id) throws Exception {
        E eInstance = e.getDeclaredConstructor(OeExecutor.class).newInstance(executor);
        OeCriteriaBuilder cb = new OeCriteriaBuilder();
        cb.column(OeConst._COL_ID).eq(id);
        JsonArray result = executor.searchReadArr(eInstance.getName(), cb.getCriteria());
        if (result != null && !result.isJsonNull() && result.size() != 0) {
            return OeBinder.bind(result.get(0).toString(), m, eInstance);
        }
        throw new OeRpcException(m.getSimpleName() + " Not found with id=" + id, null);
    }

    /**
     * Get OpenERP Executor
     *
     * @return OeExecutor
     */
    public OeExecutor getExecutor() {
        return executor;
    }

    /**
     * abstract method to get OpenERP model name
     *
     * @return OpenERP model name
     */
    public abstract String getName();

    /**
     * find OpenERP Model or Object by id and return custom Entity
     *
     * @param id Id of OpenERP Model or Object
     * @return custom Entity for OpenERP model or object
     * @throws OeRpcException if any exception happened on the OpenERP server
     */
    public M findById(Long id, String... columns) throws OeRpcException {
        List<M> list = find(new Long[]{id}, columns);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }

        throw new OeRpcException(model.getSimpleName() + " Not found with id=" + id, null);
    }

    /**
     * find OpenERP Models or Objects by id and return custom Entities
     *
     * @param ids OpenERP Models or Objects id
     * @return custom Entities for OpenERP models or objects
     * @throws OeRpcException
     */
    public final List<M> findByIds(Long... ids) throws OeRpcException {
        return find(ids);
    }

    public final List<M> findByIds(Long[] ids, String... columns) throws OeRpcException {
        return find(ids, columns);
    }

    /**
     * find all OpenERP Models or Objects
     *
     * @return list of custom Entities for OpenERP models or Objects
     * @throws OeRpcException
     */
    public final List<M> findAll(String... columns) throws OeRpcException {
        return find(executor.getContext(), columns);
    }

    public final M findFirst(String... columns) throws OeRpcException {
        return findFirst(new OeCriteriaBuilder(), columns);
    }

    /**
     * @param cb
     * @param columns
     * @return
     * @throws OeRpcException
     */
    public final M findFirst(OeCriteriaBuilder cb, String... columns) throws OeRpcException {
        List<M> find = find(cb, 0, 1, _COL_ID + ASC.toString(), columns);
        if (find != null && !find.isEmpty()) {
            return find.get(0);
        }
        return null;
    }

    /**
     * @return
     * @throws OeRpcException
     */
    public final M findLast(String... columns) throws OeRpcException {
        return findLast(new OeCriteriaBuilder(), columns);
    }

    /**
     * @param cb
     * @param columns
     * @return
     * @throws OeRpcException
     */
    public final M findLast(OeCriteriaBuilder cb, String... columns) throws OeRpcException {
        List<M> find = find(cb, 0, 1, _COL_ID + DESC.toString(), columns);
        if (find != null && !find.isEmpty()) {
            return find.get(0);
        }
        return null;
    }

    public final M findAny(String... columns) throws OeRpcException {
        return findAny(new OeCriteriaBuilder(), columns);
    }

    /**
     * @param cb
     * @param columns
     * @return
     * @throws OeRpcException
     */
    public final M findAny(OeCriteriaBuilder cb, String... columns) throws OeRpcException {
        List<M> find = find(cb, 1, columns);
        if (find != null && !find.isEmpty()) {
            return find.get(0);
        }
        return null;
    }

    /**
     * find all OpenERP Models or Objects with OpenERP context
     *
     * @param context OpenERP context
     * @return list of custom Entities for OpenERP models or Objects with
     * specific OpenERP context
     * @throws OeRpcException
     */
    public final List<M> find(Map<String, Object> context, String... columns) throws OeRpcException {
        return find(Collections.emptyList(), null, null, null, context, columns);
    }

    /**
     * find all OpenERP Models or Objects with search criteria and reading some
     * specific columns
     *
     * @param cb      search criteria created with {@link OeCriteriaBuilder}
     * @param columns one or more model columns to read
     * @return list of custom Entities for OpenERP models or Objects with some
     * specific columns
     * @throws OeRpcException
     */
    public final List<M> find(OeCriteriaBuilder cb, String... columns) throws OeRpcException {
        return find(cb.getCriteria(), columns);
    }

    /**
     * find all OpenERP Models or Objects with search criteria, OpenERP context
     * and reading some specific columns
     *
     * @param cb      search criteria created with {@link OeCriteriaBuilder}
     * @param context OpenERP context
     * @param columns one or more model columns to read
     * @return list of custom Entities for OpenERP models or Objects with some
     * specific columns and OpenERP context
     * @throws OeRpcException
     */
    public final List<M> find(OeCriteriaBuilder cb, Map<String, Object> context, String... columns)
            throws OeRpcException {
        return find(cb.getCriteria(), null, null, null, context, columns);
    }

    /**
     * find all OpenERP Models or Objects with list of search criteria and
     * reading some specific columns
     *
     * @param sc      list of search criteria
     * @param columns one or more model columns
     * @return list of custom Entities for OpenERP models or Objects with some
     * custom list of search criteria and one or more model columns
     * @throws OeRpcException
     */
    public final List<M> find(List<Object> sc, String... columns) throws OeRpcException {
        return find(sc, (String) null, columns);
    }

    /**
     * find all OpenERP Models or Objects with its id and reading some specific
     * columns
     *
     * @param ids     OpenERP models or objects id
     * @param columns one or more model columns
     * @return list of custom Entities for OpenERP models or Objects with its id
     * and one or more model columns
     * @throws OeRpcException
     */
    public final List<M> find(Object[] ids, String... columns) throws OeRpcException {
        return find(ids, executor.getContext(), columns);
    }

    /**
     * this method to specify which instance of boundary will pass to
     * {@link OeBinder} by calling
     * {@link #find(AbstractOeService, List, Integer, Integer, String, Map, String...)}
     *
     * @param sc      list of search criteria
     * @param offset
     * @param limit
     * @param order
     * @param context OpenERP context
     * @param columns one or more model columns to read
     * @return list of custom Entities for OpenERP models or Objects with list
     * of search criteria, OpenERP context and one or more model columns
     * @throws OeRpcException
     */
    public abstract List<M> find(List<Object> sc, Integer offset, Integer limit, String order,
                                 Map<String, Object> context, String... columns) throws OeRpcException;

    /**
     * @param ids     OpenERP models or objects id
     * @param context OpenERP context
     * @param columns one or more model columns to read
     * @return list of custom Entities for OpenERP models or Objects with its id, OpenERP context and
     * one or more model columns
     * @throws OeRpcException
     */
    public List<M> find(Object[] ids, Map<String, Object> context, String... columns) throws OeRpcException {
        OeCriteriaBuilder cb = new OeCriteriaBuilder();
        cb.column(_COL_ID).in(ids);
        return find(cb, context, columns);
    }

    /**
     * @param cb
     * @param limit
     * @param columns
     * @return
     * @throws OeRpcException
     */
    public final List<M> find(OeCriteriaBuilder cb, Integer limit, String... columns) throws OeRpcException {
        return find(cb.getCriteria(), limit, columns);
    }

    /**
     * @param sc
     * @param limit
     * @param columns
     * @return
     * @throws OeRpcException
     */
    public final List<M> find(List<Object> sc, Integer limit, String... columns) throws OeRpcException {
        return find(sc, null, limit, null, columns);
    }

    /**
     * @param cb
     * @param order
     * @param columns
     * @return
     * @throws OeRpcException
     */
    public final List<M> find(OeCriteriaBuilder cb, String order, String... columns) throws OeRpcException {
        return find(cb.getCriteria(), order, columns);
    }

    /**
     * @param sc
     * @param order
     * @param columns
     * @return
     * @throws OeRpcException
     */
    public final List<M> find(List<Object> sc, String order, String... columns) throws OeRpcException {
        return find(sc, null, null, order, columns);
    }

    public final List<M> findRang(Integer offset, Integer limit, String... columns) throws OeRpcException {
        return find(new OeCriteriaBuilder(), offset, limit, columns);
    }

    /**
     * @param cb
     * @param offset
     * @param limit
     * @param columns
     * @return
     * @throws OeRpcException
     */
    public final List<M> find(OeCriteriaBuilder cb, Integer offset, Integer limit, String... columns)
            throws OeRpcException {
        return find(cb.getCriteria(), offset, limit, columns);
    }

    /**
     * @param sc
     * @param offset
     * @param limit
     * @param columns
     * @return
     * @throws OeRpcException
     */
    public final List<M> find(List<Object> sc, Integer offset, Integer limit, String... columns) throws OeRpcException {
        return find(sc, offset, limit, null, columns);
    }

    public final List<M> findRang(Integer offset, Integer limit, String order, String... columns)
            throws OeRpcException {
        return find(new OeCriteriaBuilder(), offset, limit, order, columns);
    }

    /**
     * @param cb
     * @param offset
     * @param limit
     * @param order
     * @param columns
     * @return
     * @throws OeRpcException
     */
    public final List<M> find(OeCriteriaBuilder cb, Integer offset, Integer limit, String order, String... columns)
            throws OeRpcException {
        return find(cb.getCriteria(), offset, limit, order, columns);
    }

    /**
     * @param sc
     * @param offset
     * @param limit
     * @param order
     * @param columns
     * @return
     * @throws OeRpcException
     */
    public final List<M> find(List<Object> sc, Integer offset, Integer limit, String order, String... columns)
            throws OeRpcException {
        return find(sc, offset, limit, order, executor.getContext(), columns);
    }

    /**
     * find OpenERP models by calling {@link OeExecutor} searchReadMap method
     * give it the OpenERP model name and list of search criteria
     *
     * @param <E>     Executor boundary service type
     * @param e       instance of Executor boundary service
     * @param sc      list of search criteria
     * @param offset
     * @param columns one or more model columns to read
     * @param limit
     * @param order
     * @param context
     * @return list of custom Entities for OpenERP models or Objects with list
     * of search criteria, OpenERP context and one or more model columns
     * @throws OeRpcException
     */
    public final <E extends AbstractOeService> List<M> find(E e, List<Object> sc, Integer offset, Integer limit,
                                                            String order, Map<String, Object> context,
                                                            String... columns) throws OeRpcException {
        JsonArray result = executor.searchRead(getName(), sc, offset, limit, order, context, columns);
        List<M> oeModels = new ArrayList<M>(result.size());
//        Set<Map.Entry<String, JsonElement>> entries = OeUtil.parseAsJsonObject(result.get(0).toString()).entrySet();
//        for (Map.Entry<String, JsonElement> entry : entries)
//            System.out.println(entry.getKey() + " = "+ entry.getValue());
        for (int i = 0; i < result.size(); i++) {
            oeModels.add(OeBinder.bind(result.get(i).toString(), model, e));
        }
        return oeModels;
    }

    /**
     * @param method
     * @return
     * @throws OeRpcException
     */
    public Object execute(String method) throws OeRpcException {
        return execute(method, new Object[]{}, Collections.<String, Object>emptyMap());
    }

    /**
     * @param method
     * @param kwargs
     * @return
     * @throws OeRpcException
     */
    public Object execute(String method, Map<String, Object> kwargs) throws OeRpcException {
        return execute(method, new Object[]{}, kwargs);
    }

    /**
     * @param method
     * @param args
     * @return
     * @throws OeRpcException
     */
    public Object execute(String method, Object[] args) throws OeRpcException {
        return execute(method, args, Collections.<String, Object>emptyMap());
    }

    /**
     * @param method
     * @param args
     * @param kwargs
     * @return
     * @throws OeRpcException
     */
    public Object execute(String method, Object[] args, Map<String, Object> kwargs) throws OeRpcException {
        return executor.execute(getName(), method, args, kwargs);
    }

    public Long count() throws OeRpcException {
        return count(new OeCriteriaBuilder());
    }

    /**
     * @param cb
     * @return
     * @throws OeRpcException
     */
    public Long count(OeCriteriaBuilder cb) throws OeRpcException {
        return count(cb.getCriteria());
    }

    /**
     * @param sc
     * @return
     * @throws OeRpcException
     */
    public Long count(List<Object> sc) throws OeRpcException {
        return executor.count(getName(), sc);
    }

    /**
     * @param values
     * @return
     * @throws OeRpcException
     */
    public Long create(Map<String, Object> values) throws OeRpcException {
        return executor.create(getName(), values);
    }

    /**
     * @param id
     * @param values
     * @return
     * @throws OeRpcException
     */
    public Boolean write(Object id, Map<String, Object> values) throws OeRpcException {
        return executor.write(getName(), id, values);
    }

    /**
     * @param id
     * @param values
     * @return
     * @throws OeRpcException
     */
    public Boolean update(Object id, Map<String, Object> values) throws OeRpcException {
        return write(id, values);
    }

    /**
     * @param ids
     * @return
     * @throws OeRpcException
     */
    public Boolean unlike(Long... ids) throws OeRpcException {
        return executor.unlike(getName(), ids);
    }

    /**
     * @param ids
     * @return
     * @throws OeRpcException
     */
    public Boolean delete(Long... ids) throws OeRpcException {
        return unlike(ids);
    }
}
