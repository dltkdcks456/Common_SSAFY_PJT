import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router';
import Button from '../../components/commons/button';
import Input from '../../components/commons/input';
import { DELETE_USER_REQUEST } from '../../store/modules/userModule';
import { Container, InputBox, Title } from './UserDelete.style';

const UserDelete = () => {
  const [password, setPassword] = useState('');
  const userToken = sessionStorage.getItem('userToken');
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const inputPassword = e => {
    setPassword(e.target.value);
  };
  const deleteUser = e => {
    e.preventDefault();
    dispatch({
      type: DELETE_USER_REQUEST,
      data: { password, userToken, navigate },
    });
  };
  const back = e => {
    e.preventDefault();
    navigate(-1);
  };
  return (
    <Container>
      <div>
        <Title>회원탈퇴</Title>
        <h5>회원탈퇴를 하시려면 비밀번호를 입력해주세요</h5>
      </div>
      <form onSubmit={deleteUser}>
        <InputBox mt="3rem">
          <div>비밀번호</div>
          <Input
            required
            value={password}
            onChange={inputPassword}
            id="inputPassword"
            type="password"
            width="30vw"
            minWidth="200px"
            height="2.5rem"
          />
        </InputBox>
        <div style={{ display: 'flex', justifyContent: 'center' }}>
          <Button
            type="submit"
            name="탈퇴"
            width="6rem"
            height="2.5rem"
            bc="#ff7474"
            hoverColor="red"
            border="none"
            // color="white"
            mt="1rem"
          />
          <Button
            name="취소"
            width="6rem"
            height="2.5rem"
            // bc="red"
            border="none"
            // color="white"
            mt="1rem"
            ml="0.2rem"
            onClick={back}
          />
        </div>
      </form>
    </Container>
  );
};

export default UserDelete;
