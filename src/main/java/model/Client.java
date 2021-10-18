package model;

import exceptions.NoAvailableTransport;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Set;

public class Client extends User implements Serializable {

    /**
     * Variáveis de instância
     */
    private Point2D.Double position;

    /**
     * Construtor parametrizado
     *
     * @param client Cliente a copiar
     */
    public Client(Client client) {
        super(client);
        this.position = client.getPosition();
    }

    /**
     * Construtor parametrizado
     *
     * @param nome       Nome do cliente
     * @param nifCliente NIF do cliente
     * @param email      Email do cliente
     * @param morada     Morada do cliente
     * @param posX       Componente x da localização do cliente
     * @param posY       Componente y da localização do cliente
     */
    public Client(String nome,
                  int nifCliente,
                  String email,
                  String morada,
                  double posX,
                  double posY) {
        super(nome, nifCliente, email, morada);
        this.position = new Point2D.Double();
        this.position.setLocation(posX, posY);
    }

    /**
     * @param name      Nome do cliente
     * @param nif       NIF do cliente
     * @param email     Email do cliente
     * @param password  Password do cliente
     * @param address   Morada do cliente
     * @param positionX Componente x da posição do cliente
     * @param positionY Componente y da posição do cliente
     */
    public Client(String name,
                  int nif,
                  String email,
                  String password,
                  String address,
                  double positionX,
                  double positionY) {
        super(email, nif, email, address, password);
        this.position = new Point2D.Double();
        this.position.setLocation(positionX, positionY);
    }

    /**
     * Permite obter a localização do cliente
     *
     * @return Point2D.Double que representa a localização do cliente
     */
    public Point2D.Double getPosition() {
        return (Point2D.Double) this.position.clone();
    }

    public void setPosition(Point2D.Double position) {
        this.position = (Point2D.Double) position.clone();
    }

    /**
     * Permite obter uma cópia de um cliente
     *
     * @return Cópia do cliente
     */
    public Client clone() {
        return new Client(this);
    }

    /**
     * Permite comparar dois objetos do tipo 'Client'
     *
     * @param obj Objeto a comparar
     * @return 'true' se os objetos forem iguais ou 'false' caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Client client = (Client) obj;
        return this.position.equals(client.getPosition());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + position.hashCode();
        return result;
    }

    /**
     * Permite obter uma representação textual de um objeto do tipo 'Client'
     *
     * @return Representação textual do objeto do tipo 'Client'
     */
    @Override
    public String toString() {
        return "Client{" + super.toString() +
                ", position=" + position +
                '}';
    }

    /**
     * Calcula a distância entre o cliente e um transporte
     *
     * @param t Transporte para qual se calcula a distância
     * @return Distância entre o transporte e o cliente
     */
    public double distanceToTransport(Transport t) {
        return this.position.distance(t.getPosition());
    }

    /**
     * Permite verificar se um transporte tem autonomia para chegar a um determinado lugar
     *
     * @param transport Transporte a calcular
     * @param range     Sítio pretendido a alcançar
     * @return 'true' se for possível ou 'false' caso contrário
     */
    public boolean isWithinRange(Transport transport, double range) {
        return this.distanceToTransport(transport) <= range;
    }

    /**
     * Permite obter o transporte mais próxima de um cliente
     *
     * @param transports Lista de transportes existentes
     * @return Transporte mais próximo do cliente (se exisitir algum disponível)
     * @throws NoAvailableTransport Exceção caso não exista nenhum transporte disponível
     */
    public Transport getClosestCar(Set<Transport> transports) throws NoAvailableTransport {
        double closestDistance = Double.MAX_VALUE;
        double temp;
        Transport ret = null;
        for (Transport c : transports) {
            if (c.isAvailable() == true) {
                temp = distanceToTransport(c);
                if (temp < closestDistance) {
                    closestDistance = temp;
                    ret = c.clone();
                }
            }
        }
        if (ret == null) throw new NoAvailableTransport("Não existe nenhum carro próximo disponível.");
        return ret;
    }

