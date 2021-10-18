package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.Parse;

import java.awt.geom.Point2D;

public class UMCarroJaTest {
    private UMCarroJa model;

    @Before
    public void setUp() {
        this.model = Parse.importData("target/test-classes/log.test");
    }

    @Test
    public void incomeTest() {
        double distance = Point2D.distance(-7.120941, -10.399986, 51.594437, -83.07394);

        double price = distance * this.model.getTransport("CZ-73-82").getPriceKm();
        double result = 0;

        Transport transport = this.model.getTransport("CZ-73-82");

        for (Aluguer aluguer : transport.getAlugueres()) {
            result += aluguer.getPrice();
        }

        Assert.assertEquals(price, result, 0.1);
    }
}
