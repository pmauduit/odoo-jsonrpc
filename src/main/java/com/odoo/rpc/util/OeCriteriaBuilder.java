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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.odoo.rpc.util.OeCriteriaBuilder.OeOperator.AND;
import static com.odoo.rpc.util.OeCriteriaBuilder.OeOperator.EQUALS;
import static com.odoo.rpc.util.OeCriteriaBuilder.OeOperator.ILIKE;
import static com.odoo.rpc.util.OeCriteriaBuilder.OeOperator.IN;
import static com.odoo.rpc.util.OeCriteriaBuilder.OeOperator.OR;

/**
 * OeCriteriaBuilder to create Odoo domain
 *
 * @author Ahmed El-mawaziny
 * @since 1.0
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

    public final class Column {

        private final Object[] sc;

        public Column(String name) {
            sc = new Object[3];
            sc[0] = name;
        }

        /**
         * sql equals '='
         *
         * @param value value to compare
         * @return Logic
         */
        public Logic eq(Object value) {
            sc[1] = EQUALS.getSymbol();
            setValue(value);
            return new Logic();
        }

        /**
         * sql 'in'
         *
         * @param value value to compare
         * @return Logic
         */
        public Logic in(Object value) {
            sc[1] = IN.getSymbol();
            setValue(value);
            return new Logic();
        }

        /**
         * sql ilike - like with ignore case
         *
         * @param value value to compare
         * @return Logic
         */
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

        /**
         * sql OR
         *
         * @param columnName column name
         * @return Column
         * @see Column
         */
        public Column orColumn(String columnName) {
            criteria.add(i++, OR.getSymbol());
            return new Column(columnName);
        }

        /**
         * sql AND
         *
         * @param columnName string column name
         * @return Column
         * @see Column
         */
        public Column andColumn(String columnName) {
            criteria.add(i++, AND.getSymbol());
            return new Column(columnName);
        }
    }
}
