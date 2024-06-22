package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SnakePanel extends JPanel implements ActionListener {

    int dots;
    Image dotImage, headImage, appleImage;
    int total_Size = 900;
    int image_Size = 10;

    final int[] x = new int[total_Size];
    final int[] y = new int[total_Size];

    int randomPosition = 28;

    int appleXposition;
    int appleYposition;

    Timer timer;

    boolean up_direction = false;
    boolean down_direction = false;
    boolean left_direction = false;
    boolean right_direction = true;


    boolean inGame = true;



    // constructor
    SnakePanel(){

        setBackground(Color.black);
        setFocusable(true);

        addKeyListener(new KeyMovement());

        loadImage();

        initGame();

    }

    public void loadImage(){

        try {

            ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Snake/icons/head.png"));
            headImage = i1.getImage();

            ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("Snake/icons/dot.png"));
            dotImage = i2.getImage();

            ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("Snake/icons/apple.png"));
            appleImage = i3.getImage();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public void initGame(){
        dots = 3;

        for(int i = 0; i < dots; i++){

            y[i] = 50;
            x[i] = 50 - i * image_Size;

        }


        generateApple();

        timer = new Timer(200,this);
        timer.start();
    }

    public void generateApple(){

        int r1 = (int) (Math.random() * randomPosition);
        appleXposition = r1 * image_Size;

        int r2 = (int) (Math.random() * randomPosition);
        appleYposition = r2 * image_Size;
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);

        draw(g);
    }

    public void draw(Graphics g){

        if (inGame){
            // draw apple
            g.drawImage(appleImage, appleXposition, appleYposition, this);


            // draw head and dot

            for(int i = 0; i < dots; i++){

                if (i == 0) {
                    g.drawImage(headImage, x[i], y[i], this);   // head
                } else {
                    g.drawImage(dotImage, x[i], y[i], this);    // dots
                }

                Toolkit.getDefaultToolkit().sync();


            }
        } else {
            gameOver(g);
        }


    }

    public void gameOver(Graphics g){

        String message = "Game Over";
        Font font = new Font("SansSerif", Font.BOLD,25);

        FontMetrics metrices = getFontMetrics(font);
        g.setColor(Color.white);
        g.setFont(font);

        g.drawString(message, (300 - metrices.stringWidth(message))/2, 300/2);




    }

    public void moveSnake(){
        for (int i = dots; i > 0; i--){
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (right_direction){
            x[0] += image_Size;
        }

        if (left_direction){
            x[0] -= image_Size;
        }

        if (up_direction){
            y[0] -= image_Size;
        }

        if (down_direction){
            y[0] += image_Size;
        }


    }

    public void eatApple(){
        if ((x[0] == appleXposition) && (y[0] == appleYposition)){
            dots++;
            generateApple();
        }
    }

    public void snakeCollision(){
        for (int j = dots; j > 0; j--){
            if ((j > 5) && (x[0] == x[j]) && (y[0] == y[j])){
                inGame = false;
            }
        }


        if (x[0] >= 300 ){
            inGame = false;
        }

        if (y[0] >= 300){
            inGame = false;
        }

        if (x[0] < 0){
            inGame = false;
        }

        if (y[0] < 0){
            inGame = false;
        }

        if (!inGame){
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        eatApple();
        snakeCollision();
        moveSnake();

        repaint();
    }


    public class KeyMovement extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && (!right_direction)){
                left_direction = true;
                up_direction = false;
                down_direction = false;
            }

            if (key == KeyEvent.VK_RIGHT && (!left_direction)){
                right_direction = true;
                up_direction = false;
                down_direction = false;
            }

            if (key == KeyEvent.VK_UP && (!down_direction)){
                up_direction = true;
                right_direction = false;
                left_direction = false;
            }

            if (key == KeyEvent.VK_DOWN && (!up_direction)){
                down_direction = true;
                right_direction = false;
                left_direction = false;
            }
        }
    }



}

