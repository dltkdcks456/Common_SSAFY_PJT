/* eslint-disable */
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import UserAchievement from './UserAchievement';
import UserBoard from './UserBoard';
import UserDeal from './UserDeal';
import UserZzim from './UserZzim';
import { Container, TabMenu } from './User_Activity.style';

const Navbar = ({ user }) => {
  const [currentTab, setCurrentTab] = useState(0);
  const me = useSelector(state => state.myPage.user);

  const menuArr = [
    { name: '작성한 글', content: '작성한 글' },
    // { name: '획득 배지', content: '획득 배지' },
    { name: '판매 물품', content: '판매 물품' },
  ];

  if (me && me.id === user.id) {
    menuArr.push({ name: '찜 목록', content: '찜 목록' });
  }

  const selectMenu = index => {
    setCurrentTab(index);
  };

  return (
    // bootstrap의 tab 적용하기
    <Container>
      <TabMenu width={me && me.id === user.id ? '40vw' : ''}>
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
      <div>
        {currentTab === 0 && <UserBoard user={user} me={me} />}
        {/* {currentTab === 1 && <UserAchievement user={user} me={me} />} */}
        {currentTab === 1 && <UserDeal user={user} me={me} />}
        {currentTab === 2 && <UserZzim user={user} />}
      </div>
    </Container>
  );
};

export default Navbar;
