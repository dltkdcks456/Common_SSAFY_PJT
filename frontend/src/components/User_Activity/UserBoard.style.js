/* eslint-disable */
import styled from 'styled-components';

export const Container = styled.div`
  width: 40vw;
  font-family: 'Pretendard-Regular';
`;

export const BoardList = styled.ul`
  list-style: none;
  padding: 0;
  margin-bottom: 0;
`;

export const BoardItem = styled.li`
  display: flex;
  flex-direction: row;
  height: 5rem;
  border-radius: 5px;
  box-shadow: rgba(99, 99, 99, 0.5) 0px 1px 2px 0px;
  padding: 1rem;
  margin-bottom: 1rem;
`;

export const ItemTitle = styled.div`
  cursor: pointer;
  font-family: 'Pretendard-Medium';
  display: flex;
  flex-direction: column;
  .kind {
    font-size: 0.8rem;
    color: gray;
    margin-bottom: 0.2rem;
  }
  .title {
    font-size: 1.4rem;
  }
  .number {
    font-size: 1.2rem;
    display: flex;
    margin-top: auto;
    color: gray;
    .numberItem {
      vertical-align: middle;
      margin-right: 0.2rem;
    }
  }
`;

export const ItemEnd = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: auto;
  align-items: flex-end;
  justify-content: space-between;
`;
