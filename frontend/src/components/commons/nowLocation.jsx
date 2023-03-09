/* eslint-disable */
import React from 'react';
import styled from 'styled-components';

const NowBlock = styled.div`
  display: flex;
  height: ${props => (props.height ? props.height : '10rem')};
  font-size: 3rem;
  font-weight: bold;
  /* letter-spacing: 1rem; */
  /* color: #000000; */
  justify-content: center;
  align-items: center;
  overflow: hidden;
  position: relative;
  /* background-color: #ffffff; */
  /* background-color: #def1ff; */
  /* border-bottom: solid 1px; */
  margin-bottom: 1rem;
  background: linear-gradient(-45deg, #def1ff, #a4c6ec, #91e2ff, #c0c9ff);
  background-size: 400% 400%;
  animation: gradient 15s ease infinite;

  @keyframes gradient {
    0% {
      background-position: 0% 50%;
    }
    50% {
      background-position: 100% 50%;
    }
    100% {
      background-position: 0% 50%;
    }
  }

  div {
    font-family: 'Pretendard-Medium';
    /* transition: 0.5s; */
    animation: smoothAppear;
    animation-duration: ${props => (props.duration ? props.duration : '1s')};
  }

  @keyframes smoothAppear {
    from {
      opacity: 0;
      transform: translateY(3rem);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }
`;

const NowContainer = props => {
  return (
    <NowBlock height={props.height}>
      <div>{props.desc}</div>
    </NowBlock>
  );
};

export default NowContainer;
