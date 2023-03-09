/* eslint-disable */
import styled from 'styled-components';

const StyledInput = styled.input`
  width: ${props => (props.width ? props.width : '12rem')};
  min-width: ${props => (props.minWidth ? props.minWidth : '')};
  height: ${props => (props.height ? props.height : '2rem')};
  border-radius: ${props => (props.br ? props.br : '5px')};
  border: 1px solid #a3a3a3;
  padding-left: 1rem;
  font-size: 1rem;
  margin-top: ${props => (props.mt ? props.mt : '')};
  margin-left: ${props => (props.ml ? props.ml : '')};
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  margin-right: ${props => (props.mr ? props.mr : '')};
  font-family: 'Pretendard-Regular';
`;

const Input = props => (
  <StyledInput {...props} placeholder={props.placeholder} />
);

export default Input;
