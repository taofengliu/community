function post(){
	var questionId=$("#question_id").val();
	var content=$("#comment_content").val();
	comment2target(questionId,1,content);
}

function comment2target(targetId,type,content){
	if(!content){
		alert("回复内容不能为空");
	}else{
	$.ajax({
		type:"POST",
		url:"/comment",
		contentType:"application/json",
		data:JSON.stringify({
			"parentId":targetId,
			"content":content,
			"type":type
		}),
		success:function(response){
			if(response.code==200){
				window.location.reload();
			}else{
				if(response.code==2003){
					var isAccepted=confirm(response.message);
					if(isAccepted){
					    window.localStorage.setItem("closable",true);
						window.open("https://github.com/login/oauth/authorize?client_id=69831f8aa606eea27b0e&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
						
					}
				}
				else{
					alert(response.message);
				}
			}
		},
		dataType:"json"
	});}
}


function comment(e){
	var commentId=e.getAttribute("data-id");
	var content=$("#input-"+commentId).val();
	comment2target(commentId,2,content);
}

function collapseComments(e){
	var id=e.getAttribute("data-id");
	var comments=$("#comment-"+id);
	var collapse=e.getAttribute("data-collapse");
	if(collapse){
		e.classList.remove("active");
		comments.removeClass("in");
		e.removeAttribute("data-collapse");
	}
	else{
		var subCommentContainer =$("#comment-"+id);
		if(subCommentContainer.children().length!=1){
			e.classList.add("active");
			comments.addClass("in");
			e.setAttribute("data-collapse","in");
		}else{
		$.getJSON("/comment/"+id,function(data){	
			$.each(data.data.reverse(),function(index,comment){
				
				var mediaBodyElement=$("<div/>",{
					"class":"media-body"
				}).append($("<h5/>",{
					"class":"media-heading",
					html:comment.user.name
				})).append($("<div/>",{
					html:comment.content
				})).append($("<div/>",{
					"class":"menu"
				}).append($("<span/>",{
					"class":"pull-right",
					html:moment(comment.gmtCreate).format('YYYY-MM-DD') 
				})))
				var hr=$("<hr>");
				
				var avatarElement=$("<img/>",{
					"class":"media-object img-rounded",
					"src":comment.user.avatarUrl,
					"style":"width: 40px; height: 40px;"
				});
			
				var mediaLeftElement=$("<div/>",{
					"class":"media-left"
				}).append(avatarElement);

				var mediaElement=$("<div/>",{
					"class":"media"
				});
				mediaElement.append(mediaLeftElement).append(mediaBodyElement);
				var commentElement=$("<div/>",{
						"class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
				});
				commentElement.append(mediaElement);
				commentElement.append(hr);
				subCommentContainer.prepend(commentElement);
			});
		

			e.classList.add("active");
			comments.addClass("in");
			e.setAttribute("data-collapse","in");
		});}
	}
	
}

function selectTag(e){
debugger;
    var value=e.innerHTML;
	var preval=$("#tag").val();
	if(preval.indexOf(value)==-1){
	if(preval){
		$("#tag").val(preval+','+value);
	}else{
		$("#tag").val(value);
	}}
}

function showTag(){
	$("#tags").show();
}