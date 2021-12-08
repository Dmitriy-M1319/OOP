package edu.csf.oop.java.acquaintance.models;

import java.util.ArrayList;

public class Photo {
    private String description;
    private int likesCount;
    private ArrayList<Profile> likesProfiles;

    public ArrayList<Profile> getCommentProfiles() {
        return commentProfiles;
    }

    public void setCommentProfiles(ArrayList<Profile> commentProfiles) {
        this.commentProfiles = commentProfiles;
    }

    public ArrayList<String> getCommentMessages() {
        return commentMessages;
    }

    public void setCommentMessages(ArrayList<String> commentMessages) {
        this.commentMessages = commentMessages;
    }

    private ArrayList<String> commentMessages;
    private ArrayList<Profile> commentProfiles;

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


    public Photo() {

    }

    public Photo(String description) {
        this.description = description;
        this.likesCount = 0;
        this.likesProfiles = new ArrayList<>();
        this.commentMessages = new ArrayList<>();
        this.commentProfiles = new ArrayList<>();
    }

    public void like(Profile profile) {
        likesCount++;
        likesProfiles.add(profile);
    }

    public void comment(Profile profile, String comment) {
        commentMessages.add(comment);
        commentProfiles.add(profile);
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
        for (int i = 0; i < commentMessages.size(); i++) {
            builder.append(commentProfiles.get(i).getName()).
                    append(" ").append(commentProfiles.get(i).getSurname()).
                    append(" => ").append(commentMessages.get(i)).append("\n");
        }

        return builder.toString();
    }

    public String showShortPhoto() {
        return "Описание: " + this.getDescription() + ", Лайков: " + this.getLikesCount() + ", Комментариев: " + commentMessages.size() + "\n";
    }
}
