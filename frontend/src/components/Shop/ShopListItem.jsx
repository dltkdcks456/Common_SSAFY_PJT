/* eslint-disable */
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import Chip from '@mui/material/Chip';
import Stack from '@mui/material/Stack';
import eyeImg from '../../assets/images/eye.png';
import { grey } from '@mui/material/colors';

// import Button from '../commons/button';
// import { MainContainer } from './ShopListItem.style';

const StyledNameVisited = styled.div`
  color: grey;
  float: right;
  font-size: 13px;
  font-weight: bold;
  margin-top: 80px;
`;

export const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  /* min-width: 19rem;
  max-width: 19rem; */
  width: 25rem;
  height: 15rem;
  border-radius: 10px;
  background-color: white;
  box-shadow: rgba(99, 99, 99, 0.5) 1px 1px 4px 0px;
  padding: 0 0rem;
  padding: 0rem 0rem;
  margin: 0.7rem;
  cursor: pointer;

  :hover {
    transition: all 0.2s linear;
    transform: scale(1.05);
    background-color: #f1f0f0;
  }
`;

const ShopListItem = ({ shop, thumbNail }) => {
  // console.log(thumbNail);
  const navigate = useNavigate();
  // console.log(shop);
  return (
    <MainContainer
      onClick={() => {
        navigate('/shop/detail', {
          state: { dealId: shop.dealId },
        });
      }}
    >
      {shop.content && (
        <div>
          <div
            style={{
              display: 'flex',
              marginTop: '0.2rem',
              padding: '1rem',
            }}
          >
            {thumbNail ? (
              <img
                style={{
                  height: '12rem',
                  width: '14rem',
                  // margin: '1.3rem 0.2rem 0.1rem 0.3rem  ',
                  borderRadius: '1rem',
                  backgroundSize: 'cover',
                  backgroundPosition: 'center',
                }}
                src={thumbNail}
                alt="페이지를 표시할 수 없습니다."
              />
            ) : (
              <img
                style={{
                  height: '12rem',
                  width: '14rem',
                  // margin: '1.3rem 0.2rem 0.1rem 0.3rem  ',
                  borderRadius: '1rem',
                  backgroundSize: 'cover',
                  backgroundPosition: 'center',
                }}
                src="images/bike.png"
                alt="페이지를 표시할 수 없습니다."
              />
            )}
            <div style={{ marginLeft: '0.2rem', margintTop: '1.3rem' }}>
              <div style={{ marginLeft: '5px', maxWidth: '145px' }}>
                <div
                  style={{
                    width: '8.5rem',
                    fontSize: '1.3rem',
                    whiteSpace: 'nowrap',
                    textOverflow: 'ellipsis',
                    overflow: 'hidden',
                  }}
                >
                  <b>{shop.title}</b>
                  <div
                    style={{
                      marginTop: '2px',
                      width: '8.5rem',
                      fontSize: '1rem',
                      whiteSpace: 'nowrap',
                      textOverflow: 'ellipsis',
                      overflow: 'hidden',
                      color: '#928f8f',
                      fontWeight: 'bold',
                    }}
                  >
                    {shop.kind}
                  </div>
                </div>

                <div
                  style={{
                    marginTop: '3px',
                    width: '8rem',
                    fontSize: '1rem',
                    textOverflow: 'ellipsis',
                    overflow: 'hidden',
                    whiteSpace: 'nowrap',
                  }}
                >
                  <span>{shop.name}</span>
                </div>
                <div
                  style={{
                    fontSize: '1.4rem',
                    marginTop: '0.8rem',
                    marginBottom: '0.1rem',
                    textOverflow: 'ellipsis',
                    overflow: 'hidden',
                    whiteSpace: 'nowrap',
                    maxWidth: '50pxs',
                    fontWeight: 'bold',
                  }}
                >
                  {shop.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') +
                    ' 원'}
                </div>
                <StyledNameVisited>
                  <span>조회수 {shop.visited}</span>
                </StyledNameVisited>
              </div>
            </div>
          </div>
        </div>
      )}
    </MainContainer>
  );
};
export default ShopListItem;
