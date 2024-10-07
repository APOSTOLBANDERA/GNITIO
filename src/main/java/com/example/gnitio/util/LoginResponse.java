package com.example.gnitio.util;

public class LoginResponse {
    private String token;

    private long expiresIn;

    public LoginResponse(String token, long expiresIn){
        this.token = token;
        this.expiresIn = expiresIn;
    }
    public LoginResponse(){

    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return this.expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
