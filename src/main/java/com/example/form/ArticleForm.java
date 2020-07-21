package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ArticleForm {

	
	/** 投稿者名　*/
	@NotBlank(message = "投稿者名を入力してください")
	@Size(min = 1, max=127, message = "投稿者名は1桁以上127桁以下で入力してください")
	private String name;
	
	/** 投稿内容　*/
	@NotBlank(message =  "投稿内容を入力してください")
	@Size(min = 1, message = "値を入力してください")
	private String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ArticleForm [name=" + name + ", content=" + content + "]";
	}

	
	
}
