package com.ebc.ead.authuser.model;

import com.ebc.ead.authuser.model.enums.UserStatus;
import com.ebc.ead.authuser.model.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, length = 150)
    private String fullname;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;
    @Column(length = 20)
    private String phoneNumber;
    @Column(length = 20)
    private String cpf;
    private String imageUrl;
    @CreationTimestamp
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
    @UpdateTimestamp
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lasUpdateDate;
}
