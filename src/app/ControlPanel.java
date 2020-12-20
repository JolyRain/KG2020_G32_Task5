package app;

import engine.HeavenlyBody;
import engine.Space;
import math.Rectangle;
import math.Vector2;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class ControlPanel extends JPanel {
    private DrawPanel drawPanel;


    public ControlPanel(DrawPanel drawPanel) {
        JButton start = new JButton("Start");
        start.addActionListener(e -> {
            drawPanel.getDrawTimer().start();
            drawPanel.getUwt().start();
        });


        JButton stop = new JButton("Stop");
        stop.addActionListener(e -> {
            drawPanel.getDrawTimer().stop();
            drawPanel.getUwt().stop();
        });

        JButton clear = new JButton("Clear");
        clear.addActionListener(e -> {
            drawPanel.getWorld().clear();
            drawPanel.repaint();
        });


        Space space = new Space(
                new Rectangle(-2e8 * 1000, 2e8 * 1000, 4e8 * 1000, 4e8 * 1000));
        Set<HeavenlyBody> bodies = new HashSet<>();
        HeavenlyBody body1 = new HeavenlyBody("sun", 1.989e27 * 1000, space.getRectangle().getWidth()/ 20, space.getRectangle().getCenter());
        HeavenlyBody body2 = new HeavenlyBody("earth", 5.972e21 * 1000, space.getRectangle().getWidth()/ 40, new Vector2(14.95e7 * 1000, 0));
        HeavenlyBody body3 = new HeavenlyBody(7.34e19 * 1000, space.getRectangle().getWidth()/ 160, new Vector2(17e7 * 1000, 0));
        HeavenlyBody body4 = new HeavenlyBody(10.972e21 * 1000, space.getRectangle().getWidth() / 40, new Vector2(30.95e7 * 1000, 0));

        body1.setVelocity(new Vector2(0, 0));
        body2.setVelocity(new Vector2(0, 2.592e6 * 1000));
        body3.setVelocity(new Vector2(0, 2.592e6 * 1000));


        body1.setColor(Color.YELLOW);
        body2.setColor(Color.BLUE);
        body3.setColor(Color.RED);


        JButton addSun = new JButton("Add Sun");
        addSun.addActionListener(e -> {
            drawPanel.getWorld().getSolarSystem().addPlanet(body1);
            drawPanel.getWorld().initMap();
            drawPanel.repaint();
        });


        JButton addPlanet = new JButton("Add planet");
        addPlanet.addActionListener(e -> {
            drawPanel.getWorld().getSolarSystem().addPlanet(body2);
            drawPanel.getWorld().initMap();
            drawPanel.repaint();
        });


        JButton reset = new JButton("Reset");
        reset.addActionListener(e -> {
            drawPanel.getWorld().clear();
            drawPanel.getWorld().getSolarSystem().addPlanet(body1);
            drawPanel.getWorld().getSolarSystem().addPlanet(body2);
            drawPanel.getWorld().initMap();
            drawPanel.repaint();
        });


        BoundedRangeModel model = new DefaultBoundedRangeModel(10, 0, 0, 200);

        // Создание ползунков
        JSlider speedSlider = new JSlider(model);

        // Настройка внешнего вида ползунков

       JLabel sliderLable = new JLabel("slider");
        speedSlider.addChangeListener(e -> drawPanel.getUwt().setSpeedAnimation(speedSlider.getValue()));

        this.add(start);
        this.add(stop);
        this.add(clear);
//        this.add(reset);
        this.add(addSun);
        this.add(addPlanet);
        this.add(sliderLable);
        this.add(speedSlider);

    }
}
