import processing.core.PConstants;

public class BumpCube {

    private float size = 0.4f;
    private float y = size/2;
    private float vy = 0f;
    private float ay = 0f;
    private boolean isOnGround = true;

    public void jump() {
        if (isOnGround)
            ay += 30f;
    }

    public void update() {
        isOnGround = false;
        ay *= 0.94f;
        vy += (ay - 9.81f) / 60;
        y += vy / 60;
        if (y < size/2) {
            isOnGround = true;
            y = size/2;
            vy = 0;
        }
    }

    public void draw(Bumple bumple) {
        update();
        int sizePixels = bumple.metersToPixels(size);
        Point<Integer> position = bumple.metersToPixels(new Point<Float>(0f, y));
        bumple.rectMode(PConstants.CENTER);
        bumple.rect(position.x, position.y, sizePixels, sizePixels);
    }

}