package org.security.web;

public class User {
    private Long id;
    private String name;
    private String fingerPrint;

    public User(Long id, String name, String fingerPrint) {
        this.id = id;
        this.name = name;
        this.fingerPrint = fingerPrint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }
}
