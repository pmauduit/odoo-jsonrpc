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

import org.qfast.openerp.rpc.entity.OeLanguage;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.json.OeExecutor;
import org.qfast.openerp.rpc.util.OeCriteriaBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.qfast.openerp.rpc.OeConst.OeModel.LANGUAGE;
import static org.qfast.openerp.rpc.entity.OeLanguage._CODE;

/**
 * @author Ahmed El-mawaziny
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
     * @param code
     * @return
     * @throws OeRpcException
     */
    public OeLanguage findByCode(String code, String... columns) throws OeRpcException {
        OeCriteriaBuilder cb = new OeCriteriaBuilder();
        cb.column(_CODE).eq(code);
        return findAny(cb, columns);
    }

    @Override
    public Boolean update(Object id, Map<String, Object> values) throws OeRpcException {
        return super.update(new Object[]{id}, values);
    }

    @Override
    public Boolean unlike(Long... ids) throws OeRpcException {
        Map<String, Object> values = new HashMap<String, Object>(1);
        values.put(OeLanguage._ACTIVE, false);
        for (Long id : ids) {
            update(id, values);
        }
        return super.unlike(ids);
    }
}
