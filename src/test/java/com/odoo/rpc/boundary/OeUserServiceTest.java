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

import com.google.gson.JsonArray;
import com.odoo.rpc.entity.OeUser;
import com.odoo.rpc.exception.OeRpcException;
import com.odoo.rpc.json.OeExecutor;
import com.odoo.rpc.util.OeCriteriaBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.odoo.rpc.json.util.OeJUtil.convertJsonArray;
import static com.odoo.rpc.util.OeConst.OeFun.SEARCH;
import static com.odoo.rpc.util.OeConst.OeModel.USERS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Ahmed El-mawaziny
 */
public class OeUserServiceTest extends AbstractBoundaryTest {

    private static OeUserService service;

    @org.junit.BeforeClass
    public static void beforeClass() throws Exception {
        executor = OeExecutor.getInstance(SCHEME, HOST, PORT, DATABASE, USERNAME, PASSWORD);
        service = new OeUserService(executor);
    }

    @org.junit.Test
    public void testGetName() throws Exception {
        assertEquals(USERS.toString(), service.getName());
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
            OeUser oeUser = service.findById(id);
            assertEquals(id, oeUser.getId());
        }
    }

    @org.junit.Test
    public void testFindByIds() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            List<OeUser> oeUsers = service.findByIds(ids, OeUser._ID);
            assertEquals(ids.length, oeUsers.size());
            List<Long> idsAsList = Arrays.asList(ids);
            for (OeUser oeUser : oeUsers) {
                assertTrue(idsAsList.contains(oeUser.getId()));
            }
        }
    }

    @org.junit.Test
    public void testFindAll() throws Exception {
        List<OeUser> oeUsers = service.findAll(OeUser._ID);
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            assertEquals(ids.length, oeUsers.size());
            List<Long> idsAsList = Arrays.asList(ids);
            for (OeUser oeUser : oeUsers) {
                assertTrue(idsAsList.contains(oeUser.getId()));
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
            OeUser first = service.findFirst();
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
            OeUser last = service.findLast(OeUser._ID);
            if (last != null) {
                assertEquals(max, last.getId());
            }
        }
    }

    @org.junit.Test
    public void testFindAny() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            OeUser any = service.findAny(OeUser._ID);
            List<Long> idsList = Arrays.asList(ids);
            assertTrue(idsList.contains(any.getId()));
        }
    }

    @org.junit.Test
    public void testFindRang() throws Exception {
        List<OeUser> rang = service.findRang(0, 1, new String[]{OeUser._ID});
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
            cb.column(OeUser._ID).eq(ids[0]);
            assertTrue(1 == service.count(cb));
        }
    }

    @org.junit.Test
    public void testCreate() throws Exception {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put(OeUser._NAME, "User1");
        values.put(OeUser._LOGIN, "User1");
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
            OeUser oeUser = service.findById(ids[0], OeUser._NAME);
            String oldName = oeUser.getName();
            Map<String, Object> values = new HashMap<String, Object>(1);
            values.put(OeUser._NAME, name);
            Boolean updated = service.update(ids[0], values);
            assertTrue(updated);
            oeUser = service.findById(ids[0], OeUser._NAME);
            assertEquals(name, oeUser.getName());

            //rollback the updated value
            values = new HashMap<String, Object>(1);
            values.put(OeUser._NAME, oldName);
            updated = service.update(ids[0], values);
            assertTrue(updated);
            oeUser = service.findById(ids[0], OeUser._NAME);
            assertEquals(oldName, oeUser.getName());
        }
    }

    private Long[] getIds() throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(new JsonArray());
        return convertJsonArray((JsonArray) executor.execute(USERS.getName(), SEARCH.getName(), args), Long[].class);
    }
}