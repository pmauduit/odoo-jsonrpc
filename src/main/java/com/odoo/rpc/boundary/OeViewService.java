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

package com.odoo.rpc.boundary;

import com.odoo.rpc.entity.OeAction;
import com.odoo.rpc.entity.OeActionClient;
import com.odoo.rpc.entity.OeActionWindow;
import com.odoo.rpc.entity.OeView;
import com.odoo.rpc.exception.OeRpcException;
import com.odoo.rpc.json.OeExecutor;
import com.odoo.rpc.json.util.OeJUtil;
import com.odoo.rpc.util.OeBinder;
import com.odoo.rpc.util.OeConst.OeActionType;
import com.odoo.rpc.util.OeConst.OeModel;
import com.odoo.rpc.util.OeConst.OeViewMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.odoo.rpc.boundary.OeViewService.Fun.FIELDS_VIEW_GET;
import static com.odoo.rpc.util.OeConst.OeModel.ACTION_CLIENT;
import static com.odoo.rpc.util.OeConst.OeModel.ACTION_WINDOW;
import static com.odoo.rpc.util.OeConst.OeModel.VIEWS;
import static com.odoo.rpc.util.OeConst.OeViewMode.FORM;

/**
 * OeViewService for find Odoo view (ir.ui.view) by findById, finAll
 * or custom search criteria
 *
 * @author Ahmed El-mawaziny
 * @see OeView
 * @since 1.0
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
     * getting {@link OeView} by {@link OeAction}
     *
     * @param oeAction {@link OeAction} "ir.actions.client" or "ir.actions.act_window"
     * @return OeView
     * @throws OeRpcException if Odoo response with error
     * @see #getOeView(OeActionType, Long)
     */
    public OeView getOeView(OeAction oeAction) throws OeRpcException {
        return getOeView(oeAction.getType(), oeAction.getId());
    }

    /**
     * getting {@link OeView} by {@link OeActionType} and action id
     *
     * @param actionType action type "ir.actions.client" or "ir.actions.act_window"
     * @param actionId   action id
     * @return OeView
     * @throws OeRpcException if Odoo response with error
     * @see #getOeView(String, Long)
     */
    public OeView getOeView(OeActionType actionType, Long actionId) throws OeRpcException {
        return getOeView(actionType.getName(), actionId);
    }

    /**
     * getting action by action type "ir.actions.client" or "ir.actions.act_window" and action id
     * <p>
     * check if action type is "ir.actions.act_window" use {@link OeActionWindowService}
     * if action type is "ir.actions.client" use {@link OeActionClientService}
     * otherwise throw {@link UnsupportedOperationException}
     *
     * @param actionType action type "ir.actions.client" or "ir.actions.act_window"
     * @param actionId   action id
     * @return OeView
     * @throws OeRpcException                if Odoo response with error
     * @throws UnsupportedOperationException if action type unknown
     */
    public OeView getOeView(String actionType, Long actionId) throws OeRpcException {
        String modelName;
        OeViewMode viewMode = FORM;
        if (actionType.equals(ACTION_WINDOW.getName())) {
            OeActionWindowService service = new OeActionWindowService(executor);
            OeActionWindow action = service.findById(actionId);
            modelName = action.getResModel();
            viewMode = action.getOeViewModes().get(0);
        } else if (actionType.equals(ACTION_CLIENT.getName())) {
            OeActionClientService service = new OeActionClientService(executor);
            OeActionClient action = service.findById(actionId);
            modelName = action.getResModel();
        } else {
            throw new UnsupportedOperationException(actionType + " is not supported yet");
        }
        return getFieldsView(modelName, viewMode);
    }

    /**
     * Calling fields_view_get function in Odoo
     *
     * @param modelName Odoo model name
     * @param viewId    view id
     * @param viewMode  view mode FORM, KANBAN, ..etc and if its null the default value form
     * @param context   Odoo context
     * @return OeView
     * @throws OeRpcException if Odoo response with error
     */
    public OeView getFieldsView(String modelName, Long viewId, String viewMode, Map<String, Object> context)
            throws OeRpcException {
        List<Object> args = new ArrayList<Object>(3);
        args.add(viewId);
        args.add(((viewMode == null) ? FORM.getName() : viewMode));
        args.add(OeJUtil.parseAsJsonElement(context));
        Object result = executor.execute(modelName, FIELDS_VIEW_GET.getName(), args, false);
        return OeBinder.bind(result.toString(), OeView.class, this);
    }

    /**
     * Wrapper for {@link #getFieldsView(String, Long, String, Map)}
     *
     * @param modelName Odoo model name
     * @param viewMode  view mode FORM, KANBAN, ..etc and if its null the default value form
     * @param context   Odoo context
     * @return OeView
     * @throws OeRpcException if Odoo response with error
     * @see #getFieldsView(String, Long, String, Map)
     */
    public OeView getFieldsView(String modelName, String viewMode, Map<String, Object> context) throws OeRpcException {
        return getFieldsView(modelName, null, viewMode, context);
    }

    /**
     * Wrapper for {@link #getFieldsView(String, String, Map)}
     *
     * @param modelName Odoo model name
     * @param viewMode  view mode FORM, KANBAN, ..etc and if its null the default value form
     * @return OeView
     * @throws OeRpcException if Odoo response with error
     * @see #getFieldsView(String, String, Map)
     */
    public OeView getFieldsView(String modelName, String viewMode) throws OeRpcException {
        return getFieldsView(modelName, viewMode, executor.getContext());
    }

    /**
     * Wrapper for {@link #getFieldsView(String, Long, String, Map)}
     *
     * @param oeModel  Odoo model name form {@link OeModel}
     * @param viewId   view id
     * @param viewMode view mode FORM, KANBAN, ..etc and if its null the default value form {@link OeViewMode}
     * @param context  Odoo context
     * @return OeView
     * @throws OeRpcException if Odoo response with error
     * @see OeModel
     * @see OeViewMode
     */
    public OeView getFieldsView(OeModel oeModel, Long viewId, OeViewMode viewMode, Map<String, Object> context)
            throws OeRpcException {
        return getFieldsView(oeModel.getName(), viewId, viewMode.getName(), context);
    }

    /**
     * Wrapper for {@link #getFieldsView(String, String, Map)}
     *
     * @param oeModel  Odoo model name form {@link OeModel}
     * @param viewMode view mode FORM, KANBAN, ..etc and if its null the default value form {@link OeViewMode}
     * @param context  Odoo context
     * @return OeView
     * @throws OeRpcException if Odoo response with error
     * @see OeModel
     * @see OeViewMode
     */
    public OeView getFieldsView(OeModel oeModel, OeViewMode viewMode, Map<String, Object> context) throws OeRpcException {
        return getFieldsView(oeModel.getName(), viewMode.getName(), context);
    }

    /**
     * Wrapper for {@link #getFieldsView(OeModel, OeViewMode, Map)}
     *
     * @param oeModel  Odoo model name form {@link OeModel}
     * @param viewMode view mode FORM, KANBAN, ..etc and if its null the default value form {@link OeViewMode}
     * @return OeView
     * @throws OeRpcException if Odoo response with error
     * @see OeModel
     * @see OeViewMode
     */
    public OeView getFieldsView(OeModel oeModel, OeViewMode viewMode) throws OeRpcException {
        return getFieldsView(oeModel, viewMode, executor.getContext());
    }

    /**
     * Wrapper for {@link #getFieldsView(OeModel, OeViewMode, Map)}
     *
     * @param modelName Odoo model name or res model
     * @param viewMode  view mode FORM, KANBAN, ..etc and if its null the default value form {@link OeViewMode}
     * @return OeView
     * @throws OeRpcException if Odoo response with error
     * @see OeViewMode
     */
    public OeView getFieldsView(String modelName, OeViewMode viewMode) throws OeRpcException {
        return getFieldsView(modelName, viewMode.getName(), executor.getContext());
    }

    /**
     * Wrapper for {@link #getFieldsView(OeModel, Long, OeViewMode, Map)}
     *
     * @param oeModel  Odoo model name form {@link OeModel}
     * @param viewId   view id
     * @param viewMode view mode FORM, KANBAN, ..etc and if its null the default value form {@link OeViewMode}
     * @return OeView
     * @throws OeRpcException if Odoo response with error
     * @see #getFieldsView(OeModel, Long, OeViewMode, Map)
     */
    public OeView getFieldsView(OeModel oeModel, Long viewId, OeViewMode viewMode) throws OeRpcException {
        return getFieldsView(oeModel, viewId, viewMode, executor.getContext());
    }

    @Override
    public List<OeView> find(List<Object> sc, Integer offset, Integer limit, String order, Map<String, Object> context,
                             String... columns) throws OeRpcException {
        return super.find(this, sc, offset, limit, order, context, columns);
    }

    /**
     * enum with methods name in view model
     */
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
