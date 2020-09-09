package com.sprjjs.book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
//set和get和toString
@Data
//无参构造方法
@NoArgsConstructor
//有参构造方法
@AllArgsConstructor
public class TAddress implements Serializable{
    private Integer rid;

    private String uid;

    private String address;

    private Date added;

    private String receiver;

    private String rphone;

}