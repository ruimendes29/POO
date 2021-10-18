package model;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Car extends Transport implements Serializable {

    /**
     * Variáveis de instância
     */
    private String marca;
    private double consumoPercentage;

    /**
     * Construtor parametrizado
     *
     * @param marca       Marca do carro
     * @param id          Identificador do carro
     * @param nifDono     NIF do dono
     * @param email       Email
     * @param avgVelocity Velocidade média do carro
     * @param priceKm     Preço por quilómetro do carro
     * @param consumo     Consumo (percentagem) do carro
     * @param capacidade  Capacidade
     * @param posX        Componente x da posição do carro
     * @param posY        Componente y da posição do carro
     */
    public Car(String marca,
               String id,
               int nifDono,
               String email,
               double avgVelocity,
               double priceKm,
               double consumo,
               double capacidade,
               double posX,
               double posY) {
        super(id, nifDono, email, avgVelocity, priceKm, capacidade, posX, posY);
        this.marca = marca;
        this.consumoPercentage = consumo;
    }

    public Car(String marca,
               String id,
               int nifDono,
               String email,
               double avgVelocity,
               double priceKm,
               double consumo,
               double capacidade,
               Point2D.Double position) {
        super(id, nifDono, email, avgVelocity, priceKm, capacidade, position);
        this.marca = marca;
        this.consumoPercentage = consumo;
    }

    /**
     * Construtor por cópia
     *
     * @param c carro a copiar
     */
    public Car(Car c) {
        super(c);
        this.marca = c.getMarca();
        this.consumoPercentage = c.getConsumoPercentage();
    }

    /**
     * Permite obter a marca do carro
     *
     * @return Marca do carro
     */
    public String getMarca() {
        return this.marca;
    }

    /**
     * Permite obter o consumo (percentagem) do carro
     *
     * @return Consumo (percentagem) do carro
     */
    public double getConsumoPercentage() {
        return this.consumoPercentage;
    }

    /**
     * Permite definir a marca do carro
     *
     * @param marca Marca do carro
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Permite definir o consumo (percentagem) do carro
     *
     * @param consumo Consumo (percentagem) do carro
     */
    public void setConsumoPercentage(double consumo) {
        this.consumoPercentage = consumo;
    }

    /**
     * Permite obter uma cópia dos objetos do tipo 'Car'
     *
     * @return Cópia do objeto do tipo 'Car'
     */
    @Override
    public Car clone() {
        return new Car(this);
    }

    /**
     * Permite comparar dois objetos do tipo 'Car'
     *
     * @param obj Objeto a comparar
     * @return 'true' caso sejam iguais ou 'false' caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || this.getClass() != obj.getClass()) return false;

        if (!super.equals(obj)) return false;

        Car car = (Car) obj;

        return marca.equals(car.marca) && (Double.compare(car.consumoPercentage, consumoPercentage) != 0);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + marca.hashCode();
        temp = Double.doubleToLongBits(consumoPercentage);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Permite obter uma representação textual de objetos do tipo 'Car'
     *
     * @return Representação textual de objetos do tipo 'Car'
     */
    @Override
    public String toString() {
        return "Car{" + super.toString() +
                "marca=" + this.marca +
                ", consumoPercentage=" + this.consumoPercentage +
                '}';
    }

    /**
     * Permite mover um carro para um determinado destino
     *
     * @param origin      Onde vai ter de ir ter com o cliente
     * @param destination Destino
     */
    public void moveTransport(Point2D.Double origin, Point2D.Double destination) {
        double distance = this.getPosition().distance(origin) + origin.distance(destination);
        double spentFuelPercentage = distance * this.getConsumoPercentage();
        double timeSpentOnTravel = distance / this.getAvgVelocity(); // em horas
        this.setAutonomy(this.getAutonomy() - spentFuelPercentage);
        this.setAvailableAt(LocalDateTime.now().plusHours((long) timeSpentOnTravel));
        this.setPosition(destination);
    }
}
