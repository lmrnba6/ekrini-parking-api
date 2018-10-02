package ca.ekrini.parking.service;

import ca.ekrini.parking.exception.BadRequestException;
import ca.ekrini.parking.model.Address;
import ca.ekrini.parking.model.Parking;
import ca.ekrini.parking.model.Position;
import ca.ekrini.parking.model.User;
import ca.ekrini.parking.payload.PagedResponse;
import ca.ekrini.parking.payload.ParkingRequest;
import ca.ekrini.parking.payload.ParkingResponse;
import ca.ekrini.parking.repository.AddressRepository;
import ca.ekrini.parking.repository.ParkingRepository;
import ca.ekrini.parking.repository.PositionRepository;
import ca.ekrini.parking.repository.UserRepository;
import ca.ekrini.parking.security.UserPrincipal;
import ca.ekrini.parking.util.AppConstants;
import ca.ekrini.parking.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingService {

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ParkingService.class);

    public PagedResponse<ParkingResponse> getAllParkings(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<Parking> parkings = parkingRepository.findAll(pageable);

        if(parkings.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), parkings.getNumber(),
                    parkings.getSize(), parkings.getTotalElements(), parkings.getTotalPages(), parkings.isLast());
        }
        List<ParkingResponse> parkingsResponse = parkings.map(parking ->
                ModelMapper.mapParkingToParkingResponse(parking)).getContent();

        return new PagedResponse<>(parkingsResponse, parkings.getNumber(),
                parkings.getSize(), parkings.getTotalElements(), parkings.getTotalPages(), parkings.isLast());
    }

    @Transactional
    public Parking createParking(ParkingRequest parkingRequest, UserPrincipal userPrincipal) {

            Position posiition = new Position(parkingRequest.getAddress().getPosition().getLatitude(),
                    parkingRequest.getAddress().getPosition().getLongitude());
            Position positionSaved = positionRepository.save(posiition);
            Address address = new Address();
            address.setAddressOneLine(parkingRequest.getAddress().getAddressOneLine());
            address.setCity(parkingRequest.getAddress().getCity());
            address.setState(parkingRequest.getAddress().getState());
            address.setZip(parkingRequest.getAddress().getZip());
            address.setPosition(positionSaved);
            Address addressSaved = addressRepository.save(address);
            parkingRequest.setAddress(addressSaved);
            Optional opt = userRepository.findById(userPrincipal.getId());
            opt.ifPresent(user -> parkingRequest.setUser((User) user));
            Parking parking = ModelMapper.mapParkingRequestToParking(parkingRequest);
            return parkingRepository.save(parking);

    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
