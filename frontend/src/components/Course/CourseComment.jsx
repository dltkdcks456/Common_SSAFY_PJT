import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { useDispatch, useSelector } from 'react-redux';
import {
  CREATE_COURSE_COMMENT_REQUEST,
  LOAD_COURSE_DETAIL_REQUEST,
} from '../../store/modules/courseModule';
import CourseCommentList from './CourseCommentList';
import Button from '../commons/button';

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

const CourseComment = ({ user, courseBoardId }) => {
  const [comment, setComment] = useState('');

  const dispatch = useDispatch();

  // 댓글 작성 입력
  const onCommentChange = e => {
    setComment(e.target.value);
  };

  // 댓글 조회
  const getDetailCourse = async () => {
    dispatch({
      type: LOAD_COURSE_DETAIL_REQUEST,
      data: {
        courseId: courseBoardId,
        user,
      },
    });
  };

  // 댓글 전송 버튼
  const onCommentSubmit = e => {
    e.preventDefault();
    dispatch({
      type: CREATE_COURSE_COMMENT_REQUEST,
      data: {
        user_id: user.user_id,
        content: comment,
        course_board_id: courseBoardId,
      },
    });
    setComment('');
  };

  const { courseDetailData } = useSelector(state => state.course);
  const { createCourseCommentDone } = useSelector(state => state.course);
  const { deleteCourseCommentDone } = useSelector(state => state.course);

  useEffect(() => {
    if (!courseDetailData.content) {
      getDetailCourse();
    }
  }, [courseDetailData]);

  useEffect(() => {
    getDetailCourse();
  }, [createCourseCommentDone, deleteCourseCommentDone]);
  // console.log(courseDetailData);
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
          mb="0.8rem"
          height="2.3rem"
          bc="white"
          name="작성"
        />
      </form>
      {courseDetailData.comment &&
        courseDetailData.comment.map(c => (
          <CourseCommentList
            key={c.time}
            c={c}
            user={user}
            getDetailCourse={getDetailCourse}
          />
        ))}
    </div>
  );
};

export default CourseComment;
