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
public class TUser implements Serializable{
	private static final long serialVersionUID = -8141893558610357515L;

	private String phone;

    private String uname;

    private String upwd;

    private String email;

    private Integer role;

}