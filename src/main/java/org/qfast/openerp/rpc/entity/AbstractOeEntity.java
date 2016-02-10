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

import org.qfast.openerp.rpc.boundary.AbstractOeService;

import java.io.Serializable;

/**
 * @param <T>
 * @author Ahmed El-mawaziny
 */
public abstract class AbstractOeEntity<T extends AbstractOeService> implements Serializable {

    private static final long serialVersionUID = -832746926128259160L;
    protected T oe;

    public T getOe() {
        return oe;
    }

    public void setOe(T oe) {
        this.oe = oe;
    }
}
