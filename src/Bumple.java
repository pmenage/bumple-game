import processing.core.PApplet;
import processing.event.KeyEvent;

public class Bumple extends PApplet {

    private static final int PIXELS_PER_METER = 200;
    private BumpCube bumpCube = new BumpCube();
    private Obstacle obstacle = new Obstacle(Obstacle.ObstacleType.Sliding, 1f, 1.5f);

    public static void main(String[] args) {
        Bumple.main(Bumple.class.getName());
    }

    public int metersToPixels(float meters) {
        return (int) (meters * PIXELS_PER_METER);
    }

    public Point<Integer> metersToPixels(Point<Float> meters) {
        return new Point<>(metersToPixels(meters.x) + 200, - metersToPixels(meters.y) + 500);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKey() == ' ')
            bumpCube.jump();
    }

    @Override
    public void settings() {
        size(400, 600);
    }

    @Override
    public void draw() {
        clear();
        bumpCube.draw(this);
        obstacle.draw(this);
    }

}