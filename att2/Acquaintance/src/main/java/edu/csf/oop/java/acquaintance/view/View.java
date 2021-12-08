package edu.csf.oop.java.acquaintance.view;

import edu.csf.oop.java.acquaintance.services.ProfileService;

public interface View {
    ProfileService profileService = new ProfileService();

    public void login();
    public void showActualProfile();
    public void showActualPhotos();
    public void findFriends();
    public void addFriend();
    public void addPhoto();
    public void showOtherPhotos();
    public void likePhoto();
    public void commentPhoto();
    public void refactorProfile();
    public void showMenu();
    public int exit();
    public int takeFunction();
    public void save();
    public void restore();
}
