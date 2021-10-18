package model;

import exceptions.AuthenticationError;
import exceptions.NoAvailableTransport;
import util.Parse;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class UMCarroJa implements Serializable {
    private static final String DATABASE_PATH = "data/db.ser";

    private Map<String, Owner> owners;
    private Map<String, Client> clients;
    private Map<String, Transport> transports;

    public UMCarroJa() {
        this.owners = new HashMap<>();
        this.clients = new HashMap<>();
        this.transports = new HashMap<>();
    }

    public UMCarroJa(UMCarroJa other) {
        this.owners = new HashMap<>();
        this.clients = new HashMap<>();
        this.transports = new HashMap<>();

        other.owners.forEach((k, v) -> this.owners.put(k, v.clone()));
        other.clients.forEach((k, v) -> this.clients.put(k, v.clone()));
        other.transports.forEach((k, v) -> this.transports.put(k, v.clone()));
    }

    @Override
    public UMCarroJa clone() {
        return new UMCarroJa(this);
    }

    public Set<Owner> getOwners() {
        Set<Owner> owners = new TreeSet<>();

        for (Owner owner : this.owners.values()) {
            owners.add(owner.clone());
        }

        return owners;
    }

    public Set<Client> getClients() {
        Set<Client> clients = new TreeSet<>();

        for (Client client : this.clients.values()) {
            clients.add(client.clone());
        }

        return clients;
    }

    public Set<Transport> getTransports() {
        Set<Transport> transports = new TreeSet<>();

        for (Transport transport : this.transports.values()) {
            transports.add(transport.clone());
        }

        return transports;
    }

    public Set<Transport> getCarsNormal() {
        Set<Transport> transports = new TreeSet<>();

        for (Transport transport : this.transports.values()) {
            if (transport instanceof Car) {
                transports.add(transport.clone());
            }
        }

        return transports;
    }

    public Set<Transport> getCarsHybrid() {
        Set<Transport> transports = new TreeSet<>();

        for (Transport transport : this.transports.values()) {
            if (transport instanceof Hybrid) {
                transports.add(transport.clone());
            }
        }

        return transports;
    }

    public void addClient(Client client) {
        this.clients.put(client.getEmail(), client);
    }

    public boolean existsClient(String email) {
        return this.clients.containsKey(email);
    }

    public Client getClient(String email) {
        return this.clients.get(email).clone();
    }

    public void updateLocationClient(String email, Point2D.Double location) {
        this.clients.get(email).setPosition(location);
    }

    public void addRatingToClient(double rating, String email) {
        this.clients.get(email).addRating(rating);
    }

    public void addAluguerToClient(Aluguer aluguer, String email) {
        this.clients.get(email).addAluguer(aluguer);
    }

    public void addNotificationToClient(Notification notification, String email) {
        this.clients.get(email).addNotification(notification);
    }

    public void addOwner(Owner owner) {
        this.owners.put(owner.getEmail(), owner);
    }

    public Owner getOwner(String email) {
        return this.owners.get(email).clone();
    }

    public boolean existsOwner(String email) {
        return this.owners.containsKey(email);
    }

    public void addRatingToOwner(double rating, String email) {
        this.owners.get(email).addRating(rating);
    }

    public void addAluguerToOwner(Aluguer aluguer, String email) {
        this.owners.get(email).addAluguer(aluguer);
    }

    public void addNotificationToOwner(Notification notification, String email) {
        this.owners.get(email).addNotification(notification);
    }

    public void addTransport(Transport transport) {
        this.transports.put(transport.getId(), transport);
    }

    public Transport getTransport(String id) {
        return this.transports.get(id).clone();
    }

    public boolean existsTransport(String id) {
        return this.transports.containsKey(id);
    }

    public void addRatingToTransport(double rating, String id) {
        this.transports.get(id).addRating(rating);
    }

    public void addAluguerToTransport(Aluguer aluguer, String id) {
        this.transports.get(id).addAluguer(aluguer);
    }

    public void updateLocationTransport(String id, Point2D.Double origin, Point2D.Double destination) {
        this.transports.get(id).moveTransport(origin, destination);
    }

    public void refillTransport(String id) {
        this.transports.get(id).refill();
    }

    public Transport getClosestCarNormal(String email) throws NoAvailableTransport {
        return this.clients.get(email).getClosestCarNormal(this.getTransports());
    }

    public Transport getClosestCarHybrid(String email) throws NoAvailableTransport {
        return this.clients.get(email).getClosestCarHybrid(this.getTransports());
    }

    public Transport getCheapestCarNormal(String email) throws NoAvailableTransport {
        return this.clients.get(email).getCheapestCarNormal(this.getTransports());
    }

    public Transport getCheapestCarHybrid(String email) throws NoAvailableTransport {
        return this.clients.get(email).getCheapestCarHybrid(this.getTransports());
    }

    public Transport getCheapestCarNormalInWalkRange(String email, double walk) throws NoAvailableTransport {
        return this.clients.get(email).getCheapestTransportInWalkRange(this.getCarsNormal(), walk);
    }

    public Transport getCheapestCarHybridInWalkRange(String email, double walk) throws NoAvailableTransport {
        return this.clients.get(email).getCheapestTransportInWalkRange(this.getCarsHybrid(), walk);
    }

    public RentNotification getTransportRentNotification(String client, String registration, String mode, Point2D.Double destination) {
        double distance = this.getClient(client).getPosition().distance(destination);
        double eta = distance / this.getTransport(registration).getAvgVelocity();
        double price = this.getTransport(registration).getPriceKm() * distance;

        return new RentNotification(this.clients.get(client).getNif(), registration,
                client, mode, destination, price, eta + this.getDelay(client, eta));
    }

    public double getDelay(String client, double eta) {
        double delay = 0;
        int hour = LocalDateTime.now().getHour();


        if ((hour >= 8 && hour < 10) || (hour >= 17 && hour < 19)) delay = eta * 0.20;

        delay += eta * 0.001 * new Weather(this.clients.get(client).getPosition()).getPrecipitation();

        return delay;
    }

    public boolean loginCliente(String email, String password) throws AuthenticationError {
        if (this.existsClient(email)) {
            if (this.clients.get(email).getHashedPassword().equals(Client.hashPassword(password))) {
                return true;
            } else {
                return false;
            }
        } else {
            throw new AuthenticationError("No such user");
        }
    }

    public boolean loginOwner(String email, String password) throws AuthenticationError {
        if (this.existsOwner(email)) {
            if (this.owners.get(email).getHashedPassword().equals(Owner.hashPassword(password))) {
                return true;
            } else {
                return false;
            }
        } else {
            throw new AuthenticationError("No such user");
        }
    }

    public void save() throws IOException {
        Parse.saveObject(this, DATABASE_PATH);
    }

    public UMCarroJa load() throws ClassNotFoundException, IOException {
        return (UMCarroJa) Parse.loadObject(DATABASE_PATH);
    }

    public int getSize() {
        return this.clients.size() + this.owners.size() + this.transports.size();
    }

    public List<List<String>> getTopClientsBy(int n, String comparator) {
        SortedSet<Client> aux;
        List<List<String>> ret = new ArrayList<>();

        if (comparator.equals("travelledKms")) {
            aux = new TreeSet<>(User.userComparators.get("travelledKms"));
        } else if (comparator.equals("performedRents")) {
            aux = new TreeSet<>(User.userComparators.get("performedRents"));
        } else {
            aux = new TreeSet<>();
        }

        for (Client c : this.clients.values())
            aux.add(c.clone());

        Iterator<Client> itr = aux.iterator();

        for (int i = 0; itr.hasNext() && i < n; i++) {
            List<String> ls = new ArrayList<>();
            Client client = itr.next();
            ls.add("Email: " + client.getEmail());
            ls.add("Used the application: " + client.performedRents() + " times");
            ls.add("Travelled: " + client.travelledKms() + " kms");
            ret.add(ls);
        }

        return ret;
    }

    public List<List<String>> getRentsFromClient(String email) {
        List<List<String>> ret = new ArrayList<>();
        List<Aluguer> alugueres = this.clients.get(email).getRents();

        for (Aluguer aluguer : alugueres) {
            ret.add(aluguer.toShow());
        }

        return ret;
    }

    public List<List<String>> getRentsFromClientBetween(String email, LocalDateTime begin, LocalDateTime end) {
        List<List<String>> ret = new ArrayList<>();
        List<Aluguer> alugueres = this.clients.get(email).getRents();

        for (Aluguer aluguer : alugueres) {
            if (aluguer.getDate().isAfter(begin) && aluguer.getDate().isBefore(end)) {
                ret.add(aluguer.toShow());
            }
        }

        return ret;
    }

    public List<List<String>> getRentsFromOwner(String email) {
        List<List<String>> ret = new ArrayList<>();
        List<Aluguer> alugueres = this.owners.get(email).getRents();

        for (Aluguer aluguer : alugueres) {
            ret.add(aluguer.toShow());
        }

        return ret;
    }

    public List<List<String>> getRentsFromOwnerBetween(String email, LocalDateTime begin, LocalDateTime end) {
        List<List<String>> ret = new ArrayList<>();
        List<Aluguer> alugueres = this.owners.get(email).getRents();

        for (Aluguer aluguer : alugueres) {
            if (aluguer.getDate().isAfter(begin) && aluguer.getDate().isBefore(end)) {
                ret.add(aluguer.toShow());
            }
        }

        return ret;
    }

    public List<Notification> getPendingTasks(String email) {
        List<Notification> ret = new ArrayList<>();

        if (this.existsClient(email)) {
            for (Notification notification : this.clients.get(email).getPendingTasks()) {
                if (notification.isPendent())
                    ret.add(notification);
            }
        } else if (this.existsOwner(email)) {
            for (Notification notification : this.owners.get(email).getPendingTasks()) {
                if (notification.isPendent())
                    ret.add(notification);
            }
        }

        return ret;
    }

    public List<List<String>> getPendingTasksFromClient(String email) {
        List<List<String>> ret = new ArrayList<>();
        List<Notification> notifications = this.clients.get(email).getPendingTasks();

        for (Notification notification : notifications) {
            ret.add(notification.toShow());
        }

        return ret;
    }

    public List<List<String>> getPendingTasksFromOwner(String email) {
        List<List<String>> ret = new ArrayList<>();
        List<Notification> notifications = this.owners.get(email).getPendingTasks();

        for (Notification notification : notifications) {
            if (notification.isPendent()) {
                ret.add(notification.toShow());
            }
        }

        return ret;
    }

    public String getWeatherFromClient(String email) {
        return new Weather(this.clients.get(email).getPosition()).toString();
    }

    public List<List<String>> getAvaibleTransportsByDesiredAutonomy(Set<Transport> transports, double autonomy) {
        List<List<String>> ret = new ArrayList<>();

        for (Transport transport : transports) {
            if (transport.isAvailable() && transport.hasAutonomy(autonomy)) {
                ret.add(transport.toShow());
            }
        }

        return ret;
    }

    public List<List<String>> getAvaibleTransportsNormal() {
        List<List<String>> ret = new ArrayList<>();

        for (Transport transport : this.transports.values()) {
            if (transport.isAvailable() && transport instanceof Car) {
                ret.add(transport.toShow());
            }
        }

        return ret;
    }

    public List<List<String>> getAvaibleTransportsHybrid() {
        List<List<String>> ret = new ArrayList<>();

        for (Transport transport : this.transports.values()) {
            if (transport.isAvailable() && transport instanceof Hybrid) {
                ret.add(transport.toShow());
            }
        }

        return ret;
    }

    public List<List<String>> getTransportsFromOwner(String email) {
        List<List<String>> ret = new ArrayList<>();

        for (Transport transport : this.transports.values()) {
            if (transport.getEmail().equals(email)) {
                ret.add(transport.toShow());
            }
        }

        return ret;
    }

    public List<List<String>> getTotalTransportIncome(String id, LocalDateTime begin, LocalDateTime end) {
        List<List<String>> ret = new ArrayList<>();
        List<String> ls = this.transports.get(id).toShow();
        double income = 0;

        for (Aluguer aluguer : this.transports.get(id).getAlugueres()) {
            if (aluguer.getDate().isAfter(begin) && aluguer.getDate().isBefore(end))
                income += aluguer.getPrice();
        }

        ls.add("Total income: " + income + " €");

        ret.add(ls);

        return ret;
    }
}
