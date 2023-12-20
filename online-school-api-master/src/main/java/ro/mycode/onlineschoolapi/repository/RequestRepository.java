package ro.mycode.onlineschoolapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.mycode.onlineschoolapi.model.Request;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {

    @Query("select r from Request r where r.courseId=?1 and r.studentId=?2")
    Optional<Request> getRequestByCourseIdAndStudentId(Long courseId,Long studentId);
    @Transactional
    @Modifying
    @Query("delete from Request r where r.courseId=?1  and r.studentId=?2")
    void removeByCourseIdAndStudentId(Long courseId,Long studentId);
}
