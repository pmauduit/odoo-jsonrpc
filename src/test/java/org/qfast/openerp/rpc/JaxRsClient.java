/*
 * Copyright 2016 QFast Ahmed El-mawaziny
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qfast.openerp.rpc;

import org.qfast.openerp.rpc.exception.OeRpcException;

//import javax.json.JsonValue;

/**
 * @author Ahmed El-mawaziny
 */
public class JaxRsClient {

    private static final String password = "123";
    private static final String username = "admin";
    private static final String database = "acu";
//    private static String sessionId = "f70c93a930874a9388721f81271bf298";
//    private static JsonObject context = Json.createObjectBuilder().build();

    public static void main(String[] args) throws OeRpcException {
//        Client client = ClientBuilder.newClient();
//        JsonObject params = Json.createObjectBuilder()
//                .add("db", database)
//                .add("login", username)
//                                .add("password", password)
//                .build();
//        JsonObject data = Json.createObjectBuilder()
//                .add("method", "call")
//                .add("params", params)
//                .build();
//
//        JsonObject response = client
//                .target("http://localhost:8069/web/session/authenticate")
//                .request()
//                .post(Entity.json(data), JsonObject.class);
//        System.out.println(response.get("error"));
//        if (response.containsKey("error")) {
//            throw new OeRpcException(response.getJsonObject("error"));
//        } else {
//
//            System.out.println(response);
//        }
//        client.close();
//        context.put(username, Json.createObjectBuilder()
//                .add("db", database)
//                .add("login", username)
//                .add("password", password).build());
//        System.out.println(context);
        
//        OeDatabase oeDatabase = new OeDatabase("http", "localhost", 8069, "admin");
//        System.out.println(oeDatabase.create("jsonDB2", true, "en_US", "123"));
//        System.out.println(Arrays.toString(oeDatabase.doList()));
//        OeDatabaseLanguage language = new OeDatabaseLanguage("http", "localhost", 8069);
//        System.out.println(Arrays.deepToString(language.doListLang()));
//        System.out.println(oeDatabase.doDuplicate("db8", "db82"));
//        OeExecutor executer = new OeExecutor("localhost", database, username, password);
//        System.out.println(executer.getVersion());
//        System.out.println(Arrays.toString(executer.searchReadMap("res.partner", null, null, null, null, null)));
//        JSONObject jSONObject = new JSONObject();
//        jSONObject.put("session_id", executer.getSessionId());
//        jSONObject.put("context", executer.getJsonContext());
//        JsonValue result = executer.execute(OeConst.JsonMenu.LOAD.getPath(), jSONObject);
////        System.out.println(result.getValueType().equals(JsonValue.ValueType.OBJECT));
////        System.out.println(result);
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Object[].class, new ObjectArrDeserializer())
//                .registerTypeAdapter(Integer.class, new IntegerDeserializer())
//                .registerTypeAdapter(String.class, new StringDeserializer())
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                .create();
//        OeMenu fromJson = gson.fromJson(result.toString(), OeMenu.class);
//        System.out.println("fromJson = " + fromJson);
        
//        System.out.println(executer.execute(name, OeMenuService.Fun.LOAD_MENUS.getName()));
    }
}
