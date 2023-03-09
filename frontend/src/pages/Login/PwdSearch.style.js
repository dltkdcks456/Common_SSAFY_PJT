/* eslint-disable */
import styled from 'styled-components';

export const Container = styled.div`
  margin-left: auto;
  margin-right: auto;
  margin-top: 5rem;
  width: '30vw';
  min-width: '200px';
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
  font-family: 'Pretendard-Regular';
`;

export const Title = styled.div`
  font-weight: bold;
  font-size: 2.5rem;
  font-family: 'Pretendard-Medium';
  display: flex;
  justify-content: center;
`;

export const InputBox = styled.div`
  margin-top: ${props => (props.mt ? props.mt : '1rem')};
`;
