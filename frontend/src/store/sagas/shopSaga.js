/* eslint-disable */
import { all, call, fork, put, takeLatest } from '@redux-saga/core/effects';
import {
  createShopAPI,
  loadShopAPI,
  loadShopDetailAPI,
  loadShopFilterAPI,
  onDeleteShopAPI,
  searchShopAPI,
  updateShopAPI,
} from '../apis/shopApi';
import {
  CREATE_SHOP_FAILURE,
  CREATE_SHOP_REQUEST,
  CREATE_SHOP_SUCCESS,
  DELETE_SHOP_FAILURE,
  DELETE_SHOP_REQUEST,
  DELETE_SHOP_SUCCESS,
  LOAD_SHOP_DETAIL_FAILURE,
  LOAD_SHOP_DETAIL_REQUEST,
  LOAD_SHOP_DETAIL_SUCCESS,
  LOAD_SHOP_FAILURE,
  LOAD_SHOP_FILTER_FAILURE,
  LOAD_SHOP_FILTER_REQUEST,
  LOAD_SHOP_FILTER_SUCCESS,
  LOAD_SHOP_REQUEST,
  LOAD_SHOP_SUCCESS,
  SEARCH_SHOP_FAILURE,
  SEARCH_SHOP_REQUEST,
  SEARCH_SHOP_SUCCESS,
  UPDATE_SHOP_FAILURE,
  UPDATE_SHOP_REQUEST,
  UPDATE_SHOP_SUCCESS,
} from '../modules/shopModule';

// 중고 거래 생성
function* createShop(action) {
  try {
    const result = yield call(createShopAPI, action.data);
    yield put({
      type: CREATE_SHOP_SUCCESS,
      data: result,
    });
    const navigate = action.data.navigate;
    navigate(`/shop`);
  } catch (error) {
    // console.log(error);
    yield put({
      type: CREATE_SHOP_FAILURE,
    });
  }
}

function* watchCreateShop() {
  yield takeLatest(CREATE_SHOP_REQUEST, createShop);
}

// 중고 거래 수정
function* updateShop(action) {
  try {
    const result = yield call(updateShopAPI, action.data);
    yield put({
      type: UPDATE_SHOP_SUCCESS,
      data: result,
    });
    const navigate = action.data.navigate;
    // console.log(action.data);
    navigate(`/shop/detail`, { state: { dealId: action.data.deal_id } });
  } catch (error) {
    // console.log(error);
    yield put({
      type: UPDATE_SHOP_FAILURE,
    });
  }
}

function* watchUpdateShop() {
  yield takeLatest(UPDATE_SHOP_REQUEST, updateShop);
}

// 중고 거래 삭제
function* deleteShop(action) {
  try {
    const result = yield call(onDeleteShopAPI, action.data);
    yield put({
      type: DELETE_SHOP_SUCCESS,
      data: result,
    });
    const navigate = action.data.navigate;
    navigate(`/shop`);
  } catch (error) {
    // console.log(error);
    yield put({
      type: DELETE_SHOP_FAILURE,
    });
  }
}

function* watchDeleteShop() {
  yield takeLatest(DELETE_SHOP_REQUEST, deleteShop);
}

// 중고 거래 조회
function* loadShop(action) {
  try {
    const result = yield call(loadShopAPI, action.data);
    yield put({
      type: LOAD_SHOP_SUCCESS,
      data: result,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: LOAD_SHOP_FAILURE,
    });
  }
}

function* watchLoadShop() {
  yield takeLatest(LOAD_SHOP_REQUEST, loadShop);
}

// 중고 거래 필터
function* loadShopFilter(action) {
  try {
    const result = yield call(loadShopFilterAPI, action.data);
    yield put({
      type: LOAD_SHOP_FILTER_SUCCESS,
      data: result,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: LOAD_SHOP_FILTER_FAILURE,
    });
  }
}

function* watchLoadShopFilter() {
  yield takeLatest(LOAD_SHOP_FILTER_REQUEST, loadShopFilter);
}

// 중고 거래 상세 조회
function* loadShopDetail(action) {
  try {
    const result = yield call(loadShopDetailAPI, action.data);
    yield put({
      type: LOAD_SHOP_DETAIL_SUCCESS,
      data: result,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: LOAD_SHOP_DETAIL_FAILURE,
    });
  }
}

function* watchLoadShopDetail() {
  yield takeLatest(LOAD_SHOP_DETAIL_REQUEST, loadShopDetail);
}

// 중고 거래 검색
function* searchShop(action) {
  try {
    const result = yield call(searchShopAPI, action.data);
    yield put({
      type: SEARCH_SHOP_SUCCESS,
      data: result,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: SEARCH_SHOP_FAILURE,
    });
  }
}

function* watchSearchShop() {
  yield takeLatest(SEARCH_SHOP_REQUEST, searchShop);
}

export default function* shopSaga() {
  yield all([
    fork(watchCreateShop),
    fork(watchLoadShop),
    fork(watchLoadShopDetail),
    fork(watchDeleteShop),
    fork(watchUpdateShop),
    fork(watchSearchShop),
    fork(watchLoadShopFilter),
  ]);
}
