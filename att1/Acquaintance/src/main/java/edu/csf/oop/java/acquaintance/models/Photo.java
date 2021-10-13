package main.java.edu.csf.oop.java.acquaintance.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Photo {
    private Profile profileParent;
    private String description;
    private int likesCount;
    private ArrayList<Profile> likesProfiles;
    private HashMap<Profile, String> comments;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public ArrayList<Profile> getLikesProfiles() {
        return likesProfiles;
    }

    public HashMap<Profile, String> getComments() {
        return comments;
    }


    public Photo(String description) {
        this.description = description;
        this.likesCount = 0;
        this.likesProfiles = new ArrayList<>();
        this.comments = new HashMap<>();
    }

    public void like(Profile profile) {
        likesCount++;
        likesProfiles.add(profile);
    }

    public void comment(Profile profile, String comment) {
        comments.put(profile, comment);
    }

    public Profile getProfileParent() {
        return profileParent;
    }


    public String showPhoto() {
        StringBuilder builder = new StringBuilder();
        builder.append("Описание: ").append(this.getDescription()).append(";\n");
        builder.append("Количество лайков: ").append(this.getLikesCount()).append(";\n");
        builder.append("Кто лайкнул: \n");
        for (Profile profile: this.getLikesProfiles()) {
            builder.append(profile.showShortProfile(profile));
        }
        builder.append("Комментарии: \n");
        for (Map.Entry<Profile,String> comment: this.getComments().entrySet()){
            builder.append(comment.getKey().getName()).append(" ").append(comment.getKey().getSurname()).append(" => ").append(comment.getValue()).append("\n");
        }
        return builder.toString();
    }

    public String showShortPhoto() {
        return "Описание " + this.getDescription() + ", Лайков: " + this.getLikesCount() + ", Комментариев: " + getComments().size() + "\n";
    }
}
