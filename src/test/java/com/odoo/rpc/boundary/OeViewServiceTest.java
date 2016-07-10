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
import com.odoo.rpc.entity.OeView;
import com.odoo.rpc.exception.OeRpcException;
import com.odoo.rpc.json.OeExecutor;
import com.odoo.rpc.util.OeCriteriaBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.odoo.rpc.OeConst.OeFun.SEARCH;
import static com.odoo.rpc.OeConst.OeModel.VIEWS;
import static com.odoo.rpc.json.util.OeJsonUtil.convertJsonArray;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Ahmed El-mawaziny on 4/9/16.
 */
public class OeViewServiceTest extends AbstractBaseTest {

    private static OeViewService service;

    @org.junit.BeforeClass
    public static void beforeClass() throws Exception {
        executor = OeExecutor.getInstance(PROTOCOL, HOST, PORT, DATABASE, USERNAME, PASSWORD);
        service = new OeViewService(executor);
    }

    @org.junit.Test
    public void testGetName() throws Exception {
        assertEquals(VIEWS.toString(), service.getName());
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
            OeView oeView = service.findById(id);
            assertEquals(id, oeView.getId());
        }
    }

    @org.junit.Test
    public void testFindByIds() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            List<OeView> oeViews = service.findByIds(ids, OeView._ID);
            assertEquals(ids.length, oeViews.size());
            List<Long> idsAsList = Arrays.asList(ids);
            for (OeView oeView : oeViews) {
                assertTrue(idsAsList.contains(oeView.getId()));
            }
        }
    }

    @org.junit.Test
    public void testFindAll() throws Exception {
        List<OeView> oeViews = service.findAll(OeView._ID);
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            assertEquals(ids.length, oeViews.size());
            List<Long> idsAsList = Arrays.asList(ids);
            for (OeView oeView : oeViews) {
                assertTrue(idsAsList.contains(oeView.getId()));
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
            OeView first = service.findFirst();
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
            OeView last = service.findLast(OeView._ID);
            if (last != null) {
                assertEquals(max, last.getId());
            }
        }
    }

    @org.junit.Test
    public void testFindAny() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            OeView any = service.findAny(OeView._ID);
            List<Long> idsList = Arrays.asList(ids);
            assertTrue(idsList.contains(any.getId()));
        }
    }

    @org.junit.Test
    public void testFindRang() throws Exception {
        List<OeView> rang = service.findRang(0, 1, new String[]{OeView._ID});
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
            cb.column(OeView._ID).eq(ids[0]);
            assertTrue(1 == service.count(cb));
        }
    }

    @org.junit.Test
    public void testCreate() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            Long selectOne = ids[0];
            OeView oeView = service.findById(selectOne);
            Map<String, Object> values = new HashMap<String, Object>();
            values.put(OeView._NAME, "View1");
            values.put(OeView._ARCH, oeView.getArch());
            values.put(OeView._MODEL, oeView.getModel());
            Long id = service.create(values);
            assertNotNull(id);
            assertTrue(0L != id);

            //rollback that creation
            assertTrue(service.delete(id));
            assertFalse(Arrays.asList(ids).contains(id));
        }
    }

    @org.junit.Test
    public void testUpdate() throws Exception {
        Long[] ids = getIds();
        String name = "updated lang";
        if (ids != null && ids.length != 0) {
            OeView oeView = service.findById(ids[0], OeView._NAME);
            String oldName = oeView.getName();
            Map<String, Object> values = new HashMap<String, Object>(1);
            values.put(OeView._NAME, name);
            Boolean updated = service.update(ids[0], values);
            assertTrue(updated);
            oeView = service.findById(ids[0], OeView._NAME);
            assertEquals(name, oeView.getName());

            //rollback the updated value
            values = new HashMap<String, Object>(1);
            values.put(OeView._NAME, oldName);
            updated = service.update(ids[0], values);
            assertTrue(updated);
            oeView = service.findById(ids[0], OeView._NAME);
            assertEquals(oldName, oeView.getName());
        }
    }

    @org.junit.Test
    public void testGetOeView() throws Exception {
        //This is how to use getOeView by type and id
//        OeView oeView = service.getOeView(OeConst.OeActionType.ACTION_CLIENT, 99L);
//        if (oeView != null) {
//            Long[] ids = getIds();
//            if (ids != null && ids.length != 0)
//                assertTrue(Arrays.asList(ids).contains(oeView.getId()));
//        }
    }

    private Long[] getIds() throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(new JsonArray());
        return convertJsonArray((JsonArray) executor.execute(VIEWS.getName(), SEARCH.getName(), args), Long[].class);
    }
}