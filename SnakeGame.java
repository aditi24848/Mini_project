import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private final int CELL_SIZE = 10;
    private final int DELAY = 120;
    private int foodEaten = 0;
    private ArrayList<Point> snake;
    private Point food;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
    private boolean shouldGrow = false;

    public SnakeGame() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        // SnakeGameOverlay overlay = new SnakeGameOverlay((JFrame) SwingUtilities.getWindowAncestor(this));
        // overlay.setVisible(true);
        startGame();
    }

    private void startGame() {
        snake = new ArrayList<>();
        snake.add(new Point(WIDTH / 2, HEIGHT / 2));
        generateFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void generateFood() {
        // increase snake size
        Random rand = new Random();
        int x = rand.nextInt(WIDTH / CELL_SIZE) * CELL_SIZE;
        int y = rand.nextInt(HEIGHT / CELL_SIZE) * CELL_SIZE;
        food = new Point(x, y);
    }

    //   obstacles = new ArrayList<>();
    //   Random rand = new Random();
    //   int numObstacles = rand.nextInt(10) + 1;
    //   for (int i = 0; i < numObstacles; i++) {
    //     int x = rand.nextInt(WIDTH / CELL_SIZE) * CELL_SIZE;
    //     int y = rand.nextInt(HEIGHT / CELL_SIZE) * CELL_SIZE;
    //     obstacles.add(new Point(x, y));
    //   }
    // }

    private void move() {
        Point tail = snake.get(snake.size() - 1);
        Point newTail = new Point(tail.x, tail.y);
        if (shouldGrow) {
            snake.add(newTail);
            shouldGrow = false;
        }
        for (int i = snake.size() - 1; i > 0; i--) {
            Point next = snake.get(i - 1);
            snake.set(i, next);
        }
        Point head = snake.get(0);
        int x = head.x;
        int y = head.y;
        if (direction == 'L') {
            x -= CELL_SIZE;
        } else if (direction == 'R') {
            x += CELL_SIZE;
        } else if (direction == 'U') {
            y -= CELL_SIZE;
        } else if (direction == 'D') {
            y += CELL_SIZE;
        }
        Point newHead = new Point(x, y);
        // snake.set(0, newHead);
        //use head.png to replace the head of the snake
        snake.set(0, newHead);

    }

    private void checkCollision() {
        // check if snake has collided with food
        if (snake.get(0).equals(food)) {
            snake.add(food);
            this.shouldGrow = true;
            generateFood();
            foodEaten++; // increment foodEaten
            return;
        }
        // check if snake has collided with the boundaries of the window
        if (snake.get(0).x < 0 || snake.get(0).x >= WIDTH || snake.get(0).y < 0 || snake.get(0).y >= HEIGHT) {
            gameOver();
        }

        // check if snake has collided with itself
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).equals(snake.get(i))) {
                gameOver();
            }
        }
    }

    private void gameOver() {
        running = false;
        timer.stop();
        //set foodEaten to 0
        this.foodEaten = 0;
        // JOptionPane.showMessageDialog(this, "Game over!");
        int option = JOptionPane.showOptionDialog(this, "Game over! Play again?", "Game Over", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (option == JOptionPane.YES_OPTION) {
            startGame();
        } else {
            System.exit(0);
        }
    }

    public boolean shouldGrow() {
        return shouldGrow;
    }

    public void reset() {
        shouldGrow = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.drawString("Score: " + foodEaten, 10, 20);
        // draw the snake
        for (Point p : snake) {
            g.setColor(Color.WHITE);
            g.fillRect(p.x, p.y, CELL_SIZE, CELL_SIZE);
        }
        // draw the food
        g.setColor(Color.RED);
        g.fillRect(food.x, food.y, CELL_SIZE, CELL_SIZE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollision();
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT && direction != 'R') {
            direction = 'L';
        } else if (keyCode == KeyEvent.VK_RIGHT && direction != 'L') {
            direction = 'R';
        } else if (keyCode == KeyEvent.VK_UP && direction != 'D') {
            direction = 'U';
        } else if (keyCode == KeyEvent.VK_DOWN && direction != 'U') {
            direction = 'D';
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}