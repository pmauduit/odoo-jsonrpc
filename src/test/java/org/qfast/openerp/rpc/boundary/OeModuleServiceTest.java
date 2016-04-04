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
import org.qfast.openerp.rpc.entity.OeAttachment;
import org.qfast.openerp.rpc.entity.OeModule;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.json.OeExecutor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.qfast.openerp.rpc.OeConst.OeFun.SEARCH;
import static org.qfast.openerp.rpc.OeConst.OeModel.MODULES;
import static org.qfast.openerp.rpc.util.OeUtil.convertJsonArray;

/**
 * @author Ahmed El-mawaziny on 4/4/16.
 */
public class OeModuleServiceTest {

    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final int PORT = 8069;
    private static final String PASSWORD = "1";
    private static final String USERNAME = "admin";
    private static final String DATABASE = "bpm";
    private static OeExecutor executor;
    private static OeModuleService service;

    @org.junit.BeforeClass
    public static void beforeClass() throws Exception {
        executor = OeExecutor.getInstance(PROTOCOL, HOST, PORT, DATABASE, USERNAME, PASSWORD);
        service = new OeModuleService(executor);
    }

    @org.junit.AfterClass
    public static void afterClass() throws Exception {
        executor.logout();
    }

    @org.junit.Test
    public void getName() throws Exception {
        assertEquals(MODULES.toString(), service.getName());
    }

    @org.junit.Test
    public void getExecutor() throws Exception {
        assertTrue(executor == service.getExecutor());
    }

    @org.junit.Test
    public void install() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length > 0) {
            Long moduleId = ids[0];
            boolean installed = service.install(moduleId);
            assertTrue(installed);

            boolean uninstalled = service.uninstall(moduleId);
            assertTrue(uninstalled);
        }
    }

    @org.junit.Test
    public void findById() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            Long id = ids[0];
            OeModule oeModule = service.findById(id);
            assertEquals(id, oeModule.getId());
        }
    }

    @org.junit.Test
    public void testFindByIds() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            List<OeModule> oeModules = service.findByIds(ids, OeModule._ID);
            assertEquals(ids.length, oeModules.size());
            List<Long> idsAsList = Arrays.asList(ids);
            for (OeModule oeModule : oeModules) {
                assertTrue(idsAsList.contains(oeModule.getId()));
            }
        }
    }

    @org.junit.Test
    public void findAll() throws Exception {
        List<OeModule> oeModules = service.findAll(OeModule._ID);
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            assertEquals(ids.length, oeModules.size());
            List<Long> idsAsList = Arrays.asList(ids);
            for (OeModule oeModule : oeModules) {
                assertTrue(idsAsList.contains(oeModule.getId()));
            }
        }
    }

    @org.junit.Test
    public void findFirst() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            Long min = ids[0];
            for (Long id : ids) {
                min = Math.min(min, id);
            }
            OeModule first = service.findFirst();
            if (first != null) {
                assertEquals(min, first.getId());
            }
        }
    }

    @org.junit.Test
    public void findLast() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            Long max = ids[0];
            for (Long id : ids) {
                max = Math.max(max, id);
            }
            OeModule last = service.findLast(OeModule._ID);
            if (last != null) {
                assertEquals(max, last.getId());
            }
        }
    }

    @org.junit.Test
    public void findAny() throws Exception {
        Long[] ids = getIds();
        if (ids != null && ids.length != 0) {
            OeModule any = service.findAny(OeModule._ID);
            List<Long> idsList = Arrays.asList(ids);
            assertTrue(idsList.contains(any.getId()));
        }
    }

    @org.junit.Test
    public void testFindRang() throws Exception {
        List<OeModule> rang = service.findRang(0, 1, new String[]{OeModule._ID});
        if (rang != null)
            assertTrue(rang.size() == 1);
    }

    @org.junit.Test
    public void count() throws Exception {
        assertTrue(getIds().length == service.count());
    }

    @org.junit.Test
    public void create() throws Exception {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put(OeModule._NAME, "Test");
        Long id = service.create(values);
        assertNotNull(id);
        assertTrue(0L != id);

        //rollback that creation
        assertTrue(service.delete(id));
        assertFalse(Arrays.asList(getIds()).contains(id));
    }

    @org.junit.Test
    public void update() throws Exception {
        Long[] ids = getIds();
        String name = "Updated record";
        if (ids != null && ids.length != 0) {
            OeModule oeModule = service.findById(ids[0], OeModule._NAME);
            String oldName = oeModule.getName();
            Map<String, Object> values = new HashMap<String, Object>(1);
            values.put(OeModule._NAME, name);
            Boolean updated = service.update(ids[0], values);
            assertTrue(updated);
            oeModule = service.findById(ids[0], OeModule._NAME);
            assertEquals(name, oeModule.getName());

            //rollback the updated value
            values = new HashMap<String, Object>(1);
            values.put(OeAttachment._NAME, oldName);
            updated = service.update(ids[0], values);
            assertTrue(updated);
            oeModule = service.findById(ids[0], OeModule._NAME);
            assertEquals(oldName, oeModule.getName());
        }
    }

    private Long[] getIds() throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(new JsonArray());
        return convertJsonArray((JsonArray) executor.execute(MODULES.getName(), SEARCH.getName(), args), Long[].class);
    }

}