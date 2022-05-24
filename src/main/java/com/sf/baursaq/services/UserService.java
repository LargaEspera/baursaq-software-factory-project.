package com.sf.baursaq.services;

import com.sf.baursaq.entity.User;
import com.sf.baursaq.helpers.ValidateHelper;
import com.sf.baursaq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean createUser(User user){
        try{
            if(!ValidateHelper.validate(user.getUsername())) return false;
            if(userRepository.findByUsername(user.getUsername()) != null) return false;
            if(!user.getPassword().equals(user.getPassword2())) return false;
            user.setPassword2(null);
            userRepository.save(user);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean login(User user){
        try{
            User userRequest = userRepository.findByUsername(user.getUsername());
            if (user.getPassword().equals(userRequest.getPassword())) return true;
            return false;
        }catch (Exception e){
            return false;
        }
    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User returnUser(User user){
        User userRequest = userRepository.findByUsername(user.getUsername());
        return userRequest;
    }

    public boolean deleteUser(String username){
        userRepository.deleteByUsername(username);
        return true;
    }

}
