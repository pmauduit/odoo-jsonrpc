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
import com.odoo.rpc.json.util.OeJEndPoint.Menu;
import com.odoo.rpc.util.OeBinder;
import com.odoo.rpc.util.OeCriteriaBuilder;
import com.odoo.rpc.util.OeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static com.odoo.rpc.boundary.OeMenuService.Fun.LOAD_MENUS;
import static com.odoo.rpc.entity.OeMenu._GROUPS_ID;
import static com.odoo.rpc.entity.OeMenu._GROUPS_ID_ID;
import static com.odoo.rpc.entity.OeMenu._PARENT_ID;
import static com.odoo.rpc.entity.OeMenu._PARENT_ID_ID;
import static com.odoo.rpc.util.OeConst.OeModel.MENUS;

/**
 * Service to manage {@link OeMenu}
 *
 * @author Ahmed El-mawaziny
 * @since 1.0
 */
public class OeMenuService extends AbstractOeService<OeMenu> {

    public static final String name = MENUS.getName();
    private static final long serialVersionUID = -6623029584982281200L;

    /**
     * Default constructor
     *
     * @param executor instance of {@link OeExecutor}
     * @see OeExecutor
     */
    public OeMenuService(OeExecutor executor) {
        super(executor, OeMenu.class);
    }

    /**
     * Use this constructor if you extended {@link OeMenu} and add some modifications
     *
     * @param executor instance of {@link OeExecutor}
     * @param model    new extended class
     * @param <C>      type of the extended class must be extended form {@link OeMenu}
     * @see OeMenu
     */
    public <C extends OeMenu> OeMenuService(OeExecutor executor, Class<C> model) {
        super(executor, model);
    }

