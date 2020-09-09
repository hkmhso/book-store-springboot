package com.sprjjs.book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
//set和get和toString
@Data
//无参构造方法
@NoArgsConstructor
//有参构造方法
@AllArgsConstructor
public class TCollect implements Serializable{
    private Integer rid;

    private String uid;

    private String book;

}