/* eslint-disable */
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router';
import 'react-datepicker/dist/react-datepicker.css';
import { MEETING_UPDATE_REQUEST } from '../../store/modules/meetingModule';
import { address } from '../Address';
import { localStart } from '../../utils/DateFormatter';
// import { ko } from 'date-fns/esm/locale';
import {
  Footer,
  InputBlock,
  MainContainer,
  SelectBox,
  Wrapper,
} from './MeetingRegisterForm.style';
import InputContainer from '../commons/inputContainer';
import DatePick from '../commons/datePicker/datePicker';
import Button from '../commons/button';

const MeetingUpdateForm = ({ meeting, close, communityId }) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const { user } = useSelector(state => state.myPage);
  // useEffect(() => {}, [user]);
  const token = sessionStorage.getItem('userToken');
  // console.log(meeting);

  // 주소지 목록
  const options1 = ['시', ...Object.keys(address)];
  const [options2, setOptions2] = useState([]);
  const [options3, setOptions3] = useState([]);

  // 주소 최신화
  useEffect(() => {
    if (si === '시') {
      setOptions2([]);
      setOptions3([]);
    } else {
      setOptions2(['구', ...Object.keys(address[si])]);
      setOptions3([]);
    }
    if (si && gun) {
      if (gun === '구') {
        setOptions3([]);
      } else {
        setOptions3(['동', ...address[si][gun]]);
      }
    }
  }, []);

  // 방 정보
  const [title, setTitle] = useState(meeting.title);
  // const [content, setContent] = useState(meeting.content);
  const [content, setContent] = useState(meeting.content);
  const [si, setSi] = useState(meeting.si);
  const [gun, setGun] = useState(meeting.gun);
  const [dong, setDong] = useState(meeting.dong);
  const [maxPerson, setMaxPerson] = useState(meeting.max_person);
  const [startTime, setStartTime] = useState(new Date(meeting.date));
  // const [startTime, setStartTime] = useState('');

  // 주소 입력 메커니즘
  const inputSi = e => {
    if (e.target.value === '시') {
      setOptions2([]);
      setGun('');
      setOptions3([]);
      setDong('');
    } else {
      setOptions2(['구', ...Object.keys(address[e.target.value])]);
      setGun('');
      setOptions3([]);
      setDong('');
    }
    setSi(e.target.value);
  };
  const inputGun = e => {
    if (e.target.value === '구') {
      setOptions3([]);
      setDong('');
    } else {
      setOptions3(['동', ...address[si][e.target.value]]);
      setDong('');
    }
    setGun(e.target.value);
  };
  const inputDong = e => {
    setDong(e.target.value);
  };

  // Handler Function
  const inputTitle = e => {
    setTitle(e.target.value);
  };

  const inputContent = e => {
    setContent(e.target.value);
  };

  const inputMaxPerson = e => {
    let num = parseInt(e.target.value);
    if (num > 0) setMaxPerson(e.target.value);
  };
  const updateBtn = e => {
    e.preventDefault();
    if (title === '') {
      alert('모임명을 입력해주세요');
    } else if (content === '') {
      alert('모임 설명을 입력해주세요');
    } else if (si === '' || gun === '') {
      alert('모임 위치를 입력해주세요');
    } else if (startTime === '') {
      alert('모임 일시를 입력해주세요');
    } else if (maxPerson < meeting.current_person) {
      alert('최대 인원수가 현재 인원수보다 작을 수 없습니다');
    } else {
      dispatch({
        type: MEETING_UPDATE_REQUEST,
        data: {
          token,
          community_id: communityId + '',
          title,
          content,
          si,
          gun,
          dong,
          max_person: maxPerson,
          start_time: localStart(startTime),
          in_progress: maxPerson > meeting.current_person ? true : false,
          navigate,
        },
      });
    }
  };

  return (
    <>
      <MainContainer>
        <InputContainer
          desc="모임명"
          width="18rem"
          id="title"
          placeholder="모임명을 입력해주세요"
          onChange={inputTitle}
          value={title}
        />
        <InputContainer
          desc="모임 설명"
          width="18rem"
          placeholder="모임에 대한 설명을 적어주세요"
          type="textarea"
          onChange={inputContent}
          value={content}
        />
        <InputBlock>
          <Wrapper>
            <div className="desc" width="small">
              모임 위치
            </div>
            <SelectBox
              onChange={inputSi}
              value={si}
              placeholder="시"
              width="6.4rem"
            >
              {options1.map(item => (
                <option value={item} key={item}>
                  {item}
                </option>
              ))}
            </SelectBox>
            <SelectBox
              onChange={inputGun}
              value={gun}
              placeholder="구"
              width="6.4rem"
            >
              {options2?.map(item => (
                <option value={item} key={item}>
                  {item}
                </option>
              ))}
            </SelectBox>
            <SelectBox
              onChange={inputDong}
              value={dong}
              placeholder="동"
              width="6.4rem"
            >
              {options3?.map(item => (
                <option value={item} key={item}>
                  {item}
                </option>
              ))}
            </SelectBox>
          </Wrapper>
        </InputBlock>
        <InputContainer
          desc="최대 인원수"
          type="number"
          width="18rem"
          onChange={inputMaxPerson}
          value={maxPerson}
        />
        <Wrapper>
          <div className="desc">모임 일시</div>
          <DatePick startTime={startTime} setStartTime={setStartTime} />
        </Wrapper>
      </MainContainer>
      <Footer>
        <Button
          className="close"
          onClick={updateBtn}
          padding="6px 12px"
          border-radius="5px"
          font-size="1rem"
          name="수정"
          width="6rem"
          height="2.3rem"
          mr="1rem"
          hoverColor="#def1ff"
        />
        <Button
          className="close"
          onClick={close}
          padding="6px 12px"
          border-radius="5px"
          font-size="1rem"
          name="닫기"
          width="6rem"
          height="2.3rem"
        />
      </Footer>
    </>
  );
};

export default MeetingUpdateForm;
