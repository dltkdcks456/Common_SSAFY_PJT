/* eslint-disable */
import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import {
  ChatBox,
  ChatBoxHeader,
  ChatBoxBody,
  Subject,
  ChatBoxToggle,
  ChatBoxOverlay,
  ChatLogs,
  Info,
} from './ChatList.style';
import { getRoomsDetailAPI } from '../../store/apis/chatApi';
import ChatItem from './ChatItem';
import { LOG_OUT_REQUEST } from '../../store/modules/userModule';
import { useNavigate } from 'react-router';

const ChatList = ({ chatClose, setIsNew, client, isOpenChat }) => {
  const [rooms, setRooms] = useState([]);
  const token = sessionStorage.getItem('userToken');
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const getRooms = () => {
    getRoomsDetailAPI({ token })
      .then(res => {
        setRooms(res);
        // console.log('rooms 갱신');
      })
      .catch(e => {
        console.log(e);
        // dispatch({ type: LOG_OUT_REQUEST, navigate });
      });
  };

  // console.log(rooms);
  useEffect(() => {
    getRooms();
  }, [setIsNew, isOpenChat]);

  return (
    <>
      <ChatBox>
        <ChatBoxHeader>
          <Subject>채팅</Subject>
          <ChatBoxToggle>
            <div style={{ display: 'flex', alignItems: 'center' }}>
              <i
                className="material-icons"
                onClick={chatClose}
                style={{
                  cursor: 'pointer',
                }}
              >
                close
              </i>
            </div>
          </ChatBoxToggle>
        </ChatBoxHeader>
        <ChatBoxBody>
          <ChatBoxOverlay />
          <ChatLogs>
            {rooms.length > 0 ? (
              <>
                {rooms.map(room => (
                  <ChatItem
                    key={room.chattingRoomId}
                    room={room}
                    client={client}
                    setIsNew={setIsNew}
                    getRooms={getRooms}
                    chatClose={chatClose}
                    isOpenChat={isOpenChat}
                  />
                ))}
              </>
            ) : (
              <Info>현재 진행 중인 채팅이 없습니다.</Info>
            )}
          </ChatLogs>
        </ChatBoxBody>
      </ChatBox>
    </>
  );
};

export default ChatList;
