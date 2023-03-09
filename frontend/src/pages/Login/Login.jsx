/* eslint-disable */
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { LOG_IN_REQUEST } from '../../store/modules/userModule';
import { TIME_REQUEST } from '../../store/modules/logInTime';
import { Container, InputBox, Title } from './Login.style';
import Input from '../../components/commons/input';
import Button from '../../components/commons/button';

const Login = () => {
  const dispatch = useDispatch();
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');

  const inputId = e => {
    setId(e.target.value);
  };
  const inputPassword = e => {
    setPassword(e.target.value);
  };
  const navigate = useNavigate();

  const LoginButton = e => {
    const now = new Date();
    now.setHours(now.getHours() + 2);
    e.preventDefault();
    dispatch({
      type: LOG_IN_REQUEST,
      data: {
        id,
        password,
        navigate,
      },
    });
    dispatch({
      type: TIME_REQUEST,
      data: {
        now,
      },
    });
    setId('');
    setPassword('');
  };

  return (
    <>
      <Container>
        <Title>로 그 인</Title>
        <form onSubmit={LoginButton}>
          <InputBox mt="3rem">
            <div>아이디</div>
            <Input
              required
              // mt="1rem"
              width="30vw"
              minWidth="200px"
              onChange={inputId}
              value={id}
              type="text"
              placeholder="아이디를 입력해주세요."
            />
          </InputBox>
          <InputBox>
            <div>비밀번호</div>
            <Input
              required
              // mt="1rem"
              width="30vw"
              minWidth="200px"
              onChange={inputPassword}
              value={password}
              type="password"
              placeholder="비밀번호를 입력해주세요."
            />
          </InputBox>
          <Button
            name="로그인"
            mt="1.5rem"
            width="31vw"
            minWidth="220px"
            height="3rem"
            br="16px"
            border="none"
            // bc="#e9e9e9"
            hoverColor="#def1ff"
            onClick={LoginButton}
          />
        </form>
        <div
          style={{
            display: 'flex',
            justifyContent: 'space-around',
            width: '20vw',
            minWidth: '200px',
            marginTop: '1rem',
          }}
        >
          <Link
            style={{ fontSize: '13px', textDecoration: 'none', color: 'grey' }}
            to="/user/findId"
          >
            아이디 찾기
          </Link>
          <Link
            style={{ fontSize: '13px', textDecoration: 'none', color: 'grey' }}
            to="/user/findPwd"
          >
            비밀번호 찾기
          </Link>
          <Link
            style={{ fontSize: '13px', textDecoration: 'none', color: 'grey' }}
            to="/user/signup"
          >
            회원가입
          </Link>
        </div>
      </Container>
    </>
  );
};

export default Login;
