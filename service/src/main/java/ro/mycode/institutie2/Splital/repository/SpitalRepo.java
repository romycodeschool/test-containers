package ro.mycode.institutie2.Splital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.mycode.institutie2.Splital.model.Spital;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpitalRepo extends JpaRepository<Spital,Long> {
    @Query("select distinct s.denumire from Spital s ")
    List<String> getAllSpitale();

    @Query("select s from Spital s where s.denumire= ?1 and s.adresa= ?2")
    Optional<Spital>findSpitalByDenumireAndAdresa(String denumire, String adresa);
    @Query("select s from Spital s where s.denumire= ?1 ")
    Optional<Spital>findSpitalByDenumire(String denumire);

    @Modifying
    @Query("update Spital s set s.specializare= ?3 where s.denumire= ?1 and s.adresa= ?2")
    void updateSpital(String denumire, String adresa);






}
