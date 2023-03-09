/* eslint-disable */
import React, { useState, useEffect, useRef } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import { useDispatch, useSelector } from 'react-redux';
import {
  alarmOffAPI,
  callChattingAPI,
  exitUserRoomAPI,
  getChatListAPI,
  getRoomUsersAPI,
} from '../../store/apis/chatApi';
import { openChat, openChatList } from '../../store/modules/chatModule';
import { BASE_URL } from '../../utils/urls';
import {
  ChatBox,
  ChatBoxBody,
  ChatBoxHeader,
  ChatBoxOverlay,
  ChatBoxToggle,
  ChatLogs,
  Subject,
  Arrow,
  ChatForm,
  ChatInput,
  Me,
  You,
  ChatTime,
} from './ChatRoom.style';
import Button from '../commons/button';
import Input from '../commons/input';

const ChatRoom = ({
  roomId,
  chatClose,
  setIsNew,
  client,
  room,
  setNewMessage,
  isOpenChat,
}) => {
  const [chatMove, setChatMove] = useState(false);
  const { nickname } = useSelector(state => state.myPage.user);
  const { users } = useSelector(state => state.chat);
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState('');

  const dispatch = useDispatch();

  const messageBoxRef = useRef();
  const scrollToBottom = () => {
    if (messageBoxRef.current) {
      messageBoxRef.current.scrollTop = messageBoxRef.current.scrollHeight;
    }
  };

  // 뒤로가기
  const back = () => {
    dispatch(openChatList());
    // client.disconnect();
  };

  // 메시지 입력
  const onChange = e => {
    setMessage(e.target.value);
  };

  const token = sessionStorage.getItem('userToken');

  useEffect(() => {
    callChattingAPI({ roomId }).then(res => {
      setMessages(res);
    });
    setNewMessage(false);
    alarmOffAPI({ nickname, roomId })
      .then(res => console.log('성공', res.data))
      .catch(err => console.log('에러', err));
    client.connect(
      {
        // Authorization: token,
      },
      () => {
        if (room.type === 1) {
          client.subscribe(`/topic/chat/room/${roomId}`, async response => {
            // setMessages(prev => [...prev, JSON.parse(response.body)]);
            let tmp = JSON.parse(response.body);
            for (const u of users) {
              if (u.userId === tmp['sender']) {
                tmp['sender'] = u.nickname;
                setNewMessage(false);
                break;
              }
            }
            setMessages(prev => [...prev, tmp]);
            setIsNew(true);
            alarmOffAPI({ nickname, roomId })
              .then(res => console.log('성공', res.data))
              .catch(err => console.log('에러', err));
          });
        } else {
          client.subscribe(`/queue/chat/room/${roomId}`, async response => {
            let tmp = JSON.parse(response.body);
            for (const u of users) {
              if (u.userId === tmp['sender']) {
                tmp['sender'] = u.nickname;
                setNewMessage(false);
                break;
              }
            }
            setMessages(prev => [...prev, tmp]);
            setIsNew(true);
            alarmOffAPI({ nickname, roomId })
              .then(res => console.log('성공', res.data))
              .catch(err => console.log('에러', err));
          });
        }
      },
    );
    return () => client.disconnect();
  }, []);

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const onClick = e => {
    e.preventDefault();
    if (room.type === 1) {
      client.send(
        '/app/chat/message',
        {
          // Authorization: `Bearer ${token}`,
        },
        JSON.stringify({ roomId, sender: nickname, message, messageType: 1 }),
      );
    } else {
      client.send(
        '/app/chat/messageToCommunity',
        {
          // Authorization: `Bearer ${token}`,
        },
        JSON.stringify({ roomId, sender: nickname, message, messageType: 1 }),
      );
    }
    setMessage('');
  };

  // 채팅방 나가기
  const chatOut = () => {
    exitUserRoomAPI({ nickname, roomId })
      .then(() => {
        dispatch(openChat()); // chatting_room_id, users, user
      })
      .catch(err => console.log(err));
  };

  return (
    <>
      <ChatBox style={chatMove ? { display: 'none' } : {}}>
        <ChatBoxHeader>
          <Arrow>
            <div
              title="뒤로가기"
              style={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'flex-start',
                height: '100%',
                // width: '100%',
              }}
            >
              <i
                className="material-icons"
                onClick={back}
                style={{
                  cursor: 'pointer',
                }}
              >
                arrow_back
              </i>
            </div>
          </Arrow>
          <Subject>
            {room.roomType === 99 ? (
              <div>{room.name}</div>
            ) : (
              users.map((user, index) => (
                <span key={index}>
                  {user.nickname !== nickname && <>{user.nickname} </>}
                </span>
              ))
            )}
          </Subject>
          <ChatBoxToggle>
            <div
              title="채팅방 나가기"
              style={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'flex-end',
                height: '100%',
                // width: '100%',
              }}
            >
              <i
                className="material-icons"
                onClick={chatOut}
                style={{
                  cursor: 'pointer',
                }}
              >
                exit_to_app
              </i>
            </div>
          </ChatBoxToggle>
        </ChatBoxHeader>
        <ChatBoxBody>
          <ChatBoxOverlay />
          <ChatLogs ref={messageBoxRef}>
            {messages.map((content, index) =>
              content.messageType ? (
                <div key={index}>
                  {content.sender === nickname ? (
                    <div>
                      <ChatTime>
                        <div>
                          {content.sendTime
                            ? content.sendTime.slice(11, 16)
                            : null}
                        </div>
                      </ChatTime>
                      <Me>{content.message}</Me>
                    </div>
                  ) : (
                    <div>
                      <div>
                        <ChatTime>
                          {content.sender + ' '}
                          <span>
                            {content.sendTime
                              ? content.sendTime.slice(11, 16)
                              : null}
                          </span>
                        </ChatTime>
                      </div>
                      <You>{content.message}</You>
                    </div>
                  )}
                </div>
              ) : (
                <div key={index}>{content.sender}님이 입장하셨습니다</div>
              ),
            )}
          </ChatLogs>
        </ChatBoxBody>
        <ChatInput>
          <ChatForm>
            <Input
              value={message}
              onChange={onChange}
              placeholder="Send a message..."
            />
            <Button onClick={onClick} name="전송" bc="#ffffff">
              전송
            </Button>
          </ChatForm>
        </ChatInput>
      </ChatBox>
    </>
  );
};

export default ChatRoom;
