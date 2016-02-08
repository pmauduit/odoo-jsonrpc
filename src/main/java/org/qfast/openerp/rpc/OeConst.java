/*
 * Copyright 2014 QFast Ahmed El-mawaziny.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qfast.openerp.rpc;

import static org.qfast.openerp.rpc.util.OeUtil.isNULL;

/**
 * @author Ahmed El-mawaziny
 */
public class OeConst {

    public static final String _ID = "id", _NAME = "name";

    public enum OeOperator {

        EQUALS("="),
        ILIKE("ilike"),
        IN("in"),
        OR("|"),
        AND("&");

        private final String symble;

        private OeOperator(String operator) {
            this.symble = operator;
        }

        public String getSymble() {
            return symble;
        }

        @Override
        public String toString() {
            return symble;
        }
    }

    public enum OeModel {

        MODULES("ir.module.module"),
        HR_TIMESHEET_SHEET("hr_timesheet_sheet.sheet"),
        HR_ATTENDANCE("hr.attendance"),
        PARTNERS("res.partner"),
        COMPANY("res.company"),
        BPM_MESSAGING("bpm.messaging"),
        BPM_ANNOUNCEMENT("bpm.announcement"),
        SERVICE_GROUP("res.service.group"),
        BPM_SERVICE("res.service"),
        USERS("res.users"),
        GROUPS("res.groups"),
        MENUS("ir.ui.menu"),
        VIEWS("ir.ui.view"),
        ACTION_CLIENT("ir.actions.client"),
        ATTACHMENT("ir.attachment"),
        SERVICE_REQUEST("request.service"),
        REQUEST_SERVICE_SERVICES("request.service.services"),
        FINANCIAL_STATEMENT("financial.statement"),
        ACTION_WINDOW("ir.actions.act_window"),
        LANGUAGE("res.lang");

        private final String name;

        private OeModel(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static OeModel value(String name) {
            if (!isNULL(name)) {
                for (OeModel oeModel : values()) {
                    if (oeModel.name.equals(name)) {
                        return oeModel;
                    }
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum OeActionType {

        ACTION_CLIENT("ir.actions.client"),
        ACTION_WINDOW("ir.actions.act_window");
        private final String name;

        private OeActionType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static OeActionType value(String name) {
            if (!isNULL(name)) {
                for (OeActionType oeActionType : values()) {
                    if (oeActionType.name.equals(name)) {
                        return oeActionType;
                    }
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum OeViewMode {

        FORM("form"),
        KANBAN("kanban"),
        TREE("tree");

        private final String name;

        private OeViewMode(String name) {
            this.name = name;
        }

        public static OeViewMode value(String name) {
            if (!isNULL(name)) {
                return valueOf(name.toUpperCase());
            }
            return null;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum OeFieldType {

        BOOLEAN("boolean"),
        INTEGER("integer"),
        FLOAT("float"),
        CHAR("char"),
        TEXT("text"),
        BINARY("binary"),
        DATETIME("datetime"),
        DATE("date"),
        SELECTION("selection"),
        MANY2ONE("many2one"),
        ONE2MANY("one2many"),
        MANY2MANY("many2many"),
        HTML("html");

        private final String name;

        private OeFieldType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static OeFieldType value(String name) {
            if (!isNULL(name)) {
                return valueOf(name.toUpperCase());
            }
            return null;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum OeFun {

        LOGIN("login"),
        CREARE("create"),
        READ("read"),
        WRITE("write"),
        UNLINK("unlink"),
        SEARCH("search"),
        SEARCH_COUNT("search_count"),
        LIST("list"),
        COPY("copy"),
        EXECUTE("execute");

        private final String name;

        private OeFun(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum Direction {

        RTL("rtl"),
        LTR("ltr");

        private final String dir;

        private Direction(String dir) {
            this.dir = dir;
        }

        public String getDir() {
            return dir;
        }

        public static Direction value(String name) {
            if (!isNULL(name)) {
                return valueOf(name.toUpperCase());
            }
            return null;
        }

        @Override
        public String toString() {
            return dir;
        }
    }

    public enum OeAttr {

        STRING("string"),
        FOR("for"),
        CLASS("class");

        private final String name;

        private OeAttr(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static OeAttr value(String name) {
            return valueOf(name.toUpperCase());
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum OeViewElement {

        LABEL("label"),
        P("p"),
        SHEET("sheet"),
        GROUP("group"),
        NOTEBOOK("notebook");

        private final String name;

        private OeViewElement(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static OeViewElement value(String name) {
            return valueOf(name.toUpperCase());
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum JsonSession {

        GET_LANG_LIST, AUTHENTICATE, GET_SESSION_INFO, CHANGE_PASSWORD, SC_LIST,
        MODULES, SAVE_SESSION_ACTION, GET_SESSION_ACTION, CHECK, DESTROY;

        public static JsonSession value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return "session/" + this.toString();
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum JsonDatabase {

        GET_LIST, CREATE, DUPLICATE, DROP, CHANGE_PASSWORD;

        public static JsonDatabase value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return "database/" + this.toString();
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum JsonMenu {

        GET_USER_ROOTS, LOAD, LOAD_NEEDACTION, ACTION;

        public static JsonMenu value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return "menu/" + this.toString();
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum JsonWebClient {

        VERSION_INFO, CSSLIST, JSLIST, QWEBLIST, BOOTSTRAP_TRANSLATIONS,
        TRANSLATIONS;

        public static JsonWebClient value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return "webclient/" + this.toString();
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum JsonDataSet {

        SEARCH_READ, LOAD, CALL, CALL_KW, CALL_BUTTON, EXEC_WORKFLOW, RESEQUENCE;

        public static JsonDataSet value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return "dataset/" + this.toString();
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum JsonView {

        ADD_CUSTOM, UNDO_CUSTOM;

        public static JsonView value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return "view/" + this.toString();
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum JsonTreeView {

        ACTION;

        public static JsonTreeView value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return "treeview/" + this.toString();
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum JsonAction {

        LOAD, RUN;

        public static JsonAction value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return "action/" + this.toString();
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum JsonExport {

        FORMATS, GET_FIELDS, NAMELIST;

        public static JsonExport value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return "export/" + this.toString();
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum JsonProxy {

        LOAD;

        public static JsonProxy value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return "proxy/" + this.toString();
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}
