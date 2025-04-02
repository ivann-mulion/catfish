package org.cat.fish.userservice.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "unique_email", columnNames = "email"),
        @UniqueConstraint(name = "unique_phoneNumber", columnNames = "phoneNumber")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @JsonIgnore
    @NotNull(message = "Password must not be null")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    @Column(name = "password")
    private String password;

    private String email;

    @Pattern(regexp = "^\\+7[0-9]{10,11}$|^0[0-9]{10,11}$", message = "The phone number is not in the correct format")
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


}
