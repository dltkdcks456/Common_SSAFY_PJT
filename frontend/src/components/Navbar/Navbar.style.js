/* eslint-disable */
import styled from 'styled-components';

export const MainContainer = styled.div`
  
`

export const Container = styled.div`
  display: flex;
  margin: 0rem 2rem 0vw 10vw;
  justify-content: space-between;
  width: 80vw;
  z-index: 999;
  /* position: fixed; */
`;

export const Item = styled.div`
  width: 10rem;
  color: ${({ theme }) => theme.menuColor};
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: 'Pretendard-Bold';
  cursor: pointer;
`;

export const Logo = styled.img`
  cursor: pointer;
  width: 8rem;
`;

export const MenuWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const Icon = styled.img`
  width: 3rem;
  height: 3rem;
  padding: 0.5rem;
  border-radius: 50%;
`;

export const DropDownContent = styled.div`
  display: none;
  position: absolute;
  color: ${({ theme }) => theme.textColor};
  background-color: ${({ theme }) => theme.bgColor};
  min-width: 10rem;
  box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
  border-radius: 10px;
  font-family: 'Pretendard-Regular';
  /* z-index: 1; */
`;

export const DropDown = styled.div`
  position: relative;
  display: inline-block;
  z-index: 10;
  &:hover ${DropDownContent} {
    display: block;
  }
`;

export const DropDownItem = styled.div`
  cursor: pointer;
  text-align: center;
  margin: 2rem;
  font-size: ${({ theme }) => theme.fontSizes};
`;