    /**
     * Get Odoo model name
     *
     * @return Odoo model name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * load logged in user menus
     * <p>
     * Check if odoo version less than 8 then call {@link Menu#LOAD}
     * else call {@link Fun#LOAD_MENUS}
     *
     * @return User menus in {@link Map}
     * @throws OeRpcException
     * @see OeExecutor#execute(String, Map)
     * @see OeExecutor#execute(String, String)
     */
    public Map<String, Object> loadMenus() throws OeRpcException {
        if (executor.getOeVersion().getVersionNumber() < 8) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("session_id", executor.getSessionId());
            params.put("context", executor.getJsonContext());
            return executor.execute(Menu.LOAD.getPath(), params);
        } else {
            return executor.execute(name, LOAD_MENUS.getName());
        }
    }

    /**
     * load Odoo Menus by calling loadMenus function in ir.ui.menu in Odoo
     * v8. {@link #loadMenus() }
     *
     * @return user menus
     * @throws OeRpcException
     * @see OeBinder#bind(String, Class, AbstractOeService)
     * @see #loadMenus()
     */
    public OeMenu loadOeMenus() throws OeRpcException {
        Map<String, Object> result = loadMenus();
        if (result != null) {
            return OeBinder.bind(result.toString(), OeMenu.class, this);
        } else {
            return null;
        }
    }

    @Override
    public List<OeMenu> find(List<Object> sc, Integer offset, Integer limit, String order, Map<String, Object> context,
                             String... columns) throws OeRpcException {
        return super.find(this, sc, offset, limit, order, context, columns);
    }

    /**
     * Load user menus in list of {@link OeMenu}
     * <p>
     * check if <code>userId</code> same as logged in user id then call {@link #loadOeMenus()}
     * else get user group by {@link OeGroupService#findByUserId(Long)}
     * get menus by no group
     *
     * @param userId user id to get menus
     * @return list of {@link OeMenu}
     * @throws OeRpcException
     * @see OeMenu
     * @see #addChildren(List, OeMenu[])
     * @see #loadOeMenus()
     * @see OeGroupService
     * @see #findByGroupId(Long...)
     * @see #findByNoGroup(String...)
     */
    public List<OeMenu> findByUserIdAllInOne(Long userId) throws OeRpcException {
        if (OeUtil.equals(userId, executor.getUserId())) {
            List<OeMenu> oeMenus = new ArrayList<OeMenu>(10);
            addChildren(oeMenus, loadOeMenus().getChildren());
            oeMenus.addAll(findByNoGroup());
            return oeMenus;
        } else {
            OeGroupService oeGroupService = new OeGroupService(executor);
            Set<OeGroup> oeGroups = oeGroupService.findByUserId(userId);
            oeGroupService = null;
            Set<OeMenu> oeMenus = new TreeSet<OeMenu>();
            Long[] oeGroupsId = new Long[oeGroups.size()];
            int i = 0;
            for (OeGroup oeGroup : oeGroups) {
                oeGroupsId[i] = oeGroup.getId();
                i++;
            }
            oeMenus.addAll(findByGroupId(oeGroupsId));
            oeMenus.addAll(findByNoGroup());
            return new ArrayList<OeMenu>(oeMenus);
        }
    }

    /**
     * add Menu's children
     *
     * @param menus    list of {@link OeMenu}
     * @param children array of {@link OeMenu}
     */
    private void addChildren(List<OeMenu> menus, OeMenu[] children) {
        menus.addAll(Arrays.asList(children));
        for (OeMenu oeMenu : children) {
            addChildren(menus, oeMenu.getChildren());
        }
    }

    /**
     * load all menus under one menu root as a tree
     * <p>
     * Check if <code>userId</code> if same as logged in user
     * then call {@link #loadOeMenus()}
     * else find user's group and find menus by group and menus with no group
     *
     * @param userId user id to get menus
     * @return OeMenu root with id -1 and name root and its children linked
     * @throws OeRpcException
     * @see OeGroupService
     * @see OeGroup
     * @see #setMenuChildren(OeMenu, Long[])
     */
    public OeMenu findByUserId(Long userId) throws OeRpcException {
        if (!OeUtil.equals(userId, executor.getUserId())) {
            OeGroupService oeGroupService = new OeGroupService(executor);
            Set<OeGroup> oeGroups = oeGroupService.findByUserId(userId);
            oeGroupService = null;
            Long[] oeGroupsId = new Long[oeGroups.size()];
            int i = 0;
            for (OeGroup oeGroup : oeGroups) {
                oeGroupsId[i] = oeGroup.getId();
                i++;
            }
            OeMenu root = new OeMenu(this);
            root.setName("root");
            root.setParentId(new Object[]{-1, ""});
            setMenuChildren(root, oeGroupsId);
            return root;
        } else {
            return loadOeMenus();
        }
    }

    /**
     * set menus children by getting group's menus with parent id and no group with parent id
     *
     * @param parentMenu root menu
     * @param oeGroupsId array of groups id
     * @throws OeRpcException
     * @see #findByGroupIdParentId(Long, Long...)
     * @see OeMenu
     */
    private void setMenuChildren(OeMenu parentMenu, Long[] oeGroupsId) throws OeRpcException {
        Set<OeMenu> menus = new HashSet<OeMenu>(10);
        menus.addAll(findByGroupIdParentId(parentMenu.getId(), oeGroupsId));
        menus.addAll(findByNoGroupParentId(parentMenu.getId()));
        parentMenu.setChildren(menus.toArray(new OeMenu[menus.size()]));
        for (OeMenu oeMenu : menus) {
            setMenuChildren(oeMenu, oeGroupsId);
        }
    }

    /**
     * find menus that haven't group id
     *
     * @param columns array of columns
     * @return set of {@link OeMenu}
     * @throws OeRpcException
     * @see OeCriteriaBuilder
     */
    public Set<OeMenu> findByNoGroup(String... columns) throws OeRpcException {
        OeCriteriaBuilder cb = new OeCriteriaBuilder();
        cb.column(_GROUPS_ID).eq(null);
        return (new HashSet<OeMenu>(super.find(cb, columns)));
    }

    /**
     * find menus that haven't no group id and have parent id
     * <p>
     * if <code>parentId</code> is null this method uses parent_id else uses parent_id.id
     *
     * @param parentId menu parent id
     * @param columns  array of columns
     * @return set of {@link OeMenu}
     * @throws OeRpcException
     * @see OeCriteriaBuilder
     * @see #find(OeCriteriaBuilder, String...)
     */
    public Set<OeMenu> findByNoGroupParentId(Long parentId, String... columns) throws OeRpcException {
        OeCriteriaBuilder cb = new OeCriteriaBuilder();
        cb.column(_GROUPS_ID).eq(null)
                .andColumn((parentId != null) ? _PARENT_ID_ID : _PARENT_ID)
                .eq(parentId);
        return (new HashSet<OeMenu>(super.find(cb, columns)));
    }

    /**
     * find menus by group(s) id
     *
     * @param groupId group id(s)
     * @return set of {@link OeMenu}
     * @throws OeRpcException
     * @see OeCriteriaBuilder
     * @see #find(OeCriteriaBuilder, String...)
     */
    public Set<OeMenu> findByGroupId(Long... groupId) throws OeRpcException {
        OeCriteriaBuilder cb = new OeCriteriaBuilder();
        cb.column(_GROUPS_ID_ID).in(groupId);
        return (new HashSet<OeMenu>(super.find(cb)));
    }

    /**
     * find menus by group id(s) and parent id
     *
     * @param parentId menu parent id
     * @param groupId  group id
     * @return set of menus
     * @throws OeRpcException
     * @see OeCriteriaBuilder
     * @see #find(OeCriteriaBuilder, String...)
     */
    public Set<OeMenu> findByGroupIdParentId(Long parentId, Long... groupId) throws OeRpcException {
        OeCriteriaBuilder cb = new OeCriteriaBuilder();
        cb.column(_GROUPS_ID_ID).in(groupId)
                .andColumn((parentId != null ? _PARENT_ID_ID : _PARENT_ID))
                .eq(parentId);
        return (new HashSet<OeMenu>(super.find(cb)));
    }

    /**
     * enum contains some functions for loading menus
     */
    public enum Fun {

        LOAD_MENUS("load_menus"),
        LOAD_MENUS_ROOT("load_menus_root");
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
