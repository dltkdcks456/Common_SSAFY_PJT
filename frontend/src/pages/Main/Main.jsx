/* eslint-disable */
import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import axios from 'axios';
import { BASE_URL } from '../../utils/urls';
import Carousel from 'react-material-ui-carousel';
import { Paper, Button } from '@mui/material';
import MainArticleListItem from './MainArticleListItem';

import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { CardActionArea } from '@mui/material';
import CheckboxListSecondary from '../../components/Main/Rank';
import { useNavigate } from 'react-router';

import Chip from '@mui/material/Chip';
import Stack from '@mui/material/Stack';
import LoadingSpin from 'react-loading-spin';

const SuperContainer = styled.div`
  display: flex;
  justify-content: center;
`;

const MainTotalContainer = styled.div`
  height: 100vh;
  width: 75vw;
  font-family: 'Pretendard-Regular';
`;

const CardContentContainer = styled.div`
  display: flex;
  /* width: 500px;
  height: 300px; */
  /* margin: 15px; */
  margin-bottom: 10px;
  margin-top: 10px;
  margin-left: 3px;
`;

const RankContainer = styled.div`
  display: flex;
  justify-content: center;
  margin: 15px;
`;

const Container = styled.div`
  border-radius: 5px;
  border: solid 1px grey;
  padding: 15px;
  margin: 15px;
  -webkit-box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19),
    0 6px 6px rgba(0, 0, 0, 0.23);
  -moz-box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19),
    0 6px 6px rgba(0, 0, 0, 0.23);
  -ms-box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  -o-box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
`;

const BoardContainer = styled.div`
  display: flex;
  justify-content: center;
`;

const MainStyledTable = styled.table`
  width: 550px;
  height: 250px;
`;

const MainStyledTh = styled.th`
  background-color: #def1ff;
  height: 1.5rem;
  vertical-align: middle;
  text-align: center;
  font-weight: 550;
  border-radius: 3px;
  font-size: 13px;
  color: black;
`;

