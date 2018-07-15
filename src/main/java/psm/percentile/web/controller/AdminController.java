package psm.percentile.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import psm.percentile.common.model.user.ApplicationUser;
import psm.percentile.web.Application;
import psm.percentile.web.service.AdminService;
import psm.percentile.web.service.IAdminService;

import java.security.Principal;
import java.util.List;

/**
 * Created by Patrycja on 01.09.2017.
 */

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    private IAdminService adminService;

/*    @Autowired
    private IAuthenticationFacade authenticationFacade;*/


    public AdminController(AdminService adminService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminService = adminService;
    }

    @GetMapping("/details")
    ResponseEntity adminDatailes(Principal user) {

        ApplicationUser loggedUser = new ApplicationUser();
        try {
            loggedUser = adminService.findAdminDetail(user.getName());
        } catch (Throwable ex) {
            //to od exceptions UserDoesNotExist
            return new ResponseEntity("message :" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(loggedUser, HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity all(Principal user) {

        List<ApplicationUser> all;

        try {
            all = adminService.findAll();
        } catch (Throwable ex) {
            //to od exceptions UserDoesNotExist
            return new ResponseEntity("message :" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(all, HttpStatus.OK);
    }



    /*USERS*/

    @GetMapping("/users")
    ResponseEntity users(Principal user) {

        List<ApplicationUser> allUsers;
        try {
            allUsers = adminService.findAllUser();
        } catch (Throwable ex) {
            //to od exceptions UserDoesNotExist
            return new ResponseEntity("message :" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(allUsers, HttpStatus.OK);
    }

    @GetMapping("/users/names")
    ResponseEntity usersNameList(Principal user, @RequestParam String search) {

        List<String> allUsers;
        try {
            allUsers = adminService.findAllUserNames(search);
        } catch (Throwable ex) {
            //to od exceptions UserDoesNotExist
            return new ResponseEntity("message :" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(allUsers, HttpStatus.OK);
    }

    @GetMapping("/users/user")
    ResponseEntity userByName(Principal user, @RequestParam String name) {
        ApplicationUser userDetail;
        try {
            userDetail = adminService.findUserDetail(name);
        } catch (Throwable ex) {
            //to od exceptions UserDoesNotExist
            return new ResponseEntity("message :" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(userDetail, HttpStatus.OK);
    }

    @PutMapping("/users/user/edit")
    ResponseEntity editUserByName(Principal user,
                                  @RequestParam String name,
                                  @RequestBody ApplicationUser newUserDetail) {
        try {
            adminService.updateUser(name, newUserDetail);
        } catch (Throwable ex) {
           return new ResponseEntity( HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/users/user/delete")
    ResponseEntity deleteUserByName(Principal user,
                                  @RequestParam String name) {
        try {
            adminService.deleteUser(name);
        } catch (Throwable ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    /*ADMIN*/

    @GetMapping("/admins")
    ResponseEntity admins(Principal user) {
        List<ApplicationUser> allUsers;
        try {
            allUsers = adminService.findAllAdmin();
        } catch (Throwable ex) {
            //to od exceptions UserDoesNotExist
            return new ResponseEntity("message :" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(allUsers, HttpStatus.OK);
    }

    @GetMapping("/admins/names")
    ResponseEntity adminsNameList(Principal user, @RequestParam String search) {
        List<String> allUsers;
        try {
            allUsers = adminService.findAllAdminNames(search);
        } catch (Throwable ex) {
            //to od exceptions UserDoesNotExist
            return new ResponseEntity("message :" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(allUsers, HttpStatus.OK);
    }

    @GetMapping("/admins/admin")
    ResponseEntity adminByName(Principal user, @RequestParam String name) {
        ApplicationUser adminDetail;
        try {
            adminDetail = adminService.findAdminDetail(name);
        } catch (Throwable ex) {
            //to od exceptions UserDoesNotExist
            return new ResponseEntity("message :" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(adminDetail, HttpStatus.OK);
    }

    @PostMapping("/admins/admin/edit")
    ResponseEntity editAdminByName(Principal user,
                                  @RequestParam String name,
                                  @RequestBody ApplicationUser newUserDetail) {
        try {
            adminService.updateAdmin(name, newUserDetail);
        } catch (Throwable ex) {
            return new ResponseEntity( HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/admins/admin/edit2")
    ResponseEntity editAdmin(Principal user,
                                   @RequestBody ApplicationUser newUserDetail) {
        try {
            adminService.updateAdmin(newUserDetail);
        } catch (Throwable ex) {
            return new ResponseEntity( HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/admins/admin/delete")
    ResponseEntity deleteAdminByName(Principal user,
                                    @RequestParam String name) {
        try {
            adminService.deleteAdmin(name);
        } catch (Throwable ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}



