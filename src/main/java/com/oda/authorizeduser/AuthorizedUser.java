package com.oda.authorizeduser;

import com.oda.enums.UserStatus;
import com.oda.model.admin.Admin;
import com.oda.model.doctor.Doctor;
import com.oda.model.patient.Patient;

public class AuthorizedUser {
    private static Doctor doctor;
    private static Admin admin;
    private static Patient patient;
    private static UserStatus userStatus;

    public static Doctor getDoctor() {
        return doctor;
    }

    public static void setDoctor(Doctor doctor) {
        AuthorizedUser.doctor = doctor;
        AuthorizedUser.userStatus =UserStatus.DOCTOR;
    }

    public static Admin getAdmin() {
        return admin;
    }

    public static void setAdmin(Admin admin) {
        AuthorizedUser.admin = admin;
        AuthorizedUser.userStatus =UserStatus.ADMIN;

    }

    public static Patient getPatient() {
        return patient;
    }

    public static void setPatient(Patient patient) {
        AuthorizedUser.patient = patient;
        AuthorizedUser.userStatus = UserStatus.PATIENT;
    }

    public static UserStatus getUserStatus() {
        return userStatus;
    }

    public static void setUserStatus(UserStatus userStatus) {
        AuthorizedUser.userStatus = userStatus;
    }
}