    public Transport getClosestCarNormal(Set<Transport> transports) throws NoAvailableTransport {
        double closestDistance = Double.MAX_VALUE;
        double temp;
        Transport ret = null;
        for (Transport c : transports) {
            if (c.isAvailable() == true) {
                if (c instanceof Car) {
                    temp = distanceToTransport(c);
                    if (temp < closestDistance) {
                        closestDistance = temp;
                        ret = c.clone();
                    }
                }
            }
        }
        if (ret == null) throw new NoAvailableTransport("Não existe nenhum carro próximo disponível.");
        return ret;
    }

    public Transport getClosestCarHybrid(Set<Transport> transports) throws NoAvailableTransport {
        double closestDistance = Double.MAX_VALUE;
        double temp;
        Transport ret = null;
        for (Transport c : transports) {
            if (c.isAvailable() == true) {
                if (c instanceof Hybrid) {
                    temp = distanceToTransport(c);
                    if (temp < closestDistance) {
                        closestDistance = temp;
                        ret = c.clone();
                    }
                }
            }
        }
        if (ret == null) throw new NoAvailableTransport("Não existe nenhum carro hibrido próximo disponível.");
        return ret;
    }

    /**
     * Permite obter o transporte mais barato
     *
     * @param transports Lista de transportes
     * @return Transporte mais barato (se existir algum disponível)
     * @throws NoAvailableTransport Exceção caso não exista nenhum transporte disponível
     */
    public Transport getCheapestCar(Set<Transport> transports) throws NoAvailableTransport {
        double cheapestCost = Double.MAX_VALUE;
        double temp;
        Transport ret = null;
        for (Transport c : transports) {
            if (c.isAvailable() == true) {
                temp = c.getPriceKm();
                if (temp < cheapestCost) {
                    cheapestCost = temp;
                    ret = c.clone();
                }
            }
        }
        if (ret == null) throw new NoAvailableTransport("Não existe nenhum carro próximo disponível.");
        return ret;
    }

    public Transport getCheapestCarNormal(Set<Transport> transports) throws NoAvailableTransport {
        double cheapestCost = Double.MAX_VALUE;
        double temp;
        Transport ret = null;
        for (Transport c : transports) {
            if (c.isAvailable() == true) {
                if (c instanceof Car) {
                    temp = c.getPriceKm();
                    if (temp < cheapestCost) {
                        cheapestCost = temp;
                        ret = c.clone();
                    }
                }
            }
        }
        if (ret == null) throw new NoAvailableTransport("Não existe nenhum carro próximo disponível.");
        return ret;
    }

    public Transport getCheapestCarHybrid(Set<Transport> transports) throws NoAvailableTransport {
        double cheapestCost = Double.MAX_VALUE;
        double temp;
        Transport ret = null;
        for (Transport c : transports) {
            if (c.isAvailable() == true) {
                if (c instanceof Hybrid) {
                    temp = c.getPriceKm();
                    if (temp < cheapestCost) {
                        cheapestCost = temp;
                        ret = c.clone();
                    }
                }
            }
        }
        if (ret == null) throw new NoAvailableTransport("Não existe nenhum carro próximo disponível.");
        return ret;
    }

    /**
     * Permite obter o transporte mais barato dentro de uma distância que o cliente está disposto a percorrer a pé
     *
     * @param transports Lista de transportes
     * @param walk       Distância que o cliente está disposto a percorrer a pé
     * @return Transporte mais barato naquele intervalo espacial (se exister algum disponível)
     * @throws NoAvailableTransport Exceção caso não exista nenhum transporte disponível nas referidas condições
     */
    public Transport getCheapestTransportInWalkRange(Set<Transport> transports, double walk) throws NoAvailableTransport {
        double cheapest = Double.MAX_VALUE;
        double price;
        Transport ret = null;

        for (Transport transport : transports) {
            if (transport.isAvailable() && this.isWithinRange(transport, walk)) {
                price = transport.getPriceKm();
                if (price < cheapest) {
                    cheapest = price;
                    ret = transport.clone();
                }
            }
        }

        if (ret == null) throw new NoAvailableTransport("Não existe nenhum transporte nas condições requisitadas.");

        return ret;
    }
}
