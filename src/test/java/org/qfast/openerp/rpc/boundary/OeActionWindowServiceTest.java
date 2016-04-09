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
import org.qfast.openerp.rpc.entity.OeActionWindow;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.json.OeExecutor;
import org.qfast.openerp.rpc.util.OeCriteriaBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.qfast.openerp.rpc.OeConst.OeFun.SEARCH;
import static org.qfast.openerp.rpc.OeConst.OeModel.ACTION_WINDOW;
import static org.qfast.openerp.rpc.json.util.OeJsonUtil.convertJsonArray;

/**
 * @author Ahmed El-mawaziny on 3/2/16.
 */
public class OeActionWindowServiceTest extends AbstractBaseTest {

    private static OeActionWindowService service;

    @org.junit.BeforeClass
    public static void beforeClass() throws Exception {
        executor = OeExecutor.getInstance(PROTOCOL, HOST, PORT, DATABASE, USERNAME, PASSWORD);
        service = new OeActionWindowService(executor);
    }

    @org.junit.Test
    public void testGetName() throws Exception {
        assertEquals(ACTION_WINDOW.toString(), service.getName());
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
            OeActionWindow oeActionWindow = service.findById(ids[0]);
            assertEquals(id, oeActionWindow.getId());
        }
    }

    @org.junit.Test
    public void testFindByIds() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            List<OeActionWindow> oeActionWindows = service.findByIds(ids, OeActionWindow._ID);
            assertEquals(ids.length, oeActionWindows.size());
            List<Long> idsAsList = Arrays.asList(ids);
            for (OeActionWindow oeActionWindow : oeActionWindows) {
                assertTrue(idsAsList.contains(oeActionWindow.getId()));
            }
        }
    }

    @org.junit.Test
    public void testFindAll() throws Exception {
        List<OeActionWindow> oeActionWindows = service.findAll(OeActionWindow._ID);
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            assertEquals(ids.length, oeActionWindows.size());
            List<Long> idsAsList = Arrays.asList(ids);
            for (OeActionWindow oeActionWindow : oeActionWindows) {
                assertTrue(idsAsList.contains(oeActionWindow.getId()));
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
            OeActionWindow first = service.findFirst();
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
            OeActionWindow last = service.findLast(OeActionWindow._ID);
            if (last != null) {
                assertEquals(max, last.getId());
            }
        }
    }

    @org.junit.Test
    public void testFindAny() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            OeActionWindow any = service.findAny(OeActionWindow._ID);
            List<Long> idsList = Arrays.asList(ids);
            assertTrue(idsList.contains(any.getId()));
        }
    }

    @org.junit.Test
    public void testFindRang() throws Exception {
        List<OeActionWindow> rang = service.findRang(0, 1, new String[]{OeActionWindow._ID});
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
            cb.column(OeActionWindow._ID).eq(ids[0]);
            assertTrue(1 == service.count(cb));
        }
    }

    @org.junit.Test
    public void testCreate() throws Exception {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put(OeActionWindow._NAME, "Test");
        values.put(OeActionWindow._TYPE, ACTION_WINDOW.getName());
        values.put(OeActionWindow._RES_MODEL, ACTION_WINDOW.getName());
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
            OeActionWindow oeActionWindow = service.findById(ids[0], OeActionWindow._NAME);
            String oldName = oeActionWindow.getName();
            Map<String, Object> values = new HashMap<String, Object>(1);
            values.put(OeActionWindow._NAME, name);
            Boolean updated = service.update(ids[0], values);
            assertTrue(updated);
            oeActionWindow = service.findById(ids[0], OeActionWindow._NAME);
            assertEquals(name, oeActionWindow.getName());

            //rollback the updated value
            values = new HashMap<String, Object>(1);
            values.put(OeActionWindow._NAME, oldName);
            updated = service.update(ids[0], values);
            assertTrue(updated);
            oeActionWindow = service.findById(ids[0], OeActionWindow._NAME);
            assertEquals(oldName, oeActionWindow.getName());
        }
    }

    private Long[] getIds() throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(new JsonArray());
        return convertJsonArray((JsonArray) executor.execute(service.getName(), SEARCH.getName(), args), Long[].class);
    }
}