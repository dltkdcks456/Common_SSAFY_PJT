/* eslint-disable */
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';
import { getZzimList } from '../../store/apis/myPageApi';
import { localDateTime } from '../../utils/DateFormatter';
import { BASE_URL } from '../../utils/urls';
import Button from '../commons/button';
import {
  Container,
  ItemEnd,
  ItemTitle,
  ZzimItem,
  ZzimList,
} from './UserZzim.style';

const UserZzim = ({ user }) => {
  const token = sessionStorage.getItem('userToken');
  const [zzimList, setZzimList] = useState([]);
  const navigate = useNavigate();

  const getZzim = () => {
    getZzimList({ token })
      .then(res => setZzimList(res.data))
      .catch(err => console.log(err));
  };

  useEffect(() => {
    getZzim();
  }, []);

  // console.log('찜 목록', zzimList);
  return (
    <>
      <Container>
        <ZzimList>
          {zzimList.length > 0 ? (
            zzimList.map(zzim => (
              <ZzimItem key={zzim.zzim_deal_id}>
                <ItemTitle
                  onClick={e => {
                    e.preventDefault();
                    navigate('/shop/detail', {
                      state: {
                        dealId: zzim.zzim_deal_id,
                      },
                    });
                  }}
                >
                  <div className="kind">
                    {zzim.zzim_deal_kind} > {zzim.zzim_deal_name}
                  </div>
                  <div className="title">{zzim.zzim_deal_title}</div>
                  <div className="number">{zzim.zzim_deal_price}원</div>
                </ItemTitle>
                <ItemEnd>
                  <Button
                    name="찜 취소"
                    width="4rem"
                    bc="white"
                    font="Pretendard-Regular"
                    onClick={async e => {
                      e.preventDefault();
                      await axios
                        .delete(`${BASE_URL}deal/zzim/${zzim.zzim_deal_id}`, {
                          headers: { userId: user.user_id },
                        })
                        .then(() => getZzim());
                    }}
                  />
                  <div>{localDateTime(zzim.zzim_deal_time)}</div>
                </ItemEnd>
              </ZzimItem>
            ))
          ) : (
            <div style={{textAlign: 'center'}}>마음에 드는 물품을 찾아 찜을 해보세요!</div>
          )}
        </ZzimList>
      </Container>
    </>
  );
};

export default UserZzim;
