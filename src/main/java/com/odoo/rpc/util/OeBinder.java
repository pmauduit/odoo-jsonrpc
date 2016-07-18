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

package com.odoo.rpc.util;

import com.google.gson.Gson;
import com.odoo.rpc.boundary.AbstractOeService;
import com.odoo.rpc.entity.AbstractOeEntity;
import com.odoo.rpc.entity.OeView;
import com.odoo.rpc.json.util.GsonFactory;
import com.odoo.rpc.json.util.adaptor.OeViewDeserializer;

/**
 * OeBinder used for bind Odoo json response data in entities
 *
 * @author Ahmed El-mawaziny
 * @since 1.0
 */
public class OeBinder {

    /**
     * static method to bind json string in entity class
     *
     * @param result json data
     * @param tClaz  entity class extends from {@link AbstractOeEntity}
     * @param oe     service class extends from {@link AbstractOeService}
     * @param <T>    generic class extends {@link AbstractOeEntity}
     * @param <E>    generic class extends {@link AbstractOeService}
     * @return instance of entity class
     */
    @SuppressWarnings("unchecked")
    public static <T extends AbstractOeEntity, E extends AbstractOeService> T bind(String result, Class<T> tClaz, E oe) {
        Gson gson = GsonFactory.createGsonBuilder()
                .registerTypeAdapter(OeView.class, new OeViewDeserializer())
                .create();
        result = result.replace("u'", "'");
        T instance = gson.fromJson(result, tClaz);
        instance.setOe(oe);
        return instance;
    }
}
