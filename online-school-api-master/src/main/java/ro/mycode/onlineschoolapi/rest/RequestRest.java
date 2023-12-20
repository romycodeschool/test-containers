package ro.mycode.onlineschoolapi.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.mycode.onlineschoolapi.dto.*;
import ro.mycode.onlineschoolapi.model.Book;
import ro.mycode.onlineschoolapi.model.Request;
import ro.mycode.onlineschoolapi.model.Student;
import ro.mycode.onlineschoolapi.services.RequestService;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;
import static ro.mycode.onlineschoolapi.constante.Utils.JWT_TOKEN_HEADER;

@RestController
@CrossOrigin
@RequestMapping("api/v1/request")
@AllArgsConstructor
public class RequestRest {

    private RequestService requestService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('course:write')")
    public ResponseEntity<List<RequestDTORequest>> requestList() {
        List<RequestDTORequest> requestList = requestService.getAllRequest().get();
        return new ResponseEntity<>(requestList, HttpStatus.OK);
    }

    @PostMapping("/addRequest")
    @PreAuthorize("hasAuthority('course:read')")
    public ResponseEntity<EnrollRequestStudentToCourse> addRequest(@RequestBody EnrollRequestStudentToCourse enrollRequestStudentToCourse) {
        this.requestService.addRequest(enrollRequestStudentToCourse);
        return new ResponseEntity<>(enrollRequestStudentToCourse, HttpStatus.OK);
    }

    @PutMapping("/denied")
    @PreAuthorize("hasAuthority('course:write')")
    public ResponseEntity<RequestDTORequest> deniedRequest(@RequestBody RequestDTORequest requestDTORequest) {
        this.requestService.deniedRequest(requestDTORequest);
        return new ResponseEntity<>(requestDTORequest, HttpStatus.OK);
    }


    @PutMapping("/accept")
    @PreAuthorize("hasAuthority('course:write')")
    public ResponseEntity<RequestDTORequest> acceptRequest(@RequestBody RequestDTORequest requestDTORequest) {
        this.requestService.acceptRequest(requestDTORequest);
        return new ResponseEntity<>(requestDTORequest, HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('course:write')")
    public ResponseEntity<EnrollRequestStudentToCourse> deleteRequest(@RequestBody EnrollRequestStudentToCourse enrollRequestStudentToCourse) {
        this.requestService.removeRequest(enrollRequestStudentToCourse);
        return new ResponseEntity<>(enrollRequestStudentToCourse, HttpStatus.OK);
    }


}
