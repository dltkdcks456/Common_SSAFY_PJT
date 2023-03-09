/* eslint-disable */
import styled from 'styled-components';

export const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: ${props => (props.mt ? props.mt : '')};
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  flex-direction: ${props => (props.dir ? props.dir : '')};
  /* width: 27rem; */
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
  justify-content: center;
  .header {
    /* width: 20rem; */
    font-size: 1.5rem;
    font-family: 'Pretendard-Medium';
    font-weight: 600;
  }
`;

export const Body = styled.div`
  display: flex;
  flex-direction: column;
  /* margin-right: 2rem; */
  padding: 0 3rem;
  animation: ${props => (props.animation ? props.animation : '')};
`;

export const BodyContent = styled.div`
  display: flex;
  font-family: 'Pretendard-Regular';
  margin-top: 1rem;
  width: ${props => (props.width ? props.width : '')};
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
  margin-top: 1.5rem;
  /* margin-right: 1rem; */
`;

export const Image = styled.img`
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 50%;
  margin-right: 2rem;
  /* margin-bottom: 1rem; */
`;

export const ListBody = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  /* width: 20rem; */
  height: 10rem;
  overflow-y: auto;
  /* padding: 1rem; */
  /* overflow-y: scroll; */
  border-radius: 5px;
  box-shadow: rgba(99, 99, 99, 0.5) 0px 1px 2px 0px;
  &::-webkit-scrollbar-track {
    box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    background-color: #f5f5f5;
  }

  &::-webkit-scrollbar {
    width: 5px;
    background-color: #f5f5f5;
  }

  &::-webkit-scrollbar-thumb {
    background-color: #8b8b8b;
  }
`;

export const ListItem = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 18rem;
  padding: ${props => (props.p ? props.p : '1rem')};
  /* height: 10rem; */
  margin-top: ${props => (props.mt ? props.mt : '0.5rem')};
  /* margin-left: 0.5rem;
  margin-right: 0.5rem; */
  /* border-radius: 5px; */
  box-shadow: rgba(99, 99, 99, 0.5) 0px 1px 2px 0px;
  font-family: 'Pretendard-Regular';
  cursor: ${props => (props.cursor ? props.cursor : '')};
  .user {
    display: flex;
  }
  .btn {
    display: flex;
  }
`;
