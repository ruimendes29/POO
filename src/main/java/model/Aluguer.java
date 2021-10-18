package model;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Aluguer implements Serializable {

    /**
     * Variáveis de instância
     */
    private int nifCliente;
    private String email;
    private LocalDateTime time;
    private double price;
    private Point2D.Double origin;
    private Point2D.Double destination;
    private String combustivel;
    private String preferencia;

    public Aluguer(int nifCliente,
                   String email,
                   double xDestino,
                   double yDestino,
                   String combustivel,
                   String preferencia) {
        this.nifCliente = nifCliente;
        this.email = email;
        this.time = LocalDateTime.now();
        this.price = 0;
        this.origin = new Point2D.Double(0, 0);
        this.destination = new Point2D.Double(xDestino, yDestino);
        this.combustivel = combustivel;
        this.preferencia = preferencia;
    }

    public Aluguer(int nifCliente,
                   String email,
                   Point2D.Double origin,
                   Point2D.Double destination,
                   String combustivel,
                   String preferencia) {
        this.nifCliente = nifCliente;
        this.email = email;
        this.time = LocalDateTime.now();
        this.price = 0;
        this.origin = (Point2D.Double) origin.clone();
        this.destination = (Point2D.Double) destination.clone();
        this.combustivel = combustivel;
        this.preferencia = preferencia;
    }

    public Aluguer(int nifCliente,
                   String email,
                   Point2D.Double origin,
                   Point2D.Double destination,
                   double price,
                   String combustivel,
                   String preferencia) {
        this.nifCliente = nifCliente;
        this.email = email;
        this.time = LocalDateTime.now();
        this.price = price;
        this.origin = (Point2D.Double) origin.clone();
        this.destination = (Point2D.Double) destination.clone();
        this.combustivel = combustivel;
        this.preferencia = preferencia;
    }

    public Aluguer(int nifCliente,
                   String email,
                   double xOrigem,
                   double yOrigem,
                   double xDestino,
                   double yDestino,
                   double price,
                   String combustivel,
                   String preferencia) {
        this.nifCliente = nifCliente;
        this.email = email;
        this.time = LocalDateTime.now();
        this.price = price;
        this.origin = new Point2D.Double(xOrigem, yOrigem);
        this.destination = new Point2D.Double(xDestino, yDestino);
        this.combustivel = combustivel;
        this.preferencia = preferencia;
    }

    /**
     * Construtor parametrizado
     *
     * @param nif          NIF do cliente
     * @param email        Email do Cliente
     * @param price        Preço cobrado
     * @param originX      Componente x do sítio de partida
     * @param originY      Componente y do sítio de partida
     * @param destinationX Componente de x do sítio destino
     * @param destinationY Componente de y do sítio destino
     */
    public Aluguer(int nif,
                   String email,
                   double price,
                   double originX,
                   double originY,
                   double destinationX,
                   double destinationY) {
        this.nifCliente = nif;
        this.email = email;
        this.time = LocalDateTime.now();
        this.price = price;
        this.origin = new Point2D.Double(originX, originY);
        this.destination = new Point2D.Double(destinationX, destinationY);
        this.combustivel = "";
        this.preferencia = "";
    }

    /**
     * Construtor por cópia
     *
     * @param aluguer Aluguer a copiar
     */
    public Aluguer(Aluguer aluguer) {
        this.nifCliente = aluguer.getNifCliente();
        this.email = aluguer.getEmail();
        this.time = aluguer.getDate();
        this.price = aluguer.getPrice();
        this.origin = aluguer.getOrigin();
        this.destination = aluguer.getDestination();
        this.combustivel = aluguer.getCombustivel();
        this.preferencia = aluguer.getPreferencia();
    }

    /**
     * Permite obter o NIF do cliente que alugou
     *
     * @return NIF do cliente
     */
    public int getNifCliente() {
        return this.nifCliente;
    }

    /**
     * Permite obter o email do cliente que requisitou o aluguer
     *
     * @return Email do Cliente
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Permite obter a data/hora a que foi alugado um transporte
     *
     * @return Hora/Data do aluguer
     */
    public LocalDateTime getDate() {
        return this.time;
    }

    /**
     * Permite obter o preço de um aluguer
     *
     * @return Preço do aluguer
     */
    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Permite obter o local de partida de um aluguer
     *
     * @return Point2D.Double que representa o local de partida
     */
    public Point2D.Double getOrigin() {
        return (Point2D.Double) this.origin.clone();
    }

    /**
     * Permite obter o local de destino de um aluguer
     *
     * @return Point2D.Double que representa o local de destino
     */
    public Point2D.Double getDestination() {
        return (Point2D.Double) this.destination.clone();
    }

    public String getCombustivel() {
        return this.combustivel;
    }

    public String getPreferencia() {
        return this.preferencia;
    }

    /**
     * Permite obter uma cópia de um 'Aluguer'
     *
     * @return Cópia de um objeto do tipo 'Aluguer'
     */
    public Aluguer clone() {
        return new Aluguer(this);
    }

    /**
     * Permite determinar se duas instâncias de 'Aluguer' são iguais
     *
     * @param object Objeto a comparar
     * @return 'true' se forem iguais ou 'false' caso contrário
     */
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Aluguer aluguer = (Aluguer) object;
        return this.nifCliente == aluguer.getNifCliente() &&
                (this.email.compareTo(aluguer.getEmail()) == 0) &&
                this.time.equals(aluguer.getDate()) &&
                (Double.compare(this.price, aluguer.getPrice()) == 0) &&
                this.origin.equals(aluguer.getOrigin()) &&
                this.destination.equals(aluguer.getDestination()) &&
                (this.combustivel.compareTo(aluguer.getCombustivel()) == 0) &&
                (this.preferencia.compareTo(aluguer.getPreferencia()) == 0);
    }

    public List<String> toShow() {
        List<String> ls = new ArrayList<>();

        ls.add("Date: " + this.getDate().toString());
        ls.add("Origin: " + this.getOrigin().toString());
        ls.add("Destination: " + this.getDestination().toString());

        return ls;
    }

    /**
     * Permite obter uma representação textual de um objeto 'Aluguer'
     *
     * @return String com a representação textual de um objeto 'Aluguer'
     */
    @Override
    public String toString() {
        return "Aluguer{" +
                "nifCliente=" + this.nifCliente +
                ", email=" + this.email +
                ", time=" + this.time +
                ", price=" + this.price +
                ", origin=" + this.origin +
                ", destination=" + this.destination +
                ", combustivel= " + this.combustivel +
                ", preferencia= " + this.preferencia +
                '}';
    }
}
