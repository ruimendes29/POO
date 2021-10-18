package view;

import java.awt.geom.Point2D;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class Input {

    public static String getString() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        String txt = "";
        while (!ok) {
            try {
                txt = input.nextLine();
                ok = true;
            } catch (InputMismatchException e) {
                IO.error(e.getMessage());
                out.print("New value: ");
                input.nextLine();
            }
        }
        //input.close();
        return txt;
    }

    public static int getInt() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        int i = 0;
        while (!ok) {
            try {
                i = input.nextInt();
                ok = true;
            } catch (InputMismatchException e) {
                IO.error(e.getMessage());
                out.print("New value: ");
                input.nextLine();
            }
        }
        //input.close();
        return i;
    }

    public static double getDouble() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        double d = 0.0;
        while (!ok) {
            try {
                d = input.nextDouble();
                ok = true;
            } catch (InputMismatchException e) {
                IO.error(e.getMessage());
                out.print("New value: ");
                input.nextLine();
            }
        }
        //input.close();
        return d;
    }

    public static float getFloat() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        float f = 0.0f;
        while (!ok) {
            try {
                f = input.nextFloat();
                ok = true;
            } catch (InputMismatchException e) {
                IO.error(e.getMessage());
                out.print("New value: ");
                input.nextLine();
            }
        }
        //input.close();
        return f;
    }

    public static boolean getBoolean() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        boolean b = false;
        while (!ok) {
            try {
                b = input.nextBoolean();
                ok = true;
            } catch (InputMismatchException e) {
                IO.error(e.getMessage());
                out.print("New value: ");
                input.nextLine();
            }
        }
        //input.close();
        return b;
    }

    public static void getEnter() {
        out.print("\nPress" + IO.COLOR_ANSI_PURPLE + " ENTER " + IO.COLOR_ANSI_RESET + "to continue...");
        Input.getString();
    }

    public static int getOption(int max) {
        int option;
        boolean ok = false;

        do {
            out.print("Please, choose an option from list: ");
            option = Input.getInt();

            if (option >= 0 && option <= max)
                ok = true;
            else
                IO.error("Invalid Option");
        } while (!ok);

        return option;
    }

    public static String getEmail() {
        String email;
        boolean ok = false;

        do {
            out.print("Please, type in your email: ");
            email = Input.getString();

            // https://howtodoinjava.com/regex/java-regex-validate-email-address/
            if (email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+.[a-zA-Z]+$"))
                ok = true;
            else
                IO.error("Invalid Email");

        } while (!ok);

        return email;
    }

    public static String getPassword() {
        out.print("Please, type in your password: ");
        String password = Input.getString();
        return password;
    }

    public static String getAddress() {
        out.print("Please, type in your address: ");
        String address = Input.getString();
        return address;
    }

    public static LocalDate getDate(String text) {
        out.println(text);
        LocalDate date = null;
        boolean valid = false;
        String format = "dd-MM-uuuu";
        DateTimeFormatter f = DateTimeFormatter.ofPattern(format);

        do {
            try {
                out.print("Date in the " + format + " format: ");
                date = LocalDate.parse(Input.getString(), f);
                if (date != null) {
                    valid = true;
                }
            } catch (DateTimeParseException e) {
                IO.error(e.getMessage());
            }
        } while (!valid);

        return date;
    }

    public static String getName() {
        out.print("Please, type in your name: ");
        String name = Input.getString();
        return name;
    }

    public static int getNIF() {
        out.print("Please, type in your NIF: ");
        int nif = Input.getInt();
        return nif;
    }

    public static String getRegistrationPlate() {
        String registration;
        boolean ok = false;

        do {
            out.print("Please, type in the registration plate number of the vehicle: ");
            registration = Input.getString();

            if (registration.matches("(^(?:[A-Z]{2}-\\d{2}-\\d{2})|(?:\\d{2}-[A-Z]{2}-\\d{2})|(?:\\d{2}-\\d{2}-[A-Z]{2})$)"))
                ok = true;
            else
                IO.error("Invalid Registration Plate Number");

        } while (!ok);

        return registration;
    }

    public static String getTransportBrand() {
        out.print("Please, type in the brand of the vehicle: ");
        String brand = Input.getString();
        return brand;
    }

    public static double getTransportAutonomy() {
        out.println("What's the autonomy of the vehicle? ");
        double autonomy = Input.getDouble();
        return autonomy;
    }

    public static Point2D.Double getUserPosition() {
        double x, y;

        out.println("Please, type in your current position coordenates.");

        out.print("X coordenate: ");
        x = Input.getDouble();

        out.print("Y coordenate: ");
        y = Input.getDouble();

        return new Point2D.Double(x, y);
    }

    public static Point2D.Double getTransportLocation() {
        double x, y;

        out.println("Please, type in the position of the vehicle.");

        out.print("X coordenate: ");
        x = Input.getDouble();

        out.print("Y coordenate: ");
        y = Input.getDouble();

        return new Point2D.Double(x, y);
    }

    public static double getTransportAvgVelocity() {
        out.print("Please, type in the vehicle average velocity: ");
        double avgVel = Input.getDouble();
        return avgVel;
    }

    public static double getTransportPriceKm() {
        out.print("Please, type in the amount you wish to charge per kilometer: ");
        double priceKm = Input.getDouble();
        return priceKm;
    }

    public static double getCarCapacity() {
        out.print("Please, type in the amount liters / kWh the car can store: ");
        double capacidade = Input.getDouble();
        return capacidade;
    }

    public static double getCarConsume() {
        out.print("Please, type in the consume of the car per km: ");
        double consumo = Input.getDouble();
        return consumo;
    }

    public static double getRating() {
        out.print("Please, type in the rating (0-100): ");
        boolean valid = false;
        double rating;

        do {
           rating = Input.getDouble();
           if (rating >= 0 && rating <= 100)
               valid = true;
           else
               IO.error("It must be between 0 and 100;");
        } while (!valid);

        return rating;
    }

    public static Point2D.Double getOrigin() {
        double x, y;

        out.println("Please, type in your current location.");

        out.print("X coordenate: ");
        x = Input.getDouble();

        out.print("Y coordenate: ");
        y = Input.getDouble();

        return new Point2D.Double(x, y);
    }

    public static Point2D.Double getDestination() {
        double x, y;

        out.println("Please, type in the desired destination.");

        out.print("X coordenate: ");
        x = Input.getDouble();

        out.print("Y coordenate: ");
        y = Input.getDouble();

        return new Point2D.Double(x, y);
    }
}
