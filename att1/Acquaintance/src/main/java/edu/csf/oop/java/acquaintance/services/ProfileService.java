package main.java.edu.csf.oop.java.acquaintance.services;

import main.java.edu.csf.oop.java.acquaintance.models.Photo;
import main.java.edu.csf.oop.java.acquaintance.models.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ProfileService implements ProfileOptions {
    private final Logger infoLogger = LoggerFactory.getLogger("info.logger");
    private final Logger errorLogger = LoggerFactory.getLogger("error.logger");

    //Зарегистрированный в данный момент пользователь
    private Profile activeProfile = null;

    public Profile getActiveProfile() {
        return activeProfile;
    }

    @Override
    public void addToFriends(Profile friend) {
        infoLogger.info("Add friend");
        activeProfile.addToFriends(friend);
    }

    @Override
    public void like(Photo photo) {
        infoLogger.info("Like photo");
        photo.like(activeProfile);
    }

    @Override
    public void comment(Photo photo, String comment) {
        infoLogger.info("Comment photo");
        photo.comment(activeProfile, comment);
    }


    @Override
    public void register(HashMap<String, String> params) throws Exception {
        infoLogger.info("Profile registration");
        try {
            Profile newProfile = new Profile(
                    params.get("name"),
                    params.get("surname"),
                    Integer.parseInt(params.get("age")),
                    params.get("city"),
                    params.get("info"),
                    params.get("login")
            );
            DbProfileService.insert(newProfile);
            activeProfile = newProfile;
            infoLogger.info("Success");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            errorLogger.error("Failed registration");
            throw new Exception("Регистрация завершилась неудачно!");
        }
    }

    @Override
    public void login(String login) throws Exception {
        infoLogger.info("Log in profile");
        try {
            Profile result;
            result = DbProfileService.findLogin(login);
            activeProfile = result;
        } catch (Exception ex) {
            errorLogger.error("Failed authentication");
            throw new Exception("Такого пользователя нет!");
        }
    }

    @Override
    public void refactorProfile(HashMap<Integer, String> params) throws Exception {
        infoLogger.info("Refactor profile");
        Profile oldUser = activeProfile.clone();
        for (Map.Entry<Integer, String> settings: params.entrySet()) {
            switch (settings.getKey()) {
                case 1 -> activeProfile.setName(settings.getValue());
                case 2 -> activeProfile.setSurname(settings.getValue());
                case 3 -> activeProfile.setAge(Integer.parseInt(settings.getValue()));
                case 4 -> activeProfile.setCity(settings.getValue());
                case 5 -> activeProfile.setInfoAboutMe(settings.getValue());
            }
        }
        try {
            DbProfileService.update(oldUser, activeProfile);
        } catch (Exception e) {
            errorLogger.error("Failed refactoring");
            throw new Exception(e.getMessage());
        }
    }


}
