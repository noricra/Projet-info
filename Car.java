import java.util.Random;

public class Car {
    private int pos;
    private int fuel;
    private int laps;
    private String color;
    private Random random;

    public Car(String color) {
        this.pos = 0;
        this.fuel = 60;
        this.laps = 0;
        this.color = color;
        this.random = new Random();
    }

    public void run() {
        if (fuel > 0) {
            int movement = random.nextInt(6) + 1;
            pos += movement;
            fuel -= 2;
            if (fuel < 0) {
                fuel = 0;
            }
        }
    }

    public int getPos() {
        return pos;
    }

    public int getFuel() {
        return fuel;
    }

    public int getLaps() {
        return laps;
    }

    public String getColor() {
        return color;
    }
}
