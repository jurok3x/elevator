package com.yurii.elevator;

import com.yurii.elevator.entity.Building;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Press 'q' to stop");
        Thread thread = new Thread(Building.getBuilding());
        thread.start();
        if(System.in.read() == 'q') {
            thread.stop();
        }
    }

}
