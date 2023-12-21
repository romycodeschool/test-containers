package ro.mycode.institutie2.Splital.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ro.mycode.institutie2.Splital.dtos.CreateSpitalRequest;
import ro.mycode.institutie2.Splital.dtos.CreateSpitalResponse;
import ro.mycode.institutie2.Splital.dtos.UpdateSpitalRequest;
import ro.mycode.institutie2.Splital.exceptions.NoUpdate;
import ro.mycode.institutie2.Splital.exceptions.SpitalDoesntExist;
import ro.mycode.institutie2.Splital.exceptions.SpitalExist;
import ro.mycode.institutie2.Splital.exceptions.SpitalListEmpty;
import ro.mycode.institutie2.Splital.model.Spital;
import ro.mycode.institutie2.Splital.repository.SpitalRepo;

import java.util.List;
import java.util.Optional;

@Service
public class SpitalService {

    private SpitalRepo spitalRepo;

    public SpitalService(SpitalRepo spitalRepo) {
        this.spitalRepo = spitalRepo;
    }

    public List<Spital> getAllSpitale() {
        List<Spital> all = spitalRepo.findAll();
        if (all.size() == 0) {
            throw new SpitalListEmpty();
        }
        return all;
    }


    @Transactional
    public CreateSpitalResponse addSpital(CreateSpitalRequest createSpitalRequest) {

        Optional<Spital> spitalByDenmire = spitalRepo.findSpitalByDenumire(createSpitalRequest.getDenumire());
        if (spitalByDenmire.isPresent()) {

            throw new SpitalExist();
        }
        Spital spital = Spital.builder()
                .denumire(createSpitalRequest.getDenumire())
                .adresa(createSpitalRequest.getAdresa())
                .specializare(createSpitalRequest.getSpecializare())
                .build();

        Spital spital1 = spitalRepo.saveAndFlush(spital);

        return CreateSpitalResponse.builder().spital(spital1).build();
    }


    @Transactional
    public void deleteSpital(long id) {
        Optional<Spital> spital = spitalRepo.findById(id);
        if (spital.isPresent()) {
            spitalRepo.delete(spital.get());
        } else {
            throw new SpitalDoesntExist();
        }
    }



    @Transactional
    public void updateSpital(UpdateSpitalRequest updateSpitalRequest) {
        Optional<Spital>spitalOptional=spitalRepo.findSpitalByDenumire(updateSpitalRequest.getDenumire());
        if(spitalOptional.isPresent()){

           Spital splital = spitalOptional.get();


            if(!updateSpitalRequest.getAdresa().equals("")){
                splital.setAdresa(updateSpitalRequest.getAdresa());
            }


            spitalRepo.saveAndFlush(splital);

        }else{
            throw new NoUpdate();
        }
    }


}
