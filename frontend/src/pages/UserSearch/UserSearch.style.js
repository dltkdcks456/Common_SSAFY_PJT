/* eslint-disable */
import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  /* width: 100vw; */
  /* height: 100vh; */
  /* justify-content: center; */
  align-items: center;
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

export const Wrapper = styled.div`
  display: flex;
  margin-top: ${props => (props.mt ? props.mt : '1rem')};
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  justify-content: ${props => (props.jc ? props.jc : '')};
  flex-direction: ${props => (props.dir ? props.dir : '')};
  width: ${props => (props.width ? props.width : '')};
  .desc {
    width: ${props => (props.small ? '4rem' : '8rem')};
    height: 2rem;
    display: flex;
    align-items: center;
    font-family: 'Pretendard-Regular';
  }
  .body {
    font-family: 'Pretendard-Medium';
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 5rem;
    /* cursor: pointer; */
    margin-top: 0.2rem;
    margin-left: 2rem;
    margin-right: 2rem;
    padding: 0.5rem;
    border-radius: 5px;
    box-shadow: rgba(99, 99, 99, 0.5) 0px 1px 2px 0px;
  }
  .content {
    font-family: 'Pretendard-Medium';
    height: 5rem;
    display: flex;
    justify-content: center;
    align-items: center;
    .text {
      font-size: 1.2rem;
    }
  }
`;

export const Image = styled.img`
  width: 4rem;
  height: 4rem;
  border-radius: 50%;
  margin-right: 2rem;
  /* margin-bottom: 1rem; */
`;

export const SearchInput = styled.div`
  /* background: #def1ff; */
  /* position: relative; */
  width: 20rem;
  height: 47px;
  padding-top: 10px;
  padding-right: 10px;
  padding-bottom: 10px;
  padding-left: 10px;
  border: none;
  resize: none;
  outline: none;
  border: 1px solid #ccc;
  color: #888;
  border: solid 0.5px;
  border-radius: 10px;
  /* border-top: none; */
  /* border-bottom-right-radius: 5px;
  border-bottom-left-radius: 5px; */
  overflow: hidden;
`;

export const SearchForm = styled.form`
  /* display: grid;
  grid-template-columns: 2fr 1fr; */
  display: flex;
  align-items: center;
  /* padding-top: 0.5rem; */
  /* border: 1px solid #c4c4c4; */
  width: 100%;
  height: 100%;
  font-family: 'Pretendard-Regular';

  input {
    border: 0px solid;
    background-color: #ffffff;
    height: 2rem;
    /* margin-left: 0.2rem; */
    padding-right: 1rem;
    margin-right: 1rem;
    width: 100%;
  }

  button {
    width: 4rem;
    margin-left: auto;
    /* margin-right: 0.2rem; */
  }
`;
