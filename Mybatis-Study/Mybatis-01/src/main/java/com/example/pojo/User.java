package com.example.pojo;

import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import lombok.Data;

//实体类
@Data
public class User {
    private int id;
    private String name;
    private String passwd;
}
