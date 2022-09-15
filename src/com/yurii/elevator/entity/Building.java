package com.yurii.elevator.entity;

import java.util.LinkedList;
import java.util.List;

public class Building {
    
    private static final Byte MAX_FLOORS = 20;
    private static final Byte MIN_FLOORS = 5;
    private static final Byte MAX_FLOOR_CAPACITY = 10;
    private static Building building;
    private List<Passenger> passengers;
    private Elevator elevator;
    private Byte floorCount;
    
    private Building() {
        //get total floor number of the building
        this.floorCount = (byte) Math.round((Math.random() * (MAX_FLOORS - MIN_FLOORS) + MIN_FLOORS));
        //adding passengers to each floor
        this.passengers = new LinkedList<>();
        for(int i=1;i<=floorCount;i++) {
            Byte passengersCount = (byte) Math.round(Math.random() * MAX_FLOOR_CAPACITY);
            for(int j=0;j<=passengersCount;j++) {
                Passenger passenger = new Passenger(i, generateDestinationFloor(i));
                this.passengers.add(passenger);
            }
        }
        this.elevator = Elevator.getElevator();
        this.elevator.setTopFloor(this.floorCount);
    }
    
    public static Building getBuilding() {
        if(building == null) {
            building = new Building();
        }
        return building;
    }
    
    public void runElevator() {
        List<Passenger> removedPassangers = this.elevator.removePassengers();
        if(removedPassangers != null) {
            System.out.println(String.format("Removing passengers: %s, floor # %d", removedPassangers, this.elevator.getCurrentFloor()));
            //adding removed passengers back to list with new destination floor
            removedPassangers.stream().forEach(passenger -> passenger.setDestinationFloor(generateDestinationFloor(passenger.getDestinationFloor())));
            this.passengers.addAll(removedPassangers);
        }
        if(!this.elevator.isFull()) {
            List<Passenger> addedPassangers = this.elevator.addPassengers(this.passengers);
            this.passengers.removeAll(addedPassangers);
            System.out.println(String.format("Adding passengers: %s, floor # %d", addedPassangers, this.elevator.getCurrentFloor()));
        }
        this.elevator.move();
        System.out.printf("Moving to flor # %d%n", this.elevator.getCurrentFloor());
    }
    
  //function to prevent equal current and destination floors for the passenger
    private int generateDestinationFloor(int currentFloor) {
        int destinationFloor = (int) Math.round(Math.random() * (this.floorCount - 1) + 1);
        while(currentFloor == destinationFloor) {
            destinationFloor = (byte) Math.round(Math.random() * (this.floorCount - 1) + 1);
        }
        return destinationFloor;
    }

    @Override
    public String toString() {
        return "Building [passenger=" + passengers + ", elevator=" + elevator + ", floorCount=" + floorCount + "]";
    }

}
