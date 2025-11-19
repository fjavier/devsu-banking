package com.devsu.bankingcustomer.tests;

import com.intuit.karate.junit5.Karate;

public class KarateRunner {

    @Karate.Test
    Karate runAll() {
        String env = System.getenv("ENV");
        if (env == null) env = "local";

        System.out.println("Running Karate with ENV=" + env);

        String baseUrl;

        if (env.equals("docker")) {
            baseUrl = "http://banking-customer:8081";
        } else {
            baseUrl = "http://localhost:8081";
        }

        System.setProperty("baseUrl", baseUrl);
        return Karate.run().relativeTo(getClass()).karateEnv(env).systemProperty("baseUrl", baseUrl);
    }
}
