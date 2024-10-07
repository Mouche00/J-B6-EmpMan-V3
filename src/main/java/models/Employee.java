package models;

import java.util.UUID;

public class Employee {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String post;
    private Department department;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee(String name, String email, String phone, String post, Department department) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.post = post;
        this.department = department;
    }

    public Employee() {
    }
}