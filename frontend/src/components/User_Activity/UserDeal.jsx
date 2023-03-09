/* eslint-disable */
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router';
import { getSalesList } from '../../store/apis/myPageApi';
import { localDateTime } from '../../utils/DateFormatter';
import { BASE_URL } from '../../utils/urls';
import Button from '../commons/button';
import {
  Container,
  DealItem,
  DealList,
  ItemTitle,
  ItemEnd,
  Title,
} from './UserDeal.style';

const UserDeal = ({ user }) => {
  const [dealList, setDealList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const me = useSelector(state => state.myPage.user);
  const navigate = useNavigate();

  const [onSaleList, setOnSaleList] = useState([]);
  const [doneSaleList, setDoneSaleList] = useState([]);

  const getDeal = () => {
    setOnSaleList([]);
    setDoneSaleList([]);
    getSalesList({ nickname: user.nickname })
      .then(res => {
        setDealList(res.data);
        for (const deal of res.data) {
          if (deal.deal_on_sale) {
            setOnSaleList(prev => [...prev, deal]);
          } else {
            setDoneSaleList(prev => [...prev, deal]);
          }
        }
        setIsLoading(true);
      })
      .catch(err => console.log(err));
  };

  useEffect(() => {
    getDeal();
  }, []);

  return (
    <>
      <Container>
        <div>
          <Title>판매중인 물품 ({onSaleList.length})</Title>
        </div>
        <DealList>
          {onSaleList.length > 0 ? (
            onSaleList.map(deal => (
              <DealItem key={deal.deal_id}>
                <ItemTitle
                  onClick={e => {
                    e.preventDefault();
                    navigate('/shop/detail', {
                      state: {
                        dealId: deal.deal_id,
                      },
                    });
                  }}
                >
                  <div className="kind">
                    {deal.deal_kind} > {deal.deal_name}
                  </div>
                  <div className="title">{deal.deal_title}</div>
                  <div className="number">{deal.deal_price}원</div>
                </ItemTitle>
                <ItemEnd>
                  {me && me.id === user.id ? (
                    <Button
                      name="삭제"
                      width="4rem"
                      bc="white"
                      font="Pretendard-Regular"
                      onClick={async e => {
                        e.preventDefault();
                        await axios
                          .delete(`${BASE_URL}deal/${deal.deal_id}`, {
                            headers: { userId: user.user_id },
                          })
                          .then(() => getDeal());
                      }}
                    />
                  ) : (
                    <div></div>
                  )}
                  <div>{localDateTime(deal.deal_time)}</div>
                </ItemEnd>
              </DealItem>
            ))
          ) : (
            <div style={{ marginLeft: '1rem', height: '5rem' }}>
              판매중인 물품이 없습니다
            </div>
          )}
        </DealList>
      </Container>
      <br />
      <Container>
        <div>
          <Title>판매완료한 물품 ({doneSaleList.length})</Title>
        </div>
        <DealList>
          {doneSaleList.length > 0 ? (
            doneSaleList.map(deal => (
              <DealItem key={deal.deal_id}>
                <ItemTitle
                  onClick={e => {
                    e.preventDefault();
                    navigate('/shop/detail', {
                      state: {
                        dealId: deal.deal_id,
                      },
                    });
                  }}
                >
                  <div className="kind">
                    {deal.deal_kind} > {deal.deal_name}
                  </div>
                  <div className="title">{deal.deal_title}</div>
                  <div className="number">{deal.deal_price}원</div>
                </ItemTitle>
                <ItemEnd>
                  <div></div>
                  {/* <Button
                    name="삭제"
                    width="4rem"
                    bc="white"
                    font="Pretendard-Regular"
                    onClick={async e => {
                      e.preventDefault();
                      await axios
                        .delete(`${BASE_URL}deal/${deal.deal_id}`, {
                          headers: { userId: user.user_id },
                        })
                        .then(() => getDeal());
                    }}
                  /> */}
                  <div>{localDateTime(deal.deal_time)}</div>
                </ItemEnd>
              </DealItem>
            ))
          ) : (
            <div style={{ marginLeft: '1rem', height: '5rem' }}>
              판매 완료한 물품이 없습니다
            </div>
          )}
        </DealList>
      </Container>
    </>
  );
};

export default UserDeal;
