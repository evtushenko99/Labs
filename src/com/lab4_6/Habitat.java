package com.lab4_6;


import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Timer;

public class Habitat extends Applet {
    private int width = 640;
    private int height = 480;
    // tast object
    private Task task = new Task(this);
    private JFrame frameWindow = new JFrame("Frame window");// для таймера
    private JLabel font = new JLabel();

    private boolean run = false;
    private boolean flagStop = false;
    private int flag = 0;

    private Image goldFish;
    private Image guppiFish;
    private Image offScreenImage;
    private String m_FileName_GoldFish = "C:\\Users\\admin\\Desktop\\goldFish.jpg";
    private String m_FileName_GuppiFish = "C:\\Users\\admin\\Desktop\\guppi.jpg";
    private double m_time = 0;
    // variant 3
    private Random rnd = new Random();
    private int n1 = rnd.nextInt(3) + 1;
    private int n2 = rnd.nextInt(3) + 1;
    private int p1 = rnd.nextInt(10) + 1;
    private int p2 = rnd.nextInt(10) + 1;

    //LinkedList and TreeMap
    private LinkedList<GoldFish> goldFishVector = new LinkedList<>();
    private LinkedList<GuppiFish> guppiFishVector = new LinkedList<>();
    private TreeMap<Integer, Double> goldFishHM = new TreeMap<Integer, Double>();
    private TreeMap<Integer, Double> guppiFishHM = new TreeMap<Integer, Double>();
    // values
    private Timer timer = new Timer();
    private double currentTime = 0;
    private Random rand = new Random();
    private boolean hideTimer = false;
    private boolean running = false;


    public Habitat(){
// обработчик события от клавиатуры
        KeyAdapter pk;
        pk = new KeyAdapter() {
            public void keyPressed(KeyEvent e){
                int keyCode = e.getKeyCode();
                System.out.println("Pressed[" + e.getKeyChar() + "]");
                switch (keyCode) {
                    case KeyEvent.VK_W:
                        destroy();
                        break;
                    case KeyEvent.VK_B:
                        if (!running) {
                            running = true;
                            frameWindow.setVisible(true);
                            timer.schedule(task, 0, 1000);
                            repaint();
                        }
                        break;
                    case KeyEvent.VK_T:
                        hideTimer = !hideTimer;
                        repaint();
                        break;
                    case KeyEvent.VK_E:
                        frameWindow.setVisible(false);
                        timer.cancel();
                        running = false;
                        for (int i = 1; i <= goldFishHM.size(); i++) {
                            System.out.println("Gold Fish [" + i + "] birthtime: " +
                                    goldFishHM.get(i));
                        }
                        for (int i = 1; i <= guppiFishHM.size(); i++) {
                            System.out.println("Guppi fish [" + i + "] birthtime: " +
                                    guppiFishHM.get(i));
                        }
                        repaint();
                        break;
                }
            }
        };
        this.addKeyListener(pk);
    }

    public void init(){
        String paramForGoldFish;
        paramForGoldFish = getParameter("filename");
        if (paramForGoldFish != null) m_FileName_GoldFish = paramForGoldFish;
        goldFish = getToolkit().getImage(m_FileName_GoldFish);

        String paramForGuppiFish;
        paramForGuppiFish = getParameter("filename");
        if (paramForGuppiFish != null) m_FileName_GuppiFish = paramForGuppiFish;
        guppiFish = getToolkit().getImage(m_FileName_GuppiFish);

        System.out.println("Generation delay(N1): " + n1);
        System.out.println("Generation delay(N2): " + n2);
        System.out.println("Probability(P1): " + p1);
        System.out.println("Probability(P2): " + p2);

        frameWindow.setSize(100, 100);
        frameWindow.setVisible(false);

        frameWindow.add(font);

/*
        time.schedule(new Updater(this), 0, 100);

        time1.schedule(new TimerTask() {
            public void run(){
                if (flagStop == false) {
                    new Thread(() -> {
                        for (int i = 0; i < imageGoldFishes.size(); i++) {
                            y1.set(i, y1.get(i) + 15);
                            n2 = rnd.nextInt(3);
                            if (n2 == 1 || n2 == 3)
                                x1.set(i, x1.get(i) + 5);
                            // else if (n2 == 2)
                            //   x1.set(i, x1.get(i) - 10);
                            repaint();
                        }
                    }).start();

                    new Thread(() -> {
                        for (int i = 0; i < imageGuppiFishes.size(); i++) {
                            x2.set(i, x2.get(i) + 20);
                            repaint();
                        }
                    }).start();
                }
            }
        }, 0, 200);

        time2.schedule(new TimerTask() {
            @Override
            public void run(){
                if (flagStop == false) {
                    i++;
                    imageGoldFishes.add(i, goldFish);
                    y1.add(i, 0);
                    x1.add(i, 0);
                }
            }
        }, 6000, 6000);
        time3.schedule(new TimerTask() {
            @Override
            public void run(){
                if (flagStop == false) {
                    //num1 = rnd.nextInt(3);
                    if (n1 == 1) {
                        i1++;
                        imageGuppiFishes.add(i1, guppiFish);
                        x2.add(i1, 0);
                    }
                }
            }
        }, 5000, 5000);*/
    }

