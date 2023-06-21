package com.example.uhavrend.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.uhavrend.entity.Post;
import com.example.uhavrend.entity.Role;
import com.example.uhavrend.entity.User;
import com.example.uhavrend.models.AddPostRequest;
import com.example.uhavrend.models.PostsRequest;
import com.example.uhavrend.models.RolesToUser;
import com.example.uhavrend.models.UserRequest;
import com.example.uhavrend.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private UserService userService;

    @Autowired public UserController(UserService userService) {
      this.userService = userService;
    }
/*
    @PostMapping("/reg") public ResponseEntity register(@RequestBody UserRequest userRequest) {
        User user = new User(userRequest.getEmail(), userRequest.getPassword(), userRequest.getFirstName(), userRequest.getSecondName(),
                userRequest.getGender(), userRequest.getDate_of_birth());
      User result = userService.saveUser(user);
      if (result != null) {
          return new ResponseEntity("user created", HttpStatus.CREATED);
      }
      return ResponseEntity.badRequest().body("bad request");
    }*/

    @GetMapping("/saveUser/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/saveUser/adduser")
    public String addUser(@ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/user/saveUser/index";
    }

    @PostMapping("/logged/postCreate")
    public String savePost(@ModelAttribute("post") PostsRequest postReg, BindingResult result, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        postReg.setEmail(authentication.getName());
        userService.createPost(postReg);

        return "redirect:/user/logged/get";
    }

    @GetMapping("/saveUser/index")
    public String mainPage(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "index";
    }

    @PostMapping("/saveUser") ResponseEntity<User>saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/saveUser").toUriString());
        User addUser = userService.saveUser(user);
        userService.addRoleToUser(addUser.getEmail(), "ROLE_USER");
        return ResponseEntity.created(uri).body(addUser);
    }


    @GetMapping("/logged/updateView")
    public String updateView(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.getUserByEmail(authentication.getName()));
        return "update";
    }

    @PostMapping("/logged/update/{id}") public String update(@PathVariable("id") long id, @ModelAttribute("user") User userRequest, BindingResult result, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userEdit = userService.getUserByEmail(authentication.getName());
        userEdit.setFirstName(userRequest.getFirstName());
        userEdit.setSecondName(userRequest.getSecondName());
        userEdit.setGender(userRequest.getGender());
        userEdit.setDate_of_birth(userRequest.getDate_of_birth());
        userService.editUser(userEdit);

        return "redirect:/user/logged/get";
    }

    @PostMapping("/logged/delete") public String deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        boolean result = userService.delete(user);
        if (result) {
            return "redirect:/logout";
        }
        return "";
    }

    @GetMapping("/logged/get") public String getUserById(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        if (user == null) {
            return "index";
        }
        model.addAttribute("user", user);
        model.addAttribute("post", new PostsRequest());
        return "user";
    }

    @GetMapping("/logged/read") public ResponseEntity getUserByEmail(@RequestParam String email) {
        User userRequest = userService.getUserByEmail(email);
        if (userRequest == null) {
            return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userRequest);
    }

    @GetMapping("/logged/users") public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/admin/saveRole") ResponseEntity<Role>saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/saveRole").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/admin/addRoleToUser") ResponseEntity<?>addRoleToUser(@RequestBody RolesToUser rolesToUser) {
        userService.addRoleToUser(rolesToUser.getEmail(), rolesToUser.getRoleName());
        return ResponseEntity.ok().build();
    }
}
