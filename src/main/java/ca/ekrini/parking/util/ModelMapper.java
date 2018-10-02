package ca.ekrini.parking.util;

import ca.ekrini.parking.model.Parking;
import ca.ekrini.parking.payload.ParkingRequest;
import ca.ekrini.parking.payload.ParkingResponse;

public class ModelMapper {

    public static ParkingResponse mapParkingToParkingResponse(Parking parking) {
        ParkingResponse parkingResponse = new ParkingResponse();
        parkingResponse.setId(parking.getId());
        parkingResponse.setAddress(parking.getAddress());
        parkingResponse.setComment(parking.getComment());
        parkingResponse.setDate(parking.getDate());
        parkingResponse.setNumber(parking.getNumber());
        parkingResponse.setPrice(parking.getPrice());
        parkingResponse.setRecurrence(parking.getRecurrence());
        parkingResponse.setSize(parking.getSize());
        parkingResponse.setTimeEnd(parking.getTimeEnd());
        parkingResponse.setTimeStart(parking.getTimeStart());
        return parkingResponse;
    }

    public static Parking mapParkingRequestToParking(ParkingRequest parkingRequest) {
        Parking parking = new Parking();
        parking.setAddress(parkingRequest.getAddress());
        parking.setComment(parkingRequest.getComment());
        parking.setDate(parkingRequest.getDate());
        parking.setNumber(parkingRequest.getNumber());
        parking.setPrice(parkingRequest.getPrice());
        parking.setRecurrence(parkingRequest.getRecurrence());
        parking.setSize(parkingRequest.getSize());
        parking.setTimeEnd(parkingRequest.getTimeEnd());
        parking.setTimeStart(parkingRequest.getTimeStart());
        parking.setUser(parkingRequest.getUser());
        return parking;
    }

}
