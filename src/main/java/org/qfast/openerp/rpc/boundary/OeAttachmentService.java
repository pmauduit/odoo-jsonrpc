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

package org.qfast.openerp.rpc.boundary;

import org.qfast.openerp.rpc.entity.OeAttachment;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.json.OeExecutor;

import java.util.List;
import java.util.Map;

import static org.qfast.openerp.rpc.OeConst.OeModel.ATTACHMENT;

/**
 * Service to manage OpenERP {@link OeAttachment}
 *
 * @author Ahmed El-mawaziny
 * @see OeAttachment
 * @since 1.0
 */
public class OeAttachmentService extends AbstractOeService<OeAttachment> {

    public static final String name = ATTACHMENT.getName();
    private static final long serialVersionUID = 2405055031124590124L;

    public OeAttachmentService(OeExecutor executor) {
        super(executor, OeAttachment.class);
    }

    public <C extends OeAttachment> OeAttachmentService(OeExecutor executor, Class<C> model) {
        super(executor, model);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<OeAttachment> find(List<Object> sc, Integer offset, Integer limit, String order,
                                   Map<String, Object> context, String... columns) throws OeRpcException {
        return super.find(this, sc, offset, limit, order, context, columns);
    }
}
