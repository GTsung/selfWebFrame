package com.self.common.test;

/**
 * @author GTsung
 * @date 2021/9/5
 */
public class SonBusiness extends BaseBusiness {

    static {
        System.out.println("son static");
    }


    public SonBusiness() {
        System.out.println("son constructor");
    }

    {
        System.out.println("son stock");
    }

    public static void main(String[] args) {
        SonBusiness sonBusiness = new SonBusiness();
    }
}
