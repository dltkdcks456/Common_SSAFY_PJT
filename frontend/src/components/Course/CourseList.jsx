/* eslint-disable */
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  LOAD_COURSE_REQUEST,
  SEARCH_COURSE_REQUEST,
} from '../../store/modules/courseModule';
import Pagination from '../Pagination/Pagination';
import CourseListItem from './CourseListItem';
import styled from 'styled-components';

const StyledTable = styled.table`
  width: 80%;
  margin-left: 10%;
  /* border: 1px solid black; */
`;

const StyledCol = styled.col`
  width: ${props => props.width};
`;

const StyledTh = styled.td`
  background-color: #def1ff;
  height: 2.2rem;
  vertical-align: middle;
  text-align: center;
  font-weight: 600;
  border-radius: 3px;
  color: black;
  & + & {
    border-left: 2px solid ${({ theme }) => theme.ContainerColor};
  }
`;

const PageContainer = styled.div`
  margin-top: 1rem;
`;

const CourseList = ({ keyword, inputKeyword }) => {
  const [page, setPage] = useState(0);
  const dispatch = useDispatch();
  useEffect(() => {
    if (!inputKeyword) {
      dispatch({
        type: LOAD_COURSE_REQUEST,
        data: { page },
      });
    } else if (inputKeyword && inputKeyword === keyword) {
      dispatch({
        type: SEARCH_COURSE_REQUEST,
        data: {
          page,
          keyword,
        },
      });
    }
  }, [page, dispatch, inputKeyword]);
  const { courses } = useSelector(state => state.course);
  return (
    <div>
      {courses.content && (
        <StyledTable>
          <colgroup>
            <StyledCol width="7%" />
            <StyledCol width="58%" />
            <StyledCol width="13%" />
            <StyledCol width="17%" />
            <StyledCol width="5%" />
          </colgroup>
          <thead>
            <tr>
              <StyledTh>번호</StyledTh>
              <StyledTh>제목</StyledTh>
              <StyledTh>작성일</StyledTh>
              <StyledTh>작성자</StyledTh>
              <StyledTh>조회수</StyledTh>
            </tr>
          </thead>
          <tbody>
            {courses.content.map(course => (
              <CourseListItem key={course.courseBoardId} course={course} />
            ))}
          </tbody>
        </StyledTable>
      )}
      {courses.content && (
        <PageContainer>
          <Pagination
            page={page}
            setPage={setPage}
            totalPage={courses.totalPages}
          />
        </PageContainer>
      )}
    </div>
  );
};
export default CourseList;
