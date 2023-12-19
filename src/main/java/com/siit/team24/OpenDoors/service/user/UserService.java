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
import com.siit.team24.OpenDoors.model.enums.UserRole;
import com.siit.team24.OpenDoors.repository.user.UserRepository;
import com.siit.team24.OpenDoors.service.ImageService;
import com.siit.team24.OpenDoors.service.ReservationRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PasswordEncoder passwordEncoder;

    public User findById(Long id) {
        Optional<User> user = repo.findById(id);
        if (user.isEmpty()){
            throw new EntityNotFoundException();}
        return user.get();
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public void changePassword(NewPasswordDTO dto) throws AccountNotFoundException {
        //TODO: change validation to annotation
        if (!dto.getNewPassword().equals(dto.getRepeatPassword()))
            throw new PasswordNotConfirmedException();
        if (!dto.getNewPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,20}$"))
            throw new PasswordValidationException();

        User user = repo.findByUsername(dto.getUsername());
        if (user == null)
            throw new AccountNotFoundException();

        String encodedPassword = passwordEncoder.encode(dto.getOldPassword());
        if (!user.getPassword().equals(encodedPassword))
            throw new CredentialsNotValidException();

        user.setPassword(dto.getNewPassword());
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
            Image newImage = imageService.save(new ImageFileDTO(newData.getImageId(), newData.getFile(), true, user.getId()));
            user.setImage(newImage);
        }
        user.updateSimpleValues(newData); //updates all attributes except for image
        User updated = repo.save(user);
        return updated.toEditedDTO();
    }

    public User save(UserAccountDTO userAccountDTO) {
        if(userAccountDTO.getRole().equals("GUEST")) {
            Guest guest = new Guest();
            guest.setFavorites(new HashSet<>());
            guest.setUsername(userAccountDTO.getUsername());
            guest.setPassword(passwordEncoder.encode(userAccountDTO.getPassword()));
            guest.setLastPasswordResetDate(null);
            guest.setRole(UserRole.GUEST);
            guest.setFirstName(userAccountDTO.getFirstName());
            guest.setLastName(userAccountDTO.getLastName());
            guest.setPhone(userAccountDTO.getPhone());
            guest.setImage(new Image());
            guest.setAddress(new Address(userAccountDTO.getStreet(), userAccountDTO.getNumber(), userAccountDTO.getCity()
            , Country.fromString(userAccountDTO.getCountry())));
            guest.setEnabled(true);
            return repo.save(guest);
        }
        Host user = new Host();
        user.setUsername(userAccountDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userAccountDTO.getPassword()));
        user.setLastPasswordResetDate(null);
        user.setRole(UserRole.HOST);
        user.setFirstName(userAccountDTO.getFirstName());
        user.setLastName(userAccountDTO.getLastName());
        user.setPhone(userAccountDTO.getPhone());
        user.setImage(new Image());
        user.setAddress(new Address(userAccountDTO.getStreet(), userAccountDTO.getNumber(), userAccountDTO.getCity()
                , Country.fromString(userAccountDTO.getCountry())));
        user.setEnabled(true);
        return repo.save(user);

    }

    public void delete(Long id) {
        User user = findById(id);
        if (user.getRole() == UserRole.GUEST) {
            if (!reservationRequestService.findByUsernameAndStatus(user.getUsername(), ReservationRequestStatus.CONFIRMED).isEmpty())
                throw new ConfirmedReservationRequestsFound();
            reservationRequestService.deletePendingForGuest(user.getUsername());
        }
        else if (user.getRole() == UserRole.HOST) {

            //TODO
        }
        else return;

        if (user.getImage() != null) {
            imageService.delete(user.getImage().getId());
        }
        repo.deleteById(id);
    }
}
