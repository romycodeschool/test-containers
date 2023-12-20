package ro.mycode.onlineschoolapi.services;

import org.springframework.stereotype.Service;
import ro.mycode.onlineschoolapi.dto.LoginResponse;
import ro.mycode.onlineschoolapi.dto.StudentDTO;
import ro.mycode.onlineschoolapi.exception.ImageException;
import ro.mycode.onlineschoolapi.model.Image;
import ro.mycode.onlineschoolapi.repository.ImageRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    private ImageRepo imageRepo;

    public ImageService(ImageRepo imageRepo) {
        this.imageRepo = imageRepo;
    }

//    @Transactional
//    public List<Image> getAllListByStudentId(LoginResponse loginResponse) {
//        Optional<List<Image>> images = imageRepo.getAllImageByStudentId(loginResponse.studentId());
//        if (!images.isEmpty()) {
//            return images.get();
//        } else {
//            throw new ImageException("Studentul  nu are imagini !");
//        }
//    }
}
