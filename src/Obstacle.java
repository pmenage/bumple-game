import processing.core.PConstants;

public class Obstacle {

    private float height = 0.15f;
    private float width = 1f;
    private float duration;
    private float angle;
    private Point position = new Point(0f,0f);
    private ObstacleType type;

    public enum ObstacleType {
        Sliding,
        Rotating
    }

    public Point getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getAngle() {
        return angle;
    }

    public Obstacle(ObstacleType type, float y, float duration) {
        this.type = type;
        position.y = y;
        this.duration = duration;
        switch (type) {
            case Sliding:
                position.x = -2 * width;
                angle = 0;
                break;
            case Rotating:
                position.x = -width;
                angle = (float) (- Math.PI / 2);
                break;
        }
    }

    public void update() {
        switch (type) {
            case Sliding:
                if (position.x < -width)
                    position.x += width / (duration * 60);
                break;
            case Rotating:
                if (angle < 0)
                    angle += (Math.PI / 2) / (duration * 60);
                break;
        }
    }

    public void draw(Bumple bumple) {
        bumple.rectMode(PConstants.CORNER);
        Point positionPixelsLeft = bumple.metersToPixels(position);
        Point positionPixelsRight = bumple.metersToPixels(new Point(-position.x, position.y));

        // Left part of obstacle
        bumple.pushMatrix();
        bumple.translate(positionPixelsLeft.x, positionPixelsLeft.y);
        bumple.rotate(-angle);
        bumple.rect(0, 0, bumple.metersToPixels(width), bumple.metersToPixels(height));
        bumple.popMatrix();

        // Right part of obstacle
        bumple.pushMatrix();
        bumple.translate(positionPixelsRight.x, positionPixelsRight.y);
        bumple.rotate(angle);
        bumple.translate(-bumple.metersToPixels(width), 0);
        bumple.rect(0, 0, bumple.metersToPixels(width), bumple.metersToPixels(height));
        bumple.popMatrix();
    }

}
