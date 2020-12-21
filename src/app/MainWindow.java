package app;

import defaults.Defaults;
import engine.HeavenlyBody;
import math.Vector2;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainWindow extends JFrame {
    private static final int WIDTH_OF_PANEL = 100;
    private static final int WIDTH_COORDINATE_PANEL = 50;
    private static final Font FONT = new Font(Font.SERIF, Font.PLAIN, 25);
    private static final int WIDTH_LABEL = 200;
    private static final Map<String, Color> COLORS = new HashMap<>();

    private static final String[] nameColors = {"White", "Yellow", "Orange", "Blue", "Red",
            "Cyan", "Gray", "Green", "Magenta", "Pink"};

    static {
        COLORS.put(nameColors[0], Color.WHITE);
        COLORS.put(nameColors[1], Color.YELLOW);
        COLORS.put(nameColors[2], Color.ORANGE);
        COLORS.put(nameColors[3], Color.BLUE);
        COLORS.put(nameColors[4], Color.RED);
        COLORS.put(nameColors[5], Color.CYAN);
        COLORS.put(nameColors[6], Color.GRAY);
        COLORS.put(nameColors[7], Color.GREEN);
        COLORS.put(nameColors[8], Color.MAGENTA);
        COLORS.put(nameColors[9], Color.PINK);
    }

    private final JLabel nameLabel = new JLabel();
    private final JLabel massLabel = new JLabel();
    private final JLabel radiusLabel = new JLabel();
    private final JLabel posLabel = new JLabel();
    private final JTextField nameField = new JTextField();
    private final JTextField massField = new JTextField();
    private final JTextField radiusField = new JTextField();
    private final JTextField fieldPosX = new JTextField();
    private final JTextField fieldPosY = new JTextField();
    private final JTextField fieldVelX = new JTextField();
    private final JTextField fieldVelY = new JTextField();
    private final JButton startButton = new JButton("Start");
    private final JButton stopButton = new JButton("Stop");
    private final JButton clear = new JButton("Clear");
    private final JButton reset = new JButton("Reset");
    private final JLabel colorLabel = new JLabel();
    private final JComboBox<String> colorComboBox = new JComboBox<>(nameColors);
    private DrawPanel drawPanel;
    private JPanel buttonsPanel;
    private JPanel checksPanel;
    private JComponent planet;
    private JLabel velLabel = new JLabel();

    public MainWindow() throws HeadlessException {
        super("Solar system");
        panel();
    }

    private void panel() {
        drawPanel = new DrawPanel();
        this.add(drawPanel);

        setButtonsPanel();
        this.add(buttonsPanel, BorderLayout.WEST);

        JTabbedPane tabbedPane = new JTabbedPane();
        planet = new JPanel();
        tabbedPane.addTab("Planet", planet);

        setAddPlanetPanel();

        tabbedPane.setPreferredSize(new Dimension(400, 400));
        tabbedPane.setFont(FONT);
        buttonsPanel.add(tabbedPane);


        JButton addPlanet = new JButton("Add");
        addPlanet.setPreferredSize(new Dimension(300, 25));
        addPlanet.addActionListener(e -> {
            try {
                String name;
                double mass, radius, posX, posY, velX, velY;
                Vector2 position, startVelocity;

                HeavenlyBody newBody;

                name = (nameField.getText());
                mass = Double.parseDouble(massField.getText()) * 1e25;
                radius = Double.parseDouble(radiusField.getText()) * 1e8;

                posX = Double.parseDouble(fieldPosX.getText()) * 1e9;
                posY = Double.parseDouble(fieldPosY.getText()) * 1e9;
                position = new Vector2(posX, posY);

                velX = Double.parseDouble(fieldPosX.getText()) * 1e9;
                velY = Double.parseDouble(fieldPosY.getText()) * 1e9;
                startVelocity = new Vector2(velX, velY);

                newBody = new HeavenlyBody(name, mass, radius, position);
                newBody.setVelocity(startVelocity);
                newBody.setColor(COLORS.get(colorComboBox.getSelectedItem()));
                drawPanel.getWorld().getSolarSystem().getBodies().add(newBody);
                drawPanel.repaint();
            } catch (NumberFormatException exp) {
                JOptionPane.showMessageDialog(null, "Invalid input", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        addPlanet.setFont(FONT);
        buttonsPanel.add(addPlanet);
        setChecks();

    }

    private void setChecks() {
        JCheckBox orbitsCheck = new JCheckBox("Show orbits");
        orbitsCheck.setFont(FONT);
        orbitsCheck.setSelected(true);
        orbitsCheck.setPreferredSize(new Dimension(200, 25));
        orbitsCheck.addActionListener(e -> {
            if (orbitsCheck.isSelected()) drawPanel.getWorldDrawer().setShowOrbits(true);
            else drawPanel.getWorldDrawer().setShowOrbits(false);
            drawPanel.repaint();
        });
        buttonsPanel.add(orbitsCheck);

        JCheckBox namesCheck = new JCheckBox("Show names");
        namesCheck.setFont(FONT);
        namesCheck.setPreferredSize(new Dimension(200, 25));
        namesCheck.addActionListener(e -> {
            if (namesCheck.isSelected()) drawPanel.getWorldDrawer().setShowNames(true);
            else drawPanel.getWorldDrawer().setShowNames(false);
            drawPanel.repaint();
        });
        buttonsPanel.add(namesCheck);

        JCheckBox gravityCheck = new JCheckBox("Gravity");
        gravityCheck.setFont(FONT);
        gravityCheck.setSelected(true);
        gravityCheck.setPreferredSize(new Dimension(200, 25));
        gravityCheck.addActionListener(e -> {
            if (gravityCheck.isSelected()) drawPanel.getWorld().setGravity(true);
            else drawPanel.getWorld().setGravity(false);
            drawPanel.repaint();
        });
        buttonsPanel.add(gravityCheck);

    }

    private void setButtonsPanel() {
        buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(500, getHeight()));

        startButton.setPreferredSize(new Dimension(300, 25));
        startButton.setFont(FONT);
        startButton.addActionListener(e -> {
            drawPanel.getDrawTimer().start();
            drawPanel.getUwt().start();
        });
        buttonsPanel.add(startButton);

        stopButton.setPreferredSize(new Dimension(300, 25));
        stopButton.addActionListener(actionEvent -> {
            drawPanel.getDrawTimer().stop();
            drawPanel.getUwt().stop();
        });
        stopButton.setFont(FONT);
        buttonsPanel.add(stopButton);

        clear.setPreferredSize(new Dimension(300, 25));
        clear.addActionListener(actionEvent -> {
            try {
                drawPanel.getWorld().clear();
                drawPanel.repaint();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        clear.setFont(FONT);
        buttonsPanel.add(clear);

        reset.setPreferredSize(new Dimension(300, 25));
        reset.addActionListener(actionEvent -> {
            try {
                drawPanel.getWorld().clear();
                Set<HeavenlyBody> bodySet = drawPanel.getWorld().getSolarSystem().getBodies();
                Set<HeavenlyBody> sey = Defaults.PLANETS;
                bodySet.addAll(Defaults.PLANETS);
                drawPanel.repaint();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        reset.setFont(FONT);
//        buttonsPanel.add(reset);


        JLabel label = new JLabel("Animation speed");
        label.setPreferredSize(new Dimension(200, 25));
        label.setFont(FONT);
        buttonsPanel.add(label);
        BoundedRangeModel model = new DefaultBoundedRangeModel(50, 0, 0, 200);
        JSlider speedSlider = new JSlider(model);
        speedSlider.setPreferredSize(new Dimension(300, 25));
        speedSlider.addChangeListener(e -> drawPanel.getUwt().setSpeedAnimation(speedSlider.getValue()));
        buttonsPanel.add(speedSlider);
    }

    private void setAddPlanetPanel() {

        addLabel(nameLabel, "Name");
        addLongField(nameField);

        addLabel(massLabel, "Mass");
        addLongField(massField);
        addELabel("e25");


        addLabel(radiusLabel, "Radius");
        addLongField(radiusField);
        addELabel("e8, m");


        addLabel(posLabel, "Position");
        addSmallField(fieldPosX);
        addSmallField(fieldPosY);
        addELabel("e9, m");


        addLabel(velLabel, "Velocity");
        addSmallField(fieldVelX);
        addSmallField(fieldVelY);
        addELabel("e9, m/day");

        addLabel(colorLabel, "Color");
        colorComboBox.setFont(FONT);
        colorComboBox.setPreferredSize(new Dimension(100, 25));
        planet.add(colorComboBox);

    }

    private void addELabel(String text) {
        JLabel eLabel = new JLabel();
        eLabel.setText(text);
        eLabel.setPreferredSize(new Dimension(75, 20));
        planet.add(eLabel);

    }

    private void addLabel(JLabel label, String text) {
        label.setPreferredSize(new Dimension(200, 25));
        label.setFont(FONT);
        label.setText(text);
        planet.add(label);
    }

    private void addLongField(JTextField field) {
        field.setPreferredSize(new Dimension(100, 25));
        planet.add(field);
    }


    private void addSmallField(JTextField field) {
        field.setPreferredSize(new Dimension(WIDTH_COORDINATE_PANEL, 25));
        planet.add(field);
    }
}

