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

import com.google.gson.JsonArray;
import org.qfast.openerp.rpc.entity.OeGroup;
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
import static org.qfast.openerp.rpc.OeConst.OeModel.GROUPS;
import static org.qfast.openerp.rpc.util.OeUtil.convertJsonArray;

/**
 * @author Ahmed El-mawaziny on 3/6/16.
 */
public class OeGroupServiceTest {

    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final int PORT = 8069;
    private static final String PASSWORD = "1";
    private static final String USERNAME = "admin";
    private static final String DATABASE = "bpm";
    private static OeExecutor executor;
    private static OeGroupService service;

    @org.junit.BeforeClass
    public static void beforeClass() throws Exception {
        executor = OeExecutor.getInstance(PROTOCOL, HOST, PORT, DATABASE, USERNAME, PASSWORD);
        service = new OeGroupService(executor);
    }

    @org.junit.AfterClass
    public static void afterClass() throws Exception {
        executor.logout();
    }

    @org.junit.Test
    public void testGetName() throws Exception {
        assertEquals(GROUPS.toString(), service.getName());
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
            OeGroup oeGroup = service.findById(id);
            assertEquals(id, oeGroup.getId());
        }
    }

    @org.junit.Test
    public void testFindByIds() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            List<OeGroup> oeGroups = service.findByIds(ids, OeGroup._ID);
            assertEquals(ids.length, oeGroups.size());
            List<Long> idsAsList = Arrays.asList(ids);
            for (OeGroup oeGroup : oeGroups) {
                assertTrue(idsAsList.contains(oeGroup.getId()));
            }
        }
    }

    @org.junit.Test
    public void testFindAll() throws Exception {
        List<OeGroup> oeGroups = service.findAll(OeGroup._ID);
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            assertEquals(ids.length, oeGroups.size());
            List<Long> idsAsList = Arrays.asList(ids);
            for (OeGroup oeGroup : oeGroups) {
                assertTrue(idsAsList.contains(oeGroup.getId()));
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
            OeGroup first = service.findFirst();
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
            OeGroup last = service.findLast(OeGroup._ID);
            if (last != null) {
                assertEquals(max, last.getId());
            }
        }
    }

    @org.junit.Test
    public void testFindAny() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            OeGroup any = service.findAny(OeGroup._ID);
            List<Long> idsList = Arrays.asList(ids);
            assertTrue(idsList.contains(any.getId()));
        }
    }

    @org.junit.Test
    public void testFindRang() throws Exception {
        List<OeGroup> rang = service.findRang(0, 1, new String[]{OeGroup._ID});
        if (rang != null)
            assertTrue(rang.size() == 1);
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
            cb.column(OeGroup._ID).eq(ids[0]);
            assertTrue(1 == service.count(cb));
        }
    }

    @org.junit.Test
    public void testCreate() throws Exception {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put(OeGroup._NAME, "Test");
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
        String name = "Updated record";
        if (ids != null && ids.length != 0) {
            OeGroup oeGroup = service.findById(ids[0], OeGroup._NAME);
            String oldName = oeGroup.getName();
            Map<String, Object> values = new HashMap<String, Object>(1);
            values.put(OeGroup._NAME, name);
            Boolean updated = service.update(ids[0], values);
            assertTrue(updated);
            oeGroup = service.findById(ids[0], OeGroup._NAME);
            assertEquals(name, oeGroup.getName());

            //rollback the updated value
            values = new HashMap<String, Object>(1);
            values.put(OeGroup._NAME, oldName);
            updated = service.update(ids[0], values);
            assertTrue(updated);
            oeGroup = service.findById(ids[0], OeGroup._NAME);
            assertEquals(oldName, oeGroup.getName());
        }
    }

    @org.junit.Test
    public void testFindMenusByGroupId() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            Set<OeMenu> menusByGroupId = service.findMenusByGroupId(ids[0]);
            if (menusByGroupId != null && !menusByGroupId.isEmpty()) {
                OeMenu oeMenu = menusByGroupId.iterator().next();
                assertNotNull(oeMenu);
                assertNotNull(oeMenu.getName());
            }
        }
    }

    @org.junit.Test
    public void testFindByUserId() throws Exception {
        Set<OeGroup> oeGroups = service.findByUserId(1L);
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            List<Long> idsList = Arrays.asList(getIds());
            assertFalse(oeGroups.isEmpty());
            for (OeGroup oeGroup : oeGroups) {
                assertTrue(idsList.contains(oeGroup.getId()));
            }
        }
    }

    private Long[] getIds() throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(new JsonArray());
        return convertJsonArray((JsonArray) executor.execute(GROUPS.getName(), SEARCH.getName(), args), Long[].class);
    }
}