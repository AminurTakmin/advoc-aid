package bd.edu.seu.advocaid.repository;
import bd.edu.seu.advocaid.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    List<Appointment> findByUserEmail(String userEmail);
}


//package bd.edu.seu.advocaid.repository;
//
//import bd.edu.seu.advocaid.model.Appointment;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface AppointmentRepository extends MongoRepository<Appointment, String> {
//
//    /** Load a user’s appointments */
//    List<Appointment> findByUserEmail(String userEmail);
//
//    /** Block duplicates */
//    boolean existsByUserEmailAndDateAndTitle(String userEmail, String date, String title);
//}


