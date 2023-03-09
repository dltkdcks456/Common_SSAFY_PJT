/* eslint-disable */
import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { darkTheme, ligthTheme } from '../../store/modules/themeModule';
import { LOG_OUT_REQUEST } from '../../store/modules/userModule';
import {
  Container,
  DropDown,
  DropDownContent,
  DropDownItem,
  Icon,
  Item,
  Logo,
  MainContainer,
  MenuWrapper,
} from './Navbar.style';
import { BASE_URL } from '../../utils/urls';
import logo from '../../assets/images/rideway-low-resolution-logo-black-on-white-background.png';

const Navbar = ({ myPageDone, user }) => {
  const { isDarkMode } = useSelector(state => state.theme);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const token = sessionStorage.getItem('userToken');
  const logOutBtn = () => {
    dispatch({ type: LOG_OUT_REQUEST, navigate });
  };

  return (
    <MainContainer>
      <Container>
        <Item
          onClick={() => {
            navigate('/');
          }}
        >
          <Logo src={logo} />
        </Item>
        <Item
          onClick={() => {
            navigate('/community/free/100');
          }}
        >
          커뮤니티
        </Item>
        <Item
          onClick={() => {
            navigate('/shop');
          }}
        >
          중고장터
        </Item>
        <Item
          onClick={() => {
            navigate('/course');
          }}
        >
          추천코스
        </Item>
        <Item
          onClick={() => {
            navigate('/meeting');
          }}
        >
          만남의 광장
        </Item>
        <Item
          onClick={() => {
            navigate('/map');
          }}
        >
          지도
        </Item>
        <MenuWrapper>
          {myPageDone ? (
            <DropDown>
              <Item>
                <Icon
                  src={`${BASE_URL}user/imageDownloadBy/${user.image_path}`}
                />
                {user.nickname}님
              </Item>
              <DropDownContent>
                <DropDownItem onClick={() => navigate('/user/mypage')}>
                  마이페이지
                </DropDownItem>
                <DropDownItem
                  onClick={() => {
                    navigate('/user/edituser');
                  }}
                >
                  프로필 수정
                </DropDownItem>
                <DropDownItem onClick={() => navigate('/user/editPwd')}>
                  비밀번호변경
                </DropDownItem>
                <DropDownItem onClick={() => navigate('/user/search-user')}>
                  유저 검색
                </DropDownItem>
                {/* {isDarkMode ? (
                <DropDownItem onClick={() => dispatch(ligthTheme())}>
                다크모드 off
                </DropDownItem>
                ) : (
                  <DropDownItem onClick={() => dispatch(darkTheme())}>
                  다크모드 on
                  </DropDownItem>
                )} */}
                <DropDownItem onClick={logOutBtn}>로그아웃</DropDownItem>
              </DropDownContent>
            </DropDown>
          ) : (
            <Item
              onClick={() => {
                navigate('/user/login');
              }}
            >
              로그인
            </Item>
          )}
          {/* //   <span>
        //     <Link to="/user/mypage">{user.nickname}님</Link>
        //     <button onClick={logOutBtn}>로그아웃</button>
        //     <Link to="/user/edituser">회원정보수정</Link>
        //   </span>
        // ) : (
        // )} */}
          {/* <Link to="/user/search-user">유저 검색</Link> */}
        </MenuWrapper>
      </Container>
    </MainContainer>
  );
};

export default Navbar;
