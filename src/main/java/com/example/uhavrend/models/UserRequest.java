package com.example.uhavrend.models;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class UserRequest {
  private int id;
  private String email;
  private String password;
  private String firstName;
  private String secondName;
  private String gender;
  private String image;
  private String date_of_birth;
}
