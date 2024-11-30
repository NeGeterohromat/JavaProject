package org.example;

public class Student {
    private final int hash;
    private final String ulearnID;
    private final String fullName;
    private String status = null;
    private String city = null;
    private Integer vkID = null;

    public Student(String ulearnID, String fullName) {
        this.hash = hashCode(ulearnID);
        this.ulearnID = ulearnID;
        this.fullName = fullName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public String getCity() {
        return city;
    }

    public String getUlearnID() {
        return ulearnID;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getVkID() {
        return vkID;
    }

    public void setVkID(Integer vkID) {
        this.vkID = vkID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            return ((Student) obj).hash == hash;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    private int hashCode(String s) {
        return s.hashCode();
    }
}
