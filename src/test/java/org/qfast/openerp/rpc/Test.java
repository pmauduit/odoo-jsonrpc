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

package org.qfast.openerp.rpc;

import org.qfast.openerp.rpc.boundary.OePartnerService;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.json.OeExecutor;
import org.qfast.openerp.rpc.util.OeCriteriaBuilder;

//import org.apache.commons.io.FileUtils;

/**
 * @author Ahmed El-mawaziny
 */
@SuppressWarnings("unchecked")
public class Test {

    private static final String protocol = "http";
    private static final String host = "localhost";
    private static final int port = 8069;
    private static final String password = "1";
    private static final String username = "admin";
    private static final String database = "bpm";

    public static void main(String[] args) throws OeRpcException {
        OeExecutor exe = OeExecutor.getInstance(protocol, host, port, database, username, password);
        OePartnerService service = new OePartnerService(exe);
        OeCriteriaBuilder cb = new OeCriteriaBuilder();
        cb.column("id").eq(1);
        System.out.println(service.count(cb));
//        JsonArray argms = new JsonArray();
//        argms.add(exe.getJsonContext());
//        OeCriteriaBuilder cb = new OeCriteriaBuilder();
//        cb.column("name").ilike("a");
//        argms.add(OeUtil.parseAsJsonElement(cb.getCriteria()));
//        argms.add(new JsonArray());
//        Object result = exe.execute(OeConst.OeModel.PARTNERS.getName(), OeConst.OeFun.SEARCH.getName(), argms);
//        System.out.println(result);

//        Boolean unlike = exe.unlike(PARTNERS.getName(), 22);
//        System.out.println("unlike = " + unlike);
//        result = exe.execute(PARTNERS.getName(), SEARCH.getName(), argms);
//        System.out.println(result);
//        Map<String, Object> context = new HashMap<String, Object>(1);
//        context.put("lang", "ar_SY");
//        exe.setContext(context);
//        OeUserService service = new OeUserService(exe);
//        OeUser user = service.findById(1);
//        System.out.println(user);

//        UserService service = new UserService(exe);
//        User user2 = (User) service.findById(1);
//        System.out.println(user2.isEan13());
//        System.out.println(user2.getDisplayName());
//        System.out.println(user.getLanguage());
//        System.out.println(user.getMenus());
//        System.out.println(user.getLocale());
//        System.out.println(new OeMenuService(exe).findById(217));
//        Object[] ids = exe.searchReadMap(MENUS.getName(), cb.getCriteria());
//        System.out.println(Arrays.toString(ids));
//        Object[] result = exe.doRead(USERS.getName(), new Object[]{1}, new String[]{});
//        for (Object object : result) {
//            Map<String, Object> row = (Map<String, Object>) object;
//            System.out.println(Arrays.toString((Object[])row.get("partner_id")));
//            System.out.println(row.get("lang"));
//            System.out.println(row);
//        }
//        OeMenuService oeMenuService = new OeMenuService(exe);
//        Object loadMenus = oeMenuService.loadMenus();
//        Map<String, Object> root = (Map<String, Object>) loadMenus;
//        System.out.println(Arrays.toString(
//                (Object[]) ((Map<String, Object>) ((Object[]) ((Map<String, Object>) ((Object[]) root.get("children"))[2]).get("children"))[0]).get("parent_id")));
//        OeDatabase oeDatabase = new OeDatabase(protocol, host, port, null);
//        List<OeLocale> doList = oeDatabase.getOeLocales();
//        System.out.println(doList);
//        OeCriteriaBuilder cb = new OeCriteriaBuilder();
//        cb.column("state").in(new String[]{"installed", "uninstalled"});
//        Object[] result = exe.searchReadMap(OeConst.OeModel.MODULES.getName(), cb.getCriteria());
//        Long result1 = exe.count(OeConst.OeModel.MODULES.getName(), cb.getCriteria());
//        System.out.println(result1);
//        for (Object object : result) {
//            System.out.println(object);
//        }
//        Object execute = exe.execute("res.partner", "fields_view_get", false, "form");
//        System.out.println(execute);
//        System.out.println(oeDatabase.getServerVersion());
//        OeViewService oeViewService = new OeViewService(exe);
//        OeView oeView = oeViewService.getFieldsView(OeConst.OeModel.PARTNERS, OeConst.OeViewMode.FORM);
//        Document document = DocumentHelper.parseText(oeView.getArch());
//        Element rootElement = document.getRootElement();
//        loop(rootElement);
//        OeExecutor exe = new OeExecutor(host, database, username, password);
//        RequestServiceServicesService service = new RequestServiceServicesService(exe);
//        Integer[] serviceIds = new Integer[]{2, 3, 4, 5};
//        Object[][] attachmentIds = new Object[][]{{6, false, new Object[]{8, 9}}};
//        Long saveRequest = service.saveRequest(25, 26, serviceIds, attachmentIds);
//        System.out.println(service.findPreviousRequestDetails(25, true));
//        System.out.println(service.findById(8));
//        OeCompanyService service = new OeCompanyService(exe);
//        OeCompany findById = service.findById(1);
//        System.out.println(findById);
//        AnnouncementService service = new AnnouncementService(exe);
//        System.out.println(service.findLast(new OeCriteriaBuilder(), new String[]{}).getLink());
//        JSONArray params = new JSONArray();
//        params.put(1);
//        params.put("Ahmed");
//        JsonValue v =exe.execute(OeConst.OeModel.PARTNERS.getName(), "find_service_providers", params);
//        System.out.println(v);
//        ServiceRequestService service = new ServiceRequestService(exe);
//        Map<String, Object> values = new HashMap<String, Object>(3);
//        //[[6, False, [7]]]
//        Object[] o2 = new Object[]{3};
//        values.put(ServiceRequest._ATTACHMENTS, new Object[][]{{6, false, o2}});
//        Boolean create = service.write(2, values);
//        System.out.println("create = " + create);
//        OeAttachmentService service1 = new OeAttachmentService(exe);
//        Map<String, Object> values = new HashMap<String, Object>();
//        values.put(Attachment._FILE_NAME, "Test");
//        values.put(Attachment._MODEL, "bpm.messaging");
//        values.put(Attachment._COL_NAME, "Test");
//        File f = new File("/home/ahmed/Downloads/commons-io-2.4-bin.tar.gz");
//        values.put(Attachment._FILE_SIZE, f.length());
//        values.put(Attachment._DATA, convertFileToString(f));
//        Long create = service1.create(values);
//        System.out.println("create = " + create);
//        ServiceRequest findById = service.findById(1);
//        System.out.println("findById = " + findById);
//        OeAttachmentService service = new OeAttachmentService(exe);
//        OeAttachment findByPartnerId = service.findById(11);
//        System.out.println(findByPartnerId);
//        BpmServiceService service = new BpmServiceService(exe);
//        List<BpmService> findById = service.findByGroupId(1);
//        System.out.println("findById = " + findById);
//        ServiceGroupService service = new ServiceGroupService(exe);
//        ServiceGroup findById = service.findById(1);
//        System.out.println("findById = " + findById);
//        BpmMessagingService service = new BpmMessagingService(exe);
//        BpmMessaging findByPartnerId = service.findById(1);
//        System.out.println(findByPartnerId);
//        OePartnerService partnerService = new OePartnerService(exe);
//        System.out.println(exe.getContext());
//        OePartner findById = partnerService.findByPartnerCode("6");
//        System.out.println(findById);
//        exe.logout();
//        exe.updateContext("lang", "ar_SY");
//        OeMenuService service = new OeMenuService(exe);
//        System.out.println(service.loadMenus());
//        OeUser oeUser = new OeUserService(exe).findById(exe.getUserId());
//        System.out.println(oeUser.getMenus());
//        JSONArray argms = new JSONArray();
//        argms.put(exe.getJsonContext());
//        argms.put(new HashSet<>());
//        JsonValue execute = exe.execute(OeConst.OeModel.PARTNERS.getName(), "search_count", argms);
//        System.out.println(execute);
//        OeViewService oeViewService = new OeViewService(exe);
//        OeCriteriaBuilder cb = new OeCriteriaBuilder();
//        System.out.println(exe.count(OeConst.OeModel.MENUS, cb.getCriteria()));
//        Map<String, Object> vals = new HashMap<>();
//        vals.put("name", "Ahmed Hassan");
//        System.out.println(exe.create(OeConst.OeModel.PARTNERS.getName(), vals));
//        OeView oeView = oeViewService.getFieldsView(OeConst.OeModel.PARTNERS, OeConst.OeViewMode.FORM);
//        System.out.println(oeView);
//        OeView oeView = oeViewService.getOeView(OeConst.OeActionType.ACTION_WINDOW, 60);
//        System.out.println(oeView.getArch());
//        System.out.println(XML.toJSONObject(oeView.getArch()));
//        OeActionWindowService service = new OeActionWindowService(exe);
//        OeActionWindow action = service.findById(60);
//        System.out.println(action);
    }

//    private static void loop(Element rootElement) {
//        for (int i = 0, size = rootElement.nodeCount(); i < size; i++) {
//            Node node = rootElement.node(i);
//            if (node instanceof Element) {
//                System.out.println("Element: " + node.getName());
//                loop((Element) node);
//            } else {
//                System.out.println("Node: " + node.getName());
//            }
//        }
//        System.out.println(Arrays.deepToString(OeUtil.convertTupleStringToArray("('type', '=', 'receivable')")));
//        System.out.println(exe.getMajorVersion());
//        System.out.println(exe.getMinorVersion());
//        OeActionClientService service = new OeActionClientService(exe);
//        System.out.println(service.findById(98));
//        OeActionWindowService service = new OeActionWindowService(exe);
//        System.out.println(service.findById(60));
//        Map<String, Object> convertStringToMap = OeUtil.convertStringToMap("{              'default_model': 'res.users',              'default_res_id': uid,              'needaction_menu_ref': ['mail.mail_tomefeeds', 'mail.mail_starfeeds']            }");
//        convertStringToMap.putAll(context);
//        Object[] searchReadMap = exe.searchReadMap("mail.message", Collections.EMPTY_LIST, 0, 2, null, convertStringToMap);
//        Object[] doRead = exe.doRead("mail.message", searchReadMap, null, convertStringToMap);
//        System.out.println(Arrays.deepToString(doRead));
//    }
//    public static String convertFileToString(File file) {
//        String strFile = null;
//        try {
//            byte[] data = FileUtils.readFileToByteArray(file);//Convert any file, image or video into byte array
//            strFile = Base64.encodeBase64String(data);//Convert byte array into string
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return strFile;
//    }
}
