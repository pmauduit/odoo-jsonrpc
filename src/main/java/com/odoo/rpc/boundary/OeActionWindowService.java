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

import com.odoo.rpc.entity.OeActionWindow;
import com.odoo.rpc.exception.OeRpcException;
import com.odoo.rpc.json.OeExecutor;

import java.util.List;
import java.util.Map;

import static com.odoo.rpc.OeConst.OeModel.ACTION_WINDOW;

/**
 * Service to manage {@link OeActionWindow}
 *
 * @author Ahmed El-mawaziny
 * @see OeActionWindow
 * @since 1.0
 */
public class OeActionWindowService extends AbstractOeService<OeActionWindow> {

    public static final String name = ACTION_WINDOW.getName();
    private static final long serialVersionUID = -2543631133236941117L;

    public OeActionWindowService(OeExecutor executor) {
        super(executor, OeActionWindow.class);
    }

    public <C extends OeActionWindow> OeActionWindowService(OeExecutor executor, Class<C> model) {
        super(executor, model);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<OeActionWindow> find(List<Object> sc, Integer offset, Integer limit, String order,
                                     Map<String, Object> context, String... columns) throws OeRpcException {
        return super.find(this, sc, offset, limit, order, context, columns);
    }
}
