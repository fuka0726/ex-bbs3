package repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import domein.Article;

@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};
	
	
	/**
	 * 全件検索
	 * @return　記事一覧
	 */
	public List<Article> findAll(){
		String sql = "select id, name, content from articles ORDER BY id DESC ";
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
		return articleList;	
	}
	
	
	/**
	 * 記事の挿入.
	 * @param article
	 */
	public void insert(Article article) {
		String sql = "insert into articles (id, name, content) values (:id, :name, :content )";
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		template.update(sql, param);
		
	}
	
	
	/**
	 * idを元に記事を削除.
	 * @param id
	 */
	public void deleteById(Integer id) {
		String sql = "delete from articles where id = :id ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
	
	
	
}
