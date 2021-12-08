package edu.csf.oop.java.acquaintance;

import edu.csf.oop.java.acquaintance.services.DbProfileService;
import edu.csf.oop.java.acquaintance.view.ConsoleView;
import edu.csf.oop.java.acquaintance.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger("info.logger");

    public static void main(String[] args) {
        logger.info("Start MyVK");
        View view = new ConsoleView();
        //Login or registers
        view.login();
        int login = 1;
        while (login == 1) {
            view.showMenu();
            login =  view.takeFunction();
        }
        logger.info("End MyVK");
    }
}
