/* eslint-disable */
import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router';
import 'react-datepicker/dist/react-datepicker.css';
import { MEETING_CREATE_REQUEST } from '../../store/modules/meetingModule';
import { address } from '../Address';
import { localStart } from '../../utils/DateFormatter';
import { createChatRoomAPI1 } from '../../store/apis/chatApi';
import Button from '../commons/button';
import {
  Footer,
  InputBlock,
  MainContainer,
  SelectBox,
  Wrapper,
} from './MeetingRegisterForm.style';
import InputContainer from '../commons/inputContainer';
import { forwardRef } from 'react';
import DatePick from '../commons/datePicker/datePicker';

const MeetingResgisterForm = ({ close }) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const token = sessionStorage.getItem('userToken');

  // 주소지 목록
  const options1 = ['시', ...Object.keys(address)];
  const [options2, setOptions2] = useState([]);
  const [options3, setOptions3] = useState([]);

  // 방 정보
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [si, setSi] = useState('');
  const [gun, setGun] = useState('');
  const [dong, setDong] = useState('');
  const [maxPerson, setMaxPerson] = useState('1');
  const [startTime, setStartTime] = useState('');

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

  const registerBtn = e => {
    e.preventDefault();
    if (title === '') {
      alert('모임명을 입력해주세요');
    } else if (content === '') {
      alert('모임 설명을 입력해주세요');
    } else if (si === '' || gun === '') {
      alert('모임 위치를 입력해주세요');
    } else if (startTime === '') {
      alert('모임 일시를 입력해주세요');
    } else {
      // console.log(token, title, content, si, gun, dong, maxPerson, startTime);
      dispatch({
        type: MEETING_CREATE_REQUEST,
        data: {
          token,
          title,
          content,
          si,
          gun,
          dong,
          max_person: maxPerson,
          start_time: localStart(startTime),
          in_progress: true,
          navigate,
          dispatch,
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
          onClick={registerBtn}
          padding="6px 12px"
          border-radius="5px"
          font-size="1rem"
          name="생성"
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

export default MeetingResgisterForm;
