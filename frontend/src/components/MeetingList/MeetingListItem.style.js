/* eslint-disable */
import styled from 'styled-components';

export const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  /* min-width: 20rem;
  max-width: 25rem; */
  width: 25rem;
  border-radius: 10px;
  background-color: white;
  box-shadow: rgba(99, 99, 99, 0.5) 1px 1px 4px 0px;
  padding: 0 8rem;
  padding: 2rem 2rem;
  margin-bottom: 1rem;
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

export const Header = styled.div`
  display: flex;
  .header {
    /* width: 100%; */
    font-size: 1.5rem;
    font-family: 'Pretendard-Medium';
    font-weight: 600;
    cursor: pointer;
  }
`;

export const Body = styled.div`
  display: flex;
  flex-direction: column;
  margin-right: 2rem;
`;

export const BodyContent = styled.div`
  display: flex;
  font-family: 'Pretendard-Regular';
  margin-top: 1rem;
  .label {
    margin-right: auto;
    font-family: 'Pretendard-Bold';
  }
  .content {
    width: 20rem;
  }
`;

export const Footer = styled.div`
  display: flex;
  justify-content: flex-end;
  margin-top: 1rem;
  padding-right: 2rem;
`;
