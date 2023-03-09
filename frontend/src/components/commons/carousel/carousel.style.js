/* eslint-disable */
import styled from 'styled-components';

export const MainContainer = styled.div`
  width: 100%;
  display: flex;
`;

export const SubContainer = styled.div`
  width: 100%;
  overflow: hidden; // 선을 넘어간 이미지들은 보이지 않도록 처리
  display: flex;
  align-items: center;
  overflow: hidden;
`;

export const SliderCotainer = styled.div`
  justify-content: space-evenly;
  width: 100%;
  display: flex;
  /* overflow: hidden; */
`;

export const SliderBox = styled.div`
  margin-left: 1rem;
  margin-right: 1rem;
`;
