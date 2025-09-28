package com.example.yourapp.service;

import com.example.yourapp.model.Appointment;
import com.example.yourapp.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService; // Assuming you have a DoctorService

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorService doctorService) {
        this.appointmentRepository = appointmentRepository;
        this.doctorService = doctorService;
    }

    /**
     * Implements a booking method that saves an appointment.
     * Includes basic validation to prevent double-booking.
     */
    public Appointment bookAppointment(Appointment appointment) {
        // Check if the time slot is already taken
        Optional<Appointment> existingAppointment = appointmentRepository.findByDoctorIdAndAppointmentTime(
            appointment.getDoctor().getId(), appointment.getAppointmentTime()
        );

        if (existingAppointment.isPresent()) {
            throw new IllegalStateException("This time slot is already booked for the specified doctor.");
        }

        return appointmentRepository.save(appointment);
    }

    /**
     * Defines a method to retrieve appointments for a doctor on a specific date.
     */
    public List<Appointment> getAppointmentsForDoctorOnDate(Long doctorId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        // This method assumes the AppointmentRepository has a custom method.
        return appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctorId, startOfDay, endOfDay);
    }
}
