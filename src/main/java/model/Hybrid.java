package model;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Hybrid extends Transport implements Serializable {

    /**
     * Variáveis de instância
     */
    private String marca;
    private double consumoGas;
    private double consumoEletrico;
    private double autonomiaGas;
    private double autonomiaEletrico;

    public Hybrid(String marca,
                  String id,
                  int nifDono,
                  String email,
                  double avgVelocity,
                  double priceKm,
                  double capacidade,
                  double consumo,
                  double posX,
                  double posY) {
        super(id, nifDono, email, avgVelocity, priceKm, capacidade, posX, posY);
        this.consumoGas = consumo * 0.5;
        this.consumoEletrico = consumo * 0.5;
        this.autonomiaGas = capacidade * 0.5;
        this.autonomiaEletrico = capacidade * 0.5;
    }

    public Hybrid(String marca,
                  String id,
                  int nifDono,
                  String email,
                  double avgVelocity,
                  double priceKm,
                  double capacidade,
                  double consumo,
                  Point2D.Double position) {
        super(id, nifDono, email, avgVelocity, priceKm, capacidade, position);
        this.consumoGas = consumo * 0.5;
        this.consumoEletrico = consumo * 0.5;
        this.autonomiaGas = capacidade * 0.5;
        this.autonomiaEletrico = capacidade * 0.5;
    }

    public Hybrid(Hybrid h) {
        super(h);
        this.consumoGas = h.getConsumoGas();
        this.consumoEletrico = h.getConsumoEletrico();
        this.autonomiaGas = h.getAutonomiaGas();
        this.autonomiaEletrico = h.getAutonomiaEletrico();
    }

    /**
     * Permite obter o consumo (percentagem) do motor a combustíveis fosseis
     *
     * @return Consumo (percentagem) do motor a combustiveis fosseis
     */
    public double getConsumoGas() {
        return this.consumoGas;
    }

    /**
     * Permite obter o consumo (percentagem) do motor a eletricidade
     *
     * @return Consumo (percentagem) do motor a eletricidade
     */
    public double getConsumoEletrico() {
        return this.consumoEletrico;
    }

    /**
     * Permite obter a autonomia do motor a combustiveis fosseis
     *
     * @return Autonomia do motor a combustíveis fosseis
     */
    public double getAutonomiaGas() {
        return this.autonomiaGas;
    }

    /**
     * Permite obter a autonomia do motor a eletricidade
     *
     * @return Autonomia do motor elétrico
     */
    public double getAutonomiaEletrico() {
        return this.autonomiaEletrico;
    }

    /**
     * Permite definir o consumo do motor a combustíveis fosseis
     *
     * @param consumo Consumo do motor a combustíveis fosseis
     */
    public void setConsumoGas(double consumo) {
        this.consumoGas = consumo;
    }

    /**
     * Permite definir o consumo do motor a eletricidade
     *
     * @param consumo Consumo do motor a eletricidade
     */
    public void setConsumoEletrico(double consumo) {
        this.consumoEletrico = consumo;
    }

    /**
     * Permite definir a autonomia do motor a combustíveis fosseis
     *
     * @param autonomia Autonomia do motor a combustíveis fosseis
     */
    public void setAutonomiaGas(double autonomia) {
        this.autonomiaGas = autonomia;
    }

    /**
     * Permite definir a autonomia do motor a eletricidade
     *
     * @param autonomia Autonomia do motor a eletricidade
     */
    public void setAutonomiaEletrico(double autonomia) {
        this.autonomiaEletrico = autonomia;
    }

    /**
     * Permite obter uma cópia dos objetos do tipo 'Hybrid'
     *
     * @return Cópia dos objetos do tipo 'Hybrid'
     */
    public Hybrid clone() {
        return new Hybrid(this);
    }

    /**
     * Permite comparar dois objetos do tipo 'Hybrid'
     *
     * @param obj Objeto a comparar
     * @return 'true' caso os objetos sejam iguais ou 'false' caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        if (!super.equals(obj)) return false;

        Hybrid hybrid = (Hybrid) obj;

        return Double.compare(hybrid.consumoGas, consumoGas) != 0
                && Double.compare(hybrid.consumoEletrico, consumoEletrico) != 0
                && Double.compare(hybrid.autonomiaGas, autonomiaGas) != 0
                && Double.compare(hybrid.autonomiaEletrico, autonomiaEletrico) != 0
                && marca.equals(hybrid.marca);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + marca.hashCode();
        temp = Double.doubleToLongBits(consumoGas);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(consumoEletrico);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(autonomiaGas);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(autonomiaEletrico);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Permite obter uma representação textual dos objetos do tipo 'Hybrid'
     *
     * @return Representação textual dos objetos do tipo 'Hybrid'
     */
    @Override
    public String toString() {
        return "Hybrid{" + super.toString() +
                ", marca= " + this.marca +
                ", consumoGas= " + this.consumoGas +
                ", consumoEletrico= " + this.consumoEletrico +
                ", autonomiaGas= " + this.autonomiaGas +
                ", autonomiaEletrico= " + this.autonomiaEletrico +
                '}';
    }

    /**
     * Permite mover um carro para uma determinada posição
     *
     * @param origin      Onde vai ter de ir ter com o cliente
     * @param destination Posição para qual o carro se irá mover
     */
    public void moveTransport(Point2D.Double origin, Point2D.Double destination) {
        double distance = this.getPosition().distance(origin) + origin.distance(destination);
        double spentFuelPercentage = distance * this.getConsumoGas();
        double spentElectricityPercentage = distance * this.getConsumoEletrico();
        double timeSpentOnTravel = distance / this.getAvgVelocity();
        this.setAutonomiaGas(this.getAutonomiaGas() - spentFuelPercentage);
        this.setAutonomiaEletrico(this.getAutonomiaEletrico() - spentElectricityPercentage);
        if (this.getAutonomiaEletrico() < this.getAutonomiaGas()) this.setAutonomy(this.getAutonomiaGas());
        else this.setAutonomy(this.getAutonomiaEletrico());
        this.setAvailableAt(LocalDateTime.now().plusHours((long) timeSpentOnTravel));
    }
}
