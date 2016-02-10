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
package org.qfast.openerp.rpc.entity;

import org.qfast.openerp.rpc.OeConst.OeActionType;
import org.qfast.openerp.rpc.util.OeUtil;

import java.io.Serializable;

/**
 * @author Ahmed El-mawaziny
 */
public class OeAction implements Serializable {

    private static final long serialVersionUID = -1502331199963285133L;
    private Integer id;
    private OeActionType type;

    public OeAction() {
    }

    public OeAction(Integer id, OeActionType type) {
        this.id = id;
        this.type = type;
    }

    public OeAction(Integer id, String type) {
        this.id = id;
        this.type = OeActionType.value(type);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OeActionType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = OeActionType.value(type);
    }

    public void setType(OeActionType type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + OeUtil.hashCode(this.id);
        hash = 79 * hash + OeUtil.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && OeUtil.equals(this.id, ((OeAction) obj).id);
    }

    @Override
    public String toString() {
        return "OeAction{" + "id=" + id
                + ", type=" + type + '}';
    }
}