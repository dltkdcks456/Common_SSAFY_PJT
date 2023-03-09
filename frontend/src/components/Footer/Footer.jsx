/* eslint-disable */
import React from 'react';
import styled from 'styled-components';
import logo from '../../assets/images/rideway-low-resolution-logo-black-on-transparent-background.png';
import github from '../../assets/images/github.png';
import help from '../../assets/images/help.png';
import phone from '../../assets/images/phone.png';
import { customAlert, i1500 } from '../../utils/alarm';

const StyledContainer = styled.div`
  font-family: 'Pretendard-Regular';
  font-weight: bold;
  display: flex;
  justify-content: space-between;
  height: 80px;
  padding-left: 25px;
  padding-right: 25px;
  color: #000000;
  position: relative;
  background-color: #def1ff;
  margin-top: 15px;
`;

const StyledImg = styled.img`
  width: 30px;
  margin-left: 6px;
  margin-right: 6px;
`;

const LogoImg = styled.img`
  width: 100px;
`;

const StyledMargin = styled.div`
  margin-left: 10px;
  margin-right: 10px;
  font-size: 12px;
  color: #333333;
`;

const StyledText = styled.div`
  display: flex;
  font-weight: bolder;
  align-items: center;
`;

const StyledAlign = styled.div`
  display: flex;
  align-items: center;
`;

const Footer = () => {
  const onPhone = () => {
    customAlert(i1500, '010-3343-xxxx');
  };

  return (
    <>
      <StyledContainer>
        <StyledAlign>
          <LogoImg src={logo} />
        </StyledAlign>
        <StyledText>
          <StyledMargin>©SSAFY. ALL RIGHTS RESERVED</StyledMargin>
        </StyledText>
        <StyledAlign>
          <a href="https://github.com/dltkdcks456">
            <StyledImg src={github} />
          </a>
          <StyledImg
            style={{ cursor: 'pointer', marginBottom: '3px' }}
            onClick={onPhone}
            src={phone}
          />
        </StyledAlign>
      </StyledContainer>
    </>
  );
};

export default Footer;
