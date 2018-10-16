package com.mossesbasket.automation.model.tokengenerate;

public class TokenData {

    private String status;
    private long expiringTime;
    private long createdTime;
    private String userId;
    private String accessToken;

    public String getStatus() {
        return status;
    }

    public long getExpiringTime() {
        return expiringTime;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public String getUserId() {
        return userId;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
