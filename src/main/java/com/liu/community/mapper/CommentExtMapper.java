package com.liu.community.mapper;

import com.liu.community.model.Comment;
import com.liu.community.model.CommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}