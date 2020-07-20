package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domein.Comment;

@Repository
public class JoinedCommentRepository {

	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public void insert (Comment comment) {
		String sql = "insert into comments (name,content,article_id) values (:name, :content, :articleId)" ;
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		template.update(sql, param);
		
	}
	
}
