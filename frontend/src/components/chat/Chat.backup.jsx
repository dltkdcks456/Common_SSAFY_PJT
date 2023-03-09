/* eslint-disable */
import React, { useState, useEffect } from 'react';
import ChatList from './ChatList';
import { faCommentDots, faBell } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import { useDispatch, useSelector } from 'react-redux';
import { closeChat, openChat } from '../../store/modules/chatModule';
import { Container } from './Chat.style';
import { BASE_URL } from '../../utils/urls';

const Chat = () => {
  const [isNew, setIsNew] = useState(false);
  const [chatAlarm, setChatAlarm] = useState(false);
  const { user } = useSelector(state => state.myPage);
  const { isOpenChat } = useSelector(state => state.chat);
  const dispatch = useDispatch();

  // Socket 서버 URL
  // let socketUrl;
  // if (window.location.protocol === 'https:') {
  //   socketUrl = 'wss://' + window.location.host + '/my-socket';
  // } else {
  //   socketUrl = 'ws://' + window.location.host + '/my-socket';
  // }
  // let socket = new WebSocket(socketUrl);

  // sockJS에 서버 프로토콜 연결
  let sockJS = new SockJS(`${BASE_URL}ws-stomp`);

  // stomp 프로토콜 위에서 sockJS가 돌아가도록 만듦
  let client = Stomp.over(sockJS);

  const token = sessionStorage.getItem('userToken');

  useEffect(() => {
    if (user.id) {
      client.connect({}, () => {});
    }

    return () => {
      if (client.connected) client.disconnect();
    };
  }, []);

  const chatClose = () => {
    if (isOpenChat) {
      dispatch(closeChat());
      client.disconnect();
    } else {
      dispatch(openChat());
    }
    setIsNew(false);
  };

  return (
    <>
      <Container onClick={chatClose}>
        <FontAwesomeIcon icon={faCommentDots} size="xl" />
        {isNew && !isOpenChat && <FontAwesomeIcon icon={faBell} size="xl" />}
      </Container>
      <div style={{ visibility: isOpenChat ? 'visible' : 'hidden' }}>
        <ChatList
          chatClose={chatClose}
          client={client}
          setIsNew={setIsNew}
          chatAlarm={chatAlarm}
          isOpenChat={isOpenChat}
        ></ChatList>
      </div>
    </>
  );
};

export default Chat;
