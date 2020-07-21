$(function(){
	$('button').click(function(){
		var articleId = $(this).val();
		//ajaxでコントローラーにリクエストを送信
		$.ajax({
			type : "GET",
			url : "/like", //送信先のコントローラー
			data : {
				articleId : articleId
			},　//リクエストパラメーターをJSON形式で送る
			dataType : "json", //レスポンスをJSON形式で受け取る
			async : true, //true:非同期(デフォルト),false:同期
		}).done(function(data){
			//通信が成功した場合に受け取るメッセージ
			likeCount = data["likeCount"];
			$("#likeCount" + articleId).text(likeCount)
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			//検索失敗時には、その旨をダイアログ表示
			alert("リクエスト時にエラーが発生しました：" + textStatus + ":\n" + errorThrown);
		});
		
	});
	
	
	
	
});