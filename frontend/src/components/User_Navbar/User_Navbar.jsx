/* eslint-disable */
import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import MyCalendar from '../MyCalendar/MyCalendar';
import UserHealth from '../UserHealth/UserHealth';
import UserInfo from '../UserInfo/UserInfo';
import { Container, TabMenu } from './User_Navbar.style';
import { LOAD_SHOP_DETAIL_RESET } from '../../store/modules/shopModule';

const Navbar = ({ user }) => {
  const [currentTab, setCurrentTab] = useState(0);
  const me = useSelector(state => state.myPage.user);
  const menuArr = [
    { name: '활동내역', content: '활동내역' },
    { name: '운동기록', content: '운동기록' },
  ];

  if (me && me.id === user.id) {
    menuArr.push({ name: '모임일정', content: '모임일정' });
  }

  const selectMenu = index => {
    setCurrentTab(index);
  };

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch({
      type: LOAD_SHOP_DETAIL_RESET,
    });
  });
  return (
    // bootstrap의 tab 적용하기
    <Container>
      <TabMenu>
        {menuArr.map((el, index) => (
          <li
            key={el.name}
            style={
              index == currentTab
                ? { fontWeight: 'bold' }
                : { fontWeight: 'normal' }
            }
            className={index === currentTab ? 'submenu focused' : 'submenu'}
            onClick={() => selectMenu(index)}
          >
            {el.name}
          </li>
        ))}
      </TabMenu>
      <>
        {currentTab === 0 && <UserInfo user={user} me={me} />}
        {currentTab === 1 && <UserHealth user={user} />}
        {currentTab === 2 && <MyCalendar user={user} />}
      </>
    </Container>
  );
};

export default Navbar;
