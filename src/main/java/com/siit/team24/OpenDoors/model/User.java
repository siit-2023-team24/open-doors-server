package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.dto.userManagement.UserAccountViewDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserEditedDTO;
import jakarta.persistence.*;
import org.springframework.lang.Nullable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    @OneToOne
    @Nullable
    private Image image;
    @Embedded
    private Address address;
    @OneToOne(cascade = {CascadeType.ALL})
    private Account account;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public User(){

    }

    public User(Long id, String firstName, String lastName, String phone, Image image, Address address, Account account) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.image = image;
        this.address = address;
        this.account = account;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", image='" + image + '\'' +
                ", address=" + address +
                ", account=" + account +
                '}';
    }

    public UserEditedDTO toEditedDTO() {
        Long imgId = (image != null)? image.getId() : null;
        return new UserEditedDTO(id, firstName, lastName, phone, address.getStreet(), address.getNumber(), address.getCity(),
                address.getCountry().toString(), imgId, null);
    }

    public UserAccountViewDTO toAccountViewDTO() {
        Long imgId = (image != null)? image.getId() : null;
        return new UserAccountViewDTO(id, firstName, lastName, phone, address.getStreet(), address.getNumber(),
                address.getCity(), address.getCountry().getCountryName(), imgId, account.getEmail(),
                account.getRole().toString());
    }

    public void updateSimpleValues(UserEditedDTO dto) {
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.phone = dto.getPhone();
        this.address.update(dto.getCountry(), dto.getCity(), dto.getStreet(), dto.getNumber());
    }
}
