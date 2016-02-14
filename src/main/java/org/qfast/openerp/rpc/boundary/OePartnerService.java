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

package org.qfast.openerp.rpc.boundary;

import org.qfast.openerp.rpc.entity.OePartner;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.json.OeExecutor;
import org.qfast.openerp.rpc.util.OeCriteriaBuilder;

import java.util.List;
import java.util.Map;

import static org.qfast.openerp.rpc.OeConst.OeModel.PARTNERS;

/**
 * OePartnerService for find OpenERP partner (res.partner) by findById, finAll
 * or custom search criteria
 *
 * @author Ahmed El-mawaziny
 * @see OePartner
 * @since 1.0
 */
public class OePartnerService extends AbstractOeService<OePartner> {

    public static final String name = PARTNERS.getName();
    private static final long serialVersionUID = -3212551405154989640L;

    public OePartnerService(OeExecutor executor) {
        super(executor, OePartner.class);
    }

    public <C extends OePartner> OePartnerService(OeExecutor executor, Class<C> model) {
        super(executor, model);
    }

    @Override
    public String getName() {
        return name;
    }

    public OePartner findByPartnerCode(String partnerCode) throws OeRpcException {
        OeCriteriaBuilder cb = new OeCriteriaBuilder();
        cb.column(OePartner._PARTNER_CODE).eq(partnerCode);
        return findFirst(cb);
    }

    @Override
    public List<OePartner> find(List<Object> sc, Integer offset, Integer limit, String order, Map<String, Object> context,
                                String... columns) throws OeRpcException {
        return super.find(this, sc, offset, limit, order, context, columns);
    }
}
