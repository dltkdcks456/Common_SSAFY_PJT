/* eslint-disable */
import React, { useState, useRef } from 'react';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import {
  getMeetingDetail,
  userMeetingCheck,
} from '../../store/apis/meetingApi';
import {
  DELETE_MEETING_REQUEST,
  MEETING_EXIT_REQUEST,
  PARTICIPATION_REQUEST,
} from '../../store/modules/meetingModule';
import { localStartTime } from '../../utils/DateFormatter';
import Button from '../commons/button';
import Modal from '../MeetingRegister/MeetingModal';
import {
  Body,
  BodyContent,
  Footer,
  Header,
  MainContainer,
  Wrapper,
} from './MeetingListItem.style';

const MeetingListItem = ({ kingNickname, communityId, user }) => {
  const dispatch = useDispatch();
  const token = sessionStorage.getItem('userToken');
  const [isIn, setIsIn] = useState(false);
  const [meeting, setMeeting] = useState();
  const isKing = kingNickname === user.nickname ? true : false;

  useEffect(() => {
    // 모임방 정보 들고오기
    getMeetingDetail({ community_id: communityId }).then(res =>
      setMeeting(res.data),
    );

    // 유저가 해당 모임방에 들어가있는지 확인
    userMeetingCheck({ token, community_id: communityId }).then(res =>
      setIsIn(res.data),
    );
  }, [isIn]);

  // 상세 모달 열기 / 닫기
  const [modalOpen1, setModalOpen1] = useState(false);
  const openModal1 = () => {
    setModalOpen1(true);
  };
  const closeModal1 = () => {
    setModalOpen1(false);
  };

  // 수정 모달 열기 / 닫기
  const [modalOpen2, setModalOpen2] = useState(false);
  const openModal2 = () => {
    setModalOpen2(true);
  };
  const closeModal2 = () => {
    setModalOpen2(false);
  };

  // 모임방 참여
  const onIn = e => {
    e.preventDefault();
    dispatch({
      type: PARTICIPATION_REQUEST,
      data: {
        token,
        community_id: communityId + '',
      },
    });
    setIsIn(prev => !prev);
  };

  // 모임방 나가기
  const onOut = e => {
    e.preventDefault();
    // console.log(communityId);
    dispatch({
      type: MEETING_EXIT_REQUEST,
      data: {
        token,
        community_id: communityId + '',
      },
    });
    setIsIn(prev => !prev);
  };

  // 모임방 삭제
  const onDelete = e => {
    e.preventDefault();
    dispatch({
      type: DELETE_MEETING_REQUEST,
      data: {
        token,
        community_id: communityId + '',
      },
    });
  };

  return (
    <>
      {meeting && (
        <MainContainer>
          <Wrapper>
            <Header>
              <div className="header" onClick={openModal1}>
                {meeting.title}
              </div>
            </Header>
            <Body>
              <BodyContent>
                <div className="label">시작 장소</div>
                <div className="content">{`${meeting.si} ${meeting.gun} ${meeting.dong}`}</div>
              </BodyContent>
              <BodyContent>
                <div className="label">예상 일시</div>
                <div className="content">{localStartTime(meeting.date)}</div>
              </BodyContent>
              <BodyContent>
                <div className="label">모집 인원</div>
                <div className="content">
                  현재 <strong>{meeting.current_person}</strong>명 / 최대{' '}
                  <strong>{meeting.max_person}</strong>명
                </div>
              </BodyContent>
            </Body>
            <Footer>
              {isKing ? (
                <>
                  <Button
                    onClick={openModal2}
                    name="수정"
                    width="6rem"
                    height="2.5rem"
                    bc=""
                    border="none"
                    hoverColor="#def1ff"
                  />
                  <Button
                    onClick={onDelete}
                    name="삭제"
                    width="6rem"
                    height="2.5rem"
                    ml="1rem"
                    border="none"
                    hoverColor="#ff6767"
                  />
                </>
              ) : isIn ? (
                <Button
                  onClick={onOut}
                  value={false}
                  name="취소"
                  width="6rem"
                  border="none"
                  height="2.5rem"
                />
              ) : meeting.in_progress ? (
                <Button
                  onClick={onIn}
                  value={true}
                  name="신청"
                  width="6rem"
                  height="2.5rem"
                  border="none"
                  hoverColor="#def1ff"
                />
              ) : (
                <Button
                  name="정원 초과"
                  width="6rem"
                  height="2.5rem"
                  disabled
                />
              )}
            </Footer>
          </Wrapper>
        </MainContainer>
      )}
      {modalOpen1 && (
        <Modal
          open={modalOpen1}
          close={closeModal1}
          header="모임 상세 정보"
          index="2"
          communityId={communityId}
          isIn={isIn}
          isKing={isKing}
          setIsIn={setIsIn}
        />
      )}
      {modalOpen2 && (
        <Modal
          open={modalOpen2}
          close={closeModal2}
          header="모임 수정"
          index="3"
          communityId={communityId}
        />
      )}
    </>
  );
};

export default MeetingListItem;
