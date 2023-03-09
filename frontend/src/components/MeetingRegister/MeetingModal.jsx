/* eslint-disable */
import './MeetingModal.style.css';
import React, { useEffect, useState } from 'react';
import MeetingRegisterForm from './MeetingRegisterForm';
import MeetingDetail from '../MeetingList/MeetingDetail';
import MeetingUpdateForm from './MeetingUpdateForm';
import { getMeetingDetail } from '../../store/apis/meetingApi';

const Modal = props => {
  // 열기, 닫기, 모달 헤더 텍스트를 부모로부터 받아옴
  const { open, close, header, index, communityId, isIn, isKing, setIsIn } = props;
  const [meeting, setMeeting] = useState();
  if (communityId) {
    useEffect(() => {
      getMeetingDetail({ community_id: communityId + '' }).then(res =>
        setMeeting(res.data),
      );
    }, []);
  }
  // console.log(meeting);

  return (
    // 모달이 열릴때 openModal 클래스가 생성된다.
    <div className={open ? 'openModal modal' : 'modal'}>
      {open ? (
        <section>
          <header style={{ fontFamily: 'Pretendard-Medium' }}>
            {header}
            <button className="close" onClick={close}>
              &times;
            </button>
          </header>
          <main>
            {index === '1' && <MeetingRegisterForm close={close} />}
            {index === '2' && meeting && (
              <MeetingDetail
                meeting={meeting}
                close={close}
                communityId={communityId}
                isIn={isIn}
                isKing={isKing}
                setIsIn={setIsIn}
              />
            )}
            {index === '3' && meeting && (
              <MeetingUpdateForm
                meeting={meeting}
                close={close}
                communityId={communityId}
              />
            )}
          </main>
        </section>
      ) : null}
    </div>
  );
};

export default Modal;
