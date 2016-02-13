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

import org.qfast.openerp.rpc.entity.OeGroup;
import org.qfast.openerp.rpc.entity.OeMenu;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.json.OeExecutor;
import org.qfast.openerp.rpc.util.OeCriteriaBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.qfast.openerp.rpc.OeConst.OeModel.GROUPS;
import static org.qfast.openerp.rpc.OeConst._COL_ID;
import static org.qfast.openerp.rpc.entity.OeGroup._USERS_ID_ID;

/**
 * @author Ahmed El-mawaziny
 */
public class OeGroupService extends AbstractOeService<OeGroup> {

    public static final String name = GROUPS.getName();
    private static final long serialVersionUID = -4874115041971285981L;

    public OeGroupService(OeExecutor executor) {
        super(executor, OeGroup.class);
    }

    @Override
    protected String getName() {
        return name;
    }

    @Override
    public List<OeGroup> find(List<Object> sc, Integer offset, Integer limit, String order,
                              Map<String, Object> context, String... columns) throws OeRpcException {
        return super.find(this, sc, offset, limit, order, context, columns);
    }

    public Set<OeMenu> findMenusByGroupId(Integer groupId) throws OeRpcException {
        return new OeMenuService(executor).findByGroupId(groupId);
    }

    public Set<OeGroup> findByUserId(Integer userId) throws OeRpcException {
        OeCriteriaBuilder cb = new OeCriteriaBuilder();
        cb.column(_USERS_ID_ID).eq(userId);
        return (new HashSet<OeGroup>(super.find(cb, new String[]{_COL_ID})));
    }
}
