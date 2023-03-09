/* eslint-disable */
import React, { useState, useEffect } from 'react';
import ChatList from './ChatList';
import { faCommentDots, faBell } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useDispatch, useSelector } from 'react-redux';
import { closeChat, openChat } from '../../store/modules/chatModule';
import { Container } from './Chat.style';
import { BASE_URL } from '../../utils/urls';
import { alarmCheckAPI } from '../../store/apis/chatApi';

const Chat = () => {
  const [isNew, setIsNew] = useState(false);
  const { isOpenChat } = useSelector(state => state.chat);
  const dispatch = useDispatch();

  const token = sessionStorage.getItem('userToken');

  useEffect(() => {
    alarmCheckAPI({ token }).then(res => setIsNew(res.data));
  }, []);

  setInterval(() => {
    if (!isNew) {
      alarmCheckAPI({ token }).then(res => setIsNew(res.data));
    }
  }, 10000);

  const chatClose = () => {
    if (isOpenChat) {
      dispatch(closeChat());
      // client.disconnect();
    } else {
      dispatch(openChat());
    }
  };

  return (
    <>
      <Container onClick={chatClose}>
        <FontAwesomeIcon icon={faCommentDots} size="xl" />
        {isNew && !isOpenChat && <FontAwesomeIcon icon={faBell} size="xl" />}
      </Container>
      {isOpenChat && (
        <ChatList
          chatClose={chatClose}
          setIsNew={setIsNew}
          isOpenChat={isOpenChat}
        ></ChatList>
      )}
    </>
  );
};

export default Chat;
