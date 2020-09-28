package com.tamakicontrol;

import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.tamakicontrol.provider.CharmTagProvider;
import com.tamakicontrol.server.CharmTCPServer;
import com.tamakicontrol.server.CharmTCPConnectionThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

public class GatewayHook extends AbstractGatewayModuleHook {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private GatewayContext gatewayContext;
    private CharmTCPServer charmTCPServer;

    // todo make this available through gateway settings
    private static final int CHARM_TCP_PORT = 502;

    @Override
    public void setup(GatewayContext gatewayContext) {
        this.gatewayContext = gatewayContext;
        CharmTagProvider.startup(gatewayContext);

        charmTCPServer = new CharmTCPServer(CHARM_TCP_PORT) {
            @Override
            public void onSocketConnected(Socket clientSocket) throws IOException {
                gatewayContext.getExecutionManager().executeOnce(new CharmTCPConnectionThread(clientSocket));
            }
        };
    }



    @Override
    public void startup(LicenseState licenseState) {
        gatewayContext.getExecutionManager().executeOnce(charmTCPServer);
    }

    @Override
    public void shutdown() {
        CharmTagProvider.shutdown();
        charmTCPServer.shutdown();
    }

}
