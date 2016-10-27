package net.chiragaggarwal.contactsapp.dependencies;

public class Configuration {
    private String baseUrl;

    public Configuration(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
