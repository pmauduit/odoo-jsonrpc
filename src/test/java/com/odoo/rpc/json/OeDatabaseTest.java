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

package com.odoo.rpc.json;

import com.odoo.rpc.AbstractBaseTest;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Ahmed El-mawaziny
 */
public class OeDatabaseTest extends AbstractBaseTest {

    private static OeDatabase oeDatabase;

    @org.junit.BeforeClass
    public static void beforeClass() throws Exception {
        oeDatabase = OeDatabase.getInstance(SCHEME, HOST, PORT, "admin");
    }

    @Test
    public void doCreate() throws Exception {
        String dbName = "test_json_rpc";

        boolean succeeded = oeDatabase.doCreate(dbName, false, "en_US", PASSWORD);
        assertTrue(succeeded);

        String[] databases = oeDatabase.doList();
        assertTrue(Arrays.asList(databases).contains(dbName));

        succeeded = oeDatabase.doDuplicate(dbName, dbName + "1");
        assertTrue(succeeded);

        databases = oeDatabase.doList();
        assertTrue(Arrays.asList(databases).contains(dbName + "1"));

        succeeded = oeDatabase.doDrop(dbName);
        assertTrue(succeeded);

        succeeded = oeDatabase.doDrop(dbName + "1");
        assertTrue(succeeded);

        databases = oeDatabase.doList();
        assertFalse(Arrays.asList(databases).contains(dbName));

        databases = oeDatabase.doList();
        assertFalse(Arrays.asList(databases).contains(dbName + "1"));
    }
}