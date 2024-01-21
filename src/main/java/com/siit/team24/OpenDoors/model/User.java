package com.siit.team24.OpenDoors.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.siit.team24.OpenDoors.dto.userManagement.UserAccountViewDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserEditedDTO;
import com.siit.team24.OpenDoors.model.enums.NotificationType;
import com.siit.team24.OpenDoors.model.enums.UserRole;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SQLDelete(sql = "UPDATE users SET deleted=true WHERE id=?")
//@Where(clause = "deleted=false")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @JsonIgnore
    private String password;
    private Timestamp lastPasswordResetDate;
    @Enumerated
    private UserRole role;
    private String firstName;
    private String lastName;
    private String phone;
    @OneToOne(cascade = {CascadeType.ALL})
    @Nullable
    private Image image;
    @Embedded
    private Address address;
    private boolean enabled;
    private boolean blocked;
    private boolean deleted;
    private List<NotificationType> disabledTypes;


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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(this.role.name()));
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<NotificationType> getDisabledTypes() {
        return disabledTypes;
    }

    public void setDisabledTypes(List<NotificationType> disabledTypes) {
        this.disabledTypes = disabledTypes;
    }

    public User(){

    }

    public User(Long id, String email, String password, Timestamp lastPasswordResetDate, UserRole role, String firstName, String lastName, String phone, @Nullable Image image, Address address, boolean enabled, List<NotificationType> disabledTypes) {
        this.id = id;
        this.username = email;
        this.password = password;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.image = image;
        this.address = address;
        this.enabled = enabled;
        this.disabledTypes = disabledTypes;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", lastPasswordResetDate=" + lastPasswordResetDate +
                ", role=" + role +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", image=" + image +
                ", address=" + address +
                ", enabled=" + enabled +
                ", blocked=" + blocked +
                ", deleted=" + deleted +
                ", disabledTypes=" + disabledTypes +
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
                address.getCity(), address.getCountry().getCountryName(), imgId, this.getUsername(),
                this.getRole().toString());
    }

    public void updateSimpleValues(UserEditedDTO dto) {
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.phone = dto.getPhone();
        this.address.update(dto.getCountry(), dto.getCity(), dto.getStreet(), dto.getNumber());
    }
}
