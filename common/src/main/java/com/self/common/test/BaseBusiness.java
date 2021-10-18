package com.self.common.test;

/**
 * @author GTsung
 * @date 2021/8/30
 */
public class BaseBusiness {


    static {
        System.out.println("super static");
    }


    public BaseBusiness() {
        System.out.println("super constructor");
    }

    {
        System.out.println("super stock");
    }

}
