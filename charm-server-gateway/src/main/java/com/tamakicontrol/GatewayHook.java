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

import java.net.Socket;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class GatewayHook extends AbstractGatewayModuleHook {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private GatewayContext gatewayContext;
    private CharmTCPServer charmTCPServer;

    private CharmSettingsRecord settings;

    @Override
    public void setup(GatewayContext gatewayContext) {
        this.gatewayContext = gatewayContext;

        // get properties file to display webpage text
        BundleUtil.get().addBundle("charm", getClass(), "charm");
        CharmTagProvider.setup();
        settings = setupInternalDB();

        if(settings.isEnabled())
            charmTCPServer = buildTCPServer(settings.getPort());
    }

    @Override
    public void startup(LicenseState licenseState) {
        if(settings.isEnabled())
            charmTCPServer.start();

        CharmTagProvider.startup(gatewayContext);
    }

    @Override
    public void shutdown() {
        CharmTagProvider.shutdown();
        charmTCPServer.shutdown();
        BundleUtil.get().removeBundle("charm");
    }

    @Override
    public boolean isFreeModule() {
        return true;
    }

    /**
     * Builds a TCP Server on a Specified Port for CHARM Readers to Connect to
     * */
    public CharmTCPServer buildTCPServer(int port){
        return new CharmTCPServer(port) {
            @Override
            public void onSocketConnected(final Socket clientSocket) {
                // use the gateway execution manager thread pool for this
                gatewayContext.getExecutionManager().executeOnce(new CharmTCPConnectionThread(clientSocket));
            }
        };
    }

    /**
     * Reinitialize Charm Server (Builds and Spawns a Thread)
     * Ensures that the thread has been shutdown and re-spawns a new thread
     * */
    public synchronized void reinitializeTCP(int port) {
        if (charmTCPServer.isAlive()) {
            charmTCPServer.shutdown();
            try {
                charmTCPServer.join();
            } catch (InterruptedException e) {
                logger.warn("TCP Server Interrupted While Waiting for Shutdown", e);
            }
        }

        charmTCPServer = buildTCPServer(port);
        charmTCPServer.start();
    }

    // <editor-fold name="Configuration">

    private static final int DEFAULT_CHARM_TCP_PORT = 502;

    /**
     * Creates a settings record and adds a record listener to update settings if things change
     * */
    private CharmSettingsRecord setupInternalDB(){

        verifySchema();
        maybeCreateSettings();

        CharmSettingsRecord settings = gatewayContext.getLocalPersistenceInterface()
                .find(CharmSettingsRecord.META, 0L);

        CharmSettingsRecord.META.addRecordListener(new IRecordListener<CharmSettingsRecord>() {
            @Override
            public void recordUpdated(CharmSettingsRecord newSettings) {
                logger.debug("Charm Settings Updated");

                if(newSettings.isEnabled() && !charmTCPServer.isEnabled() ||
                        (newSettings.getPort() != charmTCPServer.getPort())) {
                    reinitializeTCP(newSettings.getPort());
                }

                if(!newSettings.isEnabled())
                    charmTCPServer.shutdown();
            }

            @Override
            public void recordAdded(CharmSettingsRecord charmSettingsRecord) {
                logger.warn("Charm Settings Added");
            }

            @Override
            public void recordDeleted(KeyValue keyValue) {
                logger.warn("Tamaki MES Settings Deleted");
            }

        });

        return settings;
    }

    /**
     * Adds Charm Settings Table to Internal DB
     * */
    private void verifySchema() {
        try {
            gatewayContext.getSchemaUpdater().updatePersistentRecords(CharmSettingsRecord.META);
        } catch (SQLException e) {
            logger.error("Error verifying internal database records for TamakiMES", e);
        }
    }

    /**
     * Adds a single database record for charm settings if it doesn't currently exist
     * */
    private void maybeCreateSettings() {
        logger.debug("Creating Default Charm Settings");

        try {
            CharmSettingsRecord settingsRecord = gatewayContext.getLocalPersistenceInterface().createNew(CharmSettingsRecord.META);
            settingsRecord.setId(0L);
            settingsRecord.setPort(DEFAULT_CHARM_TCP_PORT);
            settingsRecord.setEnabled(true);
            gatewayContext.getSchemaUpdater().ensureRecordExists(settingsRecord);
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
