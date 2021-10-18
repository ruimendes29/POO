package model;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Transport implements Comparable<Transport>, Serializable {

    /**
     * Variáveis de instância
     */
    private String matricula;
    private int nifDono;
    private String email;
    private Point2D.Double position;
    private double autonomy;
    private double capacity;
    private double avgVelocity;
    private double rating;
    private double priceKm;
    private List<Aluguer> alugueres;
    private List<Double> classificacoes;
    private LocalDateTime availableAt;

    private static double defaultRating = 50;
    private static double defaultPriceKm = 1.5;

    /**
     * Construtor parametrizado
     *
     * @param id          Identificador do transporte
     * @param nifDono     NIF do proprietário do carro
     * @param email       Email
     * @param avgVelocity Velocidade média do carro
     * @param priceKm     Preço por quilómetro do carro
     * @param capacity    Capacidade
     * @param posX        Componente x da posição do carro
     * @param posY        Componente y da posição do carro
     */
    public Transport(String id, int nifDono, String email, double avgVelocity, double priceKm, double capacity,
                     double posX, double posY) {
        this.matricula = id;
        this.nifDono = nifDono;
        this.email = email;
        this.position = new Point2D.Double(posX, posY);
        this.capacity = capacity;
        this.autonomy = capacity;
        this.avgVelocity = avgVelocity;
        this.rating = defaultRating;
        this.priceKm = priceKm;
        this.alugueres = new ArrayList<>();
        this.classificacoes = new ArrayList<>();
        this.availableAt = null;
    }

    public Transport(String id, int nifDono, String email, double avgVelocity, double priceKm, double capacity,
                     Point2D.Double position) {
        this.matricula = id;
        this.nifDono = nifDono;
        this.email = email;
        this.position = (Point2D.Double) position.clone();
        this.capacity = capacity;
        this.autonomy = capacity;
        this.avgVelocity = avgVelocity;
        this.rating = defaultRating;
        this.priceKm = priceKm;
        this.alugueres = new ArrayList<>();
        this.classificacoes = new ArrayList<>();
        this.availableAt = null;
    }

    /**
     * Construtor por cópia
     *
     * @param transport Transporte a copiar
     */
    public Transport(Transport transport) {
        this.matricula = transport.getId();
        this.nifDono = transport.getNifDono();
        this.email = transport.getEmail();
        this.position = transport.getPosition();
        this.capacity = transport.getCapacity();
        this.autonomy = transport.getAutonomy();
        this.avgVelocity = transport.getAvgVelocity();
        this.rating = transport.getRating();
        this.priceKm = transport.getPriceKm();
        this.alugueres = transport.getAlugueres();
        this.classificacoes = transport.getClassificacoes();
        this.availableAt = transport.getAvailableAt();
    }

    /**
     * Permite obter o identificador de um transporte
     *
     * @return Identificador do transporte
     */
    public String getId() {
        return this.matricula;
    }

    /**
     * Permite obter o NIF do proprietário
     *
     * @return NIF do proprietário
     */
    public int getNifDono() {
        return this.nifDono;
    }

    public String getEmail() {
        return this.email;
    }

    /**
     * Permite obter a localização dum transporte
     *
     * @return Localização do carro
     */
    public Point2D.Double getPosition() {
        return (Point2D.Double) this.position.clone();
    }

    public double getCapacity() {
        return this.capacity;
    }

    /**
     * Permite obter a componente x da posição de um transporte
     *
     * @return Componente x da posição de um transporte
     */
    public double getPosX() {
        return this.position.getX();
    }

    /**
     * Permite obter a componente y da posição de um transporte
     *
     * @return Componente y da posição de um transporte
     */
    public double getPosY() {
        return this.position.getY();
    }

    /**
     * Permite obter a autonomia do transporte
     *
     * @return Autonomia do transporte
     */
    public double getAutonomy() {
        return this.autonomy;
    }

    /**
     * Permite obter a velocidade média do transporte
     *
     * @return Velocidade média do transporte
     */
    public double getAvgVelocity() {
        return this.avgVelocity;
    }

    /**
     * Permite obter a classificação média do carro
     *
     * @return Classificação média do carro
     */
    public double getRating() {
        return this.rating;
    }

    /**
     * Permite obter o preço do transporte
     *
     * @return Preço do transporte
     */
    public double getPriceKm() {
        return this.priceKm;
    }

    /**
     * Permite obter a lista de alugueres associada a um transporte
     *
     * @return Lista de alugueres associados ao transporte
     */
    public List<Aluguer> getAlugueres() {
        List<Aluguer> ret = new ArrayList<>();

        for (Aluguer a : this.alugueres)
            ret.add(a.clone());

        return ret;
    }

    public List<Double> getClassificacoes() {
        List<Double> ret = new ArrayList<>();

        for (double classificacao : this.classificacoes)
            ret.add(classificacao);

        return ret;
    }

    /**
     * Permite obter a data de quanto um transporte estará disponível
     *
     * @return Data de quando um transporte estará disponível
     */
    public LocalDateTime getAvailableAt() {
        return this.availableAt;
    }

    /**
     * Permite definir o identificador de um transporte
     *
     * @param id Identificador do transporte
     */
    public void setId(String id) {
        this.matricula = id;
    }

    /**
     * Permite definir o NIF do dono do transporte
     *
     * @param nifDono NIF do dono do transporte
     */
    public void setNifDono(int nifDono) {
        this.nifDono = nifDono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Permite definir a posição de um transporte
     *
     * @param destination Posição do transporte
     */
    public void setPosition(Point2D.Double destination) {
        this.position = (Point2D.Double) destination.clone();
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Permite definir a autonomia de um transporte
     *
     * @param autonomy Autonomia a definir
     */
    public void setAutonomy(double autonomy) {
        this.autonomy = autonomy;
    }

    /**
     * Permite definir a velocidade média de um transporte
     *
     * @param avgVelocity Velocidade média a definir
     */
    public void setAvgVelocity(double avgVelocity) {
        this.avgVelocity = avgVelocity;
    }

    /**
     * Permite definir o rating de um transporte
     *
     * @param rating Rating a definir
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Permite definir o preço por quilómetro de um transporte
     *
     * @param price Preço a definir
     */
    public void setPriceKm(double price) {
        this.priceKm = price;
    }

    /**
     * Permite definir a lista de alugueres de um transporte
     *
     * @param alugueres Lista de alugueres a definir
     */
    public void setAlugueres(List<Aluguer> alugueres) {
        this.alugueres = new ArrayList<>();

        for (Aluguer aluguer : alugueres)
            this.alugueres.add(aluguer.clone());
    }

    public void setClassificacoes(List<Double> classificacoes) {
        this.classificacoes = new ArrayList<>();

        for (double classificacao : classificacoes)
            this.classificacoes.add(classificacao);
    }

    /**
     * Permite definir o instante em que o transporte se tornará disponível
     *
     * @param time Instante temporal a definir
     */
    public void setAvailableAt(LocalDateTime time) {
        this.availableAt = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transport transport = (Transport) o;

        return (nifDono != transport.nifDono) && (Double.compare(transport.autonomy, autonomy) != 0)
                && (Double.compare(transport.capacity, capacity) != 0) && (Double.compare(transport.avgVelocity, avgVelocity) != 0)
                && (Double.compare(transport.rating, rating) != 0) && (Double.compare(transport.priceKm, priceKm) != 0)
                && (!matricula.equals(transport.matricula)) && (!email.equals(transport.email))
                && (!position.equals(transport.position)) && (!alugueres.equals(transport.alugueres))
                && (!classificacoes.equals(transport.classificacoes))
                && availableAt != null ? availableAt.equals(transport.availableAt) : transport.availableAt == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = matricula.hashCode();
        result = 31 * result + nifDono;
        result = 31 * result + email.hashCode();
        result = 31 * result + position.hashCode();
        temp = Double.doubleToLongBits(autonomy);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(capacity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(avgVelocity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(priceKm);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + alugueres.hashCode();
        result = 31 * result + classificacoes.hashCode();
        result = 31 * result + (availableAt != null ? availableAt.hashCode() : 0);
        return result;
    }

    public List<String> toShow() {
        List<String> ls = new ArrayList<>();

        ls.add("Registration Plate: " + this.getId());
        ls.add("Autonomy: " + this.getAutonomy());
        ls.add("Average velocity: " + this.getAvgVelocity());
        ls.add("Price per km: " + this.getPriceKm());
        ls.add("Rating: " + this.getRating());

        return ls;
    }

    /**
     * Permite obter uma representação textual de um objeto do tipo 'Transport'
     *
     * @return Representação textual dos objetos do tipo 'Transport'
     */
    @Override
    public String toString() {
        return "Transport{" +
                "id='" + this.matricula + '\'' +
                ", nifDono=" + this.nifDono +
                ", position=" + this.position +
                ", autonomy=" + this.autonomy +
                ", avgVelocity=" + this.avgVelocity +
                ", rating=" + this.rating +
                ", priceKm=" + this.priceKm +
                ", alugueres=" + this.alugueres +
                ", availableAt=" + this.availableAt +
                '}';
    }

    @Override
    public int compareTo(Transport t) {
        return this.matricula.compareTo(t.getId());
    }

    /**
     * Permite obter uma cópia de um objeto do tipo 'Transport'
     *
     * @return Cópia do objeto do tipo 'Transport'
     */
    public abstract Transport clone();

    /**
     * Permite alterar a posição do transporte
     *
     * @param origin      Local onde está o cliente
     * @param destination destino
     */
    public abstract void moveTransport(Point2D.Double origin, Point2D.Double destination);

    public double calculateRating() {
        double sum = 0;
        for (double i : this.classificacoes)
            sum += i;
        return (sum / this.classificacoes.size());
    }

    public void addRating(double rating) {
        this.classificacoes.add(rating);
        this.rating = this.calculateRating();
    }

    public boolean isAvailable() {
        boolean r = false;
        if (this.availableAt == null) r = true;
        else if (this.availableAt.isBefore(LocalDateTime.now()) == true) r = true;
        return r;
    }

    public void refill() {
        this.autonomy = this.capacity;
    }

    public boolean hasAutonomy(double autonomy) {
        return this.autonomy >= autonomy;
    }

    public boolean hasAutonomy(Point2D.Double destiny) {
        return this.position.distance(destiny) <= this.autonomy;
    }

    public void addAluguer(Aluguer a) {
        this.alugueres.add(a);
    }
}
