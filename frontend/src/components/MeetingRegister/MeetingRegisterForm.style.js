import styled from 'styled-components';

export const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: white;
  min-width: 10rem;
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
  font-size: 0.8rem;
  margin-right: 0.1rem;
  font-family: 'Pretendard-Regular';
`;

export const Footer = styled.div`
  text-align: center;
`;
