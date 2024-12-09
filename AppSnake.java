import javax.swing.JFrame;
import java.awt.BorderLayout;

public class AppSnake {
    public static void main(String[] args) throws Exception {


        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.add(new SnakeGame(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
