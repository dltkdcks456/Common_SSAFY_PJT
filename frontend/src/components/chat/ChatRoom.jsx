/* eslint-disable */
import React, { useState, useEffect, useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  alarmOffAPI,
  callChattingAPI,
  exitUserRoomAPI,
} from '../../store/apis/chatApi';
import { openChat, openChatList } from '../../store/modules/chatModule';
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
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import { BASE_URL } from '../../utils/urls';

const ChatRoom = ({ roomId, room, setNewMessage, chatClose }) => {
  const { nickname } = useSelector(state => state.myPage.user);
  const { users } = useSelector(state => state.chat);
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const token = sessionStorage.getItem('userToken');

  const dispatch = useDispatch();

  const messageBoxRef = useRef();
  const scrollToBottom = () => {
    if (messageBoxRef.current) {
      messageBoxRef.current.scrollTop = messageBoxRef.current.scrollHeight;
    }
  };

  // sockJS에 서버 프로토콜 연결
  let sockJS = new SockJS(`${BASE_URL}ws-stomp`);

  // stomp 프로토콜 위에서 sockJS가 돌아가도록 만듦
  let client = Stomp.over(sockJS);

  // 뒤로가기
  const back = () => {
    dispatch(openChatList());
    // client.disconnect();
  };

  // 메시지 입력
  const onChange = e => {
    setMessage(e.target.value);
  };
  // console.log(sockJS);
  // console.log('렌더링됨~~~', client.connected, sockJS.readyState);
  useEffect(() => {
    // 채팅 메시지 가져오기
    callChattingAPI({ roomId }).then(res => {
      setMessages(res);
      // console.log(res);
    });
    // 메시지 읽음 처리(프론트)
    setNewMessage(false);
    // 메시지 알람 끄기(백)
    if (room.alarm) {
      alarmOffAPI({ nickname, roomId })
    }
    // 채팅방 구독
    client.connect(
      {
        Authorization: `${token}`,
      },
      () => {
        if (room.type === 1) {
          client.subscribe(`/topic/chat/room/${roomId}`, async response => {
            // setMessages(prev => [...prev, JSON.parse(response.body)]);
            let tmp = JSON.parse(response.body);
            for (const u of users) {
              if (u.userId === tmp['sender']) {
                tmp['sender_nickname'] = u.nickname;
                setNewMessage(false);
                alarmOffAPI({ nickname, roomId })
                break;
              }
            }
            setMessages(prev => [...prev, tmp]);
          });
        } else {
          client.subscribe(`/queue/chat/room/${roomId}`, async response => {
            let tmp = JSON.parse(response.body);
            for (const u of users) {
              if (u.userId === tmp['sender']) {
                tmp['sender_nickname'] = u.nickname;
                setNewMessage(false);
                alarmOffAPI({ nickname, roomId })
                break;
              }
            }
            setMessages(prev => [...prev, tmp]);
          });
        }
      },
    );
    return () => {
      client.unsubscribe();
      client.disconnect();
      // 메시지 읽음 처리(프론트)
      setNewMessage(false);
      // 메시지 알람 끄기(백)
      if (room.alarm) {
        alarmOffAPI({ nickname, roomId })
      }
    };
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
          Authorization: `${token}`,
        },
        JSON.stringify({ roomId, sender: nickname, message, messageType: 1 }),
      );
    } else {
      client.send(
        '/app/chat/messageToCommunity',
        {
          Authorization: `${token}`,
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
        chatClose();
      })
      .catch(err => console.log(err));
  };
  
  return (
    <>
      <ChatBox>
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
            {room.type === 99 ? (
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
                  {content.sender_nickname === nickname ? (
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
                          {content.sender_nickname + ' '}
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
                <div key={index}>
                  {content.sender_nickname}님이 입장하셨습니다
                </div>
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
