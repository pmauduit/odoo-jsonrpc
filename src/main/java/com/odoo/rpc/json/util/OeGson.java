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

package com.odoo.rpc.json.util;

import com.google.gson.GsonBuilder;
import com.odoo.rpc.json.util.adaptor.BooleanDeserializer;
import com.odoo.rpc.json.util.adaptor.DateDeserializer;
import com.odoo.rpc.json.util.adaptor.FloatDeserializer;
import com.odoo.rpc.json.util.adaptor.IntegerDeserializer;
import com.odoo.rpc.json.util.adaptor.LongDeserializer;
import com.odoo.rpc.json.util.adaptor.MapDeserializer;
import com.odoo.rpc.json.util.adaptor.ObjectArrDeserializer;
import com.odoo.rpc.json.util.adaptor.StringDeserializer;

import java.util.Date;
import java.util.Map;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

/**
 * @author Ahmed El-mawaziny on 4/9/16.
 */
public final class OeGson {

    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder()
                .registerTypeAdapter(Object[].class, new ObjectArrDeserializer())
                .registerTypeAdapter(Integer.class, new IntegerDeserializer())
                .registerTypeAdapter(Long.class, new LongDeserializer())
                .registerTypeAdapter(String.class, new StringDeserializer())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .registerTypeAdapter(Float.class, new FloatDeserializer())
                .registerTypeAdapter(Boolean.class, new BooleanDeserializer())
                .registerTypeAdapter(Map.class, new MapDeserializer())
                .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES);
    }
}
