package com.yurii.elevator.entity;

public class Passenger {
    
    private Integer startFloor;
    private Integer destinationFloor;
    
    public Passenger(Integer startFloor, Integer destinationFloor) {
        this.startFloor = startFloor;
        this.destinationFloor = destinationFloor;
    }
    
    @Override
    public String toString() {
        return "Passenger [startFloor=" + startFloor + ", destinationFloor=" + destinationFloor + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((destinationFloor == null) ? 0 : destinationFloor.hashCode());
        result = prime * result + ((startFloor == null) ? 0 : startFloor.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Passenger other = (Passenger) obj;
        if (destinationFloor == null) {
            if (other.destinationFloor != null)
                return false;
        } else if (!destinationFloor.equals(other.destinationFloor))
            return false;
        if (startFloor == null) {
            if (other.startFloor != null)
                return false;
        } else if (!startFloor.equals(other.startFloor))
            return false;
        return true;
    }

    public void setStartFloor(int startFloor) {
        this.startFloor = startFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }
    
    public Integer getStartFloor() {
        return startFloor;
    }

    public Integer getDestinationFloor() {
        return destinationFloor;
    }

}
