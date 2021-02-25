package com.example.laffayeh.model;

import java.util.ArrayList;

public class Adv {
    private String pic;
    private String phone;
    private String name;
    private String Language ;
    private String Nationality;
    private String Religion;
    private String WorkHours;
    private String Experience;
    private String key;
    private String type;
    private String age;
    private ArrayList<String> WorksDay = new ArrayList<>();

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getReligion() {
        return Religion;
    }

    public void setReligion(String religion) {
        Religion = religion;
    }

    public String getWorkHours() {
        return WorkHours;
    }

    public void setWorkHours(String workHours) {
        WorkHours = workHours;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public ArrayList<String> getWorksDay() {
        return WorksDay;
    }

    public void setWorksDay(ArrayList<String> worksDay) {
        WorksDay = worksDay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
