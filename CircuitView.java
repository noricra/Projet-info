import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class CircuitView extends JFrame implements IObserver {
    private int cellSize;
    private GameObservable obs;
    private JPanel circuitPanel;
    private JTextArea rankingArea;

    public CircuitView(GameObservable obs) {
        this.obs = obs;
        obs.registerObserver(this);
        cellSize = 40;

        setTitle("Course de voitures");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        circuitPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawCircuit(g);
            }
        };
        circuitPanel.setPreferredSize(new Dimension(20 * cellSize, 10 * cellSize));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(200, 400));

        JLabel title = new JLabel("Classement", SwingConstants.CENTER);
        infoPanel.add(title, BorderLayout.NORTH);

        rankingArea = new JTextArea();
        rankingArea.setEditable(false);
        infoPanel.add(new JScrollPane(rankingArea), BorderLayout.CENTER);

        add(circuitPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);

        pack();
        setLocation(300, 50);
    }

    private void drawCircuit(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, circuitPanel.getWidth(), circuitPanel.getHeight());
        colorBoard(g, obs);
        g.setColor(Color.BLACK);
        drawGrid(g, circuitPanel.getWidth(), circuitPanel.getHeight());
    }

    private void drawGrid(Graphics g, int width, int height) {
        for (int i = 0; i <= 20; i++) {
            g.drawLine(i * cellSize, 0, i * cellSize, height);
        }
        for (int j = 0; j <= 10; j++) {
            g.drawLine(0, j * cellSize, width, j * cellSize);
        }
    }

    private void colorBox(Graphics g, int i, int j) {
        g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
    }

    private void colorCar(Graphics g, int i, int j) {
        int margin = 5;
        g.fillRect(i * cellSize + margin, j * cellSize + margin, cellSize - 2 * margin, cellSize - 2 * margin);
    }

    private void colorBoard(Graphics g, GameObservable observable) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                g.setColor(new Color(0, 100, 0));
                colorBox(g, i, j);
            }
        }

        List road = observable.getRoad();
        g.setColor(Color.GRAY);
        for (int i = 0; i < road.size(); i++) {
            Point p = (Point) road.get(i);
            colorBox(g, p.x, p.y);
        }

        drawTurn(g, 1, 8, "5");
        drawTurn(g, 1, 2, "3");
        drawTurn(g, 12, 2, "2");
        drawTurn(g, 12, 5, "2");
        drawTurn(g, 15, 5, "2");
        drawTurn(g, 15, 1, "2");
        drawTurn(g, 18, 1, "5");

        drawTurn(g, 17, 8, "D");
        drawTurn(g, 18, 8, "A");

        List cars = observable.getCars();
        for (int i = 0; i < cars.size(); i++) {
            Car car = (Car) cars.get(i);
            if (car.getPos() < road.size()) {
                Point pos = (Point) road.get(car.getPos());
                g.setColor(getCarColor(car.getColor()));
                colorCar(g, pos.x, pos.y);
            }
        }
    }

    private void drawTurn(Graphics g, int x, int y, String number) {
        g.setColor(Color.YELLOW);
        colorBox(g, x, y);
        g.setColor(Color.BLACK);
        g.drawString(number, x * cellSize + 15, y * cellSize + 25);
    }

    private Color getCarColor(String color) {
        if (color.equals("RED")) {
            return Color.RED;
        } else if (color.equals("BLUE")) {
            return Color.BLUE;
        } else if (color.equals("YELLOW")) {
            return Color.ORANGE;
        } else {
            return Color.BLACK;
        }
    }

    public void update() {
        circuitPanel.repaint();
        updateRanking();
    }

    private void updateRanking() {
        List cars = new ArrayList(obs.getCars());

        for (int i = 0; i < cars.size() - 1; i++) {
            for (int j = 0; j < cars.size() - i - 1; j++) {
                Car car1 = (Car) cars.get(j);
                Car car2 = (Car) cars.get(j + 1);
                if (car1.getPos() < car2.getPos()) {
                    Car temp = car1;
                    cars.set(j, car2);
                    cars.set(j + 1, temp);
                }
            }
        }

        String ranking = "";
        int position = 1;
        for (int i = 0; i < cars.size(); i++) {
            Car car = (Car) cars.get(i);
            ranking += position + ". " + car.getColor() + " - Position: " + car.getPos() + "\n";
            position++;
        }

        rankingArea.setText(ranking);
    }
}
