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
import org.qfast.openerp.rpc.entity.OeMenu;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.json.OeExecutor;
import org.qfast.openerp.rpc.util.OeCriteriaBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.qfast.openerp.rpc.OeConst.OeFun.SEARCH;
import static org.qfast.openerp.rpc.OeConst.OeModel.MENUS;
import static org.qfast.openerp.rpc.util.OeUtil.convertJsonArray;

/**
 * @author Ahmed El-mawaziny on 3/6/16.
 */
public class OeMenuServiceTest extends AbstractBaseTest {

    private static OeMenuService service;

    @org.junit.BeforeClass
    public static void beforeClass() throws Exception {
        executor = OeExecutor.getInstance(PROTOCOL, HOST, PORT, DATABASE, USERNAME, PASSWORD);
        service = new OeMenuService(executor);
    }

    @org.junit.Test
    public void testGetName() throws Exception {
        assertEquals(MENUS.toString(), service.getName());
    }

    @org.junit.Test
    public void testGetExecutor() throws Exception {
        assertTrue(executor == service.getExecutor());
    }

    @org.junit.Test
    public void testFindById() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            Long id = ids[0];
            OeMenu oeMenu = service.findById(id);
            assertEquals(id, oeMenu.getId());
        }
    }

    @org.junit.Test
    public void testFindByIds() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            List<OeMenu> oeMenus = service.findByIds(ids, OeMenu._ID);
            assertEquals(ids.length, oeMenus.size());
            List<Long> idsAsList = Arrays.asList(ids);
            for (OeMenu oeMenu : oeMenus) {
                assertTrue(idsAsList.contains(oeMenu.getId()));
            }
        }
    }

    @org.junit.Test
    public void testFindAll() throws Exception {
        List<OeMenu> oeMenus = service.findAll();
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            assertEquals(ids.length, oeMenus.size());
            List<Long> idsAsList = Arrays.asList(ids);
            for (OeMenu oeMenu : oeMenus) {
                assertTrue(idsAsList.contains(oeMenu.getId()));
            }
        }
    }

    @org.junit.Test
    public void testFindFirst() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            Long min = ids[0];
            for (Long id : ids) {
                min = Math.min(min, id);
            }
            OeMenu first = service.findFirst();
            if (first != null) {
                assertEquals(min, first.getId());
            }
        }
    }

    @org.junit.Test
    public void testFindLast() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            Long max = ids[0];
            for (Long id : ids) {
                max = Math.max(max, id);
            }
            OeMenu last = service.findLast(OeMenu._ID);
            if (last != null) {
                assertEquals(max, last.getId());
            }
        }
    }

    @org.junit.Test
    public void testFindAny() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            OeMenu any = service.findAny(OeMenu._ID);
            List<Long> idsList = Arrays.asList(ids);
            assertTrue(idsList.contains(any.getId()));
        }
    }

    @org.junit.Test
    public void testFindRang() throws Exception {
        List<OeMenu> rang = service.findRang(0, 1, new String[]{OeMenu._ID});
        if (rang != null)
            assertTrue(rang.size() == 1);
    }

    @org.junit.Test
    public void testLoadMenus() throws Exception {
        OeMenu root = service.loadOeMenus();
        OeMenu rootByUserId = service.findByUserId(executor.getUserId());
        assertEquals(root, rootByUserId);
    }

    @org.junit.Test
    public void testFindByUserIdAllInOne() throws Exception {
        OeMenu root = service.loadOeMenus();
        Set<OeMenu> byNoGroup = service.findByNoGroup();
        Set<OeMenu> byNoGroupParentId = service.findByNoGroupParentId(null);
        Set<OeMenu> byGroupIdParentId = service.findByGroupIdParentId(null, 1L);
        List<OeMenu> byUserIdAllInOne = service.findByUserIdAllInOne(executor.getUserId());
        assertTrue(byUserIdAllInOne.containsAll(Arrays.asList(root.getChildren())));
        assertTrue(byUserIdAllInOne.containsAll(byGroupIdParentId));
        assertTrue(byUserIdAllInOne.containsAll(byNoGroupParentId));
        assertTrue(byUserIdAllInOne.containsAll(byNoGroup));
    }

    @org.junit.Test
    public void testFindByUserIdAllInOne2() throws Exception {
        OeMenu root = service.findByUserId(2L);
        Set<OeMenu> byNoGroup = service.findByNoGroup();
        Set<OeMenu> byNoGroupParentId = service.findByNoGroupParentId(null);
        Set<OeMenu> byGroupIdParentId = service.findByGroupIdParentId(null);
        List<OeMenu> byUserIdAllInOne = service.findByUserIdAllInOne(executor.getUserId());
        assertTrue(byUserIdAllInOne.containsAll(Arrays.asList(root.getChildren())));
        assertTrue(byUserIdAllInOne.containsAll(byGroupIdParentId));
        assertTrue(byUserIdAllInOne.containsAll(byNoGroupParentId));
        assertTrue(byUserIdAllInOne.containsAll(byNoGroup));
    }

    @org.junit.Test
    public void testFindByNoGroupParentId() throws OeRpcException {
        OeMenu root = service.loadOeMenus();
        List<OeMenu> rootChildren = Arrays.asList(root.getChildren());
        Set<OeMenu> menusNoGroupParentId = service.findByNoGroupParentId(null);
        assertTrue(rootChildren.containsAll(menusNoGroupParentId));
    }

    @org.junit.Test
    public void testFindByNoGroup() throws OeRpcException {
        Set<OeMenu> byNoGroup = service.findByNoGroup();
        Set<OeMenu> byNoGroupParentId = service.findByNoGroupParentId(null);
        assertTrue(byNoGroup.containsAll(byNoGroupParentId));
    }

    @org.junit.Test
    public void testCount() throws Exception {
        assertTrue(getIds().length == service.count());
    }

    @org.junit.Test
    public void testCountWithCriteriaBuilder() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            OeCriteriaBuilder cb = new OeCriteriaBuilder();
            cb.column(OeMenu._ID).eq(ids[0]);
            assertTrue(1 == service.count(cb));
        }
    }

    @org.junit.Test
    public void testCreate() throws Exception {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put(OeMenu._NAME, "Menu Test");
        Long id = service.create(values);
        assertNotNull(id);
        assertTrue(0L != id);

        //rollback that creation
        assertTrue(service.delete(id));
        assertFalse(Arrays.asList(getIds()).contains(id));
    }

    @org.junit.Test
    public void testUpdate() throws Exception {
        Long[] ids = getIds();
        String name = "updated lang";
        if (ids != null && ids.length != 0) {
            OeMenu oeMenu = service.findById(ids[0], OeMenu._NAME);
            String oldName = oeMenu.getName();
            Map<String, Object> values = new HashMap<String, Object>(1);
            values.put(OeMenu._NAME, name);
            Boolean updated = service.update(ids[0], values);
            assertTrue(updated);
            oeMenu = service.findById(ids[0], OeMenu._NAME);
            assertEquals(name, oeMenu.getName());

            //rollback the updated value
            values = new HashMap<String, Object>(1);
            values.put(OeMenu._NAME, oldName);
            updated = service.update(ids[0], values);
            assertTrue(updated);
            oeMenu = service.findById(ids[0], OeMenu._NAME);
            assertEquals(oldName, oeMenu.getName());
        }
    }

    private Long[] getIds() throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(new JsonArray());
        return convertJsonArray((JsonArray) executor.execute(MENUS.getName(), SEARCH.getName(), args), Long[].class);
    }
}