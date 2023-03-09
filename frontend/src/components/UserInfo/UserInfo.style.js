/* eslint-disable */
import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  /* flex-direction: column; */
  width: 100%;
  /* height: 100vh; */
  /* justify-items: center; */
  justify-content: center;
  /* align-items: center; */
  overflow: auto;
  /* background-color: #def1ff; */
  /* padding: 3rem 0; */
`;

export const MainContainer = styled.div`
  /* min-width: 40rem; */
  width: 40vw;
  display: flex;
  flex-wrap: wrap;
  /* justify-content: center; */
`;

export const Wrapper = styled.div`
  /* padding-left: 2rem; */
  margin-right: auto;
  margin-left: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  .title {
    font-family: 'Pretendard-Bold';
    font-size: 1.2rem;
    margin-bottom: 1rem;
  }
  .content {
    font-family: 'Pretendard-Regular';
    font-size: 1rem;
    margin-top: 0.5rem;
  }
`;

export const Image = styled.img`
  width: 8rem;
  height: 8rem;
  border-radius: 50%;
  margin-right: auto;
  margin-left: auto;
  /* margin-bottom: 1rem; */
`;
