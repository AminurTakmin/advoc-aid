package bd.edu.seu.advocaid.service;
import bd.edu.seu.advocaid.interfaces.AppointmentServiceInterface;
import bd.edu.seu.advocaid.model.Appointment;
import bd.edu.seu.advocaid.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService implements AppointmentServiceInterface {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
    @Override
    public void save(Appointment appointment) {
        appointmentRepository.save(appointment);
    }
    @Override
    public List<Appointment> getAppointmentsByUser(String userEmail) {
        return appointmentRepository.findByUserEmail(userEmail);
    }
}



//package bd.edu.seu.advocaid.service;
//
//import bd.edu.seu.advocaid.interfaces.AppointmentServiceInterface;
//import bd.edu.seu.advocaid.model.Appointment;
//import bd.edu.seu.advocaid.repository.AppointmentRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class AppointmentService implements AppointmentServiceInterface {
//
//    private final AppointmentRepository repo;
//
//    public AppointmentService(AppointmentRepository repo) {
//        this.repo = repo;
//    }
//
//    @Override
//    public Appointment save(Appointment appointment) {
//        return repo.save(appointment);
//    }
//
//    @Override
//    public Optional<Appointment> findById(String id) {
//        return repo.findById(id);
//    }
//
//    @Override
//    public void deleteById(String id) {
//        repo.deleteById(id);
//    }
//
//    @Override
//    public List<Appointment> getAppointmentsByUser(String userEmail) {
//        return repo.findByUserEmail(userEmail);
//    }
//
//    @Override
//    public boolean existsByUserEmailAndDateAndTitle(String userEmail, String date, String title) {
//        return repo.existsByUserEmailAndDateAndTitle(userEmail, date, title);
//    }
//}

