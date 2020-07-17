package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domein.Article;
import com.example.domein.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

@Controller
@RequestMapping("")
public class ArticleController {

	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	
	/**
	 * 画面表示
	 * @return
	 */
	@RequestMapping("/article")
	public String index(Model model) {
		//記事一覧を取得する
		List<Article> articleList = articleRepository.findAll();
		//記事一覧のひとつずつの記事から記事idでコメント一覧を取得
		for (Article article : articleList) {
			Integer articleId = article.getId();
			List<Comment> commentList = commentRepository.findByArticleId(articleId);
			article.setCommentList(commentList);
		}
		//コメントの詰まった記事をスコープへセット
		model.addAttribute("articleList", articleList);
		return "article";
	}
	
	/**
	 * 記事を挿入する
	 * @param form
	 * @param model
	 */
	@RequestMapping("/articleInsert")
	public String insertArticle(ArticleForm form, Model model) {
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
//		article.setName(form.getName());
//		article.setContent(form.getContent());
		articleRepository.insert(article);
		return "redirect:/article";
	}
	
	/**
	 * コメントを挿入する.
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping("/commentInsert")
	public String insertComment(CommentForm form, Model model) {
		try {
			Comment comment = new Comment();
			BeanUtils.copyProperties(form, comment);
//			comment.setName(form.getName());
//			comment.setContent(form.getContent());
			comment.setArticleId(Integer.parseInt(form.getArticleId()));
			commentRepository.insert(comment);
		}
		catch (NumberFormatException e) {
			System.out.println(form.getArticleId() + " は数値ではありません。 ");
		}
		
		return "redirect:/article";
	}
	
	/**
	 * 記事とコメントを削除する.
	 * @param id
	 * @param articleId
	 * @return
	 */
	@RequestMapping("/delete")
	public String deleteArticle(String articleId) {
		int articleIdInt = Integer.parseInt(articleId);
		commentRepository.deleteByArticleId(articleIdInt);
		articleRepository.deleteById(articleIdInt);
		
		return "redirect:/article";
		
	}
	
	
}
