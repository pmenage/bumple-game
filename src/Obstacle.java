import processing.core.PConstants;

public class Obstacle {

    private float height = 0.15f;
    private float width = 1f;
    private float duration;
    private Point<Float> position = new Point<>(0f,0f);
    private ObstacleType type;

    public enum ObstacleType {
        Sliding,
        Rotating
    }

    public Obstacle(ObstacleType type, float y, float duration) {
        this.type = type;
        position.y = y;
        this.duration = duration;
        if (type == ObstacleType.Sliding)
            position.x = -2 * width;
    }

    public void update() {
        if (position.x < -width)
            position.x += width / (duration * 60);
    }

    public void draw(Bumple bumple) {
        update();
        bumple.rectMode(PConstants.CORNER);
        Point<Integer> positionPixels = bumple.metersToPixels(position);
        bumple.rect(positionPixels.x, positionPixels.y, bumple.metersToPixels(width), bumple.metersToPixels(height));
    }

}
