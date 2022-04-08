package com.oda.component;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.model.User;
import com.oda.model.admin.Admin;
import com.oda.model.doctor.Doctor;
import com.oda.model.patient.Patient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
        if (user.getUserStatus().ordinal() == 0){
            Admin admin = new Admin();
            admin.setEmail(user.getEmail());
            AuthorizedUser.setAdmin(admin);

        }else if(user.getUserStatus().ordinal() == 1){
            Doctor doctor = new Doctor();
            doctor.setEmail(user.getEmail());
            AuthorizedUser.setDoctor(doctor);
        }else if (user.getUserStatus().ordinal() == 2){
            Patient patient = new Patient();
            patient.setEmail(user.getEmail());
            AuthorizedUser.setPatient(patient);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("Role_USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
