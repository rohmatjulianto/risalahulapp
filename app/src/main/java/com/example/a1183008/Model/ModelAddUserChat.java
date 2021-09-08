package com.example.a1183008.Model;

public class ModelAddUserChat {
    public String key;
    public String id_user;
    public String nama;
    public String username;
    public String password;
    public String role_id;
    public String status;

    public ModelAddUserChat(){
    }

    public ModelAddUserChat(String nama, String username, String password, String role_id, String status) {
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.role_id = role_id;
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
