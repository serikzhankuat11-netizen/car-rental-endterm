package com.example.carrental.patterns.singleton;

public class AppConfig {
    private static volatile AppConfig instance;

    private final String appName = "Car Rental Endterm API";

    private AppConfig() {}

    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) instance = new AppConfig();
            }
        }
        return instance;
    }

    public String getAppName() { return appName; }
}
