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
public class TBookChild extends TBook implements Serializable{
	//数量
	private int count;

	public TBookChild(String isbn, String title, String author, Double price, String press, Integer edition,
			Date published, Integer pages, Integer words, String packaging, String format, String form, Integer status,
			int count) {
		super(isbn, title, author, price, press, edition, published, pages, words, packaging, format, form, status);
		this.count = count;
	}

	public TBookChild(String isbn, String title, String author, Double price, String press, Integer edition,
			Date published, Integer pages, Integer words, String packaging, String format, String form,
			Integer status) {
		super(isbn, title, author, price, press, edition, published, pages, words, packaging, format, form, status);
	}
	
}
