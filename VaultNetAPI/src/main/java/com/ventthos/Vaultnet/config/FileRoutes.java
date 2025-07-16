package com.ventthos.Vaultnet.config;

import lombok.Getter;

@Getter
public enum FileRoutes {

    USERS("users/"),
    BUSINESS("business/"),
    PRODUCTS("products/");

    private final String route;

    FileRoutes(String route){
        this.route = route;
    }
}