const Main = () => {
  const navigate = useNavigate();
  const page = 0;
  const [freeBoards, setFreeBoards] = useState([]);
  const [QNABoards, setQNABoards] = useState([]);
  const [Shops, setShops] = useState([]);
  const [ThumbNail, setThumbNail] = useState([]);
  const [DistRanks, setDistRanks] = useState([]);
  const [TimeRanks, setTimeRanks] = useState([]);
  const [CalRanks, setCalRanks] = useState([]);
  const [Loading, setLoading] = useState(false);

  useEffect(() => {
    if (Shops.length === 0) {
      axios.get(`${BASE_URL}deal/`, { params: { page } }).then(res => {
        setShops(res.data[0]);
        setThumbNail(res.data[1]);
      });
    }
    if (freeBoards.length === 0) {
      axios.get(`${BASE_URL}board/100?page=${page}`).then(res => {
        setFreeBoards(res.data.content);
      });
    }

    if (QNABoards.length === 0) {
      axios
        .get(`${BASE_URL}board/200?page=${page}`)
        .then(res => setQNABoards(res.data.content));
    }

    if (
      Shops.length === 0 &&
      freeBoards.length === 0 &&
      QNABoards.length === 0 &&
      DistRanks.length === 0 &&
      TimeRanks.length === 0 &&
      CalRanks.length === 0
    ) {
      setLoading(true);
    }
  });

  useEffect(() => {
    if (DistRanks.length === 0) {
      axios
        .get(`${BASE_URL}recode/getBestTotalDist`)
        .then(res => setDistRanks(res.data));
    }

    if (TimeRanks.length === 0) {
      axios
        .get(`${BASE_URL}recode/getBestTotalTime`)
        .then(res => setTimeRanks(res.data));
    }

    if (CalRanks.length === 0) {
      axios
        .get(`${BASE_URL}recode/getBestTotalCal`)
        .then(res => setCalRanks(res.data));
    }
  });

  // console.log(freeBoards.length);
  // console.log(Shops);
  const shopRenderingFirst = () => {
    const resultFirst = [];

    for (let i = 0; i < 3; i++) {
      resultFirst.push(
        <Card
          sx={{
            display: 'flex',
            width: '410px',
            height: '210px',
            margin: '10px',
            overflow: 'hidden',
          }}
          key={i}
        >
          <CardActionArea
            onClick={() => {
              navigate('/shop/detail', {
                state: { dealId: Shops.content[i].dealId },
              });
            }}
          >
            <CardContentContainer>
              <img
                src={ThumbNail[Shops.content[i].dealId]}
                alt="이미지가 존재하지 않습니다"
                width="190px"
              />
              <CardContent>
                <div style={{ marginLeft: '5px', height: '100px' }}>
                  <div
                    style={{
                      fontFamily: 'Pretendard-Regular',
                      textOverflow: 'ellipsis',
                      overflow: 'hidden',
                      whiteSpace: 'nowrap',
                      fontWeight: 'bold',
                      fontSize: '17px',
                      width: '195px',
                    }}
                  >
                    {Shops.content[i].title}
                  </div>
                  <Typography
                    variant="body2"
                    color="text.secondary"
                    sx={{
                      fontFamily: 'Pretendard-Regular',
                      textOverflow: 'ellipsis',
                      overflow: 'hidden',
                      whiteSpace: 'nowrap',
                      fontSize: '18px',
                      fontWeight: 'bold',
                    }}
                  >
                    {Shops.content[i].price
                      .toString()
                      .replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
                    원
                  </Typography>
                  <Typography
                    variant="body2"
                    color="text.secondary"
                    sx={{
                      fontFamily: 'Pretendard-Regular',
                      textOverflow: 'ellipsis',
                      overflow: 'hidden',
                      whiteSpace: 'nowrap',
                    }}
                  >
                    {Shops.content[i].userNickname}
                  </Typography>
                </div>
                <Stack
                  direction="row"
                  spacing={1}
                  style={{ marginTop: '15px' }}
                >
                  <Chip label={Shops.content[i].kind} variant="outlined" />
                </Stack>
              </CardContent>
            </CardContentContainer>
          </CardActionArea>
        </Card>,
      );
    }
    return resultFirst;
  };

  const shopRenderingSecond = () => {
    const resultFirst = [];

    for (let i = 3; i < 6; i++) {
      resultFirst.push(
        <Card
          sx={{
            display: 'flex',
            width: '410px',
            height: '210px',
            margin: '10px',
            overflow: 'hidden',
          }}
          key={i}
        >
          <CardActionArea
            onClick={() => {
              navigate('/shop/detail', {
                state: { dealId: Shops.content[i].dealId },
              });
            }}
          >
            <CardContentContainer>
              <img
                src={ThumbNail[Shops.content[i].dealId]}
                alt="이미지가 존재하지 않습니다"
                width="190px"
              />
              <CardContent>
                <div style={{ marginLeft: '5px', height: '100px' }}>
                  <div
                    style={{
                      fontFamily: 'Pretendard-Regular',
                      textOverflow: 'ellipsis',
                      overflow: 'hidden',
                      whiteSpace: 'nowrap',
                      fontWeight: 'bold',
                      fontSize: '17px',
                      width: '195px',
                    }}
                  >
                    {Shops.content[i].title}
                  </div>
                  <Typography
                    variant="body2"
                    color="text.secondary"
                    sx={{
                      fontFamily: 'Pretendard-Regular',
                      textOverflow: 'ellipsis',
                      overflow: 'hidden',
                      whiteSpace: 'nowrap',
                      fontSize: '18px',
                      fontWeight: 'bold',
                    }}
                  >
                    {Shops.content[i].price
                      .toString()
                      .replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
                    원
                  </Typography>
                  <Typography
                    variant="body2"
                    color="text.secondary"
                    sx={{
                      fontFamily: 'Pretendard-Regular',
                      textOverflow: 'ellipsis',
                      overflow: 'hidden',
                      whiteSpace: 'nowrap',
                    }}
                  >
                    {Shops.content[i].userNickname}
                  </Typography>
                </div>
                <Stack
                  direction="row"
                  spacing={1}
                  style={{ marginTop: '15px' }}
                >
                  <Chip label={Shops.content[i].kind} variant="outlined" />
                </Stack>
              </CardContent>
            </CardContentContainer>
          </CardActionArea>
        </Card>,
      );
    }
    return resultFirst;
  };

  const shopRenderingThird = () => {
    const resultFirst = [];

    for (let i = 6; i < 9; i++) {
      resultFirst.push(
        <Card
          sx={{
            display: 'flex',
            width: '410px',
            height: '210px',
            margin: '10px',
            overflow: 'hidden',
          }}
          key={i}
        >
          <CardActionArea
            onClick={() => {
              navigate('/shop/detail', {
                state: { dealId: Shops.content[i].dealId },
              });
            }}
          >
            <CardContentContainer>
              <img
                src={ThumbNail[Shops.content[i].dealId]}
                alt="이미지가 존재하지 않습니다"
                width="190px"
              />
              <CardContent>
                <div style={{ marginLeft: '5px', height: '100px' }}>
                  <div
                    style={{
                      fontFamily: 'Pretendard-Regular',
                      textOverflow: 'ellipsis',
                      overflow: 'hidden',
                      whiteSpace: 'nowrap',
                      fontWeight: 'bold',
                      fontSize: '17px',
                      width: '195px',
                    }}
                  >
                    {Shops.content[i].title}
                  </div>
                  <Typography
                    variant="body2"
                    color="text.secondary"
                    sx={{
                      fontFamily: 'Pretendard-Regular',
                      textOverflow: 'ellipsis',
                      overflow: 'hidden',
                      whiteSpace: 'nowrap',
                      fontSize: '18px',
                      fontWeight: 'bold',
                    }}
                  >
                    {Shops.content[i].price
                      .toString()
                      .replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
                    원
                  </Typography>
                  <Typography
                    variant="body2"
                    color="text.secondary"
                    sx={{
                      fontFamily: 'Pretendard-Regular',
                      textOverflow: 'ellipsis',
                      overflow: 'hidden',
                      whiteSpace: 'nowrap',
                    }}
                  >
                    {Shops.content[i].userNickname}
                  </Typography>
                </div>
                <Stack
                  direction="row"
                  spacing={1}
                  style={{ marginTop: '15px' }}
                >
                  <Chip label={Shops.content[i].kind} variant="outlined" />
                </Stack>
              </CardContent>
            </CardContentContainer>
          </CardActionArea>
        </Card>,
      );
    }
    return resultFirst;
  };

  return (
    <div>
      {Loading ? (
        <SuperContainer>
          <MainTotalContainer>
            <RankContainer>
              {DistRanks.length > 0 && (
                <CheckboxListSecondary Data={DistRanks} Code={1} />
              )}
              {TimeRanks.length > 0 && (
                <CheckboxListSecondary Data={TimeRanks} Code={2} />
              )}
              {CalRanks.length > 0 && (
                <CheckboxListSecondary Data={CalRanks} Code={3} />
              )}
            </RankContainer>
            <hr></hr>
            {Shops.content && (
              <Carousel style={{ margin: '15px' }}>
                <Paper
                  style={{
                    display: 'flex',
                    height: '250px',
                    justifyContent: 'center',
                    verticalAlign: 'center',
                  }}
                >
                  {shopRenderingFirst()}{' '}
                </Paper>
                <Paper
                  style={{
                    display: 'flex',
                    height: '250px',
                    justifyContent: 'center',
                    verticalAlign: 'center',
                  }}
                >
                  {shopRenderingSecond()}{' '}
                </Paper>
                <Paper
                  style={{
                    display: 'flex',
                    height: '250px',
                    justifyContent: 'center',
                    verticalAlign: 'center',
                  }}
                >
                  {shopRenderingThird()}{' '}
                </Paper>
              </Carousel>
            )}
            {freeBoards.length > 0 && QNABoards.length > 0 ? (
              <BoardContainer>
                <Container>
                  <div style={{ marginBottom: '4px' }}>자유게시판</div>
                  {freeBoards.length > 0 && (
                    <MainStyledTable>
                      <thead>
                        <tr>
                          <MainStyledTh style={{ width: '1px' }}>
                            번호
                          </MainStyledTh>
                          <MainStyledTh
                            style={{
                              textOverflow: 'ellipsis',
                              overflow: 'hidden',
                              whiteSpace: 'nowrap',
                              width: '200px',
                            }}
                          >
                            제목
                          </MainStyledTh>
                          <MainStyledTh style={{ width: '80px' }}>
                            작성일
                          </MainStyledTh>
                          <MainStyledTh
                            style={{
                              width: '90px',
                              textOverflow: 'ellipsis',
                              overflow: 'hidden',
                              whiteSpace: 'nowrap',
                            }}
                          >
                            작성자
                          </MainStyledTh>
                          <MainStyledTh style={{ width: '40px' }}>
                            조회수
                          </MainStyledTh>
                        </tr>
                      </thead>
                      <tbody>
                        {freeBoards.map(board => (
                          <MainArticleListItem
                            key={board.boardId}
                            board={board}
                            boardCode="100"
                          />
                        ))}
                      </tbody>
                    </MainStyledTable>
                  )}
                </Container>
                <Container>
                  <div style={{ marginBottom: '4px' }}>질문게시판</div>
                  {QNABoards.length > 0 && (
                    <MainStyledTable>
                      <thead>
                        <tr>
                          <MainStyledTh style={{ width: '1px' }}>
                            번호
                          </MainStyledTh>
                          <MainStyledTh>제목</MainStyledTh>
                          <MainStyledTh style={{ width: '80px' }}>
                            작성일
                          </MainStyledTh>
                          <MainStyledTh
                            style={{
                              width: '90px',
                              textOverflow: 'ellipsis',
                              overflow: 'hidden',
                              whiteSpace: 'nowrap',
                            }}
                          >
                            작성자
                          </MainStyledTh>
                          <MainStyledTh style={{ width: '40px' }}>
                            조회수
                          </MainStyledTh>
                        </tr>
                      </thead>
                      <tbody>
                        {QNABoards.map(board => (
                          <MainArticleListItem
                            key={board.boardId}
                            board={board}
                            boardCode="200"
                          />
                        ))}
                      </tbody>
                    </MainStyledTable>
                  )}
                </Container>
              </BoardContainer>
            ) : null}
          </MainTotalContainer>
        </SuperContainer>
      ) : (
        <div
          style={{
            display: 'flex',
            marginTop: '48vh',
            justifyContent: 'center',
            alignItems: 'center',
          }}
        >
          <LoadingSpin size="50px" primaryColor="#def1ff" />
        </div>
      )}
    </div>
  );
};

export default Main;
