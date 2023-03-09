/* eslint-disable */
import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { useDispatch, useSelector } from 'react-redux';
import Navbar from '../Navbar/Navbar';
import {
  MY_PAGE_REQUEST,
  MY_PAGE_RESET,
} from '../../store/modules/myPageModule';
import Chat from '../chat/Chat';

const Layout = ({ children }) => {
  const dispatch = useDispatch();
  // 로그인 확인 변수
  const { logInDone } = useSelector(state => state.user);
  const { myPageDone, user } = useSelector(state => state.myPage);
  const userToken = sessionStorage.getItem('userToken');
  useEffect(() => {
    if (!myPageDone && userToken) {
      dispatch({
        type: MY_PAGE_REQUEST,
        data: {
          token: userToken,
        },
      });
    } else if (!userToken) {
      dispatch({
        type: MY_PAGE_RESET,
      });
    }
  }, [myPageDone, logInDone, dispatch]); // dispatch,

  return (
    <div>
      <Navbar myPageDone={myPageDone} user={user} />
      <main>{children}</main>
      {myPageDone && <Chat />}
    </div>
  );
};

Layout.propsTypes = {
  children: PropTypes.node.isRequired,
};

export default Layout;
