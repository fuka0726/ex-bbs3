package com.example.controller;

import java.util.ArrayList;
import java.util.List;

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
@RequestMapping("/article")
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
	@RequestMapping("/")
	public String index() {
//		articleRepository.findAll();
//		commentRepository.findByArticleId(articleId);
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
		article.setName(form.getName());
		article.setContent(form.getContent());
		articleRepository.insert(article);
		model.addAttribute("article", article);
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
		Comment comment = new Comment();
		comment.setName(form.getName());
		comment.setContent(form.getContent());
		comment.setArticleId(Integer.parseInt(form.getArticleId()));
		commentRepository.insert(comment);
		model.addAttribute("comment", comment);
		return "redirect:/article";
	}
	
	/**
	 * 記事とコメントを削除する.
	 * @param id
	 * @param articleId
	 * @return
	 */
	public String deleteArticle(Integer id, Integer articleId) {
		Article article = new Article();
		articleRepository.deleteById(id);
		List<Comment> commentList = new ArrayList<>();
		commentRepository.findByArticleId(articleId);
		return "redirect:/article";
	}
	
	
}
