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
package org.qfast.openerp.rpc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.qfast.openerp.rpc.OeConst.OeOperator.AND;
import static org.qfast.openerp.rpc.OeConst.OeOperator.EQUALS;
import static org.qfast.openerp.rpc.OeConst.OeOperator.ILIKE;
import static org.qfast.openerp.rpc.OeConst.OeOperator.IN;
import static org.qfast.openerp.rpc.OeConst.OeOperator.OR;

/**
 * @author Ahmed El-mawaziny
 */
public final class OeCriteriaBuilder {

    private final List<Object> criteria = new ArrayList<Object>(10);
    private int i = 0;

    public Column column(String column) {
        return new Column(column);
    }

    public List<Object> getCriteria() {
        return criteria;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(criteria.toArray());
    }

    public final class Column {

        private final Object[] sc;

        public Column(String name) {
            sc = new Object[3];
            sc[0] = name;
        }

        public Logic eq(Object value) {
            sc[1] = EQUALS.getSymbol();
            setValue(value);
            return new Logic();
        }

        public Logic in(Object value) {
            sc[1] = IN.getSymbol();
            setValue(value);
            return new Logic();
        }

        public Logic ilike(Object value) {
            sc[1] = ILIKE.getSymbol();
            setValue(value);
            return new Logic();
        }

        private void setValue(Object value) {
            sc[2] = value != null ? value : false;
            criteria.add(sc);
        }
    }

    public final class Logic {

        public Column orColumn(String columnName) {
            criteria.add(i++, OR.getSymbol());
            return new Column(columnName);
        }

        public Column andColumn(String columnName) {
            criteria.add(i++, AND.getSymbol());
            return new Column(columnName);
        }
    }
}
