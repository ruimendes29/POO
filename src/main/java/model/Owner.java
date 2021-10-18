package model;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class Owner extends User implements Serializable {

    /**
     * Construtor por cópia
     *
     * @param owner Owner a copiar
     */
    public Owner(Owner owner) {
        super(owner);
    }

    /**
     * Construtor parametrizado
     *
     * @param nome    Nome do proprietário
     * @param nif     NIF do propietário
     * @param email   Email do propietário
     * @param address Morada do propietário
     */
    public Owner(String nome,
                 int nif,
                 String email,
                 String address) {
        super(nome, nif, email, address);
    }

    /**
     * Construtor parametrizado
     *
     * @param name     Nome do proprietário
     * @param nif      NIF do proprietário
     * @param email    Email do proprietário
     * @param password Password do proprietário
     * @param address  Morada do proprietário
     */
    public Owner(String name,
                 int nif,
                 String email,
                 String password,
                 String address) {
        super(name, nif, email, address, password);
    }

    /**
     * Permite obter uma cópia dos objetos do tipo 'Owner'
     *
     * @return Cópia de um objeto do tipo 'Owner'
     */
    public Owner clone() {
        return new Owner(this);
    }

    /**
     * Permite que permite reabastecer um transporte pertencente ao proprietário
     *
     * @param t Transporte a reabastecer
     */
    public void refillTransport(Transport t) {
        t.setAutonomy(100);
    }

    /**
     * Permite alterar o preço por quilómetro de um transporte
     *
     * @param t        Transporte cujo preço será alterado
     * @param newPrice Novo preço
     */
    public void changeTrasportPriceKm(Transport t, int newPrice) {
        t.setPriceKm(newPrice);
    }

    /**
     * Permite calcular o preço de uma viagem
     *
     * @param t           Transporte a utilizar
     * @param destination Destino da viagem
     * @return Preço da viagem
     */
    public double ridePrice(Transport t, Point2D.Double destination) {
        return t.getPriceKm() * t.getPosition().distance(destination);
    }

    public void decideRentRequest(String client, String id, int decision) {
        for (Notification n : this.getPendingTasks()) {
            if (n instanceof RentNotification) {
                RentNotification x = (RentNotification) n;
                if ((x.getClient().compareTo(client) == 0) && (x.getId().compareTo(id) == 0)) {
                    x.setStatus(decision);
                }
            }
        }
    }

}
