package com.example.yourapp.service;

import com.example.yourapp.model.Doctor;
import com.example.yourapp.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * Method returns available time slots for a doctor on a given date.
     * This is a simplified version; a more complex one would consider booked appointments.
     */
    public List<String> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        return doctorOptional.map(Doctor::getAvailableTimes).orElse(List.of());
    }

    /**
     * Method validates doctor login credentials and returns a structured response.
     */
    public Optional<Doctor> validateLoginCredentials(String email, String password) {
        Optional<Doctor> doctorOptional = doctorRepository.findByEmail(email);

        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            // In a real application, you must use a password encoder (e.g., BCryptPasswordEncoder)
            // to compare the hashed password, not plain text.
            if (doctor.getPassword().equals(password)) {
                return Optional.of(doctor);
            }
        }
        return Optional.empty();
    }
}
