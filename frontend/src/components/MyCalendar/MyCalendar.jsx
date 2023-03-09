/* eslint-disable */
import React, { useEffect, useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import moment from 'moment/moment';
import { getMeetingList } from '../../store/apis/myPageApi';
import './MyCalendar.css';
import { getMeetingDetail } from '../../store/apis/meetingApi';
import Modal from '../MeetingRegister/MeetingModal';

const MyCalendar = ({ user }) => {
  const token = sessionStorage.getItem('userToken');
  const [value, setValue] = useState(new Date());
  const [meeting, setMeeting] = useState();
  const [meetingList, setMeetingList] = useState([]);
  const [marks, setMarks] = useState([]);
  const [isIn, setIsIn] = useState(true);
  let isKing;

  const getList = () => {
    setMarks([]);
    getMeetingList({ token })
      .then(res => {
        setMeetingList(res.data);
        res.data.map(mark => {
          setMarks(prev => [...prev, mark.community_date.slice(0, 10)]);
        });
      })
      .catch(err => console.log(err));
  };

  useEffect(() => {
    getList();
  }, []);
  // console.log(meetingList);

  const onchange = e => {
    setValue(e);
    if (marks.includes(moment(e).format('YYYY-MM-DD'))) {
      getMeetingDetail({
        community_id:
          meetingList[marks.indexOf(moment(e).format('YYYY-MM-DD'))]
            .community_id,
      }).then(res => {
        // setMeeting(res.data);
        // isKing = meetingList[marks.indexOf(moment(e).format('YYYY-MM-DD'))]
        // .community_글쓴이 === user.nickname ? true : false;
        openModal1();
      });
    }
  };

  // 상세 모달 열기 / 닫기
  const [modalOpen1, setModalOpen1] = useState(false);
  const openModal1 = () => {
    setModalOpen1(true);
  };
  const closeModal1 = () => {
    setModalOpen1(false);
  };

  return (
    <>
      <Calendar
        calendarType="US" // 요일을 일요일부터 시작하도록 설정
        onChange={onchange}
        value={value}
        className="mx-auto w-full text-sm border-b"
        formatDay={(locale, date) => moment(date).format('DD')}
        tileContent={({ date }) => {
          if (marks.find(x => x === moment(date).format('YYYY-MM-DD'))) {
            return (
              <>
                <div className="dotBox">
                  <div className="dot"></div>
                </div>
                <div className="content">
                  {
                    meetingList[
                      marks.indexOf(moment(date).format('YYYY-MM-DD'))
                    ].community_title
                  }
                </div>
              </>
            );
          }
        }}
      />
      {modalOpen1 && (
        // isKing 바꿔야됨!!!!!!!!!!!!!!!! +46번째줄
        <Modal
          open={modalOpen1}
          close={closeModal1}
          header="모임 상세 정보"
          index="2"
          communityId={
            meetingList[marks.indexOf(moment(value).format('YYYY-MM-DD'))]
              .community_id
          }
          isIn={isIn}
          isKing={false}
          setIsIn={setIsIn}
        />
      )}
    </>
  );
};

export default MyCalendar;
