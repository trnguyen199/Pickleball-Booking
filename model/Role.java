package com.pbs.model;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ROLE_PLAYER,
    ROLE_COACH,
    ROLE_MANAGER,
    ROLE_ADMIN;

    public static List<Role> allRoles() {
        return Arrays.asList(ROLE_PLAYER, ROLE_COACH, ROLE_MANAGER, ROLE_ADMIN);
    }
}
