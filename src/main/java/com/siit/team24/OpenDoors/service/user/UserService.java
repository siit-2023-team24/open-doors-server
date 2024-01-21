package com.siit.team24.OpenDoors.service.user;

import com.siit.team24.OpenDoors.dto.userManagement.*;
import com.siit.team24.OpenDoors.dto.image.ImageFileDTO;
import com.siit.team24.OpenDoors.exception.ConfirmedReservationRequestsFound;
import com.siit.team24.OpenDoors.exception.CredentialsNotValidException;
import com.siit.team24.OpenDoors.exception.PasswordNotConfirmedException;
import com.siit.team24.OpenDoors.exception.PasswordValidationException;
import com.siit.team24.OpenDoors.model.*;
import com.siit.team24.OpenDoors.model.enums.*;
import com.siit.team24.OpenDoors.repository.user.UserRepository;
import com.siit.team24.OpenDoors.service.AccommodationService;
import com.siit.team24.OpenDoors.service.HostReviewService;
import com.siit.team24.OpenDoors.service.ImageService;
import com.siit.team24.OpenDoors.service.ReservationRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ReservationRequestService reservationRequestService;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private HostReviewService hostReviewService;

    public User findById(Long id) {
        Optional<User> user = repo.findById(id);
        if (user.isEmpty()){
            throw new EntityNotFoundException();}
        return user.get();
    }

    public UserService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public void changePassword(NewPasswordDTO dto) {

        if (!dto.getNewPassword().equals(dto.getRepeatPassword()))
            throw new PasswordNotConfirmedException();
//        if (!dto.getNewPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,20}$"))
        if (dto.getNewPassword().length() < 5)
            throw new PasswordValidationException();

        User user = repo.findByUsername(dto.getUsername());
        if (user == null)
            throw new EntityNotFoundException();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getOldPassword()));
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new CredentialsNotValidException();
        }

        String encodedPassword = passwordEncoder.encode(dto.getNewPassword());
        user.setPassword(encodedPassword);
        repo.save(user);
    }

    public UserEditedDTO update(UserEditedDTO newData) throws IOException {
        User user = this.findById(newData.getId());

        Image oldImage = user.getImage();
        if (oldImage != null && (newData.getImageId() == null || newData.getFile() != null)) { //the user wants to delete or replace their image
            user.setImage(null);
            imageService.delete(oldImage.getId());
        }
        if (newData.getFile() != null) {    //uploaded new image
            Image newImage = imageService.save(new ImageFileDTO(newData.getImageId(), newData.getFile(), ImageType.PROFILE, user.getId()));
            user.setImage(newImage);
        }
        user.updateSimpleValues(newData); //updates all attributes except for image
        User updated = repo.save(user);
        return updated.toEditedDTO();
    }

    public User create(UserAccountDTO userAccountDTO) {
        if(userAccountDTO.getRole().equals("ROLE_GUEST")) {
            Guest guest = new Guest();
            guest.setFavorites(new HashSet<>());
            guest.setUsername(userAccountDTO.getUsername());
            guest.setPassword(passwordEncoder.encode(userAccountDTO.getPassword()));
            guest.setLastPasswordResetDate(null);
            guest.setRole(UserRole.ROLE_GUEST);
            guest.setFirstName(userAccountDTO.getFirstName());
            guest.setLastName(userAccountDTO.getLastName());
            guest.setPhone(userAccountDTO.getPhone());
            guest.setImage(null);
            guest.setAddress(new Address(userAccountDTO.getStreet(), userAccountDTO.getNumber(), userAccountDTO.getCity()
            , Country.fromString(userAccountDTO.getCountry())));
            guest.setEnabled(false);
            return repo.save(guest);
        }
        if(userAccountDTO.getRole().equals("ROLE_HOST")){
            Host host = new Host();
            host.setUsername(userAccountDTO.getUsername());
            host.setPassword(passwordEncoder.encode(userAccountDTO.getPassword()));
            host.setLastPasswordResetDate(null);
            host.setRole(UserRole.ROLE_HOST);
            host.setFirstName(userAccountDTO.getFirstName());
            host.setLastName(userAccountDTO.getLastName());
            host.setPhone(userAccountDTO.getPhone());
            host.setImage(null);
            host.setAddress(new Address(userAccountDTO.getStreet(), userAccountDTO.getNumber(), userAccountDTO.getCity()
                    , Country.fromString(userAccountDTO.getCountry())));
            host.setEnabled(false);
            return repo.save(host);
        }
        Admin admin = new Admin();
        admin.setUsername(userAccountDTO.getUsername());
        admin.setPassword(passwordEncoder.encode(userAccountDTO.getPassword()));
        admin.setLastPasswordResetDate(null);
        admin.setRole(UserRole.ROLE_ADMIN);
        admin.setFirstName(userAccountDTO.getFirstName());
        admin.setLastName(userAccountDTO.getLastName());
        admin.setPhone(userAccountDTO.getPhone());
        admin.setImage(null);
        admin.setAddress(new Address(userAccountDTO.getStreet(), userAccountDTO.getNumber(), userAccountDTO.getCity()
                , Country.fromString(userAccountDTO.getCountry())));
        admin.setEnabled(false);
        return repo.save(admin);
    }

    public void sendActivationEmail(String recipient, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("opendoorsteam24@gmail.com");
        message.setTo(recipient);
        message.setSubject("Activation mail");
        message.setText("Please verify your account here. Otherwise it will expire in the next 24 hours.\n" + link);
        javaMailSender.send(message);
    }


    public void delete(Long id) {
        User user = findById(id);

        if (user.getRole() == UserRole.ROLE_GUEST) {
            if (!reservationRequestService.findByUsernameAndStatus(user.getUsername(), ReservationRequestStatus.CONFIRMED).isEmpty())
                throw new ConfirmedReservationRequestsFound();
            reservationRequestService.deletePendingForGuest(user.getUsername());
        }

        else if (user.getRole() == UserRole.ROLE_HOST) {
            List<Accommodation> accommodations = accommodationService.findAllByHostId(user.getId());
            for (Accommodation accommodation: accommodations) {
                if (!reservationRequestService.isAccommodationReadyForDelete(accommodation.getId()))
                    throw new ConfirmedReservationRequestsFound();
            }
            for (Accommodation accommodation: accommodations) {
                removeFromAnyFavorites(accommodation);
                accommodationService.delete(accommodation.getId());
            }
            hostReviewService.deleteAllForHost(id);
        }
        else return;

        if (user.getImage() != null)
            imageService.delete(user.getImage().getId());

        repo.deleteById(id);
    }

    public void removeFromAnyFavorites(Accommodation accommodation) {
        List<User> users = repo.findAll();
        for (User user: users) {
            if (!user.getRole().equals(UserRole.ROLE_GUEST))
                continue;
            Guest guest = (Guest) user;
            if (guest.getFavorites().contains(accommodation)) {
                guest.removeFavoriteAccommodation(accommodation);
                repo.save(guest);
            }
        }
    }

    public void activateUser(Long id){
        Optional<User> user = repo.findById(id);
        if (user.isEmpty()) return;
        user.get().setEnabled(true);
        repo.save(user.get());
    }

    public void save(User user) {
        repo.save(user);
    }

    public HostPublicDataDTO getPublicData(Long hostId) {
        Host host = (Host) repo.findById(hostId).get();
        HostPublicDataDTO dto = new HostPublicDataDTO(host);
        return dto;
    }

    public List<String> getUsernames(List<Long> ids) { return this.repo.findUsernamesByIds(ids); }

    public List<UserSummaryDTO> getBlockedDTOs() {
        return repo.getBlockedDTOs();
    }

    public void block(Long id, UserRole role) {
        User user = findById(id);
        if (role.equals(UserRole.ROLE_GUEST))
            handlePendingRequests(user.getUsername());
        else if (role.equals(UserRole.ROLE_HOST))
            disableHostsAccommodations(id);
        else return;
        user.setBlocked(true);
        repo.save(user);
    }

    private void disableHostsAccommodations(Long id) {
        List<Accommodation> accommodations = accommodationService.findAllByHostId(id);
        for (Accommodation accommodation: accommodations) {
            reservationRequestService.denyActiveForAccommodation(accommodation.getId());
            removeFromAnyFavorites(accommodation);
            accommodationService.softDelete(accommodation.getId());
        }

    }

    private void handlePendingRequests(String username) {
        reservationRequestService.deletePendingForGuest(username);
        reservationRequestService.cancelFutureForGuest(username);
    }

    public void unblock(Long id) {
        User user = findById(id);
        if (user.getRole() == UserRole.ROLE_HOST)
            accommodationService.reviveByHostId(id);
        user.setBlocked(false);
        repo.save(user);
    }

    public List<NotificationType> getDisabledNotificationTypesFor(Long id) {
        User user = findById(id);
        return user.getDisabledTypes();
    }

    public void setDisabledNotificationTypesFor(Long id, List<NotificationType> types) {
        User user = findById(id);
        user.setDisabledTypes(types);
        repo.save(user);
    }


}
