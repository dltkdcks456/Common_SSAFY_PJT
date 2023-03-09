import moment from 'moment';
import React from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';

const StyledTr = styled.tr`
  /* cursor: pointer; */
  &:hover {
    background-color: ${({ theme }) => theme.noticeHoverColor};
  }
  & + & {
    border-top: 1px solid #dedede;
  }
`;

const StyledTd = styled.td`
  height: 3rem;
  vertical-align: middle;
  text-align: ${props => props.ta || 'center'};
  padding: 0 1.5rem;
`;

const CourseListItem = ({ course }) => {
  //   console.log(course);
  const articleTime = moment(course.regTime).format('YYYY-MM-DD');
  return (
    <StyledTr>
      <StyledTd>{course.courseBoardId}</StyledTd>
      <StyledTd>
        <Link
          to="/course/detail"
          state={{ courseId: course.courseBoardId }}
          style={{ textDecoration: 'none', color: 'black' }}
        >
          {course.title}
        </Link>
      </StyledTd>
      <StyledTd>{articleTime}</StyledTd>
      <StyledTd>{course.userNickname}</StyledTd>
      <StyledTd>{course.visited}</StyledTd>
    </StyledTr>
  );
};
export default CourseListItem;
