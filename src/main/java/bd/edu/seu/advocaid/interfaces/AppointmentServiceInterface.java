package bd.edu.seu.advocaid.interfaces;
import bd.edu.seu.advocaid.model.Appointment;
import java.util.List;

public interface AppointmentServiceInterface {
    void save(Appointment appointment);
    List<Appointment> getAppointmentsByUser(String userEmail);
}


//package bd.edu.seu.advocaid.interfaces;
//
//import bd.edu.seu.advocaid.model.Appointment;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface AppointmentServiceInterface {
//
//    Appointment save(Appointment appointment);
//
//    Optional<Appointment> findById(String id);
//
//    void deleteById(String id);
//
//    List<Appointment> getAppointmentsByUser(String userEmail);
//
//    boolean existsByUserEmailAndDateAndTitle(String userEmail, String date, String title);
//}

