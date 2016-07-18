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

import com.odoo.rpc.entity.OeActionClient;
import com.odoo.rpc.exception.OeRpcException;
import com.odoo.rpc.json.OeExecutor;

import java.util.List;
import java.util.Map;

import static com.odoo.rpc.util.OeConst.OeModel.ACTION_CLIENT;

/**
 * Service to manage {@link OeActionClient}
 *
 * @author Ahmed El-mawaziny
 * @see OeActionClient
 * @since 1.0
 */
public class OeActionClientService extends AbstractOeService<OeActionClient> {

    public static final String name = ACTION_CLIENT.getName();
    private static final long serialVersionUID = -2826571013901902904L;

    public OeActionClientService(OeExecutor executor) {
        super(executor, OeActionClient.class);
    }

    public <C extends OeActionClient> OeActionClientService(OeExecutor executor, Class<C> model) {
        super(executor, model);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<OeActionClient> find(List<Object> sc, Integer offset, Integer limit, String order,
                                     Map<String, Object> context, String... columns) throws OeRpcException {
        return super.find(this, sc, offset, limit, order, context, columns);
    }
}
