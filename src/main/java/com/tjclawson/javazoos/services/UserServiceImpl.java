package com.tjclawson.javazoos.services;

import com.tjclawson.javazoos.exceptions.ResourceFoundException;
import com.tjclawson.javazoos.exceptions.ResourceNotFoundException;
import com.tjclawson.javazoos.models.Role;
import com.tjclawson.javazoos.models.User;
import com.tjclawson.javazoos.models.UserRoles;
import com.tjclawson.javazoos.repositories.RoleRepo;
import com.tjclawson.javazoos.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userrepos;
    @Autowired
    private RoleRepo rolerepos;

    public User findUserById(long id) throws ResourceNotFoundException {
        return userrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public List<User> findAll(Pageable pageable) {
        List<User> list = new ArrayList<>();
        userrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public void delete(long id) {
        userrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
        userrepos.deleteById(id);
    }

    @Override
    public User findByName(String name) {
        User uu = userrepos.findByUsername(name.toLowerCase());
        if (uu == null) {
            throw new ResourceNotFoundException("User name " + name + " not found!");
        }
        return uu;
    }

    @Transactional
    @Override
    public User save(User user) {
        if (userrepos.findByUsername(user.getUsername()
                .toLowerCase()) != null) {
            throw new ResourceFoundException(user.getUsername() + " is already taken!");
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername()
                .toLowerCase());
        newUser.setPasswordNotEncrypt(user.getPassword());

        ArrayList<UserRoles> newRoles = new ArrayList<>();
        for (UserRoles ur : user.getUserroles()) {
            long id = ur.getRole()
                    .getRoleid();
            Role role = rolerepos.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not found!"));
            newRoles.add(new UserRoles(newUser,
                    role));
        }
        newUser.setUserroles(newRoles);

        return userrepos.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user,
                       long id) {

        User currentUser = findUserById(id);

        if (user.getUsername() != null) {
            currentUser.setUsername(user.getUsername()
                    .toLowerCase());
        }

        if (user.getPassword() != null) {
            currentUser.setPassword(user.getPassword());
        }

        if (user.getUserroles()
                .size() > 0) {
            throw new ResourceFoundException("User Roles are not updated through User. See endpoint POST: users/user/{userid}/role/{roleid}");
        }

        return userrepos.save(currentUser);
    }

    @Transactional
    @Override
    public void deleteUserRole(long userid,
                               long roleid) {
        userrepos.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + userid + " not found!"));
        rolerepos.findById(roleid)
                .orElseThrow(() -> new ResourceNotFoundException("Role id " + roleid + " not found!"));

        if (rolerepos.checkUserRolesCombo(userid,
                roleid)
                .getCount() > 0) {
            rolerepos.deleteUserRoles(userid,
                    roleid);
        } else {
            throw new ResourceNotFoundException("Role and User Combination Does Not Exists");
        }
    }

    @Transactional
    @Override
    public void addUserRole(long userid,
                            long roleid) {
        userrepos.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + userid + " not found!"));
        rolerepos.findById(roleid)
                .orElseThrow(() -> new ResourceNotFoundException("Role id " + roleid + " not found!"));

        if (rolerepos.checkUserRolesCombo(userid,
                roleid)
                .getCount() <= 0) {
            rolerepos.insertUserRoles(userid, roleid);
        } else {
            throw new ResourceFoundException("Role and User Combination Already Exists");
        }
    }
}