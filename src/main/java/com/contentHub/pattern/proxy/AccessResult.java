package com.contentHub.pattern.proxy;

public class AccessResult {
    private boolean granted;
    private String message;
    private String streamUrl;

    public AccessResult() {}

    public AccessResult(boolean granted, String message, String streamUrl) {
        this.granted = granted;
        this.message = message;
        this.streamUrl = streamUrl;
    }

    public boolean isGranted() { return granted; }
    public String getMessage() { return message; }
    public String getStreamUrl() { return streamUrl; }
    public void setGranted(boolean granted) { this.granted = granted; }
    public void setMessage(String message) { this.message = message; }
    public void setStreamUrl(String streamUrl) { this.streamUrl = streamUrl; }
}
