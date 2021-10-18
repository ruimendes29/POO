package view;

import model.Notification;
import model.RatingNotification;
import model.RentNotification;
import org.apache.commons.lang3.StringUtils;
import util.Parse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

public class IO {

    public static final String[] opcoesMenuPrincipal = {
            "Login",
            "Create an account",
            "Import Data in logs.bak file",
            "Load applications previous data",
            "Save current application state",
            "Get TOP 10 clients by rents",
            "Get TOP 10 clients by Kms",
            "Exit Program",
    };

    public static final String[] opcoesMenuLogin = {
            "Client Account",
            "Owner Account",
            "Exit",
    };

    public static final String[] opcoesMenuCliente = {
            "Rent a car",
            "Consult rent history",
            "Pending Tasks",
            "Logout",
            "Exit",
    };

    public static final String[] opcoesMenuProprietario = {
            "Add car",
            "Consult rent history",
            "Pending Tasks",
            "Transport total income",
            "Logout",
            "Exit",
    };

    public static final String[] opcoesMenuCarro = {
            "Eletric",
            "Gasoline",
            "Hybrid",
            "Exit",
    };

    public static final String[] opcoesMenuAluguer = {
            "Rent the closest car",
            "Rent the cheapest car",
            "Rent the cheapest car within foot range",
            "Rent a specific car",
            "Rent car with desired autonomy",
            "Check cars rental history",
            "Exit",
    };

    public static final String[] opcoesMenuNotificacoes = {
            "Fulfill tasks",
            "Exit",
    };

    public static final String[] opcoesMenuAcceptDecline = {
            "Accept",
            "Decline",
    };

    private static final String ASCII_LOGO_PATH = "img/logo.ascii";

    public static final int WINDOW_WIDTH = 90;
    public static final int TEXT_MAX_WIDTH = 80;

    /* CORES PARA PRINTES NICE */
    public static final String COLOR_ANSI_RESET = "\u001B[0m";
    public static final String COLOR_ANSI_BLACK = "\u001B[30m";
    public static final String COLOR_ANSI_RED = "\u001B[31m";
    public static final String COLOR_ANSI_GREEN = "\u001B[32m";
    public static final String COLOR_ANSI_YELLOW = "\u001B[33m";
    public static final String COLOR_ANSI_BLUE = "\u001B[34m";
    public static final String COLOR_ANSI_PURPLE = "\u001B[35m";
    public static final String COLOR_ANSI_CYAN = "\u001B[36m";
    public static final String COLOR_ANSI_WHITE = "\u001B[37m";

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void error(String error) {
        System.out.println(COLOR_ANSI_RED + "\nError: " + COLOR_ANSI_RESET + error);
    }

    public static void info(String info) {
        System.out.println(COLOR_ANSI_BLUE + "\nInfo: " + COLOR_ANSI_RESET + info);
    }

    public static void showLogo() {
        List<String> lines = Parse.readFile(ASCII_LOGO_PATH);

        out.print(COLOR_ANSI_YELLOW);
        for (String line : lines) {
            System.out.println(StringUtils.center(line, IO.WINDOW_WIDTH));
        }
        out.print(COLOR_ANSI_RESET);
    }

    public int menu(String title, String[] options) {
        IO.clear();
        IO.showLogo();
        new Menu(title, options).show();
        out.println("\n");
        return Input.getOption(options.length - 1);
    }

    public int list(String title, String [] options) {
        new Menu(title, options).list();

        return Input.getOption(options.length - 1);
    }

    public void displayTable(List<List<String>> lines, String title) {
        IO.clear();
        IO.showLogo();

        System.out.println(StringUtils.center(
                StringUtils.rightPad(" ╔", IO.TEXT_MAX_WIDTH, "═") + "╗ ", IO.WINDOW_WIDTH)
        );

        System.out.println(StringUtils.center(
                StringUtils.center(
                        StringUtils.center(title, IO.TEXT_MAX_WIDTH - 2), IO.TEXT_MAX_WIDTH, "║"
                ), IO.WINDOW_WIDTH
                )
        );

        for (List<String> line : lines) {
            System.out.println(StringUtils.center(
                    StringUtils.rightPad("╠", IO.TEXT_MAX_WIDTH - 1, "═") + "╣", IO.WINDOW_WIDTH)
            );
            for (String field : line) {
                System.out.println(StringUtils.center(
                        StringUtils.rightPad("║ " + field, IO.TEXT_MAX_WIDTH - 2) + " ║", IO.WINDOW_WIDTH)
                );
            }
        }

        System.out.println(StringUtils.center(
                StringUtils.rightPad(" ╚", IO.TEXT_MAX_WIDTH, "═") + "╝ ", IO.WINDOW_WIDTH)
        );

        Input.getEnter();
    }

    public void displayElement(List<String> line, String title) {
        IO.clear();
        IO.showLogo();

        System.out.println(StringUtils.center(
                StringUtils.rightPad(" ╔", IO.TEXT_MAX_WIDTH, "═") + "╗ ", IO.WINDOW_WIDTH)
        );

        System.out.println(StringUtils.center(
                StringUtils.center(
                        StringUtils.center(title, IO.TEXT_MAX_WIDTH - 2), IO.TEXT_MAX_WIDTH, "║"
                ), IO.WINDOW_WIDTH
                )
        );

        for (String field : line) {
            System.out.println(StringUtils.center(
                    StringUtils.rightPad("╠", IO.TEXT_MAX_WIDTH - 1, "═") + "╣", IO.WINDOW_WIDTH)
            );
            System.out.println(StringUtils.center(
                    StringUtils.rightPad("║ " + field, IO.TEXT_MAX_WIDTH - 2) + " ║", IO.WINDOW_WIDTH)
            );
        }

        System.out.println(StringUtils.center(
                StringUtils.rightPad(" ╚", IO.TEXT_MAX_WIDTH, "═") + "╝ ", IO.WINDOW_WIDTH)
        );
    }

}
