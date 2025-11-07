import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class GameObservable {
    private static GameObservable instance;
    private List<IObserver> observers;
    private List<Point> road;
    private List<Car> cars;
    private boolean paused;

    private GameObservable() {
        this.observers = new ArrayList();
        this.road = new ArrayList();
        this.cars = new ArrayList();
        this.paused = false;
        initializeRoad();
    }

    public static GameObservable getInstance() {
        if (instance == null) {
            instance = new GameObservable();
        }
        return instance;
    }

    private void initializeRoad() {
        for (int x = 17; x >= 1; x--) {
            road.add(new Point(x, 8));
        }
        for (int y = 7; y >= 2; y--) {
            road.add(new Point(1, y));
        }
        for (int x = 2; x <= 12; x++) {
            road.add(new Point(x, 2));
        }
        for (int y = 3; y <= 5; y++) {
            road.add(new Point(12, y));
        }
        for (int x = 13; x <= 15; x++) {
            road.add(new Point(x, 5));
        }
        for (int y = 4; y >= 1; y--) {
            road.add(new Point(15, y));
        }
        for (int x = 16; x <= 18; x++) {
            road.add(new Point(x, 1));
        }
        for (int y = 2; y <= 8; y++) {
            road.add(new Point(18, y));
        }
    }

    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            IObserver observer = (IObserver) observers.get(i);
            observer.update();
        }
    }

    public void startGame() {
        while (true) {
            if (!paused) {
                for (int i = 0; i < cars.size(); i++) {
                    Car car = (Car) cars.get(i);
                    car.run();
                }
                notifyObservers();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void togglePause() {
        paused = !paused;
    }

    public List getRoad() {
        return road;
    }

    public List getCars() {
        return cars;
    }
}
