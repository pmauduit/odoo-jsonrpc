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

import com.odoo.rpc.entity.OeGroup;
import com.odoo.rpc.entity.OeMenu;
import com.odoo.rpc.exception.OeRpcException;
import com.odoo.rpc.json.OeExecutor;
import com.odoo.rpc.util.OeCriteriaBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.odoo.rpc.entity.OeGroup._USERS_ID_ID;
import static com.odoo.rpc.util.OeConst.OeModel.GROUPS;
import static com.odoo.rpc.util.OeConst._COL_ID;

/**
 * Service to manage {@link OeGroup}
 *
 * @author Ahmed El-mawaziny
 * @see OeGroup
 * @since 1.0
 */
public class OeGroupService extends AbstractOeService<OeGroup> {

    public static final String name = GROUPS.getName();
    private static final long serialVersionUID = -4874115041971285981L;

    public OeGroupService(OeExecutor executor) {
        super(executor, OeGroup.class);
    }

    public <C extends OeGroup> OeGroupService(OeExecutor executor, Class<C> model) {
        super(executor, model);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<OeGroup> find(List<Object> sc, Integer offset, Integer limit, String order,
                              Map<String, Object> context, String... columns) throws OeRpcException {
        return super.find(this, sc, offset, limit, order, context, columns);
    }

    /**
     * Find menus by group id
     *
     * @param groupId group id
     * @return set of menus for the given group id
     * @throws OeRpcException if Odoo response with error
     * @see OeMenu
     * @see OeMenuService#findByGroupId(Long...)
     */
    public Set<OeMenu> findMenusByGroupId(Long groupId) throws OeRpcException {
        return new OeMenuService(executor).findByGroupId(groupId);
    }

    /**
     * Find groups by user id
     *
     * @param userId user id
     * @return set of groups
     * @throws OeRpcException if Odoo response with error
     * @see OeCriteriaBuilder
     * @see #find(OeCriteriaBuilder, String...)
     */
    public Set<OeGroup> findByUserId(Long userId) throws OeRpcException {
        OeCriteriaBuilder cb = new OeCriteriaBuilder();
        cb.column(_USERS_ID_ID).eq(userId);
        return (new HashSet<OeGroup>(super.find(cb, new String[]{_COL_ID})));
    }
}
