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

package com.odoo.rpc;

import static com.odoo.rpc.util.OeUtil.isNULL;

/**
 * @author Ahmed El-mawaziny
 */
public class OeConst {

    public static final String _COL_ID = "id", _COL_NAME = "name", _COL_CREATE_DATE = "create_date",
            _COL_WRITE_UID = "write_uid", _COL_CREATE_UID = "create_uid", _COL_DISPLAY_NAME = "display_name",
            _COL_LAST_UPDATE = "__last_update", _COL_WRITE_DATE = "write_date",
            _RS_WEB = "/web", _RS_WEB_CLIENT = "/webclient", _RS_DATA_SET = "/dataset", _RS_VIEW = "/view",
            _RS_TREE_VIEW = "/treeview", _RS_ACTION = "/action", _RS_EXPORT = "/export", _RS_PROXY = "/proxy",
            _RS_MENU = "/menu", _RS_DATABASE = "/database", _RS_SESSION = "/session";

    public enum OeOperator {

        EQUALS("="),
        ILIKE("ilike"),
        IN("in"),
        OR("|"),
        AND("&");

        private final String symbol;

        OeOperator(String operator) {
            this.symbol = operator;
        }

        public String getSymbol() {
            return symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }
    }

    public enum OeModel {

        MODULES("ir.module.module"),
        HR_TIME_SHEET_SHEET("hr_timesheet_sheet.sheet"),
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
        LANGUAGE("res.lang"),
        MESSAGES("mail.message");

        private final String name;

        OeModel(String name) {
            this.name = name;
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

        public String getName() {
            return name;
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

        OeActionType(String name) {
            this.name = name;
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

        public String getName() {
            return name;
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

        OeViewMode(String name) {
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

        OeFieldType(String name) {
            this.name = name;
        }

        public static OeFieldType value(String name) {
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

    public enum OeFun {

        LOGIN("login"),
        CREATE("create"),
        READ("read"),
        WRITE("write"),
        UNLINK("unlink"),
        SEARCH("search"),
        SEARCH_COUNT("search_count"),
        LIST("list"),
        COPY("copy"),
        EXECUTE("execute");

        private final String name;

        OeFun(String name) {
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

        Direction(String dir) {
            this.dir = dir;
        }

        public static Direction value(String name) {
            if (!isNULL(name)) {
                return valueOf(name.toUpperCase());
            }
            return null;
        }

        public String getDir() {
            return dir;
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

        OeAttr(String name) {
            this.name = name;
        }

        public static OeAttr value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getName() {
            return name;
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

        OeViewElement(String name) {
            this.name = name;
        }

        public static OeViewElement value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum SortType {
        ASC {
            @Override
            public String toString() {
                return " ASC";
            }
        }, DESC {
            @Override
            public String toString() {
                return " DESC";
            }
        }
    }

    public enum JsonSession {

        GET_LANG_LIST {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, AUTHENTICATE {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, GET_SESSION_INFO {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, CHANGE_PASSWORD {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, SC_LIST {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        },
        MODULES {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, SAVE_SESSION_ACTION {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, GET_SESSION_ACTION {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, CHECK {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, DESTROY {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        };

        public static JsonSession value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_SESSION + this.toString();
        }
    }

    public enum JsonDatabase {

        GET_LIST {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, CREATE {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, DUPLICATE {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, DROP {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, CHANGE_PASSWORD {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        };

        public static JsonDatabase value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_DATABASE + this.toString();
        }
    }

    public enum JsonMenu {

        GET_USER_ROOTS {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, LOAD {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, LOAD_NEED_ACTION {
            @Override
            public String toString() {
                return "/" + "load_needaction";
            }
        }, ACTION {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        };

        public static JsonMenu value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_MENU + this.toString();
        }
    }

    public enum JsonWebClient {

        VERSION_INFO {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, CSS_LIST {
            @Override
            public String toString() {
                return "/csslist";
            }
        }, JS_LIST {
            @Override
            public String toString() {
                return "/jslist";
            }
        }, Q_WEB_LIST {
            @Override
            public String toString() {
                return "/qweblist";
            }
        }, BOOTSTRAP_TRANSLATIONS {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        },
        TRANSLATIONS {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        };

        public static JsonWebClient value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_WEB_CLIENT + this.toString();
        }
    }

    public enum JsonDataSet {

        SEARCH_READ {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, LOAD {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, CALL {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, CALL_KW {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, CALL_BUTTON {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, EXEC_WORKFLOW {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, RE_SEQUENCE {
            @Override
            public String toString() {
                return "/resequence";
            }
        };

        public static JsonDataSet value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_DATA_SET + this.toString();
        }
    }

    public enum JsonView {

        ADD_CUSTOM {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, UNDO_CUSTOM {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        };

        public static JsonView value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_VIEW + this.toString();
        }
    }

    public enum JsonTreeView {

        ACTION {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        };

        public static JsonTreeView value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_TREE_VIEW + this.toString();
        }
    }

    public enum JsonAction {

        LOAD {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, RUN {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        };

        public static JsonAction value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_ACTION + this.toString();
        }
    }

    public enum JsonExport {

        FORMATS {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, GET_FIELDS {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        }, NAME_LIST {
            @Override
            public String toString() {
                return "/namelist";
            }
        };

        public static JsonExport value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_EXPORT + this.toString();
        }
    }

    public enum JsonProxy {

        LOAD {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        };

        public static JsonProxy value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_PROXY + this.toString();
        }
    }
}
