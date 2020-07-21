package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domein.Article;
import com.example.domein.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.JoinedArticleRepository;
import com.example.repository.JoinedCommentRepository;

@Controller
@RequestMapping("")
public class JoinedArticleController {

	@Autowired
	private JoinedArticleRepository articleRepository;
	
	@Autowired
	private JoinedCommentRepository commentRepository;
	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	
	/**
	 * 掲示板を表示します.
	 * @param model
	 * @return
	 */
	@RequestMapping("/join-article")
	public String index(Model model) {
		List<Article> articleList = articleRepository.findAll();
		model.addAttribute("articleList", articleList);
		return "join-article";
	}
	
	/**
	 * 記事を投稿します.
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping("/join-article-insert")
	public String insertArticle(@Validated ArticleForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return index(model);
		}
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleRepository.insert(article);
		return "redirect:/join-article";
	}
	
	/**
	 * コメントを投稿します.
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping("/join-comment-insert")
	public String insertComment(@Validated CommentForm form, BindingResult result,  Model model) {
		if(result.hasErrors()) {
			return index(model);
		}
		Comment comment = new Comment();
		BeanUtils.copyProperties(form, comment);
		comment.setArticleId(Integer.parseInt(form.getArticleId()));
		commentRepository.insert(comment);
		return "redirect:/join-article";
	}
	
	/**
	 * 記事を削除する.
	 * @param articleId
	 * @return
	 */
	@RequestMapping("/join-delete")
	public String deleteArticle(String articleId) {
		int articleIdInt = Integer.parseInt(articleId);
		articleRepository.delete(articleIdInt);
		return "redirect:join-article";
	}
	
	
}
