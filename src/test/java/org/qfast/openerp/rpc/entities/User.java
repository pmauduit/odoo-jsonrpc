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

package org.qfast.openerp.rpc.entities;

import org.qfast.openerp.rpc.boundry.UserService;
import org.qfast.openerp.rpc.entity.OeUser;

/**
 * @author Ahmed El-mawaziny on 2/13/16.
 */
public class User extends OeUser {

    private boolean ean13;

    public boolean isEan13() {
        UserService service = (UserService) oe;
        System.out.println(service.sayHello());
        return ean13;
    }

    public void setEan13(boolean ean13) {
        this.ean13 = ean13;
    }

    @Override
    public String toString() {
        return "User{" +
                "ean13=" + ean13 +
                '}';
    }
}
