package com.example.lms.owner;

import com.example.lms.shared.OnCreate;
import com.example.lms.shared.validations.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryOwnerRequest {

    @NotNull(groups = OnCreate.class)
    @Size(min = 3,max = 7, groups = OnCreate.class)
    private String firstName;


    @NotNull(groups = OnCreate.class)
    @Size(min = 3,max = 7, groups = OnCreate.class)
    private String lastName;


    @NotNull
    @Email
    @Unique(table = "LibraryOwner", column = "email", groups = OnCreate.class)
    private String email;


    @NotNull
    @Size(min = 6,max = 20)
    private String password;
}