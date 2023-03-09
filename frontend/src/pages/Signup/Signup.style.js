import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  /* width: 100vw; */
  /* height: 100vh; */
  justify-content: center;
  /* overflow: auto; */
  /* background-color: #def1ff; */
  padding-top: 3rem;
  /* animation: smoothAppear;
  animation-duration: ${props => (props.duration ? props.duration : '0.5s')}; */
`;
export const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  /* overflow: auto; */
  min-width: 40rem;
  background-color: white;
  /* box-shadow: rgba(99, 99, 99, 0.5) 0px 2px 8px 0px; */
  padding: 0 8rem;
  /* padding-top: 3rem; */
  margin-top: 1rem;
  margin-bottom: 1rem;
  border-radius: 10px;
`;
export const Title = styled.div`
  font-weight: bold;
  font-size: 2.5rem;
  padding-bottom: 2rem;
  font-family: 'Pretendard-Medium';
  display: flex;
  justify-content: center;
`;
export const Logo = styled.img`
  width: 10rem;
  height: fit-content;
`;
export const Desc = styled.div`
  font-size: 2.5rem;
  margin: 3rem 0;
  font-weight: 600;
`;
export const InputBlock = styled.div`
  display: flex;
`;
export const Wrapper = styled.div`
  display: flex;
  margin-top: ${props => (props.mt ? props.mt : '1rem')};
  margin-bottom: ${props => (props.mb ? props.mb : '')};
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
    align-items: center;
    /* width: rem; */
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
