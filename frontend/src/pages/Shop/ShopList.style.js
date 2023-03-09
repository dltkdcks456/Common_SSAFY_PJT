/* eslint-disable */
import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  /* width: 100vw; */
  /* height: 100vh; */
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-family: 'Pretendard-Regular';
  /* overflow: auto; */
  /* background-color: #def1ff; */
  /* padding: 3rem 0; */
  animation: smoothAppear;
  animation-duration: ${props => (props.duration ? props.duration : '0.5s')};
`;

export const Wrapper = styled.div`
  display: flex;
  margin-top: ${props => (props.mt ? props.mt : '1rem')};
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  flex-direction: ${props => (props.dir ? props.dir : '')};
  width: 60rem;
  justify-content: ${props => (props.jc ? props.jc : 'center')};
  .filter {
    display: flex;
    margin-top: ${props => (props.mt ? props.mt : '1rem')};
    margin-bottom: ${props => (props.mb ? props.mb : '')};
    margin-left: ${props => (props.ml ? props.ml : '')};
    margin-right: ${props => (props.mr ? props.mr : '')};
    .right {
      margin-left: auto;
    }
  }
`;

export const SelectBox = styled.select`
  width: ${props => (props.width ? props.width : '8rem')};
  margin-right: ${props => (props.mr ? props.mr : '')};
  margin-left: ${props => (props.ml ? props.ml : '')};
  height: 2.5rem;
  border-radius: 5px;
  text-align: center;
  font-size: 1rem;
  margin-right: 0.1rem;
  margin-left: 0.1rem;
  font-family: 'Pretendard-Regular';
`;

export const List = styled.div`
  display: flex;
  margin-top: ${props => (props.mt ? props.mt : '1rem')};
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  flex-direction: ${props => (props.dir ? props.dir : '')};
  width: 60rem;
  justify-content: ${props => (props.jc ? props.jc : 'space-between')};
  flex-wrap: wrap;
  .filter {
    display: flex;
    margin-top: ${props => (props.mt ? props.mt : '1rem')};
    margin-bottom: ${props => (props.mb ? props.mb : '')};
    margin-left: ${props => (props.ml ? props.ml : '')};
    margin-right: ${props => (props.mr ? props.mr : '')};
    .right {
      margin-left: auto;
    }
  }
`;
