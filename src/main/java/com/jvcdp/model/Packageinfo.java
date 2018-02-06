package com.jvcdp.model;

public class Packageinfo {

    private String id;
    private  String createdOn;
    private String packageVersion;

    public Packageinfo() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public void setPackageVersion(String packageVersion) {
        this.packageVersion = packageVersion;
    }


    public String getId() {
        return id;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getPackageVersion() {
        return packageVersion;
    }
}
