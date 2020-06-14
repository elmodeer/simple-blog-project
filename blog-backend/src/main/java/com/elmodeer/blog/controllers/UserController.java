package com.elmodeer.blog.controllers;

import com.elmodeer.blog.models.User;
import com.elmodeer.blog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByUserId(@PathVariable int id) {
        User user = userRepository.findById(new Long(id)).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/edit")
    public ResponseEntity<User> editUserDetails(@RequestBody User user) {
        logger.info("Editing user details with the id: " + user.getId());
        // bind the address to the user. is it a must ? yes as otherwise new address creation
        // will throw exception
        user.getAddress().setUser(user);
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }


}
