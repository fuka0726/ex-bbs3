package com.example.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domein.Article;
import com.example.domein.Comment;

@Repository
public class JoinedArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * articleListとcommentテーブルを結合したものからarticleリストを作成する.
	 * articleオブジェクト内にはcommentListを格納.
	 * 
	 */
	private static final ResultSetExtractor<List<Article>> ARTICLE_RESULT_SET_EXTRACTOR = (rs) -> {
		//空の記事リスト生成
		List<Article> articleList = new LinkedList<Article>();
		//コメントリストは空で初期化
		List<Comment> commentList = null;
		//初期の記事IDは0で初期化
		int beforeArticleId = 0;
		//while文の繰り返し作業
		while (rs.next()) {
			//現在の記事のidを取得
			int nowArticleId =rs.getInt("id");
			//現在の記事idと前の記事idが一緒でなければ(新たに記事が投稿されたら)
			if (nowArticleId != beforeArticleId) {
				//記事をインスタンス化
				Article article = new Article();
				//それぞれのフィールドの値をセット
				article.setId(nowArticleId);
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
					//空のコメントリスト作成(null→List)
					commentList = new ArrayList<Comment>();
				article.setCommentList(commentList);
				//記事リストに詰める
				articleList.add(article);
			}
			//コメントがあったらインスタンス化
			if (rs.getInt("c_id") != 0) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("c_id"));
				comment.setName(rs.getString("c_name"));
				comment.setContent(rs.getString("c_content"));
				//Articleドメインの空のCommentListにコメントをつめる
				commentList.add(comment);
			}
			//初期の記事＝現在の記事ID
			beforeArticleId = nowArticleId;
		}
		//コメントなどが詰まった記事リストが戻る
		return articleList;
	};
	
	/**
	 * 記事一覧の取得.記事に含まれているコメント一覧も同時に取得.
	 * @return
	 */
	public List<Article> findAll(){
		String sql = "SELECT a.id,a.name,a.content,c.id c_id, c.name c_name, c.content c_content,  c.article_id from articles AS a LEFT JOIN comments AS c ON a.id = c.article_id order by a.id DESC, c.id ASC ";
		List<Article> articleList = template.query(sql, ARTICLE_RESULT_SET_EXTRACTOR);
		return articleList;
	}
	
	/**
	 * 記事をインサートする
	 * @param article
	 * @return
	 */
	public void insert (Article article) {
		String sql ="insert into articles (name, content) values (:name, :content)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		template.update(sql, param);
	}
	
	/**
	 * 記事をDBから削除し、コメントも削除する.
	 * @param articleId
	 */
	public void delete (int articleId) {
		String sql = "WITH deleted AS ( delete from articles where id = :id RETURNING id) delete from comments where article_id IN (select id from deleted )";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", articleId);
		template.update(sql, param);
	}
	
	/**
	 * 記事投稿者の名前で前方一致検索をする.
	 * @param name 
	 * @return　該当記事とコメントのリスト
	 */
	public List<Article> findByUserName(String name){
		String sql = "select a.id,a.name,a.content,c.id c_id,c.name c_name,c.comtent c_content,c.article.id from articles a LEFT JOIN comments c ON a.id = c.article_id where a.name like :name order by a.id DESC, c.id ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name + "%");
		List<Article> articleList = template.query(sql, param, ARTICLE_RESULT_SET_EXTRACTOR);
		return articleList;
	}
	
	
	
}
