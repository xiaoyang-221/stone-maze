package com.yang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {
    //设置图片位置、
    private static final String imagePath = "stone-maze/src/image/";
    //准备一个数组，用户存储数字色块的行列位置，4行4列
    private int[][] imageData = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };
    //定义一个二维数组，用来存储最终游戏成功的数组
    private  int[][] winData = new int[][]{
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9,10, 11, 12},
            {13, 14, 15, 0}
    };
    //定义两个整数变量，记录当前空白色块的行列位置
    private int Row ;// 行
    private int Col ;// 列
    private int count;//总共移动的步数

    public MainFrame(){
        //调用一个初始化方法，初始化窗口大小等信息
        initFrame();
        //打乱数组色块顺序
        initRandomArray();
        //初始化界面，展示数字色块
        initImage();
        //初始化系统菜单，
        initMenu();
        //给当前窗口绑定上下左右按键事件
        initKeyPressEvent();
        //设置窗口显示
        this.setVisible(true);
    }

    private void initKeyPressEvent() {
        //给当前窗口绑定上下左右按键事件
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //获取当前按键的编号
                int keyCode = e.getKeyCode();
                //判断按键编号是否是上下左右的键
                switch (keyCode) {
                    // 上
                    case KeyEvent.VK_UP:
                        SwitchAndMove(Direction.UP);
                    break;
                    // 下
                    case KeyEvent.VK_DOWN:
                        SwitchAndMove(Direction.DOWN);
                    break;
                    // 左
                    case KeyEvent.VK_LEFT:
                        SwitchAndMove(Direction.LEFT);
                    break;
                    // 右
                    case KeyEvent.VK_RIGHT:
                        SwitchAndMove(Direction.RIGHT);
                    break;
                }
            }
        });
    }
    private void SwitchAndMove(Direction direction) {
        //判断图片的方向，再判断图片的移动
        switch (direction) {
            case UP:
                //上交换的条件的是行必须<3,然后才开始交换
                if(Row<imageData.length-1){
                    //当前空白快位置：Row Col
                    //需要被交换的位置：Row+1 Col
                    int temp = imageData[Row][Col];
                    imageData[Row][Col] = imageData[Row+1][Col];
                    imageData[Row+1][Col] = temp;
                    //更新当前空白色块的位置
                    Row++;
                    count++;
                }
                break;
            case DOWN:
                //下交换的条件的是行必须>0,然后才开始交换
                if(Row>0){
                    //当前空白块位置：Row Col
                    //需要被交换的位置：Row-1 Col
                    int temp = imageData[ Row][ Col];
                    imageData[Row][Col] = imageData[Row-1][Col];
                    imageData[Row-1][Col] = temp;
                    //更新当前空白色块的位置
                    Row--;
                    count++;
                }
                break;
            case LEFT:
                //左交换的条件是列必须<3,然后才开始交换
                if(Col<imageData.length-1){
                    //当前空白块位置：Row Col
                    //需要被交换的位置：Row Col+1
                    int temp = imageData[ Row][ Col];
                    imageData[Row][Col] = imageData[Row][Col+1];
                    imageData[Row][Col+1] = temp;
                    //更新当前空白色块的位置
                    Col++;
                    count++;
                }
                break;
            case RIGHT:
                //右交换的条件是列必须>0,然后才开始交换
                if(Col>0){
                    //当前空白块位置：Row Col
                    //需要被交换的位置：Row Col-1
                    int temp = imageData[ Row][ Col];
                    imageData[Row][Col] = imageData[Row][Col-1];
                    imageData[Row][Col-1] = temp;
                    //更新当前空白色块的位置
                    Col--;
                    count++;
                }
                break;
        }
        initImage();
    }

    private void initRandomArray() {
        for (int i = 0; i < imageData.length; i++) {
            for (int j = 0; j < imageData[i].length; j++) {
                //随即两个行列位置，让两个位置交换
                int i1= (int)(Math.random() * imageData.length);
                int j1= (int)(Math.random() * imageData.length);

                int i2= (int)(Math.random() * imageData.length);
                int j2= (int)(Math.random() * imageData.length);

                int temp = imageData[i1][j1];
                imageData[i1][j1] = imageData[i2][j2];
                imageData[i2][j2] = temp;
            }
        }
        //定位空白色块的位置
        //去二维数组中遍历，找到0，整个位置就是空白块
        OUT:
        for (int i = 0; i < imageData.length; i++) {
            for (int j = 0; j < imageData[i].length; j++) {
                if (imageData[i][j] == 0) {
                    //获取空白块的行和列
                    Row = i;
                    Col = j;
                    break OUT;//跳出内层循环
                }
            }
        }
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("系统");
        JMenuItem exitJi= new JMenuItem("退出");
        menu.add(exitJi);
        exitJi.addActionListener(e -> {
            dispose();
        });
        //天机一个重启菜单
        JMenuItem restartJi = new JMenuItem("重启");
        menu.add(restartJi);
        restartJi.addActionListener(e -> {
            //重启游戏：重新打乱二维数组的顺序，重新刷新界面
            count = 0;
            initRandomArray();
            initImage();
        });
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }

    private void initImage() {
        //先清空窗口上的所有图层
        this.getContentPane().removeAll();
        //刷新界面时可以显示步数
        JLabel countxt = new JLabel("当前移动"+count+"步");
        countxt.setBounds(20, 20, 100, 20);
        //设置文字为红色，加粗
        countxt.setForeground(Color.RED);
        countxt.setFont(new Font("楷体", Font.BOLD, 16));
        this.add(countxt);
        //判断是否胜利
        if(iswin()){
            //展示胜利图片
            JLabel winLabel = new JLabel(new ImageIcon(imagePath+"win.png"));
            winLabel.setBounds(124, 230, 266, 88);
            this.add(winLabel);
        }
        //展示一个行列矩阵的图片色块，一次铺满窗口（4*4）
        for (int i = 0; i < imageData.length; i++) {
            for (int j = 0; j < imageData[i].length; j++) {
                //拿到图片的名称
                String imageName = imageData[i][j]+".png";
                //创建一个JLbel对象，用来展示图片色块
                JLabel label = new JLabel();
                //设置图片
                label.setIcon(new ImageIcon(imagePath+imageName));
                //设置图片位置
                label.setBounds(20+j * 100, 60+i * 100, 100, 100);
                //添加到窗口中
                this.add(label);
            }
        }
        //设置背景图片
        JLabel background= new JLabel(new ImageIcon(imagePath+"background.png"));
        background.setBounds(0, 0, 450, 484);
        this.add(background);

        //再刷新图层
        this.repaint();
    }

    private boolean iswin() {
        //判断游戏二维数组和赢了之后的二维数组是否一致
        for (int i = 0; i < imageData.length; i++) {
            for (int j = 0; j < imageData[i].length; j++) {
                if(imageData[i][j] !=winData[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    private void initFrame(){
        //设置窗口标题
        this.setTitle("石头迷阵");
        //设置窗口大小
        this.setSize(465, 575);
        //设置窗口关闭方式
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口居中
        this.setLocationRelativeTo(null);
        //设置窗口布局方法为绝对布局
        this.setLayout(null);
        //给窗口添加一个展示文字的组件
        JLabel countxt = new JLabel("当前移动0步");
        countxt.setBounds(0, 0, 100, 20);
        countxt.setForeground(Color.RED);
        countxt.setFont(new Font("楷体", Font.BOLD, 16));
        this.add(countxt);
    }
}