    public void paint(Graphics g){
        // создание виртуального экрана
        int width = getSize().width;
        int height = getSize().height;
        resize(width, height);
        offScreenImage = createImage(width, height);
        // получение его контекста
        Graphics offScreenGraphics = offScreenImage.getGraphics();
        // вывод изображения на виртуальный экран
        if (running) {
            offScreenGraphics.setColor(Color.WHITE);
            offScreenGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
            offScreenGraphics.setColor(Color.BLACK);
            font.setText("Time = " + Double.toString(m_time));
            if (hideTimer) {
                frameWindow.setVisible(false);
                String timeStr = "Time = " + Integer.toString((int) currentTime);
                offScreenGraphics.setFont(new Font("Helvetica", Font.PLAIN, 22));
                offScreenGraphics.drawString(timeStr, width - 150, height - (height - 25));

            }

            for (int i = 0; i < goldFishVector.size(); i++) {
                Thread develops = new Thread(goldFishVector.get(i));
                develops.start();


                if (goldFishVector.get(i).getX() < width) {
                    offScreenGraphics.drawImage(goldFish,
                            goldFishVector.get(i).getX(), goldFishVector.get(i).getY(), this);
                }
            }
            for (int i = 0; i < guppiFishVector.size(); i++) {
                Thread managers = new Thread(guppiFishVector.get(i));
                managers.start();

                if (guppiFishVector.get(i).getX() > 0) {
                    offScreenGraphics.drawImage(guppiFish,
                            guppiFishVector.get(i).getX(), guppiFishVector.get(i).getY(), this);
                }
            }
        } else {
            int tmpWidth = 0;
            String timeStr = "Simulation currentTime: " + Double.toString(currentTime);
            String blockStr = "Gold fishes created: " + goldFishVector.size();
            String woodStr = "Guppi fishes created: " + guppiFishVector.size();

            offScreenGraphics.setFont(new Font("Helvetica", Font.PLAIN, 22));
            tmpWidth = g.getFontMetrics().stringWidth(timeStr);
            offScreenGraphics.drawString(timeStr, width - tmpWidth - 220, height - (height - 25));

            offScreenGraphics.setFont(new Font("Helvetica", Font.PLAIN, 16));
            tmpWidth = g.getFontMetrics().stringWidth(blockStr);
            offScreenGraphics.drawString(blockStr, width - tmpWidth - 220, height - (height - 45));


            offScreenGraphics.setFont(new Font("Helvetica", Font.PLAIN, 16));
            tmpWidth = g.getFontMetrics().stringWidth(woodStr);
            offScreenGraphics.drawString(woodStr, width - tmpWidth - 220, height - (height - 65));

            goldFishVector.clear();
            guppiFishVector.clear();
        }
        g.drawImage(offScreenImage, 0, 0, this);

    }


    public void Update(double elapsedTime, double frameTime){
        currentTime = elapsedTime;
        int tmpTime = (int) elapsedTime;

        m_time = elapsedTime;

        int speed = rand.nextInt(10) + 10;
        font.setText("Time = " + Double.toString(m_time));
        if (tmpTime % n1 == 0 && rand.nextInt(20) <= p1) {
            goldFishVector.add(new GoldFish(rand.nextInt(120), rand.nextInt(80), speed));
            goldFishHM.put(goldFishVector.size(), currentTime);
        }

        if (tmpTime % n2 == 0 && rand.nextInt(20) <= p2) {
            guppiFishVector.add(new GuppiFish(rand.nextInt(120), rand.nextInt(80), speed));
            guppiFishHM.put(guppiFishVector.size(), currentTime);
        }

        this.repaint();
        Toolkit.getDefaultToolkit().sync();

    }
}


