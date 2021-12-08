package edu.csf.oop.java.acquaintance.services;

import edu.csf.oop.java.acquaintance.models.Photo;
import edu.csf.oop.java.acquaintance.models.Profile;

import java.util.HashMap;

/**
 * Сервис работы с настоящим пользователем в системе
 */
public interface ProfileOptions {

    /**
     * Регистрация нового пользователя
     * @param params
     */
    public void register(HashMap<String, String> params) throws Exception;

    /**
     * Вход в систему
     * @param login
     */
    public void login(String login) throws Exception;

    /**
     * Редактирование зарегистрированного пользователя
     * @param params
     */
    public void refactorProfile(HashMap<Integer, String> params) throws Exception;

    /**
     * Добавить пользователя в друзья
     * @param friend
     */
    public void addToFriends(Profile friend) throws Exception;

    public void like(Photo photo, Profile profile) throws Exception;

    public void comment(Photo photo, String comment, Profile profile) throws Exception;
}
