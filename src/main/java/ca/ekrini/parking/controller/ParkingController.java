package ca.ekrini.parking.controller;

import ca.ekrini.parking.model.Parking;
import ca.ekrini.parking.payload.PagedResponse;
import ca.ekrini.parking.payload.ParkingRequest;
import ca.ekrini.parking.payload.ParkingResponse;
import ca.ekrini.parking.repository.ParkingRepository;
import ca.ekrini.parking.repository.UserRepository;
import ca.ekrini.parking.security.CurrentUser;
import ca.ekrini.parking.security.UserPrincipal;
import ca.ekrini.parking.service.ParkingService;
import ca.ekrini.parking.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/mobile/v1/parking")
public class ParkingController {

    private static final Logger logger = LoggerFactory.getLogger(ParkingController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private ParkingRepository parkingRepository;


    @GetMapping
    public PagedResponse<ParkingResponse> getParkings(@CurrentUser UserPrincipal currentUser,
                                                   @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                   @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return parkingService.getAllParkings(currentUser, page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createParking(@Valid @RequestBody ParkingRequest parkingRequest, @CurrentUser UserPrincipal user) {
        Parking parking = parkingService.createParking(parkingRequest, user);
        return new ResponseEntity<>(parking, HttpStatus.CREATED);
    }


    @GetMapping("/{parkingId}")
    public Optional<Parking> getParkingById(@PathVariable Long parkingId) {
        return parkingRepository.findById(parkingId);
    }
}
