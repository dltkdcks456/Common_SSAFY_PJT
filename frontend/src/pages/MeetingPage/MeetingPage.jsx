/* eslint-disable */
import React from 'react';
import NowContainer from '../../components/commons/nowLocation';
import MeetingList from '../../components/MeetingList/MeetingList';
import { Container } from './MeetingPage.style';

const Meeting = () => (
  <div>
    <NowContainer desc="만 남 의 광 장" />
    <Container duration="0.5s">
      <MeetingList />
    </Container>
  </div>
);

export default Meeting;
