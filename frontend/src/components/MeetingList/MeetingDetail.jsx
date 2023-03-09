/* eslint-disable */
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { enterUserRoomAPI } from '../../store/apis/chatApi';
import {
  banMeetingUserAPI,
  inviteMeetingAPI,
  meetingUserList,
} from '../../store/apis/meetingApi';
import { openChat } from '../../store/modules/chatModule';
import {
  SEARCH_USER_REQUEST,
  SEARCH_USER_RESET,
} from '../../store/modules/userModule';
import { localDateTime } from '../../utils/DateFormatter';
import { BASE_URL } from '../../utils/urls';
import Button from '../commons/button';
import Input from '../commons/input';
import {
  Body,
  BodyContent,
  Footer,
  Header,
  Image,
  ListBody,
  ListItem,
  Wrapper,
} from './MeetingDetail.style';
import { customAlert, s1000, w1000 } from '../../utils/alarm';

const MeetingDetail = ({
  meeting,
  close,
  isIn,
  isKing,
  communityId,
  setIsIn,
}) => {
  // console.log(meeting);
  // console.log(isKing);

  const { user } = useSelector(state => state.myPage);
  const token = sessionStorage.getItem('userToken');
  const [inviteUser, setInviteUser] = useState('');
  const [isOpen, setIsOpen] = useState(false);
  const [searchUser, setSearchUser] = useState('');
  const [userList, setUserList] = useState([]);
  const [userNicknameList, setUserNicknameList] = useState([]);
  const address = `${meeting.si} ${meeting.gun} ${meeting.dong}`;
  let searchUsers = useSelector(state => state.user.searchUsers);
  const { isOpenChat } = useSelector(state => state.chat);

  const dispatch = useDispatch();

  // 현재 참여 목록 닉네임 배열로 변환
  useEffect(() => {
    userList.map(inUser => {
      // console.log(inUser);
      setUserNicknameList(prev => [...prev, inUser.nickname]);
    });
    return () => setUserNicknameList([]);
  }, [userList]);

  // 채팅방 참여
  const onChat = e => {
    e.preventDefault();
    enterUserRoomAPI({
      nickname: user.nickname,
      roomId: meeting.chatting_room_id,
    });
    if (!isOpenChat) {
      setTimeout(() => {
        dispatch(openChat()); // chatting_room_id, users, user
        close();
      }, 80);
    }
  };

  // 검색창
  const inputNickname = e => {
    if (!e.target.value) {
      dispatch({ type: SEARCH_USER_RESET });
    }
    setSearchUser(e.target.value);
  };

  // 검색된 유저
  useEffect(() => {
    if (searchUser) {
      dispatch({ type: SEARCH_USER_REQUEST, data: { nickname: searchUser } });
    }
    // setTimeout(() => {
    // }, 500);
    return () => dispatch({ type: SEARCH_USER_RESET });
  }, [searchUser]);

  // 유저 목록 조회
  useEffect(() => {
    meetingUserList({ communityId: communityId + '' }).then(res =>
      setUserList(res.data),
    );
    if (isKing) {
      setIsIn(true);
    }
  }, [isIn]);

  // 모임 초대
  const invite = e => {
    e.preventDefault();
    inviteMeetingAPI({
      token,
      invited_user_nickname: inviteUser,
      community_id: communityId + '',
    })
      .then(() => {
        customAlert(s1000, '초대 완료');
        setIsIn(false);
      })
      .catch(() => {
        customAlert(w1000, '초대 실패');
      });
  };

  // 모임 유저 강퇴
  const ban = inUserNickname => {
    banMeetingUserAPI({
      token,
      ban_user_nickname: inUserNickname,
      community_id: communityId + '',
    })
      .then(() => {
        customAlert(s1000, '강퇴 완료');
        setIsIn(false);
      })
      .catch(() => {
        customAlert(w1000, '강퇴 실패');
      });
  };

  return (
    <Wrapper>
      <Header>
        <div className="header">{meeting.title}</div>
      </Header>
      <Body>
        <BodyContent>
          <div className="label">모임 설명</div>
          <div className="content">{meeting.content}</div>
        </BodyContent>
        <BodyContent>
          <div className="label">모임 일시</div>
          <div className="content">{localDateTime(meeting.date)}</div>
        </BodyContent>
        <BodyContent>
          <div className="label">모임 위치</div>
          <div className="content">{address}</div>
        </BodyContent>
        <BodyContent>
          <div className="label">모임 인원</div>
          <div className="content">
            현재 <strong>{userList.length}</strong>명 / 최대{' '}
            <strong>{meeting.max_person}</strong>명
          </div>
        </BodyContent>
      </Body>
      <Footer>
        {isIn && (
          <Button
            onClick={onChat}
            name="채팅방 입장"
            width="8rem"
            height="2.5rem"
            ml="1rem"
            hoverColor="#def1ff"
          />
        )}
        <Button
          onClick={close}
          name="닫기"
          width="6rem"
          height="2.5rem"
          ml="1rem"
        />
      </Footer>
      {isKing &&
        (isOpen ? (
          <Body animation={isOpen ? 'modal-show 0.3s' : ''} overflow="hidden">
            <div
              onClick={() => setIsOpen(false)}
              style={{ cursor: 'pointer', textAlign: 'center' }}
            >
              ▲
            </div>
            <BodyContent>
              <div className="label">모임 유저 목록</div>
              <ListBody>
                {userList.map(inUser => (
                  <ListItem key={inUser.nickname}>
                    <div className="user">
                      <Image
                        src={`${BASE_URL}user/imageDownloadBy/${inUser.image_path}`}
                      />
                      <div>{inUser.nickname}</div>
                    </div>
                    {inUser.nickname !== user.nickname && (
                      <div className="btn">
                        <Button
                          onClick={e => {
                            e.preventDefault();
                            ban(inUser.nickname);
                          }}
                          name="강퇴"
                          width="4rem"
                          fontSize="0.8rem"
                          // font="Pretendard-Regular"
                          // bc="#ff7575"
                          // color="#777777"
                          hoverColor="red"
                        />
                      </div>
                    )}
                  </ListItem>
                ))}
              </ListBody>
            </BodyContent>
            <BodyContent>
              <div className="label">유저 초대하기</div>
              <Input
                width="14rem"
                height="1.8rem"
                placeholder="닉네임을 입력해주세요"
                value={searchUser}
                onChange={inputNickname}
              />
              <Button
                name="초대"
                width="4rem"
                ml="1rem"
                onClick={invite}
                hoverColor="#def1ff"
              />
            </BodyContent>
            {searchUsers.length > 0 && (
              <ListBody>
                <>
                  {searchUsers.map(
                    searchUser =>
                      !userNicknameList.includes(searchUser.nickname) && (
                        <ListItem
                          key={searchUser.nickname}
                          onClick={() => {
                            setInviteUser(searchUser.nickname);
                            setSearchUser(searchUser.nickname);
                          }}
                          cursor="pointer"
                          style={{ boxShadow: 'none' }}
                          p="0rem"
                        >
                          <div className="user">
                            <Image
                              src={`${BASE_URL}user/imageDownloadBy/${searchUser.image_path}`}
                            />
                            <div>{searchUser.nickname}</div>
                          </div>
                          {/* <div>{searchUsers.image_path}</div> */}
                        </ListItem>
                      ),
                  )}
                </>
              </ListBody>
            )}
          </Body>
        ) : (
          <Body>
            <div
              onClick={() => setIsOpen(true)}
              style={{ cursor: 'pointer', textAlign: 'center' }}
            >
              ▼
            </div>
          </Body>
        ))}
    </Wrapper>
  );
};

export default MeetingDetail;
