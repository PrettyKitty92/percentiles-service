package psm.percentile.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import psm.percentile.common.model.user.ApplicationUser;
import psm.percentile.common.model.user.Authority;
import psm.percentile.web.repository.AdminRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService implements IAdminService {


    @Autowired
    AdminRepository adminRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<ApplicationUser> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public List<ApplicationUser> findAllUser() {
        return adminRepository.findAllByAuthoritiesContaining(Authority.USER.toString());
    }

    @Override
    public List<ApplicationUser> findAllAdmin() {
        return adminRepository.findAllByAuthoritiesContaining(Authority.ADMIN.toString());
    }

    @Override
    public List<String> findAllUserNames(String term) {
        if (StringUtils.isBlank(term)) {
            return Collections.emptyList();
        } else {
            return searchNamesFor(Authority.USER, StringUtils.deleteWhitespace(term));
        }
    }

    @Override
    public List<String> findAllAdminNames(String term) {
        if (StringUtils.isBlank(term)) {
            return Collections.emptyList();
        } else {
            return searchNamesFor(Authority.ADMIN, StringUtils.deleteWhitespace(term));
        }
    }

    @Override
    public ApplicationUser findUserDetail(String name) {
        return adminRepository.findByUsernameAndAuthoritiesContaining(name, Authority.USER.toString());
    }

    @Override
    public ApplicationUser findAdminDetail(String name) {
        return adminRepository.findByUsernameAndAuthoritiesContaining(name, Authority.ADMIN.toString());
    }

    @Override
    public void updateUser(String name, ApplicationUser newUserDetail) {
        ApplicationUser user = findUserDetail(name);
        updateUser(user, newUserDetail);
    }

    @Override
    public void deleteUser(String name) {
        adminRepository.deleteByUsernameAndAuthoritiesContaining(name, Authority.USER.toString());
    }

    @Override
    public void updateAdmin(String name, ApplicationUser newAdminDetail) {

        ApplicationUser oldAdminDetail = findUserDetail(name);

        ApplicationUser editedUser = findAdminDetail(oldAdminDetail.getUsername());
        if(!oldAdminDetail.getUsername().equals(newAdminDetail.getUsername())){
            editedUser.setPassword(newAdminDetail.getUsername());
        }
       if(!oldAdminDetail.getFirstName().equals(newAdminDetail.getFirstName())){
            editedUser.setFirstName(newAdminDetail.getFirstName());
        }
       if(!oldAdminDetail.getLastName().equals(newAdminDetail.getLastName())){
            editedUser.setLastName(newAdminDetail.getLastName());
        }
       if(!oldAdminDetail.getEmail().equals(newAdminDetail.getEmail())){
            editedUser.setEmail(newAdminDetail.getEmail());
        }
        if (!oldAdminDetail.getPassword().equals(bCryptPasswordEncoder.encode(newAdminDetail.getPassword()))) {
            editedUser.setPassword(newAdminDetail.getPassword());
        }
        adminRepository.save(editedUser);
    }

    @Override
    public void deleteAdmin(String name) {
        adminRepository.deleteByUsernameAndAuthoritiesContaining(name, Authority.ADMIN.toString());
    }

    @Override
    public void updateAdmin(ApplicationUser newAdminDetail) {
        ApplicationUser editedUser = adminRepository.findById(newAdminDetail.getId());
        if(!editedUser.getUsername().equals(newAdminDetail.getUsername())){
            editedUser.setPassword(newAdminDetail.getUsername());
        }
        if(!editedUser.getFirstName().equals(newAdminDetail.getFirstName())){
            editedUser.setFirstName(newAdminDetail.getFirstName());
        }
        if(!editedUser.getLastName().equals(newAdminDetail.getLastName())){
            editedUser.setLastName(newAdminDetail.getLastName());
        }
        if(!editedUser.getEmail().equals(newAdminDetail.getEmail())){
            editedUser.setEmail(newAdminDetail.getEmail());
        }
        if (!editedUser.getPassword().equals(bCryptPasswordEncoder.encode(newAdminDetail.getPassword()))) {
            editedUser.setPassword(newAdminDetail.getPassword());
        }
        adminRepository.save(editedUser);
    }

    private void updateUser(ApplicationUser user, ApplicationUser newUserDetail) {
        user.setFirstName(newUserDetail.getFirstName());
        user.setLastName(newUserDetail.getLastName());
        user.setEmail(newUserDetail.getEmail());
        user.setUsername(newUserDetail.getUsername());
        user.setPassword(newUserDetail.getPassword());
        user.setBabies(newUserDetail.getBabies());
        adminRepository.save(user);
    }

    List<String> searchNamesFor(Authority authority, String term) {
        return adminRepository.findAllByAuthoritiesContaining(authority.toString())
                .stream()
                .filter(user -> user
                        .getUsername()
                        .toLowerCase()
                        .contains(term.toLowerCase()))
                .map(user -> user.getUsername())
                .collect(Collectors.toList());
    }

}
