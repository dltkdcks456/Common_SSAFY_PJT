/* eslint-disable */
import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import Card from '@mui/material/Card';
import Avatar from '@mui/material/Avatar';
import passion from '../../assets/images/passion.png';
import medal1 from '../../assets/images/medal1.png';
import medal2 from '../../assets/images/medal2.png';
import medal3 from '../../assets/images/medal3.png';
import { useNavigate } from 'react-router';
import { BASE_URL } from '../../utils/urls';

const ListItemText = styled.div`
  font-weight: bold;
  margin-right: 15px;
`;

const StyledImg = styled.img`
  width: 20px;
`;

const Icon = styled.img`
  width: 3rem;
  height: 3rem;
  border-radius: 50%;
  cursor: pointer;
`;

const Title = styled.span`
  font-size: 20px;
  font-weight: bold;
`;

export default function CheckboxListSecondary({ Data, Code }) {
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [data, setData] = useState([]);
  // console.log(Code, Data);
  useEffect(() => {
    if (Code === 1) {
      setTitle('이번주 누적 거리 🚲');
      for (let i = 0; i < 3; i++) {
        const tempDist = Data[i].total_dist / 1000;
        const Dist =
          tempDist
            .toFixed(1)
            .toString()
            .replace(/\B(?=(\d{3})+(?!\d))/g, ',') + 'Km';
        setData(prev => [...prev, Dist]);
      }
    } else if (Code === 2) {
      setTitle('최장 라이딩 시간 ⏱');
      for (let i = 0; i < 3; i++) {
        const Hour = parseInt(Data[i].total_time / 60);
        const Min = Data[i].total_time % 60;
        const Time =
          Hour.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') +
          '분' +
          Min.toString() +
          '초';
        setData(prev => [...prev, Time]);
      }
    } else {
      setTitle('최고 칼로리양 🔥');
      for (let i = 0; i < 3; i++) {
        const kcal = Data[i].total_cal;
        const Kcal =
          kcal.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + 'Kcal';
        setData(prev => [...prev, Kcal]);
      }
    }
  }, []);
  return (
    <Card
      sx={{
        padding: '15px',
        marginLeft: '15px',
        marginRight: '15px',
      }}
    >
      {title && <Title>{title}</Title>}
      <List
        dense
        sx={{
          width: '250px',
          bgcolor: 'background.paper',
        }}
      >
        <ListItem
          key={0}
          disablePadding
          onClick={() => {
            navigate('/user/userinfo', {
              state: Data[0].nickname,
            });
          }}
        >
          <ListItemButton>
            <ListItemAvatar>
              <Icon
                src={`${BASE_URL}user/imageDownloadBy/${Data[0].profile}`}
              />
            </ListItemAvatar>
            <ListItemText>{Data[0].nickname}</ListItemText>
          </ListItemButton>
          <ListItemText>{data[0]}</ListItemText>
          <StyledImg src={medal1} />
        </ListItem>

        <ListItem
          key={1}
          disablePadding
          onClick={() => {
            navigate('/user/userinfo', {
              state: Data[1].nickname,
            });
          }}
        >
          <ListItemButton>
            <ListItemAvatar>
              <Icon
                src={`${BASE_URL}user/imageDownloadBy/${Data[1].profile}`}
              />
            </ListItemAvatar>
            <ListItemText>{Data[1].nickname}</ListItemText>
          </ListItemButton>
          <ListItemText>{data[1]}</ListItemText>
          <StyledImg src={medal2} />
        </ListItem>

        <ListItem
          key={2}
          disablePadding
          onClick={() => {
            navigate('/user/userinfo', {
              state: Data[2].nickname,
            });
          }}
        >
          <ListItemButton>
            <ListItemAvatar>
              <Icon
                src={`${BASE_URL}user/imageDownloadBy/${Data[2].profile}`}
              />
            </ListItemAvatar>
            <ListItemText>{Data[2].nickname}</ListItemText>
          </ListItemButton>
          <ListItemText>{data[2]}</ListItemText>
          <StyledImg src={medal3} />
        </ListItem>
      </List>
    </Card>
  );
}
