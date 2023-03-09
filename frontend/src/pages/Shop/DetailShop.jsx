/* eslint-disable */
import { Viewer } from '@toast-ui/react-editor';
import axios from 'axios';
import moment from 'moment';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useLocation, useNavigate } from 'react-router';
import { loadShopDetailAPI } from '../../store/apis/shopApi';
import { MY_PAGE_REQUEST } from '../../store/modules/myPageModule';
import {
  DELETE_SHOP_REQUEST,
  LOAD_SHOP_DETAIL_REQUEST,
} from '../../store/modules/shopModule';
import { BASE_URL } from '../../utils/urls';

import styled from 'styled-components';
import eyeImg from '../../assets/images/eye.png';
import Button from '../../components/commons/button';
import emptyHeartImg from '../../assets/images/emptyheart.png';
import fullHeartImg from '../../assets/images/fullheart.png';
import NowContainer from '../../components/commons/nowLocation';
import { createChatRoomAPI2 } from '../../store/apis/chatApi';
import { openChat } from '../../store/modules/chatModule';

const StyledTitleContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const StyledVisited = styled.div`
  display: flex;
`;

const StyledVisitedBox = styled.div`
  display: flex;
  align-items: flex-end;
`;

const StyledNickNameTime = styled.div`
  display: flex;
  font-size: 13px;
  justify-content: flex-end;
`;

const DetailContainer = styled.div`
  margin-top: 2rem;
  margin-left: 15%;
  margin-right: 15%;
  margin-bottom: 2rem;
  padding: 2rem 2rem;
  box-sizing: border-box;
  overflow: auto;
  border: 1px solid #a0a0a0;
  border-radius: 10px;
  animation: smoothAppear;
  animation-duration: ${props => (props.duration ? props.duration : '0.5s')};
`;

const StyleContent = styled.div`
  width: 100%;

  img {
    min-width: 70%;
    max-width: 97%;
    object-fit: cover;
  }
`;

const StyledBtn = styled.div`
  /* display: flex;
  justify-content: flex-end; */
  margin-right: 0%;
`;

const StyledUpdateDelete = styled.div`
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
`;

const ButtonBox = styled.div`
  display: flex;
  justify-content: center;
`;

const StyledChatBtn = styled.div`
  display: flex;
  justify-content: center;
  /* margin-left: 46%; */
`;

const ButtonBoxChUpDe = styled.div`
  display: flex;
  justify-content: space-between;
`;

