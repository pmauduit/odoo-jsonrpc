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

import static com.odoo.rpc.util.OeUtil.isNULL;

/**
 * @author Ahmed El-mawaziny
 */
public class OeConst {

    public static final String _COL_ID = "id";
    public static final String _COL_NAME = "name";
    public static final String _COL_CREATE_DATE = "create_date";
    public static final String _COL_WRITE_UID = "write_uid";
    public static final String _COL_CREATE_UID = "create_uid";
    public static final String _COL_DISPLAY_NAME = "display_name";
    public static final String _COL_LAST_UPDATE = "__last_update";
    public static final String _COL_WRITE_DATE = "write_date";

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

}
