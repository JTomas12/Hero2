import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.concurrent.ThreadLocalRandom;

public class Monster extends Element{
    public Monster(int x, int y) {
        super(x,y);
    }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#F54242"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "M");
    }

    public Position move() {
        int newX = ThreadLocalRandom.current().nextInt(getX()-1, getX()+2);
        int newY = ThreadLocalRandom.current().nextInt(getY()-1, getY()+2);
        Position newPosition = new Position(newX, newY);
        return newPosition;
    }
}
