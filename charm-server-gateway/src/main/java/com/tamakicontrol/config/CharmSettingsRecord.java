package com.tamakicontrol.config;

import com.inductiveautomation.ignition.gateway.localdb.persistence.*;

public class CharmSettingsRecord extends PersistentRecord {

    public static final RecordMeta<CharmSettingsRecord> META = new RecordMeta<>(CharmSettingsRecord.class, "CharmSettingsRecord")
            .setNounKey("CharmSettingsRecord.Noun").setNounPluralKey("CharmSettingsRecord.Noun.Plural");

    @Override
    public RecordMeta<?> getMeta() {
        return META;
    }

    public static final IdentityField id = new IdentityField(META);

    public Long getId() {
        return getLong(id);
    }

    public void setId(Long _id) {
        setLong(id, _id);
    }

    public static final BooleanField enabled = new BooleanField(META, "Enabled");

    public Boolean isEnabled() {
        return getBoolean(enabled);
    }

    public void setEnabled(Boolean _enabled) {
        setBoolean(enabled, _enabled);
    }

    public static final IntField port = new IntField(META, "Port");

    public Integer getPort() {
        return getInt(port);
    }

    public void setPort(Integer _port) {
        setInt(port, _port);
    }

    // create categories for our record entries, getting titles from the HCSettingsRecord.properties, and
    // ordering through integer ranking
    static final Category General = new Category("CharmSettingsRecord.Category.General", 1000)
            .include(enabled, port);

}
