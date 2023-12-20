package ro.mycode.onlineschoolapi.repository;


import ro.mycode.onlineschoolapi.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course,Long> {
    @Query("select c from Course c order by c.department asc")
    Optional<List<Course>> getCoursesOrderByDepartmentAsc();

    @Query("select c from Course c where c.department=?1 and c.name=?2")
    Optional<Course> getCourseByDepartamentAndName(String departament,String name);







}
