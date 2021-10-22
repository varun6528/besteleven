package com.verteil.besteleven.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class User {
    private String id;
    @Email(message = "Please provide a valid email id")
    private String name;
    private String password;
    private boolean admin;
    private int score;
    private int rank;
}
