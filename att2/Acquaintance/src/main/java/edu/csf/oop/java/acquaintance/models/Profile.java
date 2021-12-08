package edu.csf.oop.java.acquaintance.models;

import java.util.ArrayList;

public class Profile {
    private String name;
    private String surname;
    private int age;
    private String city;

    private ArrayList<Photo> photos;
    private ArrayList<Profile> friends;

    private String infoAboutMe;
    private String login;

    public String getInfoAboutMe() {
        return infoAboutMe;
    }
    public String getCity() {
        return city;
    }
    public int getAge() {
        return age;
    }
    public String getSurname() {
        return surname;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Profile> getFriends() { return friends; }
    public ArrayList<Photo> getPhotos() {
        return photos;
    }
    public String getLogin() {
        return login;
    }

    /**
     * Первоначальное создание профиля
     * @param name
     * @param surname
     * @param age
     * @param city
     * @param info
     */
    public Profile(String name, String surname, int age, String city, String info, String login) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.city = city;
        this.infoAboutMe = info;
        this.login = login;
        friends = new ArrayList<>();
        photos = new ArrayList<>();
    }

    public Profile() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setInfoAboutMe(String infoAboutMe) {
        this.infoAboutMe = infoAboutMe;
    }

    public Profile clone() {
        return this;
    }

    public String showProfile() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getName()).append(" ").append(this.getSurname()).append(";\n");
        builder.append("Возраст: ").append(this.getAge()).append(";\n");
        builder.append("Город: ").append(this.getCity()).append(";\n");
        builder.append("Немного о себе: ").append(this.getInfoAboutMe()).append(";\n");
        builder.append("Друзья:\n");
        for (Profile friend : this.getFriends()) {
            builder.append("    ").append(showShortProfile(friend));
        }
        builder.append("Фотографии: \n");
        for (Photo photo: this.photos) {
            builder.append(photo.showShortPhoto());
        }
        return  builder.toString();
    }

    public String showShortProfile(Profile profile) {
        return profile.getName() + " " + profile.getSurname() + ", " + profile.getCity() + "\n";
    }

    public void addToFriends(Profile profile) {
        friends.add(profile);
    }

    public void addPhoto(String description) {
        photos.add(new Photo(description));
    }
}
