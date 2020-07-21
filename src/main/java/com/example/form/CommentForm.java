package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CommentForm {
	
	/**　コメント者名　*/
	@NotBlank(message = "コメント者名は必須入力です" )
	@Size(min = 1,max=127,message = "コメント者名は1桁以上127桁以下で入力してください")
	private String name;
	
	/** コメント内容　*/
	@NotBlank(message = "コメントは必須入力です")
	@Size(min =1, message = "コメントは必須です" )
	private String content;
	
	private String articleId;

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

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	@Override
	public String toString() {
		return "CommentForm [name=" + name + ", content=" + content + ", articleId=" + articleId + "]";
	}

	
	
}
