package app;

import javax.swing.*;

import static defaults.Defaults.*;

public class App {
    private JFrame frame;
    private DrawPanel drawPanel;
    private ControlPanel controlPanel;

    public App() {
        createFrame();
        initElements();
    }

    private void createFrame() {
        frame = new JFrame("Solar system");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setFocusable(true);
    }

    public void show() {
        frame.setVisible(true);
    }

    private void initElements() {
        drawPanel = new DrawPanel();
        createLeftPanel();
        controlPanel.setBounds(0, 0, frame.getWidth() / 5, frame.getHeight());
        drawPanel.setLayout(null);
        drawPanel.setBounds(frame.getWidth() / 5, 0, frame.getWidth() - frame.getWidth() / 5, frame.getHeight());
        controlPanel.setFocusable(false);
        frame.add(drawPanel);
        frame.add(controlPanel);
        frame.addKeyListener(drawPanel);

    }

    private void createLeftPanel() {
        controlPanel = new ControlPanel(drawPanel);
        controlPanel.setFont(FONT_LABEL);
    }
}




