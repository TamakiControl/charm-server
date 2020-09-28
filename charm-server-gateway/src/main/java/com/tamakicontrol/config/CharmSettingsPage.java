package com.tamakicontrol.config;

import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.web.components.RecordEditForm;
import com.inductiveautomation.ignition.gateway.web.models.LenientResourceModel;
import com.inductiveautomation.ignition.gateway.web.pages.IConfigPage;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.wicket.Application;

public class CharmSettingsPage extends RecordEditForm {

    public static final String[] PATH = {"charm", "settings"};

    public CharmSettingsPage(final IConfigPage configPage) {
        super(configPage, null, new LenientResourceModel("TamakiMES.nav.settings.panelTitle"),
                ((GatewayContext) Application.get()).getPersistenceInterface().find(CharmSettingsRecord.META, 0L));
    }

    @Override
    public Pair<String, String> getMenuLocation() {
        return Pair.of(PATH[0], PATH[1]);
    }

}
