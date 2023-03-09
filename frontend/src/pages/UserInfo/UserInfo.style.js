/* eslint-disable */
import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  /* width: 100vw; */
  /* height: 100vh; */
  justify-content: center;
  overflow: auto;
  /* background-color: #def1ff; */
  /* padding: 3rem 0; */
  animation: smoothAppear;
  animation-duration: ${props => (props.duration ? props.duration : '0.5s')};

  @keyframes smoothAppear {
    from {
      opacity: 0;
      transform: translateY(3rem);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }
`;
export const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: auto;
  min-width: 40rem;
  background-color: white;
  /* box-shadow: rgba(99, 99, 99, 0.5) 0px 2px 8px 0px; */
  padding: 3rem 8rem;
  /* padding-top: 3rem; */
`;
export const Logo = styled.img`
  width: 7rem;
  height: fit-content;
`;
export const Desc = styled.div`
  font-size: 2.5rem;
  margin: 3rem 0;
  font-weight: 600;
`;
export const InputBlock = styled.div`
  display: flex;
  justify-content: center;
`;
export const Wrapper = styled.div`
  display: flex;
  margin-top: ${props => (props.mt ? props.mt : '1rem')};
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  justify-content: ${props => (props.jc ? props.jc : '')};
  .desc {
    width: ${props => (props.small ? '4rem' : '8rem')};
    height: 2rem;
    display: flex;
    align-items: center;
    font-family: 'Pretendard-Regular';
    .star {
      padding-top: 0.5rem;
      margin-right: 0.5rem;
      align-items: center;
      color: red;
      font-size: 1.5rem;
    }
  }
  .content {
    height: 2rem;
    display: flex;
    align-items: center;
    cursor: pointer;
    .unChecked {
      font-size: 1.7rem;
      color: #e2e2e2;
      margin-right: 0.5rem;
    }
    .checked {
      font-size: 1.7rem;
      color: #fec25c;
      margin-right: 0.5rem;
    }
    .text {
      color: #666666;
    }
  }
  .btnInput {
    display: flex;
    align-items: flex-end;
    /* width: rem; */
    div {
      display: flex;
      /* padding-bottom: 1.5rem; */
    }
  }
`;
export const SelectBox = styled.select`
  width: ${props => (props.width ? props.width : '')};
  margin-right: ${props => (props.mr ? props.mr : '')};
  height: 2.3rem;
  border-radius: 5px;
  text-align: center;
  font-size: 0.7rem;
  margin-right: 0.1rem;
  font-family: 'Pretendard-Regular';
`;

export const Image = styled.img`
  width: 10rem;
  height: 10rem;
  border-radius: 45px;
  /* margin-bottom: 1rem; */
`;

export const MenuWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const DropDownContent = styled.div`
  display: none;
  position: absolute;
  color: ${({ theme }) => theme.textColor};
  background-color: ${({ theme }) => theme.bgColor};
  /* min-width: 10rem; */
  width: 8rem;
  box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
  border-radius: 10px;
  font-family: 'Pretendard-Regular';
  text-align: center;
  /* z-index: 1; */
`;

export const DropDown = styled.div`
  position: relative;
  display: inline-block;
  &:hover ${DropDownContent} {
    display: block;
  }
`;

export const DropDownItem = styled.div`
  cursor: pointer;
  text-align: center;
  margin: 1rem;
  font-size: ${({ theme }) => theme.fontSizes};
`;
