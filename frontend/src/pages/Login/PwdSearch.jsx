/* eslint-disable */
import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import Button from '../../components/commons/button';
import Input from '../../components/commons/input';
import { FIND_PWD_REQUEST } from '../../store/modules/userModule';
import { Container, InputBox, Title } from './PwdSearch.style';

const PwdSearch = () => {
  const [name, setName] = useState('');
  const [id, setId] = useState('');
  const [email, setEmail] = useState('');

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const inputName = e => {
    setName(e.target.value);
  };
  const inputEmail = e => {
    setEmail(e.target.value);
  };

  const inputId = e => {
    setId(e.target.value);
  };

  const findPwd = e => {
    e.preventDefault();
    dispatch({
      type: FIND_PWD_REQUEST,
      data: {
        id,
        name,
        email,
        navigate,
      },
    });
  };

  return (
    <>
      <Container>
        <div>
          <Title>비밀번호 찾기</Title>
          <h5>회원가입 시 이름과 등록한 이메일로 비밀번호를 찾습니다.</h5>
        </div>
        <form onSubmit={findPwd}>
          <InputBox mt="3rem">
            <div>성명</div>
            <Input
              required
              width="30vw"
              height="2.5rem"
              minWidth="200px"
              onChange={inputName}
              id="nameInput"
              value={name}
              type="text"
            />
          </InputBox>
          <InputBox>
            <div>아이디</div>
            <Input
              required
              width="30vw"
              height="2.5rem"
              minWidth="200px"
              onChange={inputId}
              id="inputEmail"
              value={id}
              type="text"
            />
          </InputBox>
          <InputBox>
            <div>이메일</div>
            <Input
              required
              width="30vw"
              height="2.5rem"
              minWidth="200px"
              onChange={inputEmail}
              id="inputEmail"
              value={email}
              type="text"
            />
          </InputBox>
          <div style={{ display: 'flex', justifyContent: 'center' }}>
            <Button name="찾기" mt="1rem" width="8rem" height="2.5rem" />
            <Link to="/user/login">
              <Button
                name="취소"
                mt="1rem"
                width="8rem"
                height="2.5rem"
                ml="1rem"
              />
            </Link>
          </div>
        </form>
      </Container>
    </>
  );
};

export default PwdSearch;
