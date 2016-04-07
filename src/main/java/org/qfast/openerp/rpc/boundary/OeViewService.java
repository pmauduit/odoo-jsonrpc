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

import org.qfast.openerp.rpc.OeConst.OeActionType;
import org.qfast.openerp.rpc.OeConst.OeModel;
import org.qfast.openerp.rpc.OeConst.OeViewMode;
import org.qfast.openerp.rpc.entity.OeAction;
import org.qfast.openerp.rpc.entity.OeActionClient;
import org.qfast.openerp.rpc.entity.OeActionWindow;
import org.qfast.openerp.rpc.entity.OeView;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.json.OeBinder;
import org.qfast.openerp.rpc.json.OeExecutor;
import org.qfast.openerp.rpc.json.util.OeJsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.qfast.openerp.rpc.OeConst.OeModel.ACTION_CLIENT;
import static org.qfast.openerp.rpc.OeConst.OeModel.ACTION_WINDOW;
import static org.qfast.openerp.rpc.OeConst.OeModel.VIEWS;
import static org.qfast.openerp.rpc.OeConst.OeViewMode.FORM;
import static org.qfast.openerp.rpc.boundary.OeViewService.Fun.FIELDS_VIEW_GET;

/**
 * @author Ahmed El-mawaziny
 */
public class OeViewService extends AbstractOeService<OeView> {

    public static final String name = VIEWS.getName();
    private static final long serialVersionUID = 6031051003954410561L;

    public OeViewService(OeExecutor executor) {
        super(executor, OeView.class);
    }

    public <C extends OeView> OeViewService(OeExecutor executor, Class<C> model) {
        super(executor, model);
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @param oeAction
     * @return
     * @throws OeRpcException
     */
    public OeView getOeView(OeAction oeAction) throws OeRpcException {
        return getOeView(oeAction.getType(), oeAction.getId());
    }

    /**
     * @param actionType
     * @param actionId
     * @return
     * @throws OeRpcException
     */
    public OeView getOeView(OeActionType actionType, Long actionId) throws OeRpcException {
        return getOeView(actionType.getName(), actionId);
    }

    /**
     * @param actionType
     * @param actionId
     * @return
     * @throws OeRpcException
     */
    public OeView getOeView(String actionType, Long actionId) throws OeRpcException {
        OeModel modelName;
        OeViewMode viewMode = FORM;
        if (actionType.equals(ACTION_WINDOW.getName())) {
            OeActionWindowService service = new OeActionWindowService(executor);
            OeActionWindow action = service.findById(actionId);
            modelName = action.getResOeModel();
            viewMode = action.getOeViewModes().get(0);
        } else if (actionType.equals(ACTION_CLIENT.getName())) {
            OeActionClientService service = new OeActionClientService(executor);
            OeActionClient action = service.findById(actionId);
            modelName = action.getOeModel();
        } else {
            throw new UnsupportedOperationException(actionType + " is not supported yet");
        }
        return getFieldsView(modelName, viewMode);
    }

    /**
     * @param modelName
     * @param viewId
     * @param viewMode
     * @param context
     * @return
     * @throws OeRpcException
     */
    public OeView getFieldsView(String modelName, Integer viewId, String viewMode, Map<String, Object> context)
            throws OeRpcException {
        List<Object> args = new ArrayList<Object>(3);
        args.add(viewId);
        args.add(((viewMode == null) ? FORM.getName() : viewMode));
        args.add(OeJsonUtil.parseAsJsonElement(context));
        Object result = executor.execute(modelName, FIELDS_VIEW_GET.getName(), args, false);
        return OeBinder.bind(result.toString(), OeView.class, this);
    }

    /**
     * @param modelName
     * @param viewMode
     * @param context
     * @return
     * @throws OeRpcException
     */
    public OeView getFieldsView(String modelName, String viewMode, Map<String, Object> context) throws OeRpcException {
        return getFieldsView(modelName, null, viewMode, context);
    }

    /**
     * @param modelName
     * @param viewMode
     * @return
     * @throws OeRpcException
     */
    public OeView getFieldsView(String modelName, String viewMode) throws OeRpcException {
        return getFieldsView(modelName, viewMode, executor.getContext());
    }

    /**
     * @param oeModel
     * @param viewId
     * @param viewMode
     * @param context
     * @return
     * @throws OeRpcException
     */
    public OeView getFieldsView(OeModel oeModel, Integer viewId, OeViewMode viewMode, Map<String, Object> context)
            throws OeRpcException {
        return getFieldsView(oeModel.getName(), viewId, viewMode.getName(), context);
    }

    /**
     * @param oeModel
     * @param viewMode
     * @param context
     * @return
     * @throws OeRpcException
     */
    public OeView getFieldsView(OeModel oeModel, OeViewMode viewMode, Map<String, Object> context) throws OeRpcException {
        return getFieldsView(oeModel.getName(), viewMode.getName(), context);
    }

    /**
     * @param oeModel
     * @param viewMode
     * @return
     * @throws OeRpcException
     */
    public OeView getFieldsView(OeModel oeModel, OeViewMode viewMode) throws OeRpcException {
        return getFieldsView(oeModel, viewMode, executor.getContext());
    }

    /**
     * @param oeModel
     * @param viewId
     * @param viewMode
     * @return
     * @throws OeRpcException
     */
    public OeView getFieldsView(OeModel oeModel, Integer viewId, OeViewMode viewMode) throws OeRpcException {
        return getFieldsView(oeModel, viewId, viewMode, executor.getContext());
    }

    @Override
    public List<OeView> find(List<Object> sc, Integer offset, Integer limit, String order, Map<String, Object> context,
                             String... columns) throws OeRpcException {
        return super.find(this, sc, offset, limit, order, context, columns);
    }

    public enum Fun {

        FIELDS_VIEW_GET("fields_view_get"),
        FIELDS_GET("fields_get");
        private final String name;

        Fun(String name) {
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
}
