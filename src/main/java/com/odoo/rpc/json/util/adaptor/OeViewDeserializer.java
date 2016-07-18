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

package com.odoo.rpc.json.util.adaptor;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.odoo.rpc.entity.OeView;
import com.odoo.rpc.json.util.GsonFactory;
import com.odoo.rpc.json.util.OeJUtil;

import java.lang.reflect.Type;

/**
 * Gson deserializer implementation for {@link OeView}
 *
 * @author Ahmed El-mawaziny
 * @since 1.0
 * @see OeView
 */
public class OeViewDeserializer implements JsonDeserializer<OeView> {

    @Override
    public OeView deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json != null && !json.isJsonNull() && json.isJsonPrimitive()) {
            JsonPrimitive asJsonPrimitive = json.getAsJsonPrimitive();
            if (asJsonPrimitive.isBoolean()) {
                return null;
            } else if (asJsonPrimitive.isString()) {
                json = OeJUtil.parseAsJsonElement(json.getAsString());
            }
        }
        Gson gson = GsonFactory.createGsonBuilder().create();
        return gson.fromJson(json, OeView.class);
    }
}
