/*
 * @Author: Wonder2019 
 * @Date: 2020-07-30 07:48:40 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-07-30 07:48:40 
 */
package top.imwonder.stunsuite;

import java.util.ArrayList;
import java.util.List;
// import java.util.Scanner;

public class StunClient {
    private static List<String> stunserverList;

    static {
        stunserverList = new ArrayList<>();
        stunserverList.add("stun.l.google.com:19302");
        stunserverList.add("stun1.l.google.com:19302");
        stunserverList.add("stun2.l.google.com:19302");
        stunserverList.add("stun3.l.google.com:19302");
        stunserverList.add("stun4.l.google.com:19302");
        stunserverList.add("stun01.sipphone.com:3478");
        stunserverList.add("stun.ekiga.net:3478");
        stunserverList.add("stun.fwdnet.net:3478");
        stunserverList.add("stun.rixtelecom.se:3478");
        stunserverList.add("stun.schlund.de:3478");
    }

    public static void main(String[] args) {
        // try (Scanner scan = new Scanner(System.in)) {
        //     while (scan.hasNextLine()) {

        //     }
        // }
    }
}