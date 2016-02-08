/*
 * Copyright 2014 QFast Ahmed El-mawaziny.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qfast.openerp.rpc.boundary;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static org.qfast.openerp.rpc.OeConst._ID;
import org.qfast.openerp.rpc.entity.AbstractOeEntity;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.json.OeExecutor;
import org.qfast.openerp.rpc.util.OeBinder;
import org.qfast.openerp.rpc.util.OeCriteriaBuilder;

/**
 * AbstractOeService abstract class for boundary services of OpenERP. easy use
 * findById(s) findAll ...etc
 *
 * @param <M> Entity type extends from {@link AbstractOeEntity}
 * @see AbstractOeEntity
 * @since 1.0
 * @author Ahmed El-mawaziny
 */
public abstract class AbstractOeService<M extends AbstractOeEntity>
        implements Serializable {

    protected final OeExecutor executor;
    protected final Class<M> model;

    /**
     * Get OpenERP Executer
     *
     * @return OeExecuter
     */
    public OeExecutor getExecutor() {
        return executor;
    }

    /**
     * abstract method to get OpenERP model name
     *
     * @return OpenERP model name
     */
    protected abstract String getName();

    /**
     * AbstractOeService default constructor
     *
     * @param executer OpenERP Executer {@link OeExecutor}
     * @param model model class
     */
    public AbstractOeService(OeExecutor executer, Class<M> model) {
        this.executor = executer;
        this.model = model;
    }

    /**
     * find OpenERP Model or Object by id and return custom Entity
     *
     * @param id Id of OpenERP Model or Object
     * @return custom Entity for OpenERP model or object
     * @throws OeRpcException if any exception happened on the OpenERP server
     */
    public M findById(Integer id) throws OeRpcException {
        List<M> list = findById(new Integer[]{id});
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }

        throw new OeRpcException(model.getSimpleName()
                + " Not found with id=" + id, null);
    }

    /**
     * find OpenERP Models or Objects by id and return custom Entities
     *
     * @param ids OpenERP Models or Objects id
     * @return custom Entities for OpenERP models or objects
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final List<M> findById(Integer... ids) throws OeRpcException {
        return find(ids);
    }

    /**
     * find all OpenERP Models or Objects
     *
     * @return list of custom Entities for OpenERP models or Objects
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final List<M> findAll() throws OeRpcException {
        return find(executor.getContext());
    }

    /**
     *
     * @param cb
     * @param columns
     * @return
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final M findFirst(OeCriteriaBuilder cb, String... columns) throws OeRpcException {
        List<M> find = find(cb, 0, 1, _ID + " ASC", columns);
        if (find != null && !find.isEmpty()) {
            return find.get(0);
        }
        return null;
    }

    /**
     * @return
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     * @throws org.json.JSONException
     */
    public final M findLast() throws OeRpcException {
        return findLast(new OeCriteriaBuilder());
    }

    /**
     *
     * @param cb
     * @param columns
     * @return
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final M findLast(OeCriteriaBuilder cb, String... columns) throws OeRpcException {
        List<M> find = find(cb, 0, 1, _ID + " DESC", columns);
        if (find != null && !find.isEmpty()) {
            return find.get(0);
        }
        return null;
    }

    /**
     *
     * @param cb
     * @param columns
     * @return
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
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
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final List<M> find(Map<String, Object> context) throws OeRpcException {
        return find(Collections.<Object>emptyList(), null, null, null, context);
    }

    /**
     * find all OpenERP Models or Objects with search criteria and reading some
     * specific columns
     *
     * @param cb search criteria created with {@link OeCriteriaBuilder}
     * @param columns one or more model columns to read
     * @return list of custom Entities for OpenERP models or Objects with some
     * specific columns
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final List<M> find(OeCriteriaBuilder cb, String... columns) throws OeRpcException {
        return find(cb.getCriteria(), columns);
    }

    /**
     * find all OpenERP Models or Objects with search criteria, OpenERP context
     * and reading some specific columns
     *
     * @param cb search criteria created with {@link OeCriteriaBuilder}
     * @param context OpenERP context
     * @param columns one or more model columns to read
     * @return list of custom Entities for OpenERP models or Objects with some
     * specific columns and OpenERP context
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final List<M> find(OeCriteriaBuilder cb, Map<String, Object> context,
            String... columns) throws OeRpcException {
        return find(cb.getCriteria(), null, null, null, context, columns);
    }

    /**
     * find all OpenERP Models or Objects with list of search criteria and
     * reading some specific columns
     *
     * @param sc list of search criteria
     * @param columns one or more model columns
     * @return list of custom Entities for OpenERP models or Objects with some
     * custom list of search criteria and one or more model columns
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final List<M> find(List<Object> sc, String... columns) throws OeRpcException {
        return find(sc, (String) null, columns);
    }

    /**
     * find all OpenERP Models or Objects with its id and reading some specific
     * columns
     *
     * @param ids OpenERP models or objects id
     * @param columns one or more model columns
     * @return list of custom Entities for OpenERP models or Objects with its id
     * and one or more model columns
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final List<M> find(Object[] ids, String... columns) throws OeRpcException {
        return find(ids, executor.getContext(), columns);
    }

    /**
     * this method to specify which instance of boundary will pass to
     * {@link OeBinder} by calling
     * {@link #find(Object,  java.util.List, Integer, Integer, String, java.util.Map, String...)}
     *
     * @param sc list of search criteria
     * @param offset
     * @param limit
     * @param order
     * @param context OpenERP context
     * @param columns one or more model columns to read
     * @return list of custom Entities for OpenERP models or Objects with list
     * of search criteria, OpenERP context and one or more model columns
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public abstract List<M> find(List<Object> sc, Integer offset,
            Integer limit, String order, Map<String, Object> context,
            String... columns) throws OeRpcException;

    /**
     * this method to specify with instance of boundary will pass with
     * {@link OeBinder} by calling
     * {@link #find(java.lang.Object, java.lang.Object[], java.util.Map, java.lang.String...) }
     *
     * @param ids OpenERP models or objects id
     * @param context OpenERP context
     * @param columns one or more model columns to read
     * @return list of custom Entities for OpenERP models or Objects with its
     * id, OpenERP context and one or more model columns
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public List<M> find(Object[] ids, Map<String, Object> context,
            String... columns) throws OeRpcException {
        OeCriteriaBuilder cb = new OeCriteriaBuilder();
        cb.column(_ID).in(ids);
        return find(cb, (String) null, columns);
    }

    /**
     *
     * @param cb
     * @param limit
     * @param columns
     * @return
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final List<M> find(OeCriteriaBuilder cb, Integer limit,
            String... columns) throws OeRpcException {
        return find(cb.getCriteria(), limit, columns);
    }

    /**
     *
     * @param sc
     * @param limit
     * @param columns
     * @return
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final List<M> find(List<Object> sc, Integer limit,
            String... columns) throws OeRpcException {
        return find(sc, null, limit, null, columns);
    }

    /**
     *
     * @param cb
     * @param order
     * @param columns
     * @return
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final List<M> find(OeCriteriaBuilder cb, String order,
            String... columns) throws OeRpcException {
        return find(cb.getCriteria(), order, columns);
    }

    /**
     *
     * @param sc
     * @param order
     * @param columns
     * @return
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final List<M> find(List<Object> sc, String order,
            String... columns) throws OeRpcException {
        return find(sc, null, null, order, columns);
    }

    /**
     *
     * @param cb
     * @param offset
     * @param limit
     * @param columns
     * @return
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final List<M> find(OeCriteriaBuilder cb, Integer offset,
            Integer limit, String... columns) throws OeRpcException {
        return find(cb.getCriteria(), offset, limit, columns);
    }

    /**
     *
     * @param sc
     * @param offset
     * @param limit
     * @param columns
     * @return
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final List<M> find(List<Object> sc, Integer offset,
            Integer limit, String... columns) throws OeRpcException {
        return find(sc, offset, limit, null, columns);
    }

    /**
     *
     * @param cb
     * @param offset
     * @param limit
     * @param order
     * @param columns
     * @return
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final List<M> find(OeCriteriaBuilder cb, Integer offset,
            Integer limit, String order, String... columns) throws OeRpcException {
        return find(cb.getCriteria(), offset, limit, order, columns);
    }

    /**
     *
     * @param sc
     * @param offset
     * @param limit
     * @param order
     * @param columns
     * @return
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final List<M> find(List<Object> sc, Integer offset,
            Integer limit, String order, String... columns) throws OeRpcException {
        return find(sc, offset, limit, order, executor.getContext(), columns);
    }

    /**
     * find OpenERP models by calling {@link XmlOeExecuter} doSearchMap method
     * give it the OpenERP model name and list of search criteria
     *
     * @param <E> Executer boundary service type
     * @param e instance of Executer boundary service
     * @param sc list of search criteria
     * @param offset
     * @param columns one or more model columns to read
     * @param limit
     * @param order
     * @param context
     * @return list of custom Entities for OpenERP models or Objects with list
     * of search criteria, OpenERP context and one or more model columns
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     */
    public final <E extends AbstractOeService> List<M>
            find(E e, List<Object> sc, Integer offset, Integer limit, String order,
                    Map<String, Object> context, String... columns) throws OeRpcException {
        JSONArray result = executor.doSearch(getName(), sc, offset,
                limit, order, context);
        List<M> oems = new ArrayList<M>(result.length());
        for (int i = 0; i < result.length(); i++) {
            oems.add(OeBinder.bind(result.get(i).toString(), model, e));
        }
        return oems;
    }

    /**
     * @param method
     * @return
     * @throws OeRpcException
     * @throws JSONException
     */
    public Object execute(String method) throws OeRpcException, JSONException {
        return execute(method, new Object[]{}, Collections.<String, Object>emptyMap());
    }

    /**
     * @param method
     * @param kwargs
     * @return
     * @throws OeRpcException
     * @throws JSONException
     */
    public Object execute(String method, Map<String, Object> kwargs)
            throws OeRpcException, JSONException {
        return execute(method, new Object[]{}, kwargs);
    }

    /**
     * @param method
     * @param args
     * @return
     * @throws OeRpcException
     * @throws JSONException
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
     * @throws JSONException
     */
    public Object execute(String method, Object[] args, Map<String, Object> kwargs)
            throws OeRpcException {
        JSONArray argsJSON = new JSONArray(new Gson().toJson(args));
        JSONObject kwargsJSON = new JSONObject(new Gson().toJson(kwargs));
        return executor.execute(getName(), method, argsJSON, kwargsJSON);
    }

    /**
     * @param cb
     * @return
     * @throws org.qfast.openerp.rpc.exception.OeRpcException
     * @throws org.json.JSONException
     */
    public Long count(OeCriteriaBuilder cb) throws OeRpcException {
        return count(cb.getCriteria());
    }

    /**
     * @param sc
     * @return
     * @throws OeRpcException
     * @throws org.json.JSONException
     */
    public Long count(List<Object> sc) throws OeRpcException {
        return executor.doCount(getName(), sc);
    }

    /**
     * @param vals
     * @return
     * @throws OeRpcException
     * @throws JSONException
     */
    public Long create(Map<String, Object> vals) throws OeRpcException {
        return executor.doCreate(getName(), vals);
    }

    /**
     * @param id
     * @param vals
     * @return
     * @throws OeRpcException
     * @throws JSONException
     */
    public Boolean write(Integer id, Map<String, Object> vals) throws OeRpcException {
        return executor.doWrite(getName(), id, vals);
    }
    private static final long serialVersionUID = -2422695985617500754L;
}
