/* eslint-disable */
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { callChattingAPI, getRoomUsersAPI } from '../../store/apis/chatApi';
import { userInfoAPI } from '../../store/apis/userApi';
import { openChat } from '../../store/modules/chatModule';
import { BASE_URL } from '../../utils/urls';
import { Content, Date, Image, List, Middle, Name } from './ChatItem.style';
import ChatRoom from './ChatRoom';

const ChatItem = ({ room, setIsNew, isOpenChat, chatClose }) => {
  const { nickname } = useSelector(state => state.myPage.user);
  const dispatch = useDispatch();
  const [messages, setMessages] = useState([]);
  const [users, setUsers] = useState([]);
  const [user, setUser] = useState();
  const { roomId } = useSelector(state => state.chat);
  const [newMessage, setNewMessage] = useState(room.alarm);

  useEffect(() => {
    setIsNew(false);
    callChattingAPI({ roomId: room.chattingRoomId }).then(res =>
      setMessages(res),
    );
    getRoomUsersAPI({ roomId: room.chattingRoomId }).then(res => setUsers(res));
    if (newMessage) {
      setIsNew(true);
    }
  }, []);

  useEffect(() => {
    for (let user of users) {
      if (user.nickname !== nickname) {
        const tmpUser = async () => {
          // console.log(user);
          await userInfoAPI({ nickname: user.nickname }).then(res => {
            setUser(res.data);
            // console.log(res.data.nickname);
          });
        };
        tmpUser();
      }
    }
  }, [users]);

  const onClick = () => {
    users.map(user => {
      if (user !== nickname) {
        dispatch(openChat(room.chattingRoomId, users, user));
      }
    });
  };

  return (
    <>
      {roomId && (
        <ChatRoom
          roomId={roomId}
          room={room}
          key={roomId}
          setNewMessage={setNewMessage}
          isOpenChat={isOpenChat}
          chatClose={chatClose}
        ></ChatRoom>
      )}
      <List onClick={onClick}>
        {user && room.type === 1 ? (
          <Image src={`${BASE_URL}user/imageDownloadBy/${user.image_path}`} />
        ) : (
          <Image
            src={`${BASE_URL}user/imageDownloadBy/images/profile/default.png`}
          />
        )}
        <Middle>
          <Name>
            {room.type === 99 ? (
              // 단톡인 경우
              <div>
                <strong>[모임] {room.name}</strong>
              </div>
            ) : (
              // 개인톡인 경우
              <div>
                <strong>{user?.nickname}</strong>
              </div>
            )}
          </Name>
          {messages[0] && (
            <Content>{messages[messages.length - 1].message}</Content>
          )}
        </Middle>
        {messages[0] && (
          <Date>
            {messages[messages.length - 1].sendTime.slice(5, 10)}{' '}
            {messages[messages.length - 1].sendTime.slice(11, 16)}
            {newMessage && <div>!!</div>}
          </Date>
        )}
      </List>
    </>
  );
};

export default ChatItem;
