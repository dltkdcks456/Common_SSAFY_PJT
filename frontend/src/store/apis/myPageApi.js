/* eslint-disable */
import axios from 'axios';
import { BASE_URL, CONTEXT_URL } from '../../utils/urls';

// 로그인 상태 확인
export async function myPageAPI(token) {
  const result = await axios.post(`${BASE_URL}user/getUserInfo`, token);
  return result;
}

// 유저가 작성한 게시판 정보 목록 반환, {token}
export async function getBoardList({ nickname }) {
  const result = await axios.get(`${BASE_URL}mypage/boardList`, {
    params: { nickname },
  });
  return result;
}

// 유저가 달성한 업적들 정보 목록 반환
export async function getAchievementList({ nickname }) {
  const result = await axios.get(`${BASE_URL}mypage/achievementList`, {
    params: { nickname },
  });
  return result;
}

// 유저가 판매한 기록 리스트
export async function getSalesList({ nickname }) {
  const result = await axios.get(`${BASE_URL}mypage/saleList`, {
    params: { nickname },
  });
  return result;
}

// 유저가 중고거래 찜한 목록을 반환
export async function getZzimList({ token }) {
  const result = await axios.get(`${BASE_URL}mypage/zzimList`, {
    headers: { token },
  });
  return result;
}

// 유저가 포함된 모임방 정보 리스트 반환, {token}
export async function getMeetingList({ token }) {
  const result = await axios.get(`${BASE_URL}mypage/communityList`, {
    headers: { token },
  });
  return result;
}

// 운동 기록 가져오기
export async function getRecord({ nickname }) {
  const result = await axios.get(`${BASE_URL}recode/getUserInfo`, {
    params: { nickname },
  });
  return result;
}

// 운동 기록 리스트 가져오기
export async function getRecordList({ nickname }) {
  const result = await axios.get(`${BASE_URL}recode/getUserInfoAll`, {
    params: { nickname },
  });
  return result;
}
