package com.example.chatfx.model;

public class User {
    private String username;
    private String ip;
    private int port;

    public User(String username, String ip, int port) {
        this.username = username;
        this.ip = ip;
        this.port = port;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
