/* eslint-disable */
import styled from "styled-components";
 
export const Container = styled.div`
  position: relative;
  width: 350px;
  height: 350px;
  background-color: white;
  border-radius: 10px;
  margin-top: 20px;
  margin-bottom: 23px;
  box-shadow: 0 14px 28px rgb(0 0 0 / 0%), 0 10px 10px rgb(0 0 0 / 3%);
`;
 
export const Wrapper = styled.div`
  position: absolute;
  top: 48px;
  right: 20px;
`;
 
export const Title = styled.h5`
  font-size: 13.55px;
  margin-left: 18px;
  margin-top: 15px;
`;

export const SelectBox = styled.select`
  width: ${props => (props.width ? props.width : '')};
  margin-right: ${props => (props.mr ? props.mr : '')};
  margin-left: ${props => (props.ml ? props.ml : '')};
  height: 2.3rem;
  border-radius: 5px;
  text-align: center;
  font-size: 0.9rem;
  font-family: 'Pretendard-Regular';
`;
