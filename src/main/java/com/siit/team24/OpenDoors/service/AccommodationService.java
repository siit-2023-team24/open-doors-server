package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationHostDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.Host;
import com.siit.team24.OpenDoors.model.User;
import com.siit.team24.OpenDoors.repository.AccommodationRepository;
import com.siit.team24.OpenDoors.service.user.UserService;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private UserService userService;

    public Accommodation save (Accommodation accommodation) {
        System.out.println("I SAVED IT" + accommodation.toString());
        return accommodationRepository.save(accommodation);
    }

    public Collection<AccommodationHostDTO> getForHost(Long hostId) {
//        User user = userService.findById(hostId);
//        if (user.getClass() != Host.class) {
//            System.err.println("User " + hostId + " is not a host.");
//            return new ArrayList<>();
//        }
//        Host host = (Host)user;
        return accommodationRepository.findByHost(hostId);
    }

}
