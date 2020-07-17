package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domein.Comment;

@Repository
public class CommentRepository {

	@Autowired
	NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("contents"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};
	
	
	/**
	 * 記事IDをもとにコメントを見つける
	 * @param articleId
	 * @return
	 */
	public List<Comment> findByArticleId(Integer articleId){
		String sql = "select id, name, content, article_id from comments where article_id = :articleId ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		List<Comment> commentList =  template.query(sql,param,COMMENT_ROW_MAPPER );
		return commentList;
	}
	
	/**
	 * コメントを書き込む
	 * @param comment
	 */
	public void insert (Comment comment) {
		String sql = "insert into comments (id, name, content, article_id) values (:id, :name, :content, :article_id) ";
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		template.update(sql, param);
	}
	
	/**
	 * コメントを削除する
	 * @param articleId
	 */
	public void deleteByArticleId(Integer articleId) {
		String sql = "delete from comments where article_id = :articleId ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		template.update(sql, param);
	}
	
	
	
}
