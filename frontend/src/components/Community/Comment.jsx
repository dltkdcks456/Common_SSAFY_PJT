import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { useDispatch, useSelector } from 'react-redux';
import { FreeDetailAPI } from '../../store/apis/communityApi';
import { CREATE_COMMENT_REQUEST } from '../../store/modules/communityModule';
import Button from '../commons/button';
import CommentList from './CommentList';

const StyledCommentInput = styled.input`
  width: ${props => (props.width ? props.width : '95%')};
  height: ${props => (props.height ? props.height : '2rem')};
  border-radius: ${props => (props.br ? props.br : '5px')};
  border: 1px solid #a3a3a3;
  padding-left: 1rem;
  font-size: 1rem;
  margin-top: ${props => (props.mt ? props.mt : '')};
  margin-left: ${props => (props.ml ? props.ml : '')};
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  margin-right: ${props => (props.mr ? props.mr : '')};
  font-family: 'Pretendard-Regular';
`;

const Comment = ({ user, boardId, boardCode }) => {
  const [comment, setComment] = useState('');
  const [comments, setComments] = useState([]);

  const dispatch = useDispatch();

  const getDetailArticle = async () => {
    const result = await FreeDetailAPI({ boardId, boardCode, user });
    setComments(result.comment);
  };

  // 댓글 작성 입력
  const onCommentChange = e => {
    setComment(e.target.value);
  };

  // 댓글 전송 버튼
  const onCommentSubmit = async e => {
    e.preventDefault();
    dispatch({
      type: CREATE_COMMENT_REQUEST,
      data: {
        user_id: user.user_id,
        content: comment,
        board_id: boardId,
      },
    });
    setComment('');
  };
  const { createCommentDone } = useSelector(state => state.community);

  useEffect(() => {
    getDetailArticle();
  }, [createCommentDone]);

  return (
    <div>
      <form>
        <input hidden="hidden" />
        <StyledCommentInput
          required
          onChange={onCommentChange}
          value={comment}
          type="text"
          placeholder="댓글을 입력해주세요"
        />
        <Button
          onClick={onCommentSubmit}
          width="5rem"
          ml="0.1rem"
          mr="0.1rem"
          mt="0.2rem"
          height="2.3rem"
          mb="0.8rem"
          bc="white"
          name="작성"
        />
      </form>
      <div style={{ marginTop: '15px' }} />
      {comments &&
        comments.map(c => (
          <CommentList
            key={c.time}
            c={c}
            user={user}
            getDetailArticle={getDetailArticle}
          />
        ))}
    </div>
  );
};

export default Comment;
