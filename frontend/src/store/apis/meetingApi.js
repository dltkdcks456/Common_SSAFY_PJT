/* eslint-disable */
import axios from 'axios';
import { BASE_URL } from '../../utils/urls';

// 모임방 불러오기
export async function getListAPI() {
  const result = await axios.get(`${BASE_URL}community/`);
  return result;
}

// 모임방 상세 정보 불러오기
export async function getMeetingDetail(data) {
  const result = await axios.get(`${BASE_URL}community/info`, {
    params: data,
  });
  return result;
}

// 모임방 생성
export async function createMeetingAPI(data) {
  const result = await axios.post(`${BASE_URL}community/`, data);
  return result;
}

// 모임방 수정
export async function updateMeetingAPI(data) {
  // console.log(data);
  const result = await axios.put(`${BASE_URL}community/`, data);
  return result;
}

// 모임방 들어가기, {token, community_id}, 반환값 => (BAD_REQUEST : 추방됨, NOT_FOUND : 해당 모임방 없음)
export async function participationAPI(data) {
  const result = await axios.put(`${BASE_URL}community/addPerson`, data);
  return result;
}

// 모임방 삭제하기
export async function deleteMeetingAPI(data) {
  const result = await axios.delete(`${BASE_URL}community/`, {
    data,
  });
  return result;
}

// 유저가 방에 존재하는지를 확인, {token, community_id}, 반환값 => (존재O : true, 존재X : false)
export async function userMeetingCheck({ token, community_id }) {
  // console.log('끄아악', community_id, token)
  const result = await axios.get(`${BASE_URL}community/check/${community_id}`, {
    headers: { token },
  });
  return result;
}

// 모임방 나가기, {token, community_id}
export async function exitMeetingAPI(data) {
  const result = await axios.delete(`${BASE_URL}community/removePerson`, {
    data,
  });
  return result;
}

// 방장이 다른 사람 초대, {token, invited_user_nickname, community_id}
export async function inviteMeetingAPI(data) {
  // console.log(data);
  const result = await axios.put(`${BASE_URL}community/invitePerson`, data);
  return result;
}

// 방장이 모임에서 유저를 밴, {token, ban_user_nickname, community_id}
export async function banMeetingUserAPI(data) {
  const result = await axios.post(`${BASE_URL}community/banPerson`, data);
  return result;
}

// 방장이 밴 한 사람 구제, {token, ban_user_nickname, community_id}
export async function banCancelUserAPI(data) {
  const result = await axios.delete(`${BASE_URL}community/cancelBanPerson`, {
    params: data,
  });
  return result;
}

// (방장이) 밴 목록을 봄, {token, community_id}
export async function showBanListAPI(data) {
  const result = await axios.get(`${BASE_URL}community/showBanList`, {
    params: data,
  });
  return result;
}

// 모임방 검색 기능, {si, gun, dong, search_word}
export async function searchRoomAPI(data) {
  const result = await axios.get(`${BASE_URL}community/searchRoom`, {
    params: data,
  });
  return result;
}

// 모임 방 유저 목록 조회, {communityId}
export async function meetingUserList(data) {
  const result = await axios.get(`${BASE_URL}community/usersInfo`, {
    params: data,
  });
  return result;
}
