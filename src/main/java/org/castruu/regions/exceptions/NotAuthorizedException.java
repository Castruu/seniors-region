package org.castruu.regions.exceptions;

import org.castruu.regions.constants.RegionPermissions;

public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException(RegionPermissions permissions) {
        super(permissions.getPermission() + " is necessary to perform this action");
    }
}
