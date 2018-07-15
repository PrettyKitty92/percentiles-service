package psm.percentile.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import psm.percentile.common.model.user.UserAccount;
import psm.percentile.web.service.UserService;
import psm.percentile.web.service.exception.UsernameExistsException;

@RestController
@RequestMapping("/sign-up")
@CrossOrigin
public class RegistrationController {

    @Autowired
    UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegistrationController(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/user")
    public ResponseEntity<Object> signUpUser(@RequestBody UserAccount user)  {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
            userService.registerNewUserAccount(user, "USER");
        } catch (UsernameExistsException e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/admin")
    public ResponseEntity<Object> signUpAdmin(@RequestBody UserAccount user)  {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
            userService.registerNewUserAccount(user, "ADMIN");
        } catch (UsernameExistsException e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
