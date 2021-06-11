package com.liu.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liu.community.dto.CommentCreateDTO;
import com.liu.community.dto.CommentDTO;
import com.liu.community.enums.CommentType;
import com.liu.community.enums.NotificationEnum;
import com.liu.community.enums.NotificationStatusEnum;
import com.liu.community.exception.CustomizeErrorCode;
import com.liu.community.exception.CustomizeException;
import com.liu.community.mapper.CommentExtMapper;
import com.liu.community.mapper.CommentMapper;
import com.liu.community.mapper.NotificationMapper;
import com.liu.community.mapper.QuestionExtMapper;
import com.liu.community.mapper.QuestionMapper;
import com.liu.community.mapper.UserMapper;
import com.liu.community.model.Comment;
import com.liu.community.model.CommentExample;
import com.liu.community.model.Notification;
import com.liu.community.model.Question;
import com.liu.community.model.User;
import com.liu.community.model.UserExample;

@Service
public class CommentService {

	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private QuestionMapper questionMapper;
	
	@Autowired
	private QuestionExtMapper extMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CommentExtMapper cextMapper;
	
	@Autowired
	private NotificationMapper notificationMapper;
	
	@Transactional
	public void insert(Comment comment,User commentator) {
		if(comment.getParentId()==null||comment.getParentId()==0) {
			throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM__NOT_FOUND);
		}
		if(comment.getType()==null||!CommentType.isExist(comment.getType())) {
			throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM__NOT_FOUND);
		}
		if(comment.getType()==CommentType.COMMENT.getType()) {
			Comment selectByPrimaryKey = commentMapper.selectByPrimaryKey(comment.getParentId());
			if(selectByPrimaryKey==null) {
				throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
			}
			Question question = questionMapper.selectByPrimaryKey(selectByPrimaryKey.getParentId());
			if(question==null) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
			commentMapper.insert(comment);
			Comment comment2 = new Comment();
			comment2.setId(comment.getParentId());
			comment2.setCommentCount(1);
			cextMapper.incCommentCount(comment2);
			createNotify(comment, selectByPrimaryKey.getCommentator(),commentator.getName(),question.getTitle(),NotificationEnum.REPLY_COMMENT,question.getId());
		}else {
			Question selectByPrimaryKey = questionMapper.selectByPrimaryKey(comment.getParentId());
			if(selectByPrimaryKey==null) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}else {
				commentMapper.insert(comment);
				selectByPrimaryKey.setCommentCount(1);
				extMapper.incCommentCount(selectByPrimaryKey);
				createNotify(comment,selectByPrimaryKey.getCreator(),commentator.getName(),selectByPrimaryKey.getTitle(),NotificationEnum.REPLY_QUESTION,selectByPrimaryKey.getId());
			}
		}
	}

	private void createNotify(Comment comment,Long u,String notifierName,String outerTitle ,NotificationEnum e,Long outerid) {
		Notification notification = new Notification();
		notification.setGmtCreate(System.currentTimeMillis());
		notification.setType(e.getType());
		notification.setOuterid(outerid);
		notification.setNotifier(comment.getCommentator());
		notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
		notification.setReceiver(u);
		notification.setNotifierName(notifierName);
		notification.setOuterTitle(outerTitle);
		notificationMapper.insert(notification);
	}

	public List<CommentDTO> listByTargetId(Long id,CommentType type) {
		// TODO Auto-generated method stub
		CommentExample commentExample=new CommentExample();
		commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
		commentExample.setOrderByClause("gmt_create desc");
		List<Comment> selectByExample = commentMapper.selectByExample(commentExample);
		if(selectByExample.size()==0) {
			return new ArrayList();
		}
		Set<Long> commentators=selectByExample.stream().map(comment->comment.getCommentator()).collect(Collectors.toSet()); 
		List<Long> userIds=new ArrayList<>();
		userIds.addAll(commentators);
		UserExample userExample=new UserExample();
		userExample.createCriteria().andIdIn(userIds);
		List<User> selectByExample2 = userMapper.selectByExample(userExample);
		Map<Long, User> collect = selectByExample2.stream().collect(Collectors.toMap(user->user.getId(),user->user));
		List<CommentDTO> collect2 = selectByExample.stream().map(comment->{
			CommentDTO commentDTO=new CommentDTO();
			BeanUtils.copyProperties(comment, commentDTO);
			commentDTO.setUser(collect.get(comment.getCommentator()));
			return commentDTO;
		}).collect(Collectors.toList());
		return collect2;
	}

}
