/* eslint-disable */
import axios from 'axios';
import { BASE_URL } from '../../utils/urls';

// 유저의 채팅 목록 불러오기 (token)
export async function getRoomsAPI({ token }) {
  return await axios
    .get(`${BASE_URL}chat/rooms/user`, {
      headers: {
        token,
      },
    })
    .then(res => res.data);
}

// 유저의 채팅 목록 상세 정보 반환 (token) -> 유저가 포함된 채팅방을 방 ID와 방 이름, 알람여부 반환
export async function getRoomsDetailAPI({ token }) {
  return await axios
    .get(`${BASE_URL}chat/rooms/user/nameAndId`, {
      headers: {
        token,
      },
    })
    .then(res => res.data);
}

// 모임 채팅방(단톡) (token, name(방 이름))
export async function createChatRoomAPI1(data) {
  return await axios
    .post(`${BASE_URL}chat/roomForCommunity`, data)
    .then(res => res.data);
}

// 중고거래 채팅방(개인톡) (token, name(방 이름), opposite_nickname)
export async function createChatRoomAPI2(data) {
  return await axios
    .post(`${BASE_URL}chat/roomForDeal`, data)
    .then(res => res.data);
}

// 특정 유저 채팅방 초대 or 입장 (nickname, roomId)
export async function enterUserRoomAPI(data) {
  return await axios
    .post(`${BASE_URL}chat/enterUserRoom`, data)
    .then(res => res.data);
}

// 방 나가기 or 강퇴 (nickname, roomId)
export async function exitUserRoomAPI(data) {
  console.log(data);
  return await axios.delete(`${BASE_URL}chat/roomOut`, {
    data,
  });
}

// 특정 방 정보 불러오기
export async function getChatListAPI(roomId) {
  return await axios
    .get(`${BASE_URL}chat/room/${roomId}`)
    .then(res => res.data);
}

// 특정 채팅방 안의 유저들을 모두 닉네임들의 배열 형태로 반환
export async function getRoomUsersAPI({ roomId }) {
  return await axios
    .get(`${BASE_URL}chat/getRoomUsers`, {
      params: {
        roomId,
      },
    })
    .then(res => res.data);
}

// 특정 채팅방의 채팅 내역 모든 정보 날것으로 불러오기, sender가 유저 pk임
export async function callChattingDetailAPI({ roomId }) {
  return await axios
    .get(`${BASE_URL}chat/callChattingDetail`, {
      params: {
        roomId,
      },
    })
    .then(res => res.data);
}

// 특정 채팅방의 채팅 내역 타입, 내용, 보낸사람 별명, 보낸시간 가져오기
export async function callChattingAPI({ roomId }) {
  return await axios
    .get(`${BASE_URL}chat/callChatting`, {
      params: {
        roomId,
      },
    })
    .then(res => res.data);
}

// 알람 확인 API, {token}
export async function alarmCheckAPI({ token }) {
  return await axios.get(`${BASE_URL}chat/rooms/user/alarm`, {
    headers: { token },
  });
}

// 알람 끄는 API, {nickname, roomId}
export async function alarmOffAPI(data) {
  return await axios.delete(`${BASE_URL}chat/turnOffAlarm`, {
    data,
  });
}
