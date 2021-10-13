package main.java.edu.csf.oop.java.acquaintance.view;

import main.java.edu.csf.oop.java.acquaintance.models.Photo;
import main.java.edu.csf.oop.java.acquaintance.models.Profile;
import main.java.edu.csf.oop.java.acquaintance.services.DbProfileService;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ConsoleView implements View {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public int exit() {
        System.out.println("Exit. Goodbye");
        return 0;
    }

    @Override
    public int takeFunction() {
        System.out.println("Введите номер действия, которое хотите выполнить");
        int function = scanner.nextInt();
        int login = 1;
        switch (function) {
            case 1 -> addFriend();
            case 2 -> addPhoto();
            case 3 -> showActualProfile();
            case 4 -> showActualPhotos();
            case 5 -> findFriends();
            case 6 -> showOtherPhotos();
            case 7 -> likePhoto();
            case 8 -> commentPhoto();
            case 9 -> refactorProfile();
            case 10 ->  login = exit();
            default -> System.out.println("Такой функции нет!");
        }
        return login;
    }

    @Override
    public void showMenu() {
        System.out.println();
        StringBuilder builder = new StringBuilder();
        builder.append("Меню действий:\n");
        builder.append("1.Добавить друга\n");
        builder.append("2.Добавить фотографию\n");
        builder.append("3.Просмотреть информацию о вашей странице\n");
        builder.append("4.Просмотреть ваши фотографии\n");
        builder.append("5.Найти друзей\n");
        builder.append("6.Просмотреть фотографии другого пользователя\n");
        builder.append("7.Лайкнуть фотографию\n");
        builder.append("8.Прокомментировать фотографию\n");
        builder.append("9.Редактировать свой профиль\n");
        builder.append("10.Выйти");
        System.out.println(builder);
        System.out.println();
    }

    @Override
    public void refactorProfile() {
        scanner = new Scanner(System.in);
        HashMap<Integer, String> params = new HashMap<>();
        System.out.println("Для редактирования доступны следующие части страницы:");
        System.out.println("1.Имя\n2.Фамилия\n3.Возраст\n4.Город\n5.Информация о себе");
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("Введите номер поля для редактирования, а потом его новое значение:");
                System.out.println("Если вы хотите закончить редактирование, введите \"Да\" чтобы соохранить изменения, или \"Нет\", чтобы ничего не изменять");
                Integer key = scanner.nextInt();

                if (params.containsKey(key)) {
                    System.out.println("Такое поле уже заполнено для изменения, введите другое");
                    i--;
                    continue;
                }
                if (key.equals("Да")) {
                    profileService.refactorProfile(params);
                    System.out.println("Сохранение прошло успешно");
                    return;
                } else if (key.equals("Нет")) {
                    System.out.println("Выход без изменения профиля");
                    return;
                }

                String value = scanner.nextLine();
                params.put(key, value);
            }

            profileService.refactorProfile(params);
            System.out.println("Профиль редактирован успешно");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void login() {
        scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в MyVK");
        System.out.print("Введите свой логин: ");
        String login = scanner.nextLine();
        try {
            profileService.login(login);
            System.out.println("Авторизация прошла успешно");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Хотите зарегистрироваться?");
            System.out.print("Напишите да или нет: ");
            String answer = scanner.nextLine();
            if (answer.equals("да")) {
                register();
            } else {
                System.exit(1);
            }
        }
    }

    private void register() {
        scanner = new Scanner(System.in);
        HashMap<String, String> params = new HashMap<>();
        System.out.print("Введите полное имя: ");
        params.put("name", scanner.nextLine());
        System.out.print("Введите свою фамилию: ");
        params.put("surname", scanner.nextLine());
        System.out.print("Возраст: ");
        params.put("age", scanner.nextLine());
        System.out.print("Ваш город: ");
        params.put("city", scanner.nextLine());
        System.out.println("Расскажите немного о себе");
        params.put("info", scanner.nextLine());
        System.out.print("Введите логин для будущей авторизации: ");
        params.put("login", scanner.nextLine());
        try {
            profileService.register(params);
            System.out.println("Регистрация прошла успешно!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void showActualProfile() {
        System.out.println("Информация о вашей странице");
        System.out.println(profileService.getActiveProfile().showProfile());
    }

    @Override
    public void showActualPhotos() {
        System.out.println("Ваши фотографии:");
        for (Photo photo:profileService.getActiveProfile().getPhotos()) {
            System.out.println(photo.showPhoto());
        }
    }

    @Override
    public void findFriends() {
        List<Profile> result = find();
        if (result.size() == 0) {
            System.out.println("Таких пользователей не существует");
            return;
        } else {
            for (Profile p: result) {
                System.out.println(p.showShortProfile(p));
            }
        }
    }

    private List<Profile> find() {
        scanner = new Scanner(System.in);
        System.out.println("Введите имя: ");
        String name = scanner.nextLine();
        System.out.println("Введите фамилию: ");
        String surname = scanner.nextLine();

        return DbProfileService.findProfiles(name, surname);
    }

    @Override
    public void addFriend() {
        try {
            Profile friend = findProfile();
               if (friend == null) {
                   System.out.println("Пользователя  с таким городом не существует");
                   return;
               } else {
                   profileService.addToFriends(friend);
                   System.out.println("Вы добавили нового друга!");
               }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addPhoto() {
        scanner = new Scanner(System.in);
        System.out.println("Напишите небольшое описание к фотографии:");
        String description = scanner.nextLine();
        profileService.getActiveProfile().addPhoto(description);
        System.out.println("Фотография была добавлена");

    }

    @Override
    public void showOtherPhotos() {
        try {
            Profile user = findProfile();
            if (user == null) {
                System.out.println("Пользователя  с таким городом не существует");
                return;
            } else {
                System.out.println("Его фотографии:");
                for (Photo photo: user.getPhotos()) {
                    System.out.println(photo.showPhoto());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void likePhoto() {
        scanner = new Scanner(System.in);
        try {
            Profile profile = findProfile();
            System.out.println("Фотографии пользователя:");
            for (int i = 0; i < profile.getPhotos().size(); i++) {
                System.out.printf("%d. %s\n", i + 1, profile.getPhotos().get(i).showShortPhoto());
            }
            System.out.println("Введите номер фотографии, которую вы хотите лайкнуть");
            int index = scanner.nextInt();
            if (index < 1 || index > profile.getPhotos().size() + 1) {
                System.out.println("Неправильно введено значение!");
                return;
            }
            Photo photo = profile.getPhotos().get(index - 1);
            profileService.like(photo);
            System.out.println("Вы оценили фотографию");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
		
		
    }

    @Override
    public void commentPhoto() {
        scanner = new Scanner(System.in);
        try {
            Profile profile = findProfile();
            System.out.println("Фотографии пользователя:");
            for (int i = 0; i < profile.getPhotos().size(); i++) {
                System.out.printf("%d. %s\n", i + 1, profile.getPhotos().get(i).showShortPhoto());
            }
            System.out.println("Введите номер фотографии, которую вы хотите прокомментировать");
            int index = scanner.nextInt();
            if (index < 1 || index > profile.getPhotos().size() + 1) {
                System.out.println("Неправильно введено значение!");
                return;
            }
            Photo photo = profile.getPhotos().get(index - 1);
            System.out.println("Напишите комментарий:");
            scanner = new Scanner(System.in);
            String comment = scanner.nextLine();
            profileService.comment(photo, comment);
            System.out.println("Вы прокомментировали фотографию фотографию");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Profile findProfile() throws Exception {
        scanner = new Scanner(System.in);
        List<Profile> profiles = find();
        if (profiles.size() == 0) {
            throw  new Exception("Таких пользователей не существует");
        }
        System.out.println("Теперь введите его город:");
        String city = scanner.nextLine();
        Profile profile = null;
        for (Profile p: profiles) {
            if (p.getCity().equals(city)) {
                profile = p;
            }
        }
        return profile;
    }


}
