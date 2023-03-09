/* eslint-disable */
import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';

import Button from '../../components/commons/button';
import NowContainer from '../../components/commons/nowLocation';
import CourseList from '../../components/Course/CourseList';
import { SEARCH_COURSE_REQUEST } from '../../store/modules/courseModule';

import { Container } from './CouserMain.style';
import styled from 'styled-components';

const StyledInput = styled.input`
  width: ${props => (props.width ? props.width : '12rem')};
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

const Input = props => (
  <StyledInput {...props} placeholder={props.placeholder} />
);

const CourseMain = () => {
  const [keyword, setKeyword] = useState('');
  const [inputKeyword, setInputKeyword] = useState('');
  const dispatch = useDispatch();
  const onChange = e => {
    setKeyword(e.target.value);
  };
  const navigate = useNavigate();

  const onSubmit = e => {
    e.preventDefault();
    setInputKeyword(keyword);
    dispatch({
      type: SEARCH_COURSE_REQUEST,
      data: {
        page: 0,
        keyword,
      },
    });
  };

  const onClick = () => {
    navigate('/course/create');
  };

  return (
    <div>
      <NowContainer desc="추 천 코 스" />
      <Container>
        <CourseList keyword={keyword} inputKeyword={inputKeyword} />
        <div
          style={{
            display: 'flex',
            width: '80%',
            justifyContent: 'space-between',
            marginLeft: '10%',
          }}
        >
          {/* <Link to="/course/create">글쓰기</Link> */}
          <form style={{ marginLeft: '38%' }} onSubmit={onSubmit}>
            <Input
              onChange={onChange}
              value={keyword}
              type="text"
              placeholder="검색어를 입력해주세요"
              width="18rem"
              mr="0.1rem"
              ml="0.1rem"
              mt="0.2rem"
            />
            <Button
              width="5rem"
              ml="0.1rem"
              mr="0.1rem"
              mt="0.2rem"
              height="2.3rem"
              bc="white"
              name="검색"
            />
          </form>
          <Button
            onClick={onClick}
            width="5rem"
            ml="0.1rem"
            mr="0.1rem"
            mt="0.2rem"
            height="2.3rem"
            bc="white"
            name="글쓰기"
          />
        </div>
      </Container>
    </div>
  );
};

export default CourseMain;
