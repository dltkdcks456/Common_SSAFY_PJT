/* eslint-disable */
import axios from 'axios';
import { BASE_URL } from '../../utils/urls';

export async function createShopAPI({
  token,
  title,
  content,
  kind,
  name,
  price,
}) {
  await axios.post(`${BASE_URL}deal/`, {
    token,
    title,
    content,
    kind,
    name,
    price,
  });
}

export async function loadShopFilterAPI({ page, kind }) {
  console.log('요소', page, kind);
  const result = await axios.get(`${BASE_URL}deal/kind`, {
    params: { page, kind },
  });
  // console.log('첫번째 인자', result.data[0].content);
  // console.log('두번째 인자', result.data[1]);
  return result.data;
}

export async function loadShopAPI({ page }) {
  // console.log(page);
  const result = await axios.get(`${BASE_URL}deal/`, { params: { page } });
  // console.log(result.data);
  return result.data;
}

export async function searchShopAPI({ page, keyword }) {
  // console.log('꾸르르르륵', page, keyword);
  const result = await axios.get(`${BASE_URL}deal/search`, {
    params: { page, keyword },
  });
  // console.log(result.data);
  return result.data;
}

// 중고 거래 상세 보기 API
export async function loadShopDetailAPI({ dealId, userId }) {
  const result = await axios.get(`${BASE_URL}deal/detail`, {
    params: { dealId },
    headers: { userId },
  });
  return result.data;
}

// 중고 거래 상세 보기 삭제 API
export async function onDeleteShopAPI({ dealId }) {
  await axios.delete(`${BASE_URL}deal/${dealId}`);
}

// 중고 거래 수정
export async function updateShopAPI({
  token,
  deal_id,
  title,
  content,
  kind,
  name,
  price,
}) {
  await axios.put(`${BASE_URL}deal/`, {
    token,
    deal_id,
    title,
    content,
    kind,
    name,
    price,
  });
}
