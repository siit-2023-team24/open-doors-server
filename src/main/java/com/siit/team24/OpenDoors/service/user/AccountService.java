package com.siit.team24.OpenDoors.service.user;

import com.siit.team24.OpenDoors.dto.userManagement.NewPasswordDTO;
import com.siit.team24.OpenDoors.exception.CredentialsNotValidException;
import com.siit.team24.OpenDoors.exception.PasswordNotConfirmedException;
import com.siit.team24.OpenDoors.exception.PasswordValidationException;
import com.siit.team24.OpenDoors.model.Account;
import com.siit.team24.OpenDoors.repository.user.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repo;

    public void changePassword(NewPasswordDTO dto) throws AccountNotFoundException {
        if (!dto.getNewPassword().equals(dto.getRepeatPassword()))
            throw new PasswordNotConfirmedException();
        if (!dto.getNewPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,20}$"))
            throw new PasswordValidationException();

        Optional<Account> account = repo.findById(dto.getUser());
        if (account.isEmpty())
            throw new AccountNotFoundException();

        if (!account.get().getPassword().equals(dto.getOldPasswod()))
            throw new CredentialsNotValidException();

        account.get().setPassword(dto.getNewPassword());
        repo.save(account.get());
    }

}
