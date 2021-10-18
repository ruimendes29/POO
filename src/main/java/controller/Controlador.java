package controller;

import exceptions.AuthenticationError;
import exceptions.NoAvailableTransport;
import model.*;
import util.Parse;
import view.IO;
import view.Input;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Controlador {

    private IO gui;
    private UMCarroJa model;
    /* Conta que está login */
    private String email;

    private static final int LOGIN = 1;
    private static final int CREATE_ACCOUNT = 2;
    private static final int IMPORT_DATA = 3;
    private static final int LOAD_DATA = 4;
    private static final int SAVE_DATA = 5;
    private static final int GET_TOP_10_CLIENTS_BY_RENTS = 6;
    private static final int GET_TOP_10_CLIENTS_BY_KMS = 7;
    private static final int EXIT = 0;

    private static final int CLIENT = 1;
    private static final int OWNER = 2;

    private static final int ELETRIC = 1;
    private static final int GASOLINE = 2;
    private static final int HYBRID = 3;

    private static final String LOG_FILE = "data/logs.bak";

    public Controlador() {
        this.gui = new IO();
        this.model = new UMCarroJa();
        this.email = null;
    }

    public void run() {
        int option = this.gui.menu("Main Menu", IO.opcoesMenuPrincipal);

        switch (option) {
            case LOGIN:
                this.login();
                break;
            case CREATE_ACCOUNT:
                this.signup();
                break;
            case IMPORT_DATA:
                this.model = Parse.importData(LOG_FILE);
                IO.info("Imported a total of " + this.model.getSize() + " between transports, clients and owners");
                Input.getEnter();
                this.run();
                break;
            case LOAD_DATA:
                try {
                    this.model = this.model.load();
                } catch (ClassNotFoundException | IOException e) {
                    IO.error(e.getMessage());
                }
                Input.getEnter();
                this.run();
                break;
            case SAVE_DATA:
                try {
                    this.model.save();
                } catch (IOException e) {
                    IO.error(e.getMessage());
                }
                Input.getEnter();
                this.run();
                break;
            case GET_TOP_10_CLIENTS_BY_RENTS:
                this.gui.displayTable(this.model.getTopClientsBy(10, "performedRents"), "Top 10 Clients by Rents");
                this.run();
                break;
            case GET_TOP_10_CLIENTS_BY_KMS:
                this.gui.displayTable(this.model.getTopClientsBy(10, "travelledKms"), "Top 10 Clients by Kms");
                this.run();
                break;
            case EXIT:
                option = this.gui.list("Do you want to save?", IO.opcoesMenuAcceptDecline);

                if (option == 0) { // Accepts
                    try {
                        this.model.save();

                    } catch (IOException e) {
                        IO.error("Saving was not possible: " + e.getMessage());
                    }
                }

                System.exit(0);
                break;
        }
    }

    public void login() {
        int option = this.gui.menu("Login Menu", IO.opcoesMenuLogin);

        switch (option) {
            case EXIT:
                this.run();
                break;
            default:
                String email = Input.getEmail();
                String password = Input.getPassword();
                try {
                    if (option == CLIENT) {
                        if (this.model.loginCliente(email, password)) {
                            this.email = email;
                            this.client();
                        } else {
                            IO.error("Wrong Password!");
                        }
                    } else if (option == OWNER) {
                        if (this.model.loginOwner(email, password)) {
                            this.email = email;
                            this.owner();
                        } else {
                            IO.error("Wrong Password!");
                        }
                    }
                } catch (AuthenticationError e) {
                    IO.error(e.getMessage());
                }
                Input.getEnter();
                this.run();
        }
    }

    public void signup() {
        int option = this.gui.menu("Sign up Menu", IO.opcoesMenuLogin);

        Point2D.Double pos;
        String nome, email, password, address;
        int nif;

        switch (option) {
            case CLIENT:
                nome = Input.getName();
                nif = Input.getNIF();
                email = Input.getEmail();
                password = Input.getPassword();
                address = Input.getAddress();
                pos = Input.getUserPosition();
                this.model.addClient(new Client(nome, nif, email, password, address, pos.getX(), pos.getY()));
                this.login();
                break;
            case OWNER:
                nome = Input.getName();
                nif = Input.getNIF();
                email = Input.getEmail();
                password = Input.getPassword();
                address = Input.getAddress();
                this.model.addOwner(new Owner(nome, nif, email, password, address));
                this.login();
                break;
            case EXIT:
                this.run();
                break;
        }

    }

    public void client() {
        int option = this.gui.menu("Client Menu", IO.opcoesMenuCliente);

        switch (option) {
            case 1: // Rent a Car
                this.rentCar();
                this.client();
                break;
            case 2: // Consult rent history
                LocalDate begin = Input.getDate("Please, type the begin date.");
                LocalDate end = Input.getDate("Please, type the end date.");
                this.gui.displayTable(this.model.getRentsFromClientBetween(this.email, begin.atStartOfDay(), end.atTime(23, 59)), "Client Rents History (" + this.email + ")");
                this.client();
                break;
            case 3: // Pending Tasks
                this.gui.displayTable(this.model.getPendingTasksFromClient(this.email), "Notifications");
                option = this.gui.menu("Notifications Menu", IO.opcoesMenuNotificacoes);
                if (option == 1) { // Fulfill Notifications
                    List<Notification> notifications = this.model.getPendingTasks(this.email);
                    for (Notification notification : notifications) {
                        if (notification instanceof RatingNotification) {
                            this.gui.displayElement(notification.toShow(), "Rating Notification");
                            double rating = Input.getRating();
                            this.model.addRatingToTransport(rating, ((RatingNotification) notification).getId());
                            notification.setStatus(1);
                        }
                    }
                }
                IO.info("No more tasks");
                Input.getEnter();
                this.client();
                break;
            case 4: // Logout
                this.email = null;
                this.login();
                break;
            case EXIT:
                System.exit(0);
                break;
        }
    }

    public void owner() {
        int option = this.gui.menu("Owner Menu", IO.opcoesMenuProprietario);
        LocalDateTime begin;
        LocalDateTime end;

        switch (option) {
            case 1: // Add car
                this.addCar();
                this.owner();
                break;
            case 2: // Consult rent history
                begin = Input.getDate("Please, type the begin date.").atStartOfDay();
                end = Input.getDate("Please, type the end date.").atTime(23, 59);
                this.gui.displayTable(this.model.getRentsFromOwnerBetween(this.email, begin, end), "Client Rents History (" + this.email + ")");
                this.owner();
                break;
            case 3: // Pending Tasks
                this.gui.displayTable(this.model.getPendingTasksFromOwner(this.email), "Notifications");
                option = this.gui.menu("Notifications Menu", IO.opcoesMenuNotificacoes);
                if (option == 1) { // Fulfill Notifications
                    List<Notification> notifications = this.model.getPendingTasks(this.email);
                    for (Notification notification : notifications) {
                        if (notification instanceof RatingNotification) {
                            this.gui.displayElement(notification.toShow(), "Rating Notification");
                            double rating = Input.getDouble();
                            this.model.addRatingToClient(rating, ((RatingNotification) notification).getId());
                            notification.setStatus(1);
                        }
                        if (notification instanceof RentNotification) {
                            this.gui.displayElement(notification.toShow(), "Rent Notification");
                            option = this.gui.list("Choose an action", IO.opcoesMenuAcceptDecline);
                            if (option == 0) { // Accept
                                RentNotification rnt = (RentNotification) notification;

                                // Adicionar notificao de classificao ao cliente
                                this.model.addNotificationToClient(
                                        new RatingNotification(rnt.getId(), 2, rnt.getEta())
                                        , rnt.getClient()
                                );

                                Point2D.Double origin = this.model.getClient(rnt.getClient()).getPosition();

                                // TODO: Ao criar aluguer, o tipo de combustivel não existe maneira de o descobrir
                                Aluguer aluguer = new Aluguer(rnt.getNifCliente(), rnt.getClient()
                                        , origin, rnt.getDestination()
                                        , origin.distance(rnt.getDestination()) * this.model.getTransport(rnt.getId()).getPriceKm()
                                        , "Combustivel", rnt.getMode()
                                );

                                if (!this.model.getTransport(rnt.getId()).hasAutonomy(rnt.getDestination())) {
                                    option = this.gui.list("Your transport doens't have enough autonomy. Do you want to refill?", IO.opcoesMenuAcceptDecline);
                                    if (option == 0) this.model.refillTransport(rnt.getId());
                                    else notification.setStatus(-1);
                                }

                                if (option == 0) { // Isto quer dizer ou que não foi preciso fazer refill ou então ele aceitou fazer
                                    this.model.addAluguerToClient(aluguer, rnt.getClient());
                                    this.model.addAluguerToOwner(aluguer, this.email);
                                    this.model.addAluguerToTransport(aluguer, rnt.getId());

                                    // Mover o client para o destino
                                    this.model.updateLocationClient(rnt.getClient(), rnt.getDestination());

                                    // Mover o carro até ao origen e depois para o destino
                                    this.model.updateLocationTransport(rnt.getId(), aluguer.getOrigin(), aluguer.getDestination());

                                    notification.setStatus(1);
                                }
                            } else { // Decline
                                notification.setStatus(-1);
                            }
                        }
                    }
                }
                IO.info("No more tasks");
                Input.getEnter();
                this.owner();
                break;
            case 4: // Transport total income
                this.gui.displayTable(this.model.getTransportsFromOwner(this.email), "List of Transports form " + this.email);
                String registration = Input.getRegistrationPlate();
                begin = Input.getDate("Please, type the begin date.").atStartOfDay();
                end = Input.getDate("Please, type the end date.").atTime(23, 59);
                this.gui.displayTable(this.model.getTotalTransportIncome(registration, begin, end), "Total Income from Transport");
                this.owner();
                break;
            case 5: // Logout
                this.email = null;
                this.login();
                break;
            case EXIT:
                System.exit(0);
                break;
        }
    }

    public void rentCar() {
        int typeofcar = this.gui.menu("Type of Car to Rent (" + this.model.getWeatherFromClient(this.email) + ")", IO.opcoesMenuCarro);

        switch (typeofcar) {
            case EXIT:
                this.client();
                break;
            default:
                // TODO: distinguir carros eletricos de carros normais
                int option = this.gui.menu("Rent Menu", IO.opcoesMenuAluguer);
                if(option == EXIT) break;

                Transport transport;
                String registration;
                RentNotification rentNotification;
                boolean valid;
                Point2D.Double origin = Input.getOrigin();
                this.model.updateLocationClient(this.email, origin);
                Point2D.Double destination = Input.getDestination();

                try {
                    switch (option) {
                        case 1: // Rent the closest car
                            if (typeofcar == HYBRID) { // Hybrid
                                transport = this.model.getClosestCarHybrid(this.email);
                            } else { // Eletric || Gasoline
                                transport = this.model.getClosestCarNormal(this.email);
                            }

                            rentNotification = this.model.getTransportRentNotification(this.email, transport.getId(), "MaisPerto", destination);

                            this.gui.displayElement(rentNotification.toShow(), "Rent");
                            option = this.gui.list("Choose action", IO.opcoesMenuAcceptDecline);

                            if (option == 0) { // Accept
                                this.model.addNotificationToOwner(rentNotification, transport.getEmail());
                                IO.info("Wait for the owner " + transport.getEmail() + " to accept your order for the transport " + transport.getId());
                                Input.getEnter();
                            }
                            break;
                        case 2: // Rent the cheapest car
                            if (typeofcar == HYBRID) { // Hybrid
                                transport = this.model.getCheapestCarHybrid(this.email);
                            } else { // Eletric || Gasoline
                                transport = this.model.getCheapestCarNormal(this.email);
                            }

                            rentNotification = this.model.getTransportRentNotification(this.email, transport.getId(), "MaisPerto", destination);

                            this.gui.displayElement(rentNotification.toShow(), "Rent");
                            option = this.gui.list("Choose action", IO.opcoesMenuAcceptDecline);

                            if (option == 0) { // Accept
                                this.model.addNotificationToOwner(rentNotification, transport.getEmail());
                                IO.info("Wait for the owner " + transport.getEmail() + " to accept your order for the transport " + transport.getId());
                                Input.getEnter();
                            }
                            break;
                        case 3: // Rent the cheapest car within foot range
                            System.out.print("How far are you willing to walk (in kms)? ");
                            double walk = Input.getDouble();
                            if (typeofcar == HYBRID) { // Hybrid
                                transport = this.model.getCheapestCarHybridInWalkRange(this.email, walk);
                            } else { // 1 Eletric || 2 Gasoline
                                transport = this.model.getCheapestCarNormalInWalkRange(this.email, walk);
                            }

                            rentNotification = this.model.getTransportRentNotification(this.email, transport.getId(), "MaisPerto", destination);

                            this.gui.displayElement(rentNotification.toShow(), "Rent");
                            option = this.gui.list("Choose action", IO.opcoesMenuAcceptDecline);

                            if (option == 0) { // Accept
                                this.model.addNotificationToOwner(rentNotification, transport.getEmail());
                                IO.info("Wait for the owner " + transport.getEmail() + " to accept your order for the transport " + transport.getId());
                                Input.getEnter();
                            }
                            break;
                        case 4: // Rent a specific car
                            List<List<String>> cars;
                            valid = false;

                            if (typeofcar == HYBRID) { // Hybrid
                                cars = this.model.getAvaibleTransportsHybrid();
                            } else { // 1 Eletric || 2 Gasoline
                                cars = this.model.getAvaibleTransportsNormal();
                            }

                            if (cars.size() != 0) {
                                this.gui.displayTable(cars, "List of Avaible Transports");
                                do {
                                    registration = Input.getRegistrationPlate();
                                    if (this.model.existsTransport(registration) && this.model.getTransport(registration).isAvailable())
                                        valid = true;
                                    else
                                        IO.error("Requested transport is not available or doesn't exist.");
                                } while (!valid);

                                transport = this.model.getTransport(registration);

                                rentNotification = this.model.getTransportRentNotification(this.email, transport.getId(), "MaisPerto", destination);

                                this.gui.displayElement(rentNotification.toShow(), "Rent");
                                option = this.gui.list("Choose action", IO.opcoesMenuAcceptDecline);

                                if (option == 0) { // Accept
                                    this.model.addNotificationToOwner(rentNotification, transport.getEmail());
                                    IO.info("Wait for the owner " + transport.getEmail() + " to accept your order for the transport " + transport.getId());
                                    Input.getEnter();
                                }
                            } else {
                                IO.error("No avaible transports.\nPlease, try again later.");
                                Input.getEnter();
                            }
                            break;
                        case 5: // Rent car with desired autonomy
                            List<List<String>> list;
                            double autonomy = Input.getTransportAutonomy();
                            valid = false;

                            if (typeofcar == HYBRID) { // Hybrid
                                list = this.model.getAvaibleTransportsByDesiredAutonomy(this.model.getCarsHybrid(), autonomy);
                            } else { // 1 Eletric || 2 Gasoline
                                list = this.model.getAvaibleTransportsByDesiredAutonomy(this.model.getCarsNormal(), autonomy);
                            }

                            if (list.size() != 0) {
                                this.gui.displayTable(list, "List of Avaible Transports with desired Autonomy");
                                do {
                                    registration = Input.getRegistrationPlate();
                                    if (this.model.existsTransport(registration) && this.model.getTransport(registration).isAvailable())
                                        valid = true;
                                    else
                                        IO.error("Requested transport is not available or doesn't exist.");
                                } while (!valid);

                                transport = this.model.getTransport(registration);

                                rentNotification = this.model.getTransportRentNotification(this.email, transport.getId(), "MaisPerto", destination);

                                this.gui.displayElement(rentNotification.toShow(), "Rent");
                                option = this.gui.list("Choose action", IO.opcoesMenuAcceptDecline);

                                if (option == 0) { // Accept
                                    this.model.addNotificationToOwner(rentNotification, transport.getEmail());
                                    IO.info("Wait for the owner " + transport.getEmail() + " to accept your order for the transport " + transport.getId());
                                    Input.getEnter();
                                }
                            } else {
                                IO.error("No available transports with desired autonomy.\nPlease, try again later.");
                                Input.getEnter();
                            }
                            break;
                    }
                } catch (NoAvailableTransport e) {
                    IO.error(e.getMessage() + "\nTry again later, please.");
                }
        }
    }

    public void addCar() {
        int option = this.gui.menu("Type of Car to Add", IO.opcoesMenuCarro);

        String brand, registration;
        double avgVel, priceKm, capacity, consume;
        Point2D.Double position;
        Transport transport;

        switch (option) {
            case EXIT:
                this.owner();
                break;
            default:
                registration = Input.getRegistrationPlate();

                if (this.model.existsTransport(registration)) {
                    IO.error("The transport ID already exists");
                    break;
                }

                brand = Input.getTransportBrand();
                avgVel = Input.getTransportAvgVelocity();
                priceKm = Input.getTransportPriceKm();
                capacity = Input.getCarCapacity();
                consume = Input.getCarConsume();
                position = Input.getTransportLocation();

                // autonomy = Input.getTransportAutonomy(); // TODO: Não se pede isto ¯\_(ツ)_/¯

                if (option == ELETRIC || option == GASOLINE) { // Eletric or Gasoline
                    // TODO: distinguir carros eletricos de carros a gasolina
                    transport = new Car(brand, registration, this.model.getOwner(this.email).getNif(), this.email, avgVel, priceKm, consume, capacity, position);
                } else { // Hybrid
                    transport = new Hybrid(brand, registration, this.model.getOwner(this.email).getNif(), this.email, avgVel, priceKm, consume, capacity, position);
                }

                this.model.addTransport(transport);
                IO.info("A Car with the registration plate number: " + registration + " was added successfully!");
                Input.getEnter();
        }
    }
}
