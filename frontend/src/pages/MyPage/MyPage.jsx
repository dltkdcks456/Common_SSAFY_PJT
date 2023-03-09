/* eslint-disable */
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router';
import NowContainer from '../../components/commons/nowLocation';
import { UserNavbar } from '../../components/User_Navbar';
import {
  USER_INFO_REQUEST,
  USER_INFO_RESET,
} from '../../store/modules/userModule';
import { Container } from './MyPage.style';

const MyPage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const user = useSelector(state => state.myPage.user);
  useEffect(() => {
    dispatch({
      type: USER_INFO_REQUEST,
      data: {
        nickname: user.nickname,
        navigate,
      },
    });
    return () =>
      dispatch({
        type: USER_INFO_RESET,
      });
  }, [user]);
  return (
    <div>
      <NowContainer desc="마 이 페 이 지" />
      <Container>
        <UserNavbar user={user} />
      </Container>
    </div>
  );
};

export default MyPage;
