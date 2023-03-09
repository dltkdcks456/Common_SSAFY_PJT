/* eslint-disable */
import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { address } from '../Address';
import Modal from '../MeetingRegister/MeetingModal';
import MeetingListItem from './MeetingListItem';
import { Container, List, SelectBox, Wrapper } from './MeetingList.style';
import Button from '../commons/button';
import Input from '../commons/input';
import { searchRoomAPI } from '../../store/apis/meetingApi';

const MeetingList = () => {
  // 유저 정보
  const { user } = useSelector(state => state.myPage);

  // 주소지 관련 정보
  const options1 = ['시', ...Object.keys(address)];
  const [options2, setOptions2] = useState(['구']);
  const [options3, setOptions3] = useState(['동']);
  const [si, setSi] = useState('');
  const [gun, setGun] = useState('');
  const [dong, setDong] = useState('');

  // 검색, 등록 관련
  const [keyword, setKeyword] = useState('');
  const [modalOpen, setModalOpen] = useState(false);

  // 인피니티 스크롤 관련
  const [list, setList] = useState([]); // 보여줄 전체 리스트
  const [offset, setOffset] = useState(0); // back에 요청할 페이지 데이터 순서 정보
  // offset 이후 순서의 데이터부터 받아옴
  const [target, setTarget] = useState(null); // 관찰 대상 target
  const [isLoaded, setIsLoaded] = useState(false); // Load 중인가를 판별하는 boolean (반복 요청 방지)
  const [stop, setStop] = useState(false); // 마지막 데이터까지 다 불러온 경우 스탑

  useEffect(() => {
    let observer;
    if (target && !stop) {
      // callback 함수로 onIntersect를 지정
      // 시간차를 둬서 바로 다음 부분 관찰 못하게 막음 -> 안하면 다음꺼까지 불러와짐
      setTimeout(() => {
        observer = new IntersectionObserver(onIntersect, {
          threshold: 1, // 배열의 요소가 100% 보여질때마다 콜백을 실행
        });
        observer.observe(target);
      }, 100);
    }
    return () => observer && observer.disconnect();
  }, [target, isLoaded]);

  // isLoaded가 변할 때 실행
  useEffect(() => {
    // isLoaded가 true일 때 + 마지막 페이지가 아닌 경우 = 요청보내기
    const data = {
      page: offset,
      search_word: keyword,
      si: si != '시' ? si : '',
      gun: gun != '구' ? gun : '',
      dong: dong != '동' ? dong : '',
    };
    if (isLoaded && !stop) {
      searchRoomAPI(data).then(res => {
        // console.log(res.data.content);
        // 받아온 데이터를 보여줄 전체 리스트에 concat으로 넣어줌 (concat : 배열 합치기)
        setList(prev => prev.concat(res.data.content));
        // 다음 요청할 데이터 offset 정보
        setOffset(prev => prev + 1);
        // 다음 요청 전까지 요청 그만 보내도록 false로 변경
        setIsLoaded(false);

        if (res.data.content.length < 10) {
          // 전체 페이지를 다 불러온 경우
          setStop(true);
        }
      });
    }
  }, [isLoaded]);
  // console.log(isLoaded);
  // 데이터 불러오기
  const getMoreItem = () => {
    // 데이터를 받아오도록 true로 변경
    setIsLoaded(true);
  };

  // callback
  const onIntersect = async ([entry], observer) => {
    // entry 요소가 교차되거나 Load중이 아니면
    if (entry.isIntersecting && !isLoaded) {
      // 관찰은 일단 멈추고
      observer.unobserve(entry.target);
      // 데이터 불러오기
      getMoreItem();

      // 불러온 후 다시 관찰 실행
      observer.observe(entry.target);
    }
  };

  // console.log('리스트', list);

  // 모달 열기 / 닫기
  const openModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  };

  const onChange = e => {
    setKeyword(e.target.value);
  };

  // 검색 기능
  const onClick = e => {
    e.preventDefault();
    setIsLoaded(true);
    setList([]);
    setOffset(0);
    setStop(false);
  };

  // 주소 목록
  const inputSi = e => {
    if (e.target.value === '시') {
      setOptions2(['구']);
      setGun('');
      setOptions3(['동']);
      setDong('');
    } else {
      setOptions2(['구', ...Object.keys(address[e.target.value])]);
      setGun('');
      setOptions3(['동']);
      setDong('');
    }
    setSi(e.target.value);
  };
  const inputGun = e => {
    if (e.target.value === '구') {
      setOptions3(['동']);
      setDong('');
    } else {
      setOptions3(['동', ...address[si][e.target.value]]);
      setDong('');
    }
    setGun(e.target.value);
  };
  const inputDong = e => {
    setDong(e.target.value);
  };

  return (
    <>
      <Container>
        <Wrapper mt="0">
          <div className="filter">
            <div>
              <SelectBox onChange={inputSi} value={si} placeholder="시">
                {options1.map(item => (
                  <option value={item} key={item}>
                    {item}
                  </option>
                ))}
              </SelectBox>
              <SelectBox onChange={inputGun} value={gun} placeholder="구">
                {options2?.map(item => (
                  <option value={item} key={item}>
                    {item}
                  </option>
                ))}
              </SelectBox>
              <SelectBox onChange={inputDong} value={dong} placeholder="동">
                {options3?.map(item => (
                  <option value={item} key={item}>
                    {item}
                  </option>
                ))}
              </SelectBox>
            </div>
          </div>
        </Wrapper>
        <Wrapper mt="0" jc="flex-end">
          <span className="filter">
            <Input
              type="text"
              value={keyword}
              onChange={onChange}
              placeholder="검색어를 입력해주세요"
              width="18rem"
              mr="0.1rem"
              ml="0.1rem"
              mt="0.2rem"
            />
            <Button
              onClick={onClick}
              name="검색"
              width="5rem"
              ml="0.1rem"
              mr="0.1rem"
              mt="0.2rem"
              height="2.3rem"
              bc="white"
            />
          </span>
          <Button
            onClick={openModal}
            name="모임생성"
            mt="0.2rem"
            ml="10.7rem"
            bc="white"
            height="2.3rem"
            width="7rem"
          />
        </Wrapper>
        <Wrapper>
          <Modal
            open={modalOpen}
            close={closeModal}
            header="모임 생성"
            index="1"
          />
        </Wrapper>
        <List>
          {list.length ? (
            list.map(meeting => (
              <MeetingListItem
                key={meeting.communityId}
                // meeting={meeting}
                communityId={meeting.communityId}
                kingNickname={meeting.userNickname}
                user={user}
              />
            ))
          ) : (
            <></>
          )}
        </List>
        <div ref={setTarget}></div>
      </Container>
    </>
  );
};

export default MeetingList;
