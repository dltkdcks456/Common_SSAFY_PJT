/* eslint-disable */
import axios from 'axios';
import { BASE_URL } from '../../utils/urls';

// 회원가입
export async function signUpAPI({
  name,
  age,
  si,
  gun,
  dong,
  email,
  id,
  pwd,
  nick,
  gender,
  weight,
  bikeweight,
  // imagePath,
  open,
}) {
  if (weight === 0) {
    // console.log('바꾼다');
    weight = 62;
  }
  if (bikeweight === 0) {
    // console.log('자전거 바꾼다');
    bikeweight = 12;
  }
  const result = await axios.post(`${BASE_URL}user/signup`, {
    name,
    age,
    si,
    gun,
    dong,
    email,
    id,
    password: pwd,
    permission: 0,
    nickname: nick,
    gender,
    weight,
    cycle_weight: bikeweight,
    // image_path: imagePath,
    open,
  });
  return result;
}

// 회원정보수정
export async function editUserAPI({
  si,
  gun,
  dong,
  email,
  nick,
  weight,
  bikeweight,
  // imagePath,
  open,
  token,
}) {
  const result = await axios.put(`${BASE_URL}user/edit`, {
    si,
    gun,
    dong,
    email,
    nickname: nick,
    weight: weight ? weight : 0,
    cycle_weight: bikeweight ? bikeweight : 0,
    // image_path: imagePath,
    open,
    token,
  });
  console.log(result);
  return result;
}

// 로그인
export async function logInAPI({ id, password }) {
  // console.log(id, password);
  const result = await axios.post(`${BASE_URL}user/login`, {
    id,
    password,
  });
  // console.log(result);
  return result;
}

// 아이디찾기
export async function findIdAPI({ name, email }) {
  const result = await axios.post(`${BASE_URL}user/findId`, {
    name,
    email,
  });
  // console.log(result);
  return result.data;
}

// 비밀번호 찾기
export async function findPwdAPI({ name, id, email }) {
  const result = await axios.post(`${BASE_URL}user/findPassword`, {
    name,
    id,
    email,
  });
  // console.log(result);
  return result;
}

// 비밀번호 변경
export async function editPwdAPI({ nowPassword, newPassword }) {
  const token = sessionStorage.getItem('userToken');
  const result = await axios.put(`${BASE_URL}user/editpwd`, {
    nowPassword,
    newPassword,
    token,
  });
  // console.log(result);
  return result;
}

// 로그아웃
// export async function logOutAPI() {
//   sessionStorage.clear();
//   location.href = `${CONTEXT_URL}`;
// }

// Email 인증 보내기
export async function sendMailAPI(email) {
  const result = await axios.get(`${BASE_URL}user/registerMail`, {
    params: { email },
  });
  return result;
}

// Email 인증 확인
export async function checkCertiAPI(code) {
  const promise = await axios.get(`${BASE_URL}user/certMail`, {
    params: { code },
  });
  return promise.data;
}

// 아이디 중복 체크
export async function checkIdAPI(id) {
  const promise = await axios.get(`${BASE_URL}user/signup/id`, {
    params: { id },
  });
  // console.log(promise.data);
  return promise.data;
}

// 닉네임 중복 체크
export async function checkNickAPI(nick) {
  const promise = await axios.get(`${BASE_URL}user/signup/nickname`, {
    params: { nickname: nick },
  });
  return promise.data;
}

// 회원탈퇴
export async function userDeleteAPI(data) {
  const promise = await axios.delete(`${BASE_URL}user/deleteUser`, {
    data: {
      password: data.password,
      token: data.userToken,
    },
  });
  return promise.data;
}

// 유저 검색
export async function userSearchAPI(data) {
  const result = axios.get(`${BASE_URL}user/search/nickname`, {
    params: {
      nickname: data.nickname,
    },
  });
  return result;
}

// 유저 상세페이지
export async function userInfoAPI({ nickname }) {
  const promise = axios.get(`${BASE_URL}user/search`, {
    params: { nickname },
  });
  return promise;
}

// 프로필사진 업로드
export async function imageUploadAPI(formData) {
  // for (let key of formData.keys()) {
  //   console.log(key);
  // }
  // for (let value of formData.values()) {
  //   console.log(value);
  // }
  const result = await axios
    .post(`${BASE_URL}user/imageUpload`, formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    })
    .then(response => {
      console.log(response.data);
    })
    .catch(error => {
      console.error(error);
    });
  return result;
}
