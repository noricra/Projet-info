import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CarDashboard extends JFrame implements IObserver {
    private GameObservable obs;
    private Car car;
    private JLabel fuelLabel;
    private JLabel lapsLabel;
    private JButton pauseButton;

    public CarDashboard(GameObservable obs, Car car) {
        this.obs = obs;
        this.car = car;
        this.obs.registerObserver(this);

        setTitle("Tableau de bord - " + car.getColor());
        setSize(250, 150);
        setLayout(new BorderLayout());

        Color backgroundColor = getCarColor(car.getColor());
        getContentPane().setBackground(backgroundColor);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 1));
        infoPanel.setBackground(backgroundColor);

        fuelLabel = new JLabel("Carburant: " + car.getFuel());
        lapsLabel = new JLabel("Tours: " + car.getLaps());

        infoPanel.add(fuelLabel);
        infoPanel.add(lapsLabel);

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                obs.togglePause();
                if (pauseButton.getText().equals("Pause")) {
                    pauseButton.setText("Reprendre");
                } else {
                    pauseButton.setText("Pause");
                }
            }
        });

        add(infoPanel, BorderLayout.CENTER);
        add(pauseButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private Color getCarColor(String color) {
        if (color.equals("RED")) {
            return Color.RED;
        } else if (color.equals("BLUE")) {
            return Color.BLUE;
        } else if (color.equals("YELLOW")) {
            return Color.ORANGE;
        } else {
            return Color.WHITE;
        }
    }

    public void update() {
        fuelLabel.setText("Carburant: " + car.getFuel());
        lapsLabel.setText("Tours: " + car.getLaps());
    }
}
