package com.yurii.elevator.entity;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Elevator {
    
    private final static int MAX_CAPACITY = 5;
    private List<Passenger> passengers;
    private int currentFloor;
    private int destinationFloor;
    private Direction currentDirection;
    private static Elevator elevator;
    private int topFloor;

    private Elevator() {
        passengers = new LinkedList<>();
        currentDirection = Direction.UP;
        currentFloor = 1;
    }
    
    public static Elevator getElevator() {
        if(elevator == null) {
            elevator = new Elevator();
        }
        return elevator;
    }

    public void setTopFloor(int topFloor) {
        this.topFloor = topFloor;
    }
    
    public List<Passenger> addPassengers(List<Passenger> passengers) {
        List<Passenger> addedPassengers = new LinkedList<>();
        if(this.currentDirection == Direction.UP) {
            passengers = passengers.stream()
                    .filter(passenger -> passenger.getStartFloor() == this.currentFloor)
                    .filter(passenger -> passenger.getDestinationFloor() > this.currentFloor)
                    .collect(Collectors.toList());
        } else {
            passengers = passengers.stream()
                    .filter(passenger -> passenger.getStartFloor() == this.currentFloor)
                    .filter(passenger -> passenger.getDestinationFloor() < this.currentFloor)
                    .collect(Collectors.toList());
        }
        Iterator<Passenger> it = passengers.iterator();
        while((addedPassengers.size() + this.passengers.size()) != MAX_CAPACITY && it.hasNext()) {
            addedPassengers.add(it.next());
        }
        this.passengers.addAll(addedPassengers);
        setDestinationFloor();
        return addedPassengers;
    }
    
    public Boolean isFull() {
        return this.passengers.size() == MAX_CAPACITY;
    }
    
    //removing passengers with destination floor equal to current floor
    public List<Passenger> removePassengers() {
        if(this.passengers.isEmpty()) {
            return null;
        }
        List<Passenger> removedPassengers = this.passengers.stream()
                .filter(passenger -> passenger.getDestinationFloor() == this.currentFloor).collect(Collectors.toList());
        if(removedPassengers.isEmpty()) {
            return null;
        }
        this.passengers.removeAll(removedPassengers);
        return removedPassengers;
    }
    
    public int getCurrentFloor() {
        return currentFloor;
    }

    //set new destination floor when new passengers are on board
    private void setDestinationFloor() {
     //when no one entered the empty elevator
        if(this.passengers.isEmpty()) {
            this.destinationFloor = (currentDirection == Direction.UP && this.currentFloor == topFloor) ? 1 : topFloor;
            return;
        }
        //change direction if we reached the destination floor
        if(this.destinationFloor == this.currentFloor) {
            if(this.passengers.stream().filter(passenger -> passenger.getDestinationFloor() > this.currentFloor).count() >= this.passengers.size() / 2) {
                this.currentDirection = Direction.UP;
            } else {
                this.currentDirection = Direction.DOWN;
            }
        }
        if(currentDirection == Direction.UP) {
            int maxFloor = this.passengers.stream().map(passenger -> passenger.getDestinationFloor()).max(Comparator.comparing(Integer::intValue)).get();
            if(this.currentFloor == maxFloor) {
                this.destinationFloor = this.passengers.stream().map(passenger -> passenger.getDestinationFloor()).min(Comparator.comparing(Integer::intValue)).get();
                return;
            } else {
                this.destinationFloor = maxFloor;
                return;
            }
        } else {
            int minFloor = this.passengers.stream().map(passenger -> passenger.getDestinationFloor()).max(Comparator.comparing(Integer::intValue)).get();
            if(this.currentFloor == minFloor) {
                this.destinationFloor = this.passengers.stream().map(passenger -> passenger.getDestinationFloor()).min(Comparator.comparing(Integer::intValue)).get();
                return;
            } else {
                this.destinationFloor = minFloor;
                return;
            }
        }
    }
    
    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void move() {
        if(this.currentDirection == Direction.UP) {
            if(this.currentFloor == this.topFloor || this.currentFloor == this.destinationFloor) {
                this.currentDirection = Direction.DOWN;
                this.currentFloor -= 1;
            } else {
                this.currentFloor += 1;
            }
        } else {
            if(this.currentFloor == 1 || this.currentFloor == this.destinationFloor) {
                this.currentDirection = Direction.UP;
                this.currentFloor += 1;
            } else {
                this.currentFloor -= 1;   
            }
        } 
    }

    @Override
    public String toString() {
        return "Elevator [passengers=" + passengers + ", currentFloor=" + currentFloor + ", destinationFloor="
                + destinationFloor + ", currentDirection=" + currentDirection + "]";
    }

}
