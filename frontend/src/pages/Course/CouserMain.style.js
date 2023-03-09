/* eslint-disable */
import styled from 'styled-components';

export const Container = styled.div`
//   display: flex;
//   /* width: 100vw; */
//   /* height: 100vh; */
//   justify-content: center;
//   /* overflow: auto; */
//   /* background-color: #def1ff; */
//   /* padding: 3rem 0; */
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