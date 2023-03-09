/* eslint-disable */
import React from 'react';
import styled from 'styled-components';
import Input from './input';
import ValidContainer from './validContainer';

const InputBlock = styled.div`
  display: flex;
  margin-top: 1rem;
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  div {
    font-family: 'Pretendard-Regular';
    /* color: ${({ theme }) => theme.textColor}; */
  }
  .type {
    width: 8rem;
    height: 2rem;
    display: flex;
    align-items: center;
    .star {
      padding-top: 0.5rem;
      margin-right: 0.5rem;
      align-items: center;
      color: red;
      font-size: 1.5rem;
    }
  }
`;

const InputContainer = props => {
  return (
    <InputBlock mb={props.mb}>
      <div className="type">
        {props.star && <div className="star">*</div>}
        {props.desc}
      </div>
      <div>
        <Input
          onChange={props.onChange}
          name={props.name}
          type={props.type ? props.type : 'text'}
          width={props.width}
          value={props.value}
        />
        {props.errMsg && (
          <ValidContainer isValid={props.isValid} errMsg={props.errMsg} />
        )}
      </div>
    </InputBlock>
  );
};

export default InputContainer;
