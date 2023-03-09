/* eslint-disable */

import React, { useEffect, useState } from 'react';
import { Outlet, useNavigate, useParams } from 'react-router';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import NowContainer from '../../components/commons/nowLocation';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';

const Container = styled.div`
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

const Free = () => {
  const navigate = useNavigate();
  const { boardCode } = useParams();
  const [value, setValue] = useState('1');

  // const handleChange = (event: React.SyntheticEvent, newValue: string) => {
  //   setValue(newValue);
  // };
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const onClick1 = () => {
    navigate('100');
  };
  const onClick2 = () => {
    navigate('200');
  };

  const onClick3 = () => {
    navigate('300');
  };
  const onClick4 = () => {
    navigate('400');
  };

  useEffect(() => {
    switch (boardCode) {
      case '100':
        setValue('1');
        break;
      case '200':
        setValue('2');
        break;
      case '300':
        setValue('3');
        break;
      default:
        setValue('4');
        break;
    }
  });

  return (
    <div>
      <NowContainer desc="커 뮤 니 티" />
      <Container>
        <Box sx={{ width: '100%', typography: 'body1' }}>
          <TabContext value={value}>
            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
              <TabList
                sx={{ marginLeft: '10%' }}
                onChange={handleChange}
                aria-label="lab API tabs example"
              >
                <Tab label="자유게시판" onClick={onClick1} value="1" />
                <Tab label="질문게시판" onClick={onClick2} value="2" />
                <Tab label="인증게시판" onClick={onClick3} value="3" />
                <Tab label="정보게시판" onClick={onClick4} value="4" />
              </TabList>
            </Box>
          </TabContext>
        </Box>
        <Outlet />
      </Container>
    </div>
  );
};

export default Free;
