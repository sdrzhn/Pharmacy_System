package com.example.uhavrend.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RolesToUser {
    private String email;
    private String roleName;
}
