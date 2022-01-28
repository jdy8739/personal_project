package com.example.projectbackend.controller.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MemberInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long memberNo;
    private String id;
    private String identity;
}
