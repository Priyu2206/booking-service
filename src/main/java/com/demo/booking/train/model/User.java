package com.demo.booking.train.model;

import com.demo.booking.train.dto.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public static User build(UserRequest request) {
        return User.builder().email(request.getEmail()).firstName(request.getFirstName()).
                lastName(request.getLastName()).build();

    }
}