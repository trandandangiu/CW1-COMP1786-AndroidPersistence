package com.example.cw1_androidpersistence;

public class Contact {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String avatar; // có thể là URI hoặc drawable name

    public Contact() {}

    public Contact(int id, String name, String phone, String email, String avatar) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
    }

    public Contact(String name, String phone, String email, String avatar) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
    }

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
}
