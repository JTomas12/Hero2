import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.googlecode.lanterna.input.KeyType.*;

public class Arena {
    private int width;
    private int height;

    private List<Wall> walls;

    private List<Coin> coins;

    private List<Monster> monsters;

    private Hero hero = new Hero(10,10);

    public Arena (int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters= createMonsters();
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            coins.add(new Coin(random.nextInt(width - 2) + 1,random.nextInt(height - 2) + 1));
        return coins;
    }

    private void retrieveCoins() {
        for (Coin coin : coins) {
            if (coin.getPosition().equals(hero.getPosition())) {
                coins.remove(coin);
                break;
            }
        }
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            monsters.add(new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        }
        return monsters;
    }

    private boolean canHeroMove(Position position) {
        for (Wall wall : walls)
            if (wall.getPosition().equals(position)) return false;
        return true;
    }

    private boolean canMonsterMove(Position position) {
        return canHeroMove(position);
    }

    private void  verifyMonsterCollisions(TextGraphics graphics) {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(hero.getPosition())) {
                    System.out.println("Game Over");
                    System.exit(0);
            }
        }
    }

    public void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
            retrieveCoins();
    }

    private void moveMonsters() {
        for (Monster monster : monsters) {
            Position position = monster.move();
            if (canMonsterMove(position))
                monster.setPosition(position);
        }

    }



    public void processKey(KeyStroke key,TextGraphics graphics ) {
        moveMonsters();
        verifyMonsterCollisions(graphics);
        switch (key.getKeyType()) {
            case ArrowUp: moveHero(hero.moveUp()); break;
            case ArrowRight: moveHero(hero.moveRight()); break;
            case ArrowDown: moveHero(hero.moveDown()); break;
            case ArrowLeft: moveHero(hero.moveLeft()); break;
        }
    }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        for (Wall wall : walls)
            wall.draw(graphics);
        for (Coin coin : coins)
            coin.draw(graphics);
        for (Monster monster : monsters)
            monster.draw(graphics);}

}
