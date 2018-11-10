package ru.job4j.forum;

import static org.hamcrest.Matchers.is;
import org.junit.Test;

import static org.junit.Assert.assertThat;


public class ShipTest {
    @Test
    public void whentestPortDatabase() throws InterruptedException {
        Thread t = Thread.currentThread();
        t.setName("Корабль expected");

        PortDataBase<Integer> port = new PortDataBase<Integer>();
        Thread ship1 = new Thread(new Ship.ShipDownload(port, 10), "Корабль поставщик ship1");
        Thread ship2 = new Thread(new Ship.ShipDownload(port, 10), "Корабль поставщик ship2");
        Thread ship3 = new Thread(new Ship.ShipDownload(port, 10), "Корабль поставщик ship3");
        Thread ship4 = new Thread(new Ship.ShipDownload(port, 10), "Корабль поставщик ship4");
        Thread ship5 = new Thread(new Ship.ShipDownload(port, 10), "Корабль поставщик ship5");
        Thread ship6 = new Thread(new Ship.ShipDownload(port, 10), "Корабль поставщик ship6");
        Thread ship7 = new Thread(new  Ship.ShipUpload(port), "Корабль отгрузчик ship7");
        Thread ship8 = new Thread(new  Ship.ShipUpload(port), "Корабль отгрузчик ship8");
        Thread ship9 = new Thread(new  Ship.ShipUpload(port), "Корабль отгрузчик ship9");
        Thread ship10 = new Thread(new Ship.ShipUpload(port), "Корабль отгрузчик ship10");
        Thread ship11 = new Thread(new Ship.ShipUpload(port), "Корабль отгрузчик ship11");

        ship1.start();
        ship2.start();
        ship3.start();
        ship4.start();
        ship5.start();
        ship6.start();
        ship7.start();
        ship8.start();
        ship9.start();
        ship10.start();
        ship11.start();

        ship1.join();
        ship2.join();
        ship3.join();
        ship4.join();
        ship5.join();
        ship6.join();
        ship7.join();
        ship8.join();
        ship9.join();
        ship10.join();
        ship11.join();

        assertThat(port.del(), is(10));
    }

}