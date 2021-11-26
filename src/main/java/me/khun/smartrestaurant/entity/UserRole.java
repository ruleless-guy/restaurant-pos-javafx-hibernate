package me.khun.smartrestaurant.entity;

import me.khun.smartrestaurant.auth.Permission;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static me.khun.smartrestaurant.auth.Permission.*;


public enum UserRole {

    ADMIN {

        private final Set<Permission> permissionSet = EnumSet.of(
                MANAGE_MANAGER, MANAGE_CASHIER, MANAGE_MEAL_CATEGORY, MANAGE_MEAL, MANAGE_RESTAURANT_TABLE, VIEW_DASH_BOARD
        );

        @Override
        protected Set<Permission> getPermissions() {
            return permissionSet;
        }

    },

    MANAGER {

        private final Set<Permission> permissionSet = EnumSet.of(
                MANAGE_CASHIER, MANAGE_MEAL_CATEGORY, MANAGE_MEAL, MANAGE_RESTAURANT_TABLE, VIEW_DASH_BOARD
        );

        @Override
        protected Set<Permission> getPermissions() {
            return permissionSet;
        }
    },

    CASHIER {
        private final Set<Permission> permissionSet = EnumSet.of(
                MAKE_ORDER, VIEW_DASH_BOARD
        );

        @Override
        protected Set<Permission> getPermissions() {
            return permissionSet;
        }
    };

    protected abstract Set<Permission> getPermissions();

    public boolean hasAccessForAll(Permission ... permissions) {
        return getPermissions().containsAll(List.of(permissions));
    }

}
