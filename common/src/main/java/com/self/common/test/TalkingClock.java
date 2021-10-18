package com.self.common.test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

/**
 * @author GTsung
 * @date 2021/9/20
 */
public class TalkingClock {
    private int interval;
    private boolean beep;

    public TalkingClock(int interval, boolean beep) {
        this.interval = interval;
        this.beep = beep;
    }

    public void start() {
        final int s = 1;
        class TimePrinter2 implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("=====");
                if (beep) {
                    Toolkit.getDefaultToolkit().beep();
                }
                System.out.println(s);
            }
        }
    }

    // 内部类可以访问外部类的属性，有一个指向外部类的指针
    public class TimePrinter implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("At the tone, the time is " +
                    Instant.ofEpochMilli(e.getWhen()));
            if (beep) {
                Toolkit.getDefaultToolkit().beep();
            }
        }

    }

    public static class InnerStaticClass {
        // 静态内部类并没有指向外部类的指针，因此不能直接访问外部类的属性
        // 静态内部类可以有静态方法和字段
    }

    public static void main(String[] args) {
        // 匿名内部类
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("");
            }
        });
    }
}
