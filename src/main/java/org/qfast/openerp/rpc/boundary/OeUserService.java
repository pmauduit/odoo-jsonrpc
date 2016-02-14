/*
 * Copyright 2016 QFast Ahmed El-mawaziny
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
import org.qfast.openerp.rpc.entity.OeMenu;
import org.qfast.openerp.rpc.entity.OeUser;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.json.OeExecutor;

import java.util.List;
import java.util.Map;

import static org.qfast.openerp.rpc.OeConst.OeModel.USERS;

/**
 * OeUserService for find OpenERP user (res.user) by findById, finAll or custom search criteria
 *
 * @author Ahmed El-mawaziny
 * @see OeUser
 * @since 1.0
 */
public class OeUserService extends AbstractOeService<OeUser> {

    public static final String name = USERS.getName();
    private static final long serialVersionUID = -4120158493458451517L;

    public OeUserService(OeExecutor executor) {
        super(executor, OeUser.class);
    }

    public <C extends OeUser> OeUserService(OeExecutor executor, Class<C> model) {
        super(executor, model);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<OeUser> find(List<Object> sc, Integer offset, Integer limit, String order, Map<String, Object> context,
                             String... columns) throws OeRpcException {
        return super.find(this, sc, offset, limit, order, context, columns);
    }

    /**
     * find menus by user id
     *
     * @param userId user id
     * @return Collection Set of OeMenu
     * @throws OeRpcException
     */
    public OeMenu findMenusByUserId(Integer userId) throws OeRpcException {
        return new OeMenuService(executor).findByUserId(userId);
    }

    public List<OeMenu> findMenusByUserIdAllInOne(Integer userId) throws OeRpcException {
        return new OeMenuService(executor).findByUserIdAllInOne(userId);
    }

    public OeLanguage findLangaugeByCode(String code) throws OeRpcException {
        return new OeLanguageService(executor).findByCode(code);
    }
}
