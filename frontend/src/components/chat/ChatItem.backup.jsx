/* eslint-disable */
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  callChattingAPI,
  getChatListAPI,
  getRoomUsersAPI,
} from '../../store/apis/chatApi';
import { userInfoAPI } from '../../store/apis/userApi';
import { openChat } from '../../store/modules/chatModule';
import { BASE_URL } from '../../utils/urls';
import { Content, Date, Image, List, Middle, Name } from './ChatItem.style';
import ChatRoom from './ChatRoom';

const ChatItem = ({ room, client, setIsNew, getRooms, isOpenChat }) => {
  const { nickname } = useSelector(state => state.myPage.user);
  const dispatch = useDispatch();
  const [messages, setMessages] = useState([]);
  const [users, setUsers] = useState([]);
  const [user, setUser] = useState();
  const { roomId } = useSelector(state => state.chat);
  const [newMessage, setNewMessage] = useState(room.alarm);

  useEffect(() => {
    callChattingAPI({ roomId: room.chattingRoomId }).then(res =>
      setMessages(res),
    );
    getRoomUsersAPI({ roomId: room.chattingRoomId }).then(res => setUsers(res));
    if (newMessage) {
      setIsNew(true);
    }
  }, []);

  useEffect(() => {
    setTimeout(() => {
      if (room.type === 1) {
        client.subscribe(
          `/topic/chat/room/${room.chattingRoomId}`,
          async response => {
            let tmp = JSON.parse(response.body);
            for (const u of users) {
              if (u.userId === tmp['sender']) {
                tmp['sender'] = u.nickname;
                setTimeout(() => setNewMessage(false), 1000);
                console.log('알림뜨면안됨');
              }
            }
            setMessages(prev => [...prev, tmp]);
            setIsNew(true);
            setNewMessage(true);
            getRooms();
          },
        );
      } else {
        client.subscribe(
          `/queue/chat/room/${room.chattingRoomId}`,
          async response => {
            let tmp = JSON.parse(response.body);
            for (const u of users) {
              if (u.userId === tmp['sender']) {
                tmp['sender'] = u.nickname;
                setTimeout(() => setNewMessage(false), 1000);
                console.log('알림뜨면안됨');
              }
            }
            setMessages(prev => [...prev, tmp]);
            setIsNew(true);
            setNewMessage(true);
            getRooms();
          },
        );
      }
    }, 500);
    return () => {
      if (client.connected) client.disconnect();
    };
  }, []);

  useEffect(() => {
    for (let user of users) {
      if (user.nickname !== nickname) {
        const tmpUser = async () => {
          // console.log(user);
          await userInfoAPI({ nickname: user.nickname }).then(res => {
            setUser(res.data);
            // console.log(res.data);
          });
        };
        tmpUser();
      }
    }
  }, [users]);

  const onClick = () => {
    // console.log(users);
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
          setIsNew={setIsNew}
          client={client}
          key={roomId}
          setNewMessage={setNewMessage}
          isOpenChat={isOpenChat}
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
              users.map((user, i) => (
                <span key={i}>
                  <strong>{user !== nickname && <>{user} </>}</strong>
                </span>
              ))
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
            <div>{newMessage && <>!!</>}</div>
          </Date>
        )}
      </List>
    </>
  );
};

export default ChatItem;
