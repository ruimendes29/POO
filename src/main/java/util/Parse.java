package util;

import exceptions.NoAvailableTransport;
import exceptions.NoSuchUser;
import model.*;
import view.IO;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Parse {
    private static final String EMAIL_SUFFIX = "@gmail.com";

    /**
     * Function that reads all lines from a file.
     *
     * @param file from where should read
     * @return List of Strings (each string is a line from the file)
     */
    public static List<String> readFile(String file) {
        List<String> lines = new ArrayList<>();
        BufferedReader inFile;
        String line;

        try {
            inFile = new BufferedReader(new FileReader(file));
            while ((line = inFile.readLine()) != null) lines.add(line);
        } catch (IOException e) {
            IO.error(e.getMessage());
        }

        return lines;
    }

    public static void saveObject(Object object, String file) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(object);
    }

    public static Object loadObject(String file) throws ClassNotFoundException, IOException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        Object obj = in.readObject();
        return obj;
    }

    public static UMCarroJa importData(String file) {
        List<String> lines = Parse.readFile(file);
        UMCarroJa model = new UMCarroJa();

        for (String line : lines) {
            Parse.processLine(model, line);
        }

        return model;
    }

    private static void processLine(UMCarroJa model, String line) {
        String[] keyword = line.split(":");
        String[] fields = keyword[1].split(",");
        switch (keyword[0]) {
            case "NovoProp":
                Owner owner = Parse.buildOwner(fields);
                if (owner != null) model.addOwner(owner);
                else IO.error(line);
                break;
            case "NovoCliente":
                Client client = Parse.buildClient(fields);
                if (client != null) model.addClient(client);
                else IO.error(line);
                break;
            case "NovoCarro":
                Transport transport = Parse.buildTransport(fields);
                if (transport != null) model.addTransport(Parse.buildTransport(fields));
                else IO.error(line);
                break;
            case "Aluguer":
                String email = fields[0] + Parse.EMAIL_SUFFIX;
                Transport tpt = null;
                Aluguer aluguer;
                try {
                    aluguer = Parse.buildAluguer(model, fields);
                    switch (fields[4]) {
                        case "MaisBarato":
                            switch (fields[3]) {
                                case "Electrico":
                                case "Gasolina":
                                    tpt = model.getCheapestCarNormal(email);
                                    break;
                                case "Hibrido":
                                    tpt = model.getCheapestCarHybrid(email);
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "MaisPerto":
                            switch (fields[3]) {
                                case "Electrico":
                                case "Gasolina":
                                    tpt = model.getClosestCarNormal(email);
                                    break;
                                case "Hibrido":
                                    tpt = model.getClosestCarHybrid(email);
                                    break;
                                default:
                                    break;
                            }
                            break;
                        default:
                            break;
                    }
                    if (aluguer != null && tpt != null) {
                        double distance = aluguer.getOrigin().distance(aluguer.getDestination());
                        double price = distance * tpt.getPriceKm();
                        aluguer.setPrice(price);
                        model.updateLocationClient(email, aluguer.getDestination());
                        model.updateLocationTransport(tpt.getId(), aluguer.getOrigin(), aluguer.getDestination());
                        model.addAluguerToClient(aluguer, email);
                        model.addAluguerToOwner(aluguer, tpt.getEmail());
                        model.addAluguerToTransport(aluguer, tpt.getId());
                    }
                } catch (NoAvailableTransport | NoSuchUser e) {
                    IO.error(e.getMessage() + " in line: " + line);
                    break;
                }
                break;
            case "Classificar":
                try {
                    Parse.classificar(model, fields);
                } catch (InputMismatchException | NumberFormatException e) {
                    IO.error(line);
                }
                break;
            default:
                IO.error(line);
                break;
        }
    }

    private static Owner buildOwner(String[] fields) {
        int nif;

        try {
            nif = Integer.parseInt(fields[1]);
        } catch (InputMismatchException | NumberFormatException e) {
            return null;
        }

        return new Owner(fields[0], nif, fields[2], fields[3]);
    }

    private static Client buildClient(String[] fields) {
        int nif;
        double posX, posY;

        try {
            nif = Integer.parseInt(fields[1]);
            posX = Double.parseDouble(fields[4]);
            posY = Double.parseDouble(fields[5]);

        } catch (InputMismatchException | NumberFormatException e) {
            return null;
        }

        return new Client(fields[0], nif, fields[2], fields[3], posX, posY);
    }

    private static Transport buildTransport(String[] fields) {
        int nif;
        double avgVelocity, priceKm, consumo, autonomia, posX, posY;

        try {
            nif = Integer.parseInt(fields[3]);
            avgVelocity = Double.parseDouble(fields[4]);
            priceKm = Double.parseDouble(fields[5]);
            consumo = Double.parseDouble(fields[6]);
            autonomia = Double.parseDouble(fields[7]);
            posX = Double.parseDouble(fields[8]);
            posY = Double.parseDouble(fields[9]);
        } catch (InputMismatchException | NumberFormatException e) {
            return null;
        }

        switch (fields[0]) {
            case "Electrico":
            case "Gasolina":
                return new Car(fields[1], fields[2], nif, "" + nif + Parse.EMAIL_SUFFIX, avgVelocity, priceKm, consumo, autonomia, posX, posY);
            case "Hibrido":
                return new Hybrid(fields[1], fields[2], nif, "" + nif + Parse.EMAIL_SUFFIX, avgVelocity, priceKm, consumo, autonomia, posX, posY);
            default:
                return null;
        }
    }

    private static Aluguer buildAluguer(UMCarroJa model, String[] fields) throws NoSuchUser {
        Point2D.Double origin, destination;
        int nif;
        String email = fields[0] + Parse.EMAIL_SUFFIX;

        try {
            nif = Integer.parseInt(fields[0]);
            destination = new Point2D.Double(Double.parseDouble(fields[1]), Double.parseDouble(fields[2]));
        } catch (InputMismatchException | NumberFormatException e) {
            return null;
        }

        if (model.existsClient(email)) {
            origin = model.getClient(email).getPosition();
        } else {
            throw new NoSuchUser("No client associated with NIF");
        }

        return new Aluguer(nif, email, origin, destination, fields[3], fields[4]);
    }

    private static void classificar(UMCarroJa model, String[] fields) throws InputMismatchException {
        try {
            int nif = Integer.parseInt(fields[0]);
            double rating = Double.parseDouble(fields[1]);
            String email = "" + nif + Parse.EMAIL_SUFFIX;
            if (model.existsClient(email)) {
                model.addRatingToClient(rating, email);
            } else if (model.existsOwner(email)) {
                model.addRatingToOwner(rating, email);
            }
        } catch (InputMismatchException | NumberFormatException e) {
            String matricula = fields[0];
            double ratingCarro = Double.parseDouble(fields[1]);
            if (model.existsTransport(matricula)) {
                model.addRatingToTransport(ratingCarro, matricula);
            }
        }
    }
}
