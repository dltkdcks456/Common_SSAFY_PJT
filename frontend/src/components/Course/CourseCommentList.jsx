/* eslint-disable */
import React, { useState, useEffect } from 'react';
import moment from 'moment';
import styled from 'styled-components';
import { useDispatch } from 'react-redux';
import {
  DELETE_COURSE_COMMENT_REQUEST,
  UPDATE_COURSE_COMMENT_REQUEST,
} from '../../store/modules/courseModule';
import updateImg from '../../assets/images/pencil.png';
import deleteImg from '../../assets/images/trash.png';
import { BASE_URL } from '../../utils/urls';
import Button from '../commons/button';
import { useNavigate } from 'react-router';
import { userInfoAPI } from '../../store/apis/userApi';

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

const Icon = styled.img`
  width: 3rem;
  height: 3rem;
  border-radius: 50%;
  cursor: pointer;
`;

const StyledDate = styled.span`
  font-size: 11px;
  color: gray;
  padding-left: 5px;
  padding-right: 5px;
`;

const StyledCommentContainer = styled.div`
  display: flex;
`;

const StyledCommentSubContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
`;

const StyledUserNickName = styled.span`
  font-weight: bolder;
`;

const CourseCommentList = ({ c, user }) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [update, setUpdate] = useState(false);
  const [comment, setComment] = useState(c.content);
  const [ImgPath, setImgPath] = useState(null);

  const commentTime = moment(c.time).format('YY/MM/DD');

  // 댓글 삭제하기
  const onDelete = e => {
    const index = e.target.value;
    // console.log('캬핫', index);
    dispatch({
      type: DELETE_COURSE_COMMENT_REQUEST,
      data: {
        courseBoardCommentId: c.courseBoardCommentId,
      },
    });
  };
  // 댓글 수정하기
  const onUpdate = () => {
    setUpdate(prev => !prev);
  };

  const getProfileImg = async nickname => {
    await userInfoAPI({ nickname }).then(res =>
      setImgPath(res.data.image_path),
    );
  };

  useEffect(() => {
    setComment(comment);
    getProfileImg(c.userNickname);
  }, [comment]);

  const onChange = e => {
    setComment(e.target.value);
  };

  const onSubmit = e => {
    e.preventDefault();
    dispatch({
      type: UPDATE_COURSE_COMMENT_REQUEST,
      data: {
        course_board_comment_id: c.courseBoardCommentId,
        content: comment,
      },
    });
    onUpdate();
  };

  return (
    <div>
      {update ? (
        <div>
          <form onSubmit={onSubmit} style={{ marginBottom: '10px' }}>
            <StyledCommentInput
              onChange={onChange}
              value={comment}
              type="text"
              placeholder="댓글을 입력해주세요"
            />
            <Button
              width="5rem"
              ml="0.1rem"
              mr="0.1rem"
              mt="0.2rem"
              height="2.3rem"
              bc="white"
              name="수정"
            />
            <Button
              onClick={onUpdate}
              width="5rem"
              ml="0.1rem"
              mr="0.1rem"
              mt="0.2rem"
              height="2.3rem"
              bc="white"
              name="취소"
            />
          </form>
        </div>
      ) : (
        <div>
          <StyledCommentContainer>
            <Icon
              onClick={() => {
                navigate('/user/userinfo', {
                  state: c.userNickname,
                });
              }}
              src={`${BASE_URL}user/imageDownloadBy/${ImgPath}`}
            />
            <StyledCommentSubContainer>
              <div style={{ marginLeft: '12px' }}>
                <StyledUserNickName>{c.userNickname}</StyledUserNickName>
                <StyledDate>{commentTime}</StyledDate>
                {user.user_id === c.userId ? (
                  <span>
                    <img
                      src={updateImg}
                      style={{ width: '15px', cursor: 'pointer' }}
                      onClick={onUpdate}
                    />
                    <img
                      src={deleteImg}
                      style={{
                        width: '15px',
                        cursor: 'pointer',
                        marginLeft: '5px',
                      }}
                      onClick={onDelete}
                    />
                  </span>
                ) : null}
                <div>
                  <span>{comment}</span>
                </div>
              </div>
            </StyledCommentSubContainer>
          </StyledCommentContainer>
        </div>
      )}
    </div>
  );
};
export default CourseCommentList;
