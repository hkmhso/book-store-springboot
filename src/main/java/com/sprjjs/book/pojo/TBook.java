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
public class TBook implements Serializable{
    private String isbn;

    private String title;

    private String author;

    private Double price;

    private String press;

    private Integer edition;

    private Date published;

    private Integer pages;

    private Integer words;

    private String packaging;

    private String format;

    private String form;

    private Integer status;

}