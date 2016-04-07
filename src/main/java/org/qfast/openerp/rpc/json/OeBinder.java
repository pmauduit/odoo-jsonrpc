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

package org.qfast.openerp.rpc.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.qfast.openerp.rpc.boundary.AbstractOeService;
import org.qfast.openerp.rpc.entity.AbstractOeEntity;
import org.qfast.openerp.rpc.entity.OeView;
import org.qfast.openerp.rpc.json.adaptor.BooleanDeserializer;
import org.qfast.openerp.rpc.json.adaptor.DateDeserializer;
import org.qfast.openerp.rpc.json.adaptor.FloatDeserializer;
import org.qfast.openerp.rpc.json.adaptor.IntegerDeserializer;
import org.qfast.openerp.rpc.json.adaptor.LongDeserializer;
import org.qfast.openerp.rpc.json.adaptor.MapDeserializer;
import org.qfast.openerp.rpc.json.adaptor.ObjectArrDeserializer;
import org.qfast.openerp.rpc.json.adaptor.OeViewDeserializer;
import org.qfast.openerp.rpc.json.adaptor.StringDeserializer;

import java.util.Date;
import java.util.Map;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

/**
 * @author Ahmed El-mawaziny
 */
public class OeBinder {

    @SuppressWarnings("unchecked")
    public static <T extends AbstractOeEntity, E extends AbstractOeService> T bind(String result, Class<T> tClaz, E oe)
            throws JsonSyntaxException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Object[].class, new ObjectArrDeserializer())
                .registerTypeAdapter(Integer.class, new IntegerDeserializer())
                .registerTypeAdapter(Long.class, new LongDeserializer())
                .registerTypeAdapter(String.class, new StringDeserializer())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .registerTypeAdapter(Float.class, new FloatDeserializer())
                .registerTypeAdapter(Boolean.class, new BooleanDeserializer())
                .registerTypeAdapter(Map.class, new MapDeserializer())
                .registerTypeAdapter(OeView.class, new OeViewDeserializer())
                .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
                .create();
        T instance = gson.fromJson(result, tClaz);
        instance.setOe(oe);
        return instance;
    }
}
