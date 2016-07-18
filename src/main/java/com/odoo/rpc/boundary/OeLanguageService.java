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

import com.odoo.rpc.entity.OeLanguage;
import com.odoo.rpc.exception.OeRpcException;
import com.odoo.rpc.json.OeExecutor;
import com.odoo.rpc.util.OeCriteriaBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.odoo.rpc.entity.OeLanguage._CODE;
import static com.odoo.rpc.util.OeConst.OeModel.LANGUAGE;

/**
 * Service to manage {@link OeLanguage}
 *
 * @author Ahmed El-mawaziny
 * @see OeLanguage
 * @since 1.0
 */
public class OeLanguageService extends AbstractOeService<OeLanguage> {

    public static final String name = LANGUAGE.getName();
    private static final long serialVersionUID = -5453046024395914541L;

    public OeLanguageService(OeExecutor executor) {
        super(executor, OeLanguage.class);
    }

    public <C extends OeLanguage> OeLanguageService(OeExecutor executor, Class<C> model) {
        super(executor, model);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<OeLanguage> find(List<Object> sc, Integer offset, Integer limit, String order,
                                 Map<String, Object> context, String... columns) throws OeRpcException {
        return super.find(this, sc, offset, limit, order, context, columns);
    }

    /**
     * find Odoo language by its code
     *
     * @param code language code
     * @return language Odoo model
     * @throws OeRpcException
     * @see #findAny(OeCriteriaBuilder, String...)
     * @see OeCriteriaBuilder
     */
    public OeLanguage findByCode(String code, String... columns) throws OeRpcException {
        OeCriteriaBuilder cb = new OeCriteriaBuilder();
        cb.column(_CODE).eq(code);
        return findAny(cb, columns);
    }

    /**
     * Overriding update method because there is a bug in Language write method trying to loop on int value
     * which is not iterable
     * <p>
     * fixing bug by wrapping the id in an array
     *
     * @param id     record id
     * @param values new values to update
     * @return true means record is successfully updated
     * @throws OeRpcException
     * @see #write(Object, Map)
     */
    @Override
    public Boolean update(Object id, Map<String, Object> values) throws OeRpcException {
        return write(id, values);
    }

    /**
     * Overriding write method because there is a bug in Language write method trying to loop on int value
     * which is not iterable
     * <p>
     * fixing bug by wrapping the id in an array
     *
     * @param id     record id
     * @param values new values to update
     * @return true means record is successfully updated
     * @throws OeRpcException
     * @see super#write(Object, Map)
     */
    @Override
    public Boolean write(Object id, Map<String, Object> values) throws OeRpcException {
        return super.write(new Object[]{id}, values);
    }

    /**
     * Overriding unlike method because in Odoo if you want to delete/unlike language you have to deactivate it first
     *
     * @param ids ids to be removed
     * @return true if the record is successfully deleted or unlike
     * @throws OeRpcException
     * @see #delete(Long...)
     */
    @Override
    public Boolean unlike(Long... ids) throws OeRpcException {
        return delete(ids);
    }

    /**
     * Overriding delete method because in Odoo if you want to delete/unlike language you have to deactivate it first
     *
     * @param ids ids to be removed
     * @return true if the record is successfully deleted or unlike
     * @throws OeRpcException
     * @see #write(Object, Map)
     * @see super#unlike(Long...)
     */

    @Override
    public Boolean delete(Long... ids) throws OeRpcException {
        Map<String, Object> values = new HashMap<String, Object>(1);
        values.put(OeLanguage._ACTIVE, false);
        for (Long id : ids) {
            write(id, values);
        }
        return super.unlike(ids);
    }
}
