package com.xmio.wowza.examples;

import com.xmio.wowza.utils.TokenManager;

public class test {
    public static void main(String[] args) {
        System.out.println(TokenManager.verify("t=e84c37d78b98e1c9367c0d81e002ef40&e=1450430107", "nas2/2410_Giac_mo_Arizona___Arizona_Dream_tap_1_3394000.mp4", "192.168.90.27"));
    }
}
