package edu.csf.oop.java.acquaintance.services;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import edu.csf.oop.java.acquaintance.models.Profile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Сервис для работы с базой данных пользователей
 */
public class DbProfileService {
    private List<Profile> profiles = null;

    public List<Profile> findProfiles(String name, String surname) {
        List<Profile> result = new ArrayList<>();

        for (Profile profile: profiles) {
            if (profile.getName().equals(name) && profile.getSurname().equals(surname)) {
                result.add(profile);
            }
        }
        return result;
    }

    public Profile findLogin(String login) throws Exception {
        for (Profile profile: profiles) {
            if (login.equals(profile.getLogin())) {
                return profile;
            }
        }
        throw new Exception();
    }

    /**
     * Обновить доступную информацию о пользователе
     * @param oldProf
     * @param newProf
     */
    public void update(Profile oldProf, Profile newProf) throws Exception {
        if(!profiles.contains(oldProf)) {
            throw new Exception("No such as profile");
        }
        int index = profiles.indexOf(oldProf);
        profiles.set(index, newProf);
    }

    /**
     * Добавить нового пользователя
     * @param profile
     */
    public void insert(Profile profile) throws Exception {
        if (profile == null) {
            throw new Exception("Empty user!");
        }
        profiles.add(profile);
    }

    /**
     * Сохранить базу данных в json бэкап
     * @param filename
     */
    public void save(String filename) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

        writer.writeValue(Paths.get(filename).toFile(), profiles);
    }

    /**
     * Восстановить базу данных из бэкапа
     * @param filename
     */
    public void restore(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        profiles = Arrays.asList(mapper.readValue(Paths.get(filename).toFile(), Profile[].class));
    }

    //Database seeder
    public void seed() {
        profiles = new ArrayList<>();
        Profile profile1 = new Profile("Иван", "Иванов", 25, "Воронеж", "студент ВГУ", "ivan555");
        profile1.addPhoto("Отдыхаю на пляже");
        profile1.addPhoto("Зимняя атмосфера прекрасна");
        profiles.add(profile1);

        Profile profile2= new Profile("Дмитрий", "Мамонов", 18, "Воронеж", "ку", "dimonchik_dimok");
        profiles.add(profile2);
    }


}
