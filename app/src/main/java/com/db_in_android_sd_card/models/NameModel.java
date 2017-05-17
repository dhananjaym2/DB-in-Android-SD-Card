package com.db_in_android_sd_card.models;

/**
 * Model/Bean class for Name Model data exchange.
 */

public class NameModel {
    private String id_NameModel;
    private String name;
    private String appPkg;

    public void setId_NameModel(String id_NameModel) {
        this.id_NameModel = id_NameModel;
    }

    public String getId_NameModel() {
        return id_NameModel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAppPkg(String appPkg) {
        this.appPkg = appPkg;
    }

    public String getAppPkg() {
        return appPkg;
    }
}