const DetailShop = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [detail, setDetail] = useState('');
  const [zzim, setZzim] = useState(false);
  const [onSale, setOnSale] = useState(true);
  const {
    state: { dealId },
  } = useLocation();
  const { ShopDetail } = useSelector(state => state.shop);
  const { loadShopDetailDone } = useSelector(state => state.shop);
  const { logInDone } = useSelector(state => state.user);
  const { myPageDone, user } = useSelector(state => state.myPage);
  const userToken = sessionStorage.getItem('userToken');
  // 찜 조회하기
  const getZzim = async () => {
    const result = await axios.get(`${BASE_URL}deal/zzimCheck`, {
      params: { userId: user.user_id, dealId },
    });
    setZzim(result.data);
  };

  const getDetailShop = async () => {
    const result = await loadShopDetailAPI({ dealId, userId: user.user_id });
    dispatch({
      type: LOAD_SHOP_DETAIL_REQUEST,
      data: {
        dealId,
        userId: user.user_id,
      },
    });
    setDetail(result);
    setOnSale(result.onSale);
  };

  useEffect(() => {
    if (!detail) {
      getDetailShop();
    }
    if (user.user_id) {
      getZzim();
    }
    if (!myPageDone && userToken) {
      dispatch({
        type: MY_PAGE_REQUEST,
        data: {
          token: userToken,
        },
      });
    }
  }, [detail, dispatch, myPageDone, logInDone]);
  const shopDetailTime = moment(detail.time).format('YYYY-MM-DD');

  // 삭제하기 버튼 액션
  const onDelete = async () => {
    dispatch({
      type: DELETE_SHOP_REQUEST,
      data: {
        dealId,
        navigate,
      },
    });
  };

  // 수정하기 버튼 액션
  const onUpdate = async () => {
    try {
      navigate(`/shop/update`, { state: detail });
    } catch (error) {
      console.log(error);
    }
  };
  // 찜 버튼 클릭
  const onZzim = async () => {
    await axios.get(`${BASE_URL}deal/addZzim`, {
      params: { dealId, userId: user.user_id },
    });
    setZzim(prev => !prev);
  };
  // 찜 삭제
  const deleteZzim = async () => {
    await axios.delete(`${BASE_URL}deal/zzim/${dealId}`, {
      headers: { userId: user.user_id },
    });
    setZzim(prev => !prev);
  };

  // 판매 완료
  const onSoldOut = async () => {
    await axios.get(`${BASE_URL}deal/soldOut`, { params: { dealId } });
    setOnSale(prev => !prev);
  };

  const onMeeting = () => {
    const mySessionId = detail.dealId;
    const myUserName = user.nickname;

    navigate('/video', {
      state: {
        mySessionId,
        myUserName,
      },
    });
  };

  const onChatting = e => {
    e.preventDefault();
    createChatRoomAPI2({
      token: userToken,
      name: `${detail.userNickname}${user.nickname}`,
      opposite_nickname: detail.userNickname,
    })
      // .then(res => console.log(res))
      .catch(err => console.log(err));
    setTimeout(() => {
      dispatch(openChat());
    }, 500);
  };

  return (
    <div>
      <NowContainer desc="중 고 장 터" />
      {detail && (
        <div>
          <DetailContainer>
            <StyledTitleContainer>
              <h1>{detail.title}</h1>
              <StyledVisitedBox>
                <StyledVisited>
                  <img
                    src={eyeImg}
                    style={{
                      width: '20px',
                      marginLeft: '5px',
                      marginRight: '5px',
                      height: '20px',
                    }}
                    alt="a"
                  ></img>
                  <div>{detail.visited}</div>
                </StyledVisited>
              </StyledVisitedBox>
            </StyledTitleContainer>
            <hr />
            <StyledNickNameTime>
              <div style={{ marginRight: '0.5rem' }}>
                <b
                  style={{ cursor: 'pointer' }}
                  onClick={() => {
                    navigate('/user/userinfo', {
                      state: detail.userNickname,
                    });
                  }}
                >
                  {detail.userNickname}
                </b>
              </div>
              <div>{shopDetailTime}</div>
            </StyledNickNameTime>
            <br />
            <StyleContent>
              <Viewer initialValue={detail.content} />
            </StyleContent>
            <hr />
            분류 : <b>{detail.kind}</b> <br />
            제품명 : <b>{detail.name}</b> <br />
            가격 :{' '}
            <b>
              {detail.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
            </b>
            원
            <ButtonBox>
              <StyledBtn>
                {zzim ? (
                  <img
                    src={fullHeartImg}
                    style={{ width: '50px', cursor: 'pointer' }}
                    onClick={deleteZzim}
                  ></img>
                ) : (
                  <img
                    src={emptyHeartImg}
                    style={{ width: '50px', cursor: 'pointer' }}
                    onClick={onZzim}
                  ></img>
                )}
              </StyledBtn>
            </ButtonBox>
            <hr />
            <ButtonBoxChUpDe>
              <StyledChatBtn>
                <Button
                  width="5rem"
                  ml="0.1rem"
                  mr="0.1rem"
                  mt="0.2rem"
                  height="2.3rem"
                  bc="white"
                  name="1:1 채팅"
                  onClick={onChatting}
                ></Button>
                <Button
                  width="5rem"
                  ml="0.1rem"
                  mr="0.1rem"
                  mt="0.2rem"
                  height="2.3rem"
                  bc="white"
                  name="화상 채팅"
                  onClick={onMeeting}
                ></Button>
                {detail.userId === user.user_id && (
                  <div>
                    {onSale ? (
                      <Button
                        width="5rem"
                        ml="0.1rem"
                        mr="0.1rem"
                        mt="0.2rem"
                        height="2.3rem"
                        bc="white"
                        name="판매 중"
                        onClick={onSoldOut}
                      ></Button>
                    ) : (
                      <Button
                        disabled
                        width="5rem"
                        ml="0.1rem"
                        mr="0.1rem"
                        mt="0.2rem"
                        height="2.3rem"
                        bc="white"
                        name="판매 완료"
                      ></Button>
                    )}
                  </div>
                )}
              </StyledChatBtn>
              {/* <button onClick={onMeeting}>화상채팅</button> */}
              {user && detail && user.user_id === detail.userId && (
                <StyledUpdateDelete>
                  {user && detail && user.user_id === detail.userId && (
                    <>
                      <Button
                        width="5rem"
                        ml="0.1rem"
                        mr="0.1rem"
                        mt="0.2rem"
                        height="2.3rem"
                        bc="white"
                        name="수정하기"
                        onClick={onUpdate}
                      ></Button>
                      <Button
                        width="5rem"
                        ml="0.1rem"
                        mr="0.1rem"
                        mt="0.2rem"
                        height="2.3rem"
                        bc="white"
                        name="삭제하기"
                        onClick={onDelete}
                      ></Button>
                    </>
                  )}
                  {/* <button onClick={onUpdate}>수정하기</button>
                <button onClick={onDelete}>삭제하기</button> */}
                </StyledUpdateDelete>
              )}
            </ButtonBoxChUpDe>
          </DetailContainer>
        </div>
      )}
    </div>
  );
};

export default DetailShop;
