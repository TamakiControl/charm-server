package com.tamakicontrol;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.gateway.localdb.persistence.IRecordListener;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.web.models.ConfigCategory;
import com.inductiveautomation.ignition.gateway.web.models.DefaultConfigTab;
import com.inductiveautomation.ignition.gateway.web.models.IConfigTab;
import com.inductiveautomation.ignition.gateway.web.models.KeyValue;
import com.tamakicontrol.config.CharmSettingsPage;
import com.tamakicontrol.config.CharmSettingsRecord;
import com.tamakicontrol.provider.CharmTagProvider;
import com.tamakicontrol.server.CharmTCPServer;
import com.tamakicontrol.server.CharmTCPConnectionThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class GatewayHook extends AbstractGatewayModuleHook {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private GatewayContext gatewayContext;
    private CharmTCPServer charmTCPServer;

    @Override
    public void setup(GatewayContext gatewayContext) {
        this.gatewayContext = gatewayContext;

        // get properties file to display webpage text
        BundleUtil.get().addBundle("charm", getClass(), "charm");

        CharmTagProvider.startup(gatewayContext);
        CharmSettingsRecord settings = setupInternalDB(gatewayContext);
        charmTCPServer = buildTCPServer(settings.getPort());
    }

    @Override
    public void startup(LicenseState licenseState) {
        gatewayContext.getExecutionManager().executeOnce(charmTCPServer);
    }

    @Override
    public void shutdown() {
        CharmTagProvider.shutdown();
        charmTCPServer.shutdown();
        BundleUtil.get().removeBundle("charm");
    }


    public CharmTCPServer buildTCPServer(int port){
        return new CharmTCPServer(port) {
            @Override
            public void onSocketConnected(Socket clientSocket) {
                gatewayContext.getExecutionManager().executeOnce(new CharmTCPConnectionThread(clientSocket));
            }
        };
    }

    // <editor-fold name="Configuration">

    private static final int DEFAULT_CHARM_TCP_PORT = 502;

    private CharmSettingsRecord setupInternalDB(GatewayContext gatewayContext){

        verifySchema(gatewayContext);
        maybeCreateSettings(gatewayContext);

        CharmSettingsRecord settings = gatewayContext.getLocalPersistenceInterface()
                .find(CharmSettingsRecord.META, 0L);

        CharmSettingsRecord.META.addRecordListener(new IRecordListener<CharmSettingsRecord>() {
            @Override
            public void recordUpdated(CharmSettingsRecord settings) {
                logger.debug("Charm Settings Updated");
                charmTCPServer.setEnabled(settings.getEnabled());

                if(settings.getPort() != charmTCPServer.getPort()){
                    charmTCPServer.shutdown();
                    charmTCPServer = buildTCPServer(settings.getPort());
                }
            }

            @Override
            public void recordAdded(CharmSettingsRecord charmSettingsRecord) {
                logger.info("Charm Settings Added");
            }

            @Override
            public void recordDeleted(KeyValue keyValue) {
                logger.warn("Tamaki MES Settings Deleted");
            }
        });

        return settings;
    }

    private void verifySchema(GatewayContext gatewayContext) {
        try {
            gatewayContext.getSchemaUpdater().updatePersistentRecords(CharmSettingsRecord.META);
        } catch (SQLException e) {
            logger.error("Error verifying internal database records for TamakiMES", e);
        }
    }

    private void maybeCreateSettings(GatewayContext context) {
        logger.debug("Creating Default Charm Settings");

        try {
            CharmSettingsRecord settingsRecord = context.getLocalPersistenceInterface().createNew(CharmSettingsRecord.META);
            settingsRecord.setId(0L);
            settingsRecord.setPort(DEFAULT_CHARM_TCP_PORT);
            settingsRecord.setEnabled(true);
            context.getSchemaUpdater().ensureRecordExists(settingsRecord);
        } catch (Exception e) {
            logger.error("Failed to add default record for Charm settings", e);
        }

    }

    private static final ConfigCategory CONFIG_CATEGORY = new ConfigCategory("charm", "charm.nav.header", 700);

    @Override
    public List<ConfigCategory> getConfigCategories() {
        return Collections.singletonList(CONFIG_CATEGORY);
    }

    private static final IConfigTab MES_CONFIG_ENTRY = DefaultConfigTab.builder()
            .category(CONFIG_CATEGORY)
            .name("charm")
            .i18n("charm.nav.settings.title")
            .page(CharmSettingsPage.class)
            .terms("charm settings")
            .build();

    @Override
    public List<? extends IConfigTab> getConfigPanels() {
        return Collections.singletonList(MES_CONFIG_ENTRY);
    }

    // </editor-fold>

}
