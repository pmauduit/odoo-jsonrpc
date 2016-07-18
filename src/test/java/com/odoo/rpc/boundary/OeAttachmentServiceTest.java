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
import com.odoo.rpc.entity.OeAttachment;
import com.odoo.rpc.exception.OeRpcException;
import com.odoo.rpc.json.OeExecutor;
import com.odoo.rpc.util.OeCriteriaBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.odoo.rpc.json.util.OeJUtil.convertJsonArray;
import static com.odoo.rpc.util.OeConst.OeFun.SEARCH;
import static com.odoo.rpc.util.OeConst.OeModel.ATTACHMENT;
import static com.odoo.rpc.util.OeConst.OeModel.PARTNERS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Ahmed El-mawaziny
 */
public class OeAttachmentServiceTest extends AbstractBoundaryTest {

    private static OeAttachmentService service;

    @org.junit.BeforeClass
    public static void beforeClass() throws Exception {
        executor = OeExecutor.getInstance(SCHEME, HOST, PORT, DATABASE, USERNAME, PASSWORD);
        service = new OeAttachmentService(executor);
    }

    @org.junit.Test
    public void testGetName() throws Exception {
        assertEquals(ATTACHMENT.toString(), service.getName());
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
            OeAttachment oeAttachment = service.findById(id);
            assertEquals(id, oeAttachment.getId());
        }
    }

    @org.junit.Test
    public void testFindByIds() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            List<OeAttachment> oeAttachments = service.findByIds(ids, OeAttachment._ID);
            assertEquals(ids.length, oeAttachments.size());
            List<Long> idsAsList = Arrays.asList(ids);
            for (OeAttachment oeAttachment : oeAttachments) {
                assertTrue(idsAsList.contains(oeAttachment.getId()));
            }
        }
    }

    @org.junit.Test
    public void testFindAll() throws Exception {
        List<OeAttachment> oeAttachments = service.findAll(OeAttachment._ID);
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            assertEquals(ids.length, oeAttachments.size());
            List<Long> idsAsList = Arrays.asList(ids);
            for (OeAttachment oeAttachment : oeAttachments) {
                assertTrue(idsAsList.contains(oeAttachment.getId()));
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
            OeAttachment first = service.findFirst();
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
            OeAttachment last = service.findLast(OeAttachment._ID);
            if (last != null) {
                assertEquals(max, last.getId());
            }
        }
    }

    @org.junit.Test
    public void testFindAny() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            OeAttachment any = service.findAny(OeAttachment._ID);
            List<Long> idsList = Arrays.asList(ids);
            assertTrue(idsList.contains(any.getId()));
        }
    }

    @org.junit.Test
    public void testFindRang() throws Exception {
        List<OeAttachment> rang = service.findRang(0, 1, new String[]{OeAttachment._ID});
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
            cb.column(OeAttachment._ID).eq(ids[0]);
            assertTrue(1 == service.count(cb));
        }
    }

    @org.junit.Test
    public void testCreate() throws Exception {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put(OeAttachment._NAME, "Test");
        values.put(OeAttachment._DATAS_FNAME, "Test");
        values.put(OeAttachment._MODEL, PARTNERS.getName());
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
            OeAttachment oeAttachment = service.findById(ids[0], OeAttachment._NAME);
            String oldName = oeAttachment.getName();
            Map<String, Object> values = new HashMap<String, Object>(1);
            values.put(OeAttachment._NAME, name);
            Boolean updated = service.update(ids[0], values);
            assertTrue(updated);
            oeAttachment = service.findById(ids[0], OeAttachment._NAME);
            assertEquals(name, oeAttachment.getName());

            //rollback the updated value
            values = new HashMap<String, Object>(1);
            values.put(OeAttachment._NAME, oldName);
            updated = service.update(ids[0], values);
            assertTrue(updated);
            oeAttachment = service.findById(ids[0], OeAttachment._NAME);
            assertEquals(oldName, oeAttachment.getName());
        }
    }

    private Long[] getIds() throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(new JsonArray());
        return convertJsonArray((JsonArray) executor.execute(ATTACHMENT.getName(), SEARCH.getName(), args), Long[].class);
    }
}