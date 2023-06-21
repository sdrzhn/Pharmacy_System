package com.example.uhavrend.services;

import com.example.uhavrend.entity.Post;
import com.example.uhavrend.entity.Role;
import com.example.uhavrend.entity.User;
import com.example.uhavrend.models.PostsRequest;
import com.example.uhavrend.repository.PostRepository;
import com.example.uhavrend.repository.RoleRepository;
import com.example.uhavrend.repository.UserRepository;
import javafx.geometry.Pos;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PostRepository postRepository;
  @Autowired
  private RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;


  public Post createPost(PostsRequest request) {
    User user = userRepository.findByEmail(request.getEmail());
    if(user == null) {
      return null;
    }
    request.setDate(new Date());
    Post p = postRepository.save(new Post(request));
    user.getPosts().add(p);
    return p;
  }

  public User saveUser(User user) {
    log.info("Saving user");
//    User byEmail = userRepository.findByEmail(user.getEmail());
//    if(byEmail != null) {
//      return null;
//    }
    user.setEnabled(true);
    Role role = roleRepository.findByName("ROLE_USER");
    user.getRoles().add(role);

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public Role saveRole(Role role) {
    return roleRepository.save(role);
  }

  public void addRoleToUser(String email, String roleName) {
    User user = userRepository.findByEmail(email);
    Role role = roleRepository.findByName(roleName);
    user.getRoles().add(role);
  }

  public User getUser(Integer id) {
    return userRepository.findById(id.longValue()).orElse(null);
  }

  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public boolean editUser(User user) {
    userRepository.save(user);
    return true;
  }

  public boolean delete(User user) {
    if (user == null){
      return false;
    }
    userRepository.delete(user);
    return true;
  }

  public List<User> getUsers() {
    return (List<User>) userRepository.findAll();
  }


/*
  public boolean editUser(UserRequest userRequest) {
    users.put(userRequest.getEmail(), userRequest);
    return true;
  }

  public boolean deleteUser(String email) {
    if(!ValidateHelper.validate(email)) {
      return false;
    }
    if (users.containsKey(email)) {
      return false;
    }
    users.remove(email);
    return true;
  }
*/
}
