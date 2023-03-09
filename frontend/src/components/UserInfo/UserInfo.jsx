/* eslint-disable */
import React from 'react';
import { UserActivity } from '../User_Activity';
import { Container, Image, MainContainer, Wrapper } from './UserInfo.style';
import { BASE_URL } from '../../utils/urls';
import Button from '../commons/button';
import { useNavigate } from 'react-router';
import { createChatRoomAPI2 } from '../../store/apis/chatApi';
import { openChat } from '../../store/modules/chatModule';
import { useDispatch } from 'react-redux';

const UserInfo = ({ user, me }) => {
  const navigate = useNavigate();
  const address = `${user.si} ${user.gun}`;
  const url = `${BASE_URL}user/imageDownloadBy/${user.image_path}`;
  const token = sessionStorage.getItem('userToken');
  const dispatch = useDispatch();
  const onChat = e => {
    e.preventDefault();
    createChatRoomAPI2({
      token,
      name: `${user.id}${me.id}`,
      opposite_nickname: user.nickname,
    })
      // .then(res => console.log(res))
      .catch(err => console.log(err));
    setTimeout(() => {
      dispatch(openChat());
    }, 500);
  };
  const onVideo = () => {
    navigate('/video');
  };
  return (
    <>
      <Container>
        {user && (
          <MainContainer>
            <Image src={url} />
            <Wrapper>
              <div className="title">{user.nickname} 님</div>
              {me.id === user.id ? (
                <>
                  <div className="content">나이 {user.age}</div>
                  {/* <div>성별 {user.gender}</div> */}
                  <div className="content">주소 {address}</div>
                </>
              ) : (
                <>
                  {user.open === 'true' && (
                    <>
                      <div className="content">나이 {user.age}</div>
                      {/* <div>성별 {user.gender}</div> */}
                      <div className="content">주소 {address}</div>
                    </>
                  )}
                </>
              )}
              <div style={{ display: 'flex' }}>
                {me.id !== user.id && (
                  <Button
                    name="채팅하기"
                    width="6rem"
                    mr="1rem"
                    mt="1rem"
                    onClick={onChat}
                    bc="white"
                  />
                )}
                <Button
                  name="화상채팅"
                  width="6rem"
                  mt="1rem"
                  onClick={onVideo}
                  bc="white"
                />
              </div>
            </Wrapper>
          </MainContainer>
        )}
      </Container>
      <div>
        <UserActivity user={user} />
      </div>
    </>
  );
};

export default UserInfo;
