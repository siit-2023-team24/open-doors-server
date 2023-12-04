package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.repository.user.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repo;

}
