/* eslint-disable */
import React from 'react';
import styled from 'styled-components';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  background-color: #cacaca;
  border-radius: 10px;
  padding: 1rem 2rem;
  font-family: 'Pretendard-Regular';
`;

const Title = styled.div`
  font-weight: bold;
  margin-bottom: 0.5rem;
`;

const RecordBox = styled.div`
  display: flex;
  align-items: baseline;
  margin-bottom: 0.5rem;
  .record {
    font-size: 2rem;
    font-weight: 800;
    margin-right: 1rem;
  }
  .unit {
    font-weight: 800;
  }
`;

const Time = styled.div`
  font-size: small;
  display: flex;
  align-items: center;
  margin-bottom: 0.2rem;
  font-weight: 600;
`;

const Name = styled.div`
  font-size: small;
  display: flex;
  align-items: center;
  font-weight: 600;
`;

const RecordSlide = ({ props }) => (
  <>
    <Container>
      <Title>{props?.title}오늘의 최장 거리</Title>
      <RecordBox>
        <div className="record">{props?.record}300</div>
        <div className="unit">{props?.unit}Km</div>
      </RecordBox>
      <Time>
        <i
          className="material-icons"
          style={{ fontSize: 'small', marginRight: '0.3rem' }}
        >
          timer
        </i>
        {props?.time}02 : 10 : 11
      </Time>
      <Name>
        <i
          className="material-icons"
          style={{ fontSize: 'small', marginRight: '0.3rem' }}
        >
          person
        </i>
        {props?.name}기록자 이름
      </Name>
    </Container>
  </>
);

export default RecordSlide;
