/* eslint-disable */
import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 80vw;
  min-height: 80vh;
  justify-content: flex-start;
  align-items: center;
  overflow: auto;
  /* background-color: #def1ff; */
  /* padding: 3rem 0; */
`;

export const TabMenu = styled.ul`
  /* background-color: #dcdcdc; */
  /* font-weight: bold; */
  padding-left: 0;
  display: flex;
  width: ${props => props.width ? props.width : '60vw'};
  flex-direction: row;
  justify-items: center;
  justify-content: space-evenly;
  align-items: center;
  list-style: none;
  font-family: 'Pretendard-Regular';
  border-bottom: solid 0.5px;
  border-color: #ccc;

  .submenu {
    width: 100% auto;
    padding: 15px 10px;
    cursor: pointer;
  }
`;
