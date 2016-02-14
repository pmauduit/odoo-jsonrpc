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

import org.qfast.openerp.rpc.entity.Attachment;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.json.OeExecutor;

import java.util.List;
import java.util.Map;

import static org.qfast.openerp.rpc.OeConst.OeModel.ATTACHMENT;

/**
 * OePartnerService for find OpenERP partner (res.partner) by findById, finAll
 * or custom search criteria
 *
 * @author Ahmed El-mawaziny
 * @since 1.0
 */
public class AttachmentService extends AbstractOeService<Attachment> {

    public static final String name = ATTACHMENT.getName();
    private static final long serialVersionUID = 2405055031124590124L;

    public AttachmentService(OeExecutor executor) {
        super(executor, Attachment.class);
    }

    public <C extends Attachment> AttachmentService(OeExecutor executor, Class<C> model) {
        super(executor, model);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Attachment> find(List<Object> sc, Integer offset, Integer limit, String order,
                                 Map<String, Object> context, String... columns) throws OeRpcException {
        return super.find(this, sc, offset, limit, order, context, columns);
    }
}
