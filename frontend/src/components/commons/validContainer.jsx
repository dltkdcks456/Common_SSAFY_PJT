/* eslint-disable */
import React from 'react';
import styled from 'styled-components';

const ValidBlock = styled.div`
  align-items: flex-start;
  margin-top: ${props => (props.mt ? props.mt : '')};
  /* margin-left: 1rem; */
  height: 2rem;
  display: flex;
  flex-wrap: wrap;
  color: ${props => (props.isValid ? 'blue' : 'red')};
  font-size: 0.7rem;
  width: ${props => (props.width ? props.width : '')};
  /* min-width: 11rem; */
  font-family: 'Pretendard-Regular';
`;

const ValidContainer = props => {
  return <ValidBlock isValid={props.isValid}>{props.errMsg}</ValidBlock>;
};

export default ValidContainer;
