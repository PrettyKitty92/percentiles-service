package psm.percentile.web.service;

import psm.percentile.common.model.user.ApplicationUser;

import java.util.List;

public interface IAdminService {
    List<ApplicationUser> findAll();

    List<ApplicationUser> findAllUser();

    List<ApplicationUser> findAllAdmin();

    List<String> findAllUserNames(String term);

    List<String> findAllAdminNames(String term);

    ApplicationUser findUserDetail(String name);

    ApplicationUser findAdminDetail(String name);

    void updateUser(String name, ApplicationUser newUserDetail);

    void deleteUser(String name);

    void updateAdmin(String name, ApplicationUser newAdminDetail);

    void deleteAdmin(String name);

    void updateAdmin(ApplicationUser newUserDetail);
    
}
