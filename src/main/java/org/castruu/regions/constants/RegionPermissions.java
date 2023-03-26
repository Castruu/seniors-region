package org.castruu.regions.constants;

public enum RegionPermissions {

    MENU,
    ADD,
    REMOVE,
    WHITELIST,
    CREATE,
    BYPASS;

    public String getPermission() {
        return "region." + this.name().toLowerCase();
    }
}
