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

/**
 * @author Ahmed El-mawaziny
 */
public class OeJEndPoint {

    public static final String _RS_WEB = "/web";
    public static final String _RS_WEB_CLIENT = "/webclient";
    public static final String _RS_DATA_SET = "/dataset";
    public static final String _RS_VIEW = "/view";
    public static final String _RS_TREE_VIEW = "/treeview";
    public static final String _RS_ACTION = "/action";
    public static final String _RS_EXPORT = "/export";
    public static final String _RS_PROXY = "/proxy";
    public static final String _RS_MENU = "/menu";
    public static final String _RS_DATABASE = "/database";
    public static final String _RS_SESSION = "/session";

    public enum Session {

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

        public static Session value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_SESSION + this.toString();
        }
    }

    public enum Database {

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

        public static Database value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_DATABASE + this.toString();
        }
    }

    public enum Menu {

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

        public static Menu value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_MENU + this.toString();
        }
    }

    public enum WebClient {

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

        public static WebClient value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_WEB_CLIENT + this.toString();
        }
    }

    public enum DataSet {

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

        public static DataSet value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_DATA_SET + this.toString();
        }
    }

    public enum View {

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

        public static View value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_VIEW + this.toString();
        }
    }

    public enum TreeView {

        ACTION {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        };

        public static TreeView value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_TREE_VIEW + this.toString();
        }
    }

    public enum Action {

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

        public static Action value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_ACTION + this.toString();
        }
    }

    public enum Export {

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

        public static Export value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_EXPORT + this.toString();
        }
    }

    public enum Proxy {

        LOAD {
            @Override
            public String toString() {
                return "/" + name().toLowerCase();
            }
        };

        public static Proxy value(String name) {
            return valueOf(name.toUpperCase());
        }

        public String getPath() {
            return _RS_WEB + _RS_PROXY + this.toString();
        }
    }
}
