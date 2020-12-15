package app;

import javax.swing.*;

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
            drawPanel.clear();
            drawPanel.repaint();
        });

        this.add(start);
        this.add(stop);
            this.add(clear);
    }
}
