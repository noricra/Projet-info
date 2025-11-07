public class App {
    public static void main(String[] args) {
        GameObservable obs = GameObservable.getInstance();

        Car car1 = new Car("RED");
        Car car2 = new Car("BLUE");
        Car car3 = new Car("YELLOW");

        obs.getCars().add(car1);
        obs.getCars().add(car2);
        obs.getCars().add(car3);

        CircuitView circuitView = new CircuitView(obs);
        circuitView.setVisible(true);

        CarDashboard dashboard1 = new CarDashboard(obs, car1);
        dashboard1.setLocation(100, 100);
        dashboard1.setVisible(true);

        CarDashboard dashboard2 = new CarDashboard(obs, car2);
        dashboard2.setLocation(100, 300);
        dashboard2.setVisible(true);

        CarDashboard dashboard3 = new CarDashboard(obs, car3);
        dashboard3.setLocation(100, 500);
        dashboard3.setVisible(true);

        Thread gameThread = new Thread() {
            public void run() {
                obs.startGame();
            }
        };
        gameThread.start();
    }
}
