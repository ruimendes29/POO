package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClientTest {
    private Client client;
    private Transport transport;

    @Before
    public void setUp() {
        transport = new Car("Ferrari", "NM-82-58", 666666666, "pedro@uminho.pt", 135, 2.5, 10, 1000, 20, 10);
        this.client = new Client("Nelson", 999999999, "nelson@estevao.xyz", "Braga", 10, 10);
    }

    @Test
    public void distanceToTransport() {
        Assert.assertEquals(10, this.client.distanceToTransport(transport), 0.01);
    }

    @Test
    public void withinRange() {
        Assert.assertTrue(client.isWithinRange(transport, 11));
    }
}
