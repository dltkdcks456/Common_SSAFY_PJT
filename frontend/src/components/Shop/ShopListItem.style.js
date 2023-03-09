/* eslint-disable */
import styled from 'styled-components';

export const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  /* min-width: 19rem;
  max-width: 19rem; */
  width: 19rem;
  border-radius: 10px;
  background-color: white;
  box-shadow: rgba(99, 99, 99, 0.5) 1px 1px 4px 0px;
  padding: 0 0rem;
  padding: 0rem 0rem;
  margin: 0.3rem 0.3rem 0.3rem 0.3rem;
`;

export const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: ${props => (props.mt ? props.mt : '')};
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  flex-direction: ${props => (props.dir ? props.dir : '')};
  width: 27rem;
  justify-content: ${props => (props.jc ? props.jc : 'center')};
  .desc {
    width: ${props => (props.small ? '4rem' : '8rem')};
    height: 2rem;
    display: flex;
    align-items: center;
    font-family: 'Pretendard-Regular';
  }
`;

