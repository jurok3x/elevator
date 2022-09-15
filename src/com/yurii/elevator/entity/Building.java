package com.yurii.elevator.entity;

import com.yurii.elevator.utils.ConsoleWriter;

import java.util.LinkedList;
import java.util.List;

public class Building implements Runnable{
    
    private static final Byte MAX_FLOORS = 20;
    private static final Byte MIN_FLOORS = 5;
    private static final Byte MAX_FLOOR_CAPACITY = 10;
    private static Building building;
    private List<Passenger> passengers;
    private Elevator elevator;
    private int floorCount;
    
    private Building() {
        //get total floor number of the building
        this.floorCount = (int) Math.round((Math.random() * (MAX_FLOORS - MIN_FLOORS) + MIN_FLOORS));
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
    
    public void run() {
        ConsoleWriter writer = new ConsoleWriter(floorCount, this.passengers, elevator);
        while(!Thread.currentThread().isInterrupted()) {
            List<Passenger> removedPassangers = this.elevator.removePassengers();
            if(removedPassangers != null) {
                //adding removed passengers back to list with new destination floor
                removedPassangers.stream().forEach(passenger -> passenger.setDestinationFloor(generateDestinationFloor(passenger.getDestinationFloor())));
                this.passengers.addAll(removedPassangers);
            }
            if(!this.elevator.isFull()) {
                List<Passenger> addedPassangers = this.elevator.addPassengers(this.passengers);
                this.passengers.removeAll(addedPassangers);
            }
            writer.log();
            System.out.printf("Moving to floor # %d%n", this.elevator.getCurrentFloor() + 1);
            this.elevator.move();
            try {
                Thread.currentThread();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
