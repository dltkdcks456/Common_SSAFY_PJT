import React, { useRef, useEffect, useState } from 'react';
import styled from 'styled-components';

const Div = styled.div`
  display: flex;
  margin-top: 1rem;
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  margin-left: 1rem;
  font-family: 'Pretendard-Regular';
  align-items: center;
`;

const Timer = () => {
  const [min, setMin] = useState(5);
  const [sec, setSec] = useState(0);
  const time = useRef(299);
  const timerId = useRef(null);

  // eslint-disable-next-line consistent-return
  useEffect(() => {
    timerId.current = setInterval(() => {
      setMin(parseInt(time.current / 60, 10));
      setSec(time.current % 60);
      time.current -= 1;
    }, 1000);

    return () => clearInterval(timerId.current);
  }, []);

  useEffect(() => {
    // 만약 타임아웃이 발생했을 경우
    if (time.current <= 0 && sec === 0) {
      alert('인증번호를 다시 요청해주세요');
      clearInterval(timerId.current);
      // dispatch event
    }
  }, [sec]);

  return <Div>{sec >= 10 ? `${min}:${sec}` : `${min}:0${sec}`}</Div>;
};

export default Timer;
