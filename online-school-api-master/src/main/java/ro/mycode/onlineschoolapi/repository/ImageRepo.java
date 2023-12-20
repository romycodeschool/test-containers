package ro.mycode.onlineschoolapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.mycode.onlineschoolapi.model.Course;
import ro.mycode.onlineschoolapi.model.Image;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepo extends JpaRepository<Image,Long> {

    @Query("select i from Image i where i.student.id=?1")
    Optional<List<Image>> getAllImageByStudentId(Long id);


}
