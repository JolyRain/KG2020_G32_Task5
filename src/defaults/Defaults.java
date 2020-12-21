package defaults;


import engine.HeavenlyBody;
import engine.Space;
import math.Rectangle;
import math.Vector2;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Defaults {

    public static final int FRAME_WIDTH = 1660;
    public static final int FRAME_HEIGHT = 1080;
    public static final double REAL_SIZE = 10;
    public static final double CORNER_X = -REAL_SIZE / 2;
    public static final double CORNER_Y = REAL_SIZE / 2;
    public static final double ZOOM_INCREASE = 1.05;
    public static final double ZOOM_DECREASE = 0.95;
    public static final Font FONT_LABEL = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    public static final Font FONT_SIGNATURES = new Font(Font.DIALOG, Font.BOLD, 15);
    public static final Color MAIN_GRID_COLOR = new Color(150, 150, 150);
    public static final Color SMALL_GRID_COLOR = new Color(200, 200, 200);
    public static final Color LIGHT_GRAY = new Color(220, 220, 220);
    public static final Color VIOLET = new Color(100, 10, 255);
    public static final Color BLUE = new Color(0, 100, 255);
    public static final double LOWER_LIMIT_NUMBER = 0.001;
    public static final double UPPER_LIMIT_NUMBER = 10000;
    public static final int SMALL_GRID_DIVIDER = 5;
    public static final Set<HeavenlyBody> PLANETS = new HashSet<>();
    public static final Space SPACE = new Space(new Rectangle(-2e8 * 1000, 2e8 * 1000, 4e8 * 1000, 4e8 * 1000));
    private static final HeavenlyBody sun = new HeavenlyBody("sun", 3e30, SPACE.getRectangle().getWidth() / 20, SPACE.getRectangle().getCenter());
    private static final HeavenlyBody mercury = new HeavenlyBody("mercury", 3.33e23, SPACE.getRectangle().getWidth() / 90, new Vector2(0, 58e9));
    private static final HeavenlyBody venus = new HeavenlyBody("venus", 4.867e24, SPACE.getRectangle().getWidth() / 40, new Vector2(108e9, 0));
    private static final HeavenlyBody earth = new HeavenlyBody("earth", 5.972e24, SPACE.getRectangle().getWidth() / 40, new Vector2(139.5e9, 0));
    private static final HeavenlyBody mars = new HeavenlyBody("mars", 6.39e23, SPACE.getRectangle().getWidth() / 40, new Vector2(0, 170e9));
    private static final HeavenlyBody jupiter = new HeavenlyBody("jupiter", 1.898e27, SPACE.getRectangle().getWidth() / 30, new Vector2(0, 210e9));
    private static final HeavenlyBody saturn = new HeavenlyBody("saturn", 5.683e26, SPACE.getRectangle().getWidth() / 30, new Vector2(0, -230e9));
    private static final HeavenlyBody uranus = new HeavenlyBody("uranus", 8.68e25, SPACE.getRectangle().getWidth() / 40, new Vector2(0, 300e9));
    private static final HeavenlyBody neptune = new HeavenlyBody("neptune", 1e26, SPACE.getRectangle().getWidth() / 40, new Vector2(0, 320e9));
    private static final HeavenlyBody pluto = new HeavenlyBody("pluto", 1.e22, SPACE.getRectangle().getWidth() / 100, new Vector2(0, -500e9));

    static {
        sun.setVelocity(new Vector2(0, 0));
        earth.setVelocity(new Vector2(0, 3e9));
        mercury.setVelocity(new Vector2(-4.3e9, 0));
        venus.setVelocity(new Vector2(0, 3.5e9));
        mars.setVelocity(new Vector2(-2.9e9, 0));
        jupiter.setVelocity(new Vector2(-3e9, 1e7));
        saturn.setVelocity(new Vector2(3e9, 0));
        uranus.setVelocity(new Vector2(2e9, 0));
        neptune.setVelocity(new Vector2(-2e9, 0));
        pluto.setVelocity(new Vector2(-2e9, 0));

        sun.setColor(Color.YELLOW);
        earth.setColor(Color.BLUE);
        mercury.setColor(Color.GRAY);
        venus.setColor(Color.ORANGE);
        mars.setColor(Color.RED);
        jupiter.setColor(Color.GRAY);
        saturn.setColor(Color.GRAY);
        uranus.setColor(Color.BLUE);
        neptune.setColor(Color.GRAY);
        pluto.setColor(Color.MAGENTA);

        PLANETS.add(sun);
        PLANETS.add(earth);
        PLANETS.add(mercury);
        PLANETS.add(venus);
        PLANETS.add(mars);
        PLANETS.add(jupiter);
        PLANETS.add(saturn);
        PLANETS.add(uranus);
        PLANETS.add(neptune);
        PLANETS.add(pluto);

    }

    public static Space getSPACE() {
        return SPACE;
    }

    public static Set<HeavenlyBody> getPLANETS() {
        return PLANETS;
    }
}
