package com.example.lms.patron;

import com.example.lms.shared.OnCreate;
import com.example.lms.shared.OnUpdate;
import com.example.lms.shared.validations.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class PatronRequest {


    @NotNull
    @Email
    @Unique(table = "Patron",column = "email", groups = {OnCreate.class, OnUpdate.class})
    private String email;


    @NotNull
    @Size(min = 6,max = 20)
    private String password;

    @NotNull(groups = OnCreate.class)
    private String name;


    @NotNull(groups = OnCreate.class)
    private Gender gender;

    @NotNull(groups = OnCreate.class)
    private String phoneNumber;

    @NotNull(groups = OnCreate.class)
    private String address;

}