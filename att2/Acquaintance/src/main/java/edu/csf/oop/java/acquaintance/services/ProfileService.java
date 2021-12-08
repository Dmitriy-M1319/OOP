package edu.csf.oop.java.acquaintance.services;

import edu.csf.oop.java.acquaintance.Main;
import edu.csf.oop.java.acquaintance.models.Photo;
import edu.csf.oop.java.acquaintance.models.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileService implements ProfileOptions {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    //Зарегистрированный в данный момент пользователь
    private Profile activeProfile = null;
    //Экземпляр сервиса базы данных для активного пользователя
    private DbProfileService dbService = new DbProfileService();

    public Profile getActiveProfile() {
        return activeProfile;
    }

    public List<Profile> findProfiles(String name, String surname) {
        return dbService.findProfiles(name, surname);
    }

    public void seed() {
        dbService.seed();
    }

    public void save(String filename) throws Exception {
        logger.info("Save backup");
        dbService.save(filename);
    }

    public void restore(String filename) throws Exception {
        logger.info("Restore backup");
        try {
            dbService.restore(filename);
            if (activeProfile != null) {
                activeProfile = dbService.findLogin(activeProfile.getLogin());
            }
        } catch (Exception e) {
            logger.error("Failed restore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void addToFriends(Profile friend) throws Exception {
        logger.info("Add friend");
        Profile oldUser = activeProfile.clone();
        activeProfile.addToFriends(friend);
        try {
            dbService.update(oldUser, activeProfile);
        } catch (Exception e) {
            logger.error("Failed update database: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void like(Photo photo, Profile profile) throws Exception{
        logger.info("Like photo");
        Profile old = profile.clone();
        photo.like(activeProfile);
        try {
            dbService.update(old, profile);
        } catch (Exception e) {
            logger.error("Failed update database: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void comment(Photo photo, String comment, Profile profile) throws Exception {
        logger.info("Comment photo");
        Profile old = profile.clone();
        photo.comment(activeProfile, comment);
        try {
            dbService.update(old, profile);
        } catch (Exception e) {
            logger.error("Failed update database: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public void register(HashMap<String, String> params) throws Exception {
        logger.info("Profile registration");
        try {
            Profile newProfile = new Profile(
                    params.get("name"),
                    params.get("surname"),
                    Integer.parseInt(params.get("age")),
                    params.get("city"),
                    params.get("info"),
                    params.get("login")
            );
            dbService.insert(newProfile);
            activeProfile = newProfile;
            logger.info("Registration successful");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            logger.error("Failed registration");
            throw new Exception("Регистрация завершилась неудачно!");
        }
    }

    @Override
    public void login(String login) throws Exception {
        logger.info("Log in profile");
        try {
            Profile result;
            result = dbService.findLogin(login);
            activeProfile = result;
        } catch (Exception ex) {
            logger.error("Failed authentication");
            throw new Exception("Такого пользователя нет!");
        }
    }

    @Override
    public void refactorProfile(HashMap<Integer, String> params) throws Exception {
        logger.info("Refactor profile");
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
            dbService.update(oldUser, activeProfile);
        } catch (Exception e) {
            logger.error("Failed refactoring");
            throw new Exception(e.getMessage());
        }
    }


}
