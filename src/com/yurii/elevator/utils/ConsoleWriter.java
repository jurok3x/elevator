package com.yurii.elevator.utils;

import com.yurii.elevator.entity.Elevator;
import com.yurii.elevator.entity.Passenger;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleWriter {
    
    private int floorsCount;
    private List<Passenger> buildingPassengers;
    private Elevator elevator;
    private StringBuilder format;
    
    public ConsoleWriter(int floorsCount, List<Passenger> buildingPassengers, Elevator elevator) {
        this.floorsCount = floorsCount;
        this.buildingPassengers = buildingPassengers;
        this.elevator = elevator;
        this.format = new StringBuilder();
        this.format.append("| Floor #%d |- %s -| %s |%n");
    }
    
    public void log() {
        List<Passenger> currentFloorPassengers = this.buildingPassengers.stream()
                .filter(passenger -> passenger.getStartFloor() == this.elevator.getCurrentFloor()).collect(Collectors.toList());
        System.out.printf(this.format.toString(), this.elevator.getCurrentFloor(), this.elevator.getPassengers(), currentFloorPassengers);
    }
 
}
