package Snake;
import javax.swing.*;


public class SnakeFrame extends JFrame
{

    // constructor

    SnakeFrame(){

        super("Snake Game");

        add(new SnakePanel());
        pack();

        this.setSize(300,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }


    public static void main( String[] args )
    {
        new SnakeFrame();

    }
}

