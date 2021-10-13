package main.java.edu.csf.oop.java.acquaintance.services;

import main.java.edu.csf.oop.java.acquaintance.models.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для работы с базой данных пользователей
 */
public class DbProfileService {
    private static final Logger infoLogger = LoggerFactory.getLogger("info.logger");
    private static final Logger errorLogger = LoggerFactory.getLogger("error.logger");

    private static List<Profile> profiles = null;

    public static List<Profile> findProfiles(String name, String surname) {
        List<Profile> result = new ArrayList<>();

        for (Profile profile: profiles) {
            if (profile.getName().equals(name) && profile.getSurname().equals(surname)) {
                result.add(profile);
            }
        }
        return result;
    }

    public static Profile findLogin(String login) throws Exception {
        for (Profile profile: profiles) {
            if (login.equals(profile.getLogin())) {
                return profile;
            }
        }
        errorLogger.error("Find login error");
        throw new Exception();
    }

    /**
     * Обновить доступную информацию о пользователе
     * @param oldProf
     * @param newProf
     */
    public static void update(Profile oldProf, Profile newProf) throws Exception {
        if(!profiles.contains(oldProf)) {
            errorLogger.error("Updating error");
            throw new Exception("Not such as profile");
        }
        int index = profiles.indexOf(oldProf);
        profiles.set(index, newProf);
    }

    /**
     * Добавить нового пользователя
     * @param profile
     */
    public static void insert(Profile profile) throws Exception {
        if (profile == null) {
            errorLogger.error("Inserting error");
            throw new Exception("Empty user!");
        }
        profiles.add(profile);
    }

    //Database seeder
    public static void seed() {
        infoLogger.info("Seeding database");
        profiles = new ArrayList<>();
        Profile profile1 = new Profile("Иван", "Иванов", 25, "Воронеж", "студент ВГУ", "ivan555");
        profile1.addPhoto("Отдыхаю на пляже");
        profile1.addPhoto("Зимняя атмосфера прекрасна");
        profiles.add(profile1);

        Profile profile2= new Profile("Дмитрий", "Мамонов", 18, "Воронеж", "ку", "dimonchik_dimok");
        profiles.add(profile2);
    }


}
