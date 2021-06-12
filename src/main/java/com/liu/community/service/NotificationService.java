package com.liu.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.executor.BatchExecutorException;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liu.community.dto.NotificationDTO;
import com.liu.community.dto.Pagination;
import com.liu.community.enums.NotificationEnum;
import com.liu.community.enums.NotificationStatusEnum;
import com.liu.community.exception.CustomizeErrorCode;
import com.liu.community.exception.CustomizeException;
import com.liu.community.mapper.NotificationMapper;
import com.liu.community.mapper.UserMapper;
import com.liu.community.model.Notification;
import com.liu.community.model.NotificationExample;
import com.liu.community.model.User;

@Service
public class NotificationService {

	@Autowired
	private NotificationMapper notificationMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	public Pagination list(Long userId, Integer page, Integer size) {
		Pagination<NotificationDTO> pagination = new Pagination<NotificationDTO>();
		NotificationExample notificationExample = new NotificationExample();
		notificationExample.createCriteria().andReceiverEqualTo(userId);
		
		Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
		Integer totalPage;
		if(totalCount%size==0) {
			totalPage=totalCount/size;
		}else {
			totalPage=totalCount/size+1;
		}
		if(page<1) page=1;
		else if(page>totalPage) page=totalPage;
		if(page==0) page=1;
		Integer offset=size*(page-1);
		NotificationExample notificationExample2 = new NotificationExample();
		notificationExample2.createCriteria().andReceiverEqualTo(userId);
		notificationExample2.setOrderByClause("gmt_create desc");
		List<Notification> notifications=notificationMapper.selectByExampleWithRowbounds(notificationExample2,new RowBounds(offset,size));
		if(notifications.size()==0) {
			return pagination;
		}
		List<NotificationDTO> notificationDTOList=new ArrayList<>();
				
		for(Notification notification:notifications) {
			NotificationDTO notificationDTO = new NotificationDTO();
			BeanUtils.copyProperties(notification, notificationDTO);
			notificationDTO.setTypeName(NotificationEnum.nameOf(notification.getType()));
			notificationDTOList.add(notificationDTO);
		}
		
		pagination.setData(notificationDTOList);
		pagination.setPagination(totalPage,page);
		return pagination;		
	}

	public Long unreadCount(Long id) {
		NotificationExample notificationExample = new NotificationExample();
		notificationExample.createCriteria().andReceiverEqualTo(id).andStatusEqualTo(0);
		long countByExample = notificationMapper.countByExample(notificationExample);
		return countByExample;
	}

	public NotificationDTO read(Long id, User user) {
		Notification selectByPrimaryKey = notificationMapper.selectByPrimaryKey(id);
		if(selectByPrimaryKey==null) throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
		if(selectByPrimaryKey.getReceiver()!=user.getId()) {
			throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
		}
		selectByPrimaryKey.setStatus(NotificationStatusEnum.READ.getStatus());
		notificationMapper.updateByPrimaryKey(selectByPrimaryKey);
		
		
		
		NotificationDTO notificationDTO = new NotificationDTO();
		BeanUtils.copyProperties(selectByPrimaryKey, notificationDTO);
		notificationDTO.setTypeName(NotificationEnum.nameOf(selectByPrimaryKey.getType()));
		return notificationDTO;
	}

}
