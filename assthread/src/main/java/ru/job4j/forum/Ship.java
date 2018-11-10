package ru.job4j.forum;
/**
 * Класс корабли
 */
abstract class Ship extends Thread {

    public static class ShipDownload extends Ship {
        private final PortDataBase port;
        private int cargo;

        public ShipDownload(PortDataBase port, int cargo) {
            this.port = port;
            this.cargo = cargo;
        }

        @Override
        public void run() {
            //noinspection unchecked
            this.port.add(this.cargo);
        }
    }
    public static class ShipUpload extends Ship {
        private final PortDataBase port;

        public ShipUpload(PortDataBase port) {
            this.port = port;
        }

        @Override
        public void run() {
            this.port.del();
        }
    }
}
