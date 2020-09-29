package com.tamakicontrol.provider;

import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.sqltags.simple.ProviderConfiguration;
import com.inductiveautomation.ignition.gateway.sqltags.simple.SimpleTagProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharmTagProvider {
    private static final Logger logger = LoggerFactory.getLogger(CharmTagProvider.class);

    private static CharmTagProvider instance;
    private final SimpleTagProvider simpleTagProvider;

    public CharmTagProvider(){
        simpleTagProvider = new SimpleTagProvider("CHARM");
        ProviderConfiguration providerConfiguration = new ProviderConfiguration()
                .setAllowTagCustomization(false)
                .setPersistTags(true)
                .setPersistValues(true);

        simpleTagProvider.configureProvider(providerConfiguration);
    }

    public static void setup(){
        if(instance == null) {
            instance = new CharmTagProvider();
        }
    }

    /**
     * Starts up the tag provider and creates and instance of the singleton
     * */
    public static void startup(GatewayContext gatewayContext){
        try {
            instance.simpleTagProvider.startup(gatewayContext);
        } catch (Exception e) {
            logger.error("Error Starting up CHARM Tag Provider", e);
        }
    }

    public static CharmTagProvider getInstance(){
        return instance;
    }

    /**
     * Shuts down the tag provider and destroys the instance of the singleton
     * */
    public static void shutdown(){
        if(instance != null)
        instance.simpleTagProvider.shutdown();
        instance = null;
    }

    public SimpleTagProvider getTagProvider(){
        return simpleTagProvider;
    }

}
