/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qfast.openerp.rpc;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.qfast.openerp.rpc.entity.OeMenu;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.json.OeExecutor;
import org.qfast.openerp.rpc.json.adaptor.IntegerDeserializer;
import org.qfast.openerp.rpc.json.adaptor.ObjectArrDeserializer;
import org.qfast.openerp.rpc.json.adaptor.StringDeserializer;

import javax.json.JsonValue;

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
//        System.out.println(oeDatabase.doCreate("jsonDB2", true, "en_US", "123"));
//        System.out.println(Arrays.toString(oeDatabase.doList()));
//        OeDatabaseLanguage language = new OeDatabaseLanguage("http", "localhost", 8069);
//        System.out.println(Arrays.deepToString(language.doListLang()));
//        System.out.println(oeDatabase.doDuplicate("db8", "db82"));
        OeExecutor executer = new OeExecutor("localhost", database, username, password);
//        System.out.println(executer.getVersion());
//        System.out.println(Arrays.toString(executer.doSearchMap("res.partner", null, null, null, null, null)));
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("session_id", executer.getSessionId());
        jSONObject.put("context", executer.getJSONContext());
        JsonValue result = executer.execute(OeConst.JsonMenu.LOAD.getPath(), jSONObject);
//        System.out.println(result.getValueType().equals(JsonValue.ValueType.OBJECT));
//        System.out.println(result);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Object[].class, new ObjectArrDeserializer())
                .registerTypeAdapter(Integer.class, new IntegerDeserializer())
                .registerTypeAdapter(String.class, new StringDeserializer())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        OeMenu fromJson = gson.fromJson(result.toString(), OeMenu.class);
        System.out.println("fromJson = " + fromJson);
        
//        System.out.println(executer.execute(name, OeMenuService.Fun.LOAD_MENUS.getName()));
    }
}
