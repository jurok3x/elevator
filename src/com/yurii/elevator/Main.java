package com.yurii.elevator;

import com.yurii.elevator.entity.Building;

public class Main {

    public static void main(String[] args) {
        for(int i = 0; i<15; i++) {
            Building.getBuilding().runElevator(); 
        }
        
    }

}
