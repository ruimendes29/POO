package model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class RentNotification extends Notification {

    private int nifCliente;
    private String id;
    private String cliente;
    private String mode;
    private Point2D.Double destination;
    private double price;
    /**
     * Estimated Time to Arrival
     */
    private double eta;

    public RentNotification(int nifCliente,
                            String id,
                            String cliente,
                            String mode,
                            Point2D.Double destination,
                            double price,
                            double eta) {
        super();
        this.nifCliente = nifCliente;
        this.id = id;
        this.cliente = cliente;
        this.mode = mode;
        this.destination = (Point2D.Double) destination.clone();
        this.price = price;
        this.eta = eta;
    }

    public RentNotification(RentNotification n) {
        super(n);
        this.nifCliente = n.getNifCliente();
        this.id = n.getId();
        this.cliente = n.getClient();
        this.mode = n.getMode();
        this.destination = n.getDestination();
        this.price = n.getPrice();
        this.eta = n.getEta();
    }

    public int getNifCliente() {
        return this.nifCliente;
    }

    public String getId() {
        return this.id;
    }

    public String getClient() {
        return this.cliente;
    }

    public String getMode() {
        return this.mode;
    }

    public Point2D.Double getDestination() {
        return this.destination;
    }

    public double getPrice() {
        return this.price;
    }

    public double getEta() {
        return this.eta;
    }

    public void setNifCliente(int nif) {
        this.nifCliente = nif;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setDestination(double posX, double posY) {
        this.destination = new Point2D.Double();
        this.destination.setLocation(posX, posY);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setEta(double eta) {
        this.eta = eta;
    }

    public RentNotification clone() {
        return new RentNotification(this);
    }

    public List<String> toShow() {
        List<String> ret = new ArrayList<>();

        ret.add("Client: " + this.getClient());
        ret.add("Registration Plate Number: " + this.getId());
        ret.add("Mode: " + this.getMode());
        ret.add("Estimated Time: " + this.getEta());
        ret.add("Price: " + this.getPrice());
        ret.add("Destination: " + this.getDestination().toString());

        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RentNotification that = (RentNotification) o;

        return this.nifCliente == that.nifCliente
                && (Double.compare(that.price, this.price) == 0)
                && (Double.compare(that.eta, this.eta) == 0)
                && this.id.equals(that.id)
                && this.cliente.equals(that.cliente)
                && this.mode.equals(that.mode)
                && this.destination.equals(that.destination);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = this.nifCliente;
        result = 31 * result + this.id.hashCode();
        result = 31 * result + this.cliente.hashCode();
        result = 31 * result + this.mode.hashCode();
        result = 31 * result + this.destination.hashCode();
        temp = Double.doubleToLongBits(this.price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.eta);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
