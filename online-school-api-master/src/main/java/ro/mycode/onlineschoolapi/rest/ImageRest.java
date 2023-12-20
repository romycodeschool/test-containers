package ro.mycode.onlineschoolapi.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.mycode.onlineschoolapi.dto.LoginResponse;
import ro.mycode.onlineschoolapi.model.Image;
import ro.mycode.onlineschoolapi.repository.ImageRepo;
import ro.mycode.onlineschoolapi.services.ImageService;

import javax.persistence.GeneratedValue;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/images")
public class ImageRest {
//    private ImageService imageService;
//    @Autowired
//    private ImageRepo imageRepository;
//
//
//    @PostMapping("/upload")
//    public ResponseEntity<Long> uploadImage(@RequestParam("file") MultipartFile file) {
//        try {
//            Image image = new Image();
//            image.setName(file.getOriginalFilename());
//            image.setData(file.getBytes());
//
//            Image savedImage = imageRepository.save(image);
//
//            return ResponseEntity.ok(savedImage.getId());
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<byte[]> downloadImage(@PathVariable Long id) {
//        Optional<Image> optionalImage = imageRepository.findById(id);
//        if (optionalImage.isPresent()) {
//            Image image = optionalImage.get();
//
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
//                    .body(image.getData());
//        } else {
//            return ResponseEntity.notFound().build();
//
//        }
//    }
//
//    @GetMapping()
//    public ResponseEntity<List<Image>> getImages(){
//        List<Image> images = this.imageRepository.findAll();
//        return new ResponseEntity<List<Image>>(images, HttpStatus.OK);
//    }


//    @GetMapping("/studentId")
//    public ResponseEntity<List<Image>> getImageByStudent(@RequestBody LoginResponse loginResponse)
//    {
//        List<Image> images = imageService.getAllListByStudentId(loginResponse);
//        return new ResponseEntity<List<Image>>(images, HttpStatus.OK);
//    }

}
