/* eslint-disable */
import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate, useParams } from 'react-router-dom';
import NowContainer from '../../components/commons/nowLocation';
import ShopListItem from '../../components/Shop/ShopListItem';
import {
  LOAD_SHOP_FILTER_REQUEST,
  LOAD_SHOP_FILTER_SUCCESS,
  LOAD_SHOP_REQUEST,
  SEARCH_SHOP_REQUEST,
} from '../../store/modules/shopModule';
import {
  Container,
  Image,
  InputBlock,
  MainContainer,
  List,
} from './ShopList.style';
import Button from '../../components/commons/button';
import InputContainer from '../../components/commons/inputContainer';
import Input from '../../components/commons/input';
import { width } from '@mui/system';
import {
  loadShopAPI,
  loadShopFilterAPI,
  searchShopAPI,
} from '../../store/apis/shopApi';

const Wrapper = styled.div`
  display: flex;
  margin-top: ${props => (props.mt ? props.mt : '1rem')};
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  flex-direction: ${props => (props.dir ? props.dir : '')};
  width: 80rem;
  justify-content: ${props => (props.jc ? props.jc : 'center')};
  .filter {
    display: flex;
    margin-top: ${props => (props.mt ? props.mt : '1rem')};
    margin-bottom: ${props => (props.mb ? props.mb : '')};
    margin-left: ${props => (props.ml ? props.ml : '')};
    margin-right: ${props => (props.mr ? props.mr : '')};
    .right {
      margin-left: auto;
    }
  }
`;

export const SelectBox = styled.select`
  width: ${props => (props.width ? props.width : '8rem')};
  margin-right: ${props => (props.mr ? props.mr : '')};
  margin-left: ${props => (props.ml ? props.ml : '')};
  height: 2.2rem;
  border-radius: 5px;
  text-align: center;
  font-size: 1rem;
  margin-right: 0.1rem;
  margin-left: 0.1rem;
  font-family: 'Pretendard-Regular';
`;

const NavContainer = styled.div`
  width: 78rem;
  padding-left: 0.8rem;
`;

const ShopList = () => {
  // 검색, 등록 관련
  const [keyword, setKeyword] = useState('');
  const [inputKeyword, setInputKeyword] = useState('');
  const [kind, setKind] = useState('');
  const dispatch = useDispatch();

  //인피니트 스크롤 관련 데이터
  const [shops, setShops] = useState([]); // 보여줄 전체 리스트
  const [thumbNail, setThumbNail] = useState([]); // 보여줄 전체 리스트
  const [page, setPage] = useState(0);
  const [totalPage, setTotalPage] = useState('');

  //offset 이후 순서의 데이터부터 받아옴
  const [target, setTarget] = useState(null); // 관찰 대상 target
  const [isLoaded, setIsLoaded] = useState(false); // 로드 중인지 판별
  const [stop, setStop] = useState(false); //마지막 데이터까지 다 불러온 경우 멈춤

  useEffect(() => {
    let observer;
    if (target && !stop) {
      setTimeout(() => {
        observer = new IntersectionObserver(onIntersect, {
          threshold: 1,
        });
        observer.observe(target);
      }, 100);
    }
    return () => observer && observer.disconnect();
  }, [target, isLoaded]);

  useEffect(() => {
    if (isLoaded && !stop && !kind && !inputKeyword) {
      loadShopAPI({ page }).then(res => {
        setTotalPage(res[0].totalPages);
        setShops(prev => prev.concat(res[0].content));
        setThumbNail(prev => Object.assign(prev, res[1]));
        setPage(prev => prev + 1);
        setIsLoaded(false);
        if (page === res[0].totalPages) {
          setStop(true);
        }
      });
    }

    if (isLoaded && !stop && kind) {
      loadShopFilterAPI({ page, kind }).then(res => {
        setTotalPage(res[0].totalPages);
        setShops(prev => prev.concat(res[0].content));
        setThumbNail(prev => Object.assign(prev, res[1]));
        setPage(prev => prev + 1);
        setIsLoaded(false);
        if (page === res[0].totalPages) {
          setStop(true);
        }
      });
    }

    if (isLoaded && !stop && inputKeyword) {
      searchShopAPI({ page, keyword: inputKeyword }).then(res => {
        setTotalPage(res[0].totalPages);
        setShops(prev => prev.concat(res[0].content));
        setThumbNail(prev => Object.assign(prev, res[1]));
        setPage(prev => prev + 1);
        setIsLoaded(false);
        if (page === res[0].totalPages) {
          setStop(true);
        }
      });
    }
  });

  // console.log('샵', shops);
  // console.log('썸네일', thumbNail[21]);

  // console.log('중고거래', shops);
  const getMoreItem = () => {
    // 데이터를 받아오도록 true로 변경
    setIsLoaded(true);
  };

  // callback
  const onIntersect = async ([entry], observer) => {
    // entry 요소가 교차되거나 Load중이 아니면
    if (entry.isIntersecting && !isLoaded) {
      // 관찰 멈추기
      observer.unobserve(entry.target);

      getMoreItem();

      observer.observe(entry.target);
    }
  };

  const onChange = e => {
    setKeyword(e.target.value);
  };

  const onSubmit = e => {
    e.preventDefault();
    setPage(0);
    setIsLoaded(true);
    setShops([]);
    setThumbNail([]);
    setStop(false);
    setKind('');
    setInputKeyword(keyword);
  };

  const onSelect = e => {
    setPage(0);
    setIsLoaded(true);
    setShops([]);
    setThumbNail([]);
    setKind(e.target.value);
    setStop(false);
  };

  const navigate = useNavigate();

  const onClick = () => {
    navigate('/shop/create');
  };

  return (
    <div>
      <NowContainer desc="중 고 장 터" />
      <Container>
        <Wrapper
          style={{
            flexDirection: 'column',
            marginBottom: '2rem',
          }}
        >
          <NavContainer mt="0" mb="0.2rem">
            <div>
              <SelectBox onChange={onSelect} placeholder="전체">
                <option value="">전체</option>
                <option value="완성차 / 프레임">완성차 / 프레임</option>
                <option value="구동계">구동계</option>
                <option value="휠셋">휠셋</option>
                <option value="부속품">부속품</option>
                <option value="의류">의류</option>
                <option value="기타 장비">기타 장비</option>
              </SelectBox>
              <Input
                type="text"
                value={keyword}
                onChange={onChange}
                placeholder="검색어를 입력해주세요"
                width="18rem"
                mr="0.1rem"
                ml="0.1rem"
                mt="0.4rem"
                mb="0.2rem"
              />
              <Button
                onClick={onSubmit}
                name="검색"
                width="5rem"
                ml="0.1rem"
                mr="0.1rem"
                mt="0.2rem"
                height="2.3rem"
                bc="white"
              />
              <Button
                onClick={onClick}
                name="글쓰기"
                width="5rem"
                ml="39.8rem"
                mr="0.1rem"
                mt="0.2rem"
                height="2.3rem"
                bc="white"
              />
            </div>
          </NavContainer>

          {shops.length > 0 && (
            <>
              <div style={{ display: 'flex', flexWrap: 'wrap' }}>
                {shops.map((shop, i) => (
                  <ShopListItem
                    key={shop.time}
                    shop={shop}
                    thumbNail={thumbNail[shop.dealId]}
                  />
                ))}
              </div>
            </>
          )}
        </Wrapper>
      </Container>
      <div ref={setTarget}></div>
    </div>
  );
};
export default ShopList;
