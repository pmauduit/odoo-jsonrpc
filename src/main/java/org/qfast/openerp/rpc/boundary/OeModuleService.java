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
import org.qfast.openerp.rpc.entity.OeModule;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.json.OeExecutor;
import org.qfast.openerp.rpc.util.OeUtil;

import java.util.List;
import java.util.Map;

import static org.qfast.openerp.rpc.OeConst.OeModel.MODULES;

/**
 * OeModuleService for find OpenERP module (ir.module.module) by findById, finAll or
 * custom search criteria
 *
 * @author Ahmed El-mawaziny
 * @see OeModule
 * @since 1.0
 */
public class OeModuleService extends AbstractOeService<OeModule> {

    public static final String name = MODULES.getName();
    private static final long serialVersionUID = 3639886772896021816L;

    public OeModuleService(OeExecutor executor) {
        super(executor, OeModule.class);
    }

    public <C extends OeModule> OeModuleService(OeExecutor executor, Class<C> model) {
        super(executor, model);
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean install(Object... ids) throws OeRpcException {
        executor.execute(name, Fun.INSTALL.name, OeUtil.parseAsJsonArray(ids));
        return true;
    }

    public boolean uninstall(Object... ids) throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(OeUtil.parseAsJsonArray(ids));
        executor.execute(name, Fun.UNINSTALL.name, args);
        return true;
    }

    @Override
    public List<OeModule> find(List<Object> sc, Integer offset, Integer limit, String order, Map<String, Object> context,
                               String... columns) throws OeRpcException {
        return super.find(this, sc, offset, limit, order, context, columns);
    }

    public enum Fun {

        INSTALL("button_immediate_install"),
        UNINSTALL("button_immediate_uninstall");

        private final String name;

        Fun(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
