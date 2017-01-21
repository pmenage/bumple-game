public class Obstacle {

    private float height = 0.15f;
    private float width = 1f;
    private Point<Float> position = new Point<Float>(0f,0f);
    private ObstacleType type;

    public enum ObstacleType {
        Sliding,
        Rotating
    }

    public Obstacle(ObstacleType type, float y) {
        this.type = type;
        position.y = y;
        if (type == ObstacleType.Sliding)
            position.x = 0.25f;
    }

    public void draw(Bumple bumple) {
        Point<Integer> positionPixels = bumple.metersToPixels(position);
        bumple.rect(positionPixels.x, positionPixels.y, bumple.metersToPixels(width), bumple.metersToPixels(height));
    }

}
