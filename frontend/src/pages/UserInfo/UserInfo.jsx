import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useLocation, useNavigate } from 'react-router';
import { USER_INFO_REQUEST } from '../../store/modules/userModule';
import { UserNavbar } from '../../components/User_Navbar';
import NowContainer from '../../components/commons/nowLocation';
import { Container } from './UserInfo.style';

const UserPage = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const me = useSelector(state => state.myPage.user);
  const { state } = useLocation();
  useEffect(() => {
    if (me && me.nickname === state) {
      navigate('/user/mypage');
    }
    dispatch({
      type: USER_INFO_REQUEST,
      data: {
        nickname: state,
        navigate,
      },
    });
  }, []);
  const user = useSelector(request => request.user.userInfo);
  return (
    <div>
      <NowContainer desc="유 저 정 보" />
      <Container>
        <UserNavbar user={user} />
      </Container>
    </div>
  );
};

export default UserPage;
