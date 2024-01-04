package com.siit.team24.OpenDoors.service.user;

import com.siit.team24.OpenDoors.dto.image.ImageFileDTO;
import com.siit.team24.OpenDoors.dto.userManagement.NewPasswordDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserAccountDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserEditedDTO;
import com.siit.team24.OpenDoors.exception.ConfirmedReservationRequestsFound;
import com.siit.team24.OpenDoors.exception.CredentialsNotValidException;
import com.siit.team24.OpenDoors.exception.PasswordNotConfirmedException;
import com.siit.team24.OpenDoors.exception.PasswordValidationException;
import com.siit.team24.OpenDoors.model.*;
import com.siit.team24.OpenDoors.model.enums.Country;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import com.siit.team24.OpenDoors.model.enums.ImageType;
import com.siit.team24.OpenDoors.model.enums.UserRole;
import com.siit.team24.OpenDoors.repository.user.UserRepository;
import com.siit.team24.OpenDoors.service.AccommodationService;
import com.siit.team24.OpenDoors.service.ImageService;
import com.siit.team24.OpenDoors.service.PendingAccommodationService;
import com.siit.team24.OpenDoors.service.ReservationRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
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

        System.err.println(dto);
        //TODO: change validation to annotation
        if (!dto.getNewPassword().equals(dto.getRepeatPassword()))
            throw new PasswordNotConfirmedException();
//        if (!dto.getNewPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,20}$"))
        if (dto.getNewPassword().length() < 3)
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
                if (reservationRequestService.countConfirmedFutureFor(accommodation.getId()) > 0)
                    throw new ConfirmedReservationRequestsFound();
            }
            for (Accommodation accommodation: accommodations)
                accommodationService.delete(accommodation.getId());
        }

        else return;

        if (user.getImage() != null) {
            imageService.delete(user.getImage().getId());
        }
        repo.deleteById(id);
    }

    public void activateUser(Long id){
        Optional<User> user = repo.findById(id);
        if (user.isEmpty()) return;
        user.get().setEnabled(true);
        repo.save(user.get());
    }


}
