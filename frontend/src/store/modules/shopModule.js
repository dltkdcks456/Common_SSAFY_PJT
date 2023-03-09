/* eslint-disable */
import produce from 'immer';

const initialStatement = {
  createShopLoading: false,
  createShopDone: false,
  createShopError: null,
  updateShopLoading: false,
  updateShopDone: false,
  updateShopError: null,
  deleteShopLoading: false,
  deleteShopDone: false,
  deleteShopError: null,
  loadShopLoading: false,
  loadShopDone: false,
  loadShopError: null,
  loadShopFilterLoading: false,
  loadShopFilterDone: false,
  loadShopFilterError: null,
  searchShopLoading: false,
  searchShopDone: false,
  searchShopError: null,
  loadShopDetailLoading: false,
  loadShopDetailDone: false,
  loadShopDetailError: null,
  page: 0,
  Shops: {},
  thumbNail: [],
  ShopDetail: '',
  onSale: false,
  likeCount: 0,
};
// 중고 거래 생성
export const CREATE_SHOP_REQUEST = 'CREATE_SHOP_REQUEST';
export const CREATE_SHOP_SUCCESS = 'CREATE_SHOP_SUCCESS';
export const CREATE_SHOP_FAILURE = 'CREATE_SHOP_FAILURE';
// 중고 거래 생성
export const UPDATE_SHOP_REQUEST = 'UPDATE_SHOP_REQUEST';
export const UPDATE_SHOP_SUCCESS = 'UPDATE_SHOP_SUCCESS';
export const UPDATE_SHOP_FAILURE = 'UPDATE_SHOP_FAILURE';
// 중고 거래 삭제
export const DELETE_SHOP_REQUEST = 'DELETE_SHOP_REQUEST';
export const DELETE_SHOP_SUCCESS = 'DELETE_SHOP_SUCCESS';
export const DELETE_SHOP_FAILURE = 'DELETE_SHOP_FAILURE';
// 중고 거래 목록 조회
export const LOAD_SHOP_REQUEST = 'LOAD_SHOP_REQUEST';
export const LOAD_SHOP_SUCCESS = 'LOAD_SHOP_SUCCESS';
export const LOAD_SHOP_FAILURE = 'LOAD_SHOP_FAILURE';
// 중고 거래 목록 조회
export const LOAD_SHOP_FILTER_REQUEST = 'LOAD_SHOP_FILTER_REQUEST';
export const LOAD_SHOP_FILTER_SUCCESS = 'LOAD_SHOP_FILTER_SUCCESS';
export const LOAD_SHOP_FILTER_FAILURE = 'LOAD_SHOP_FILTER_FAILURE';
// 중고 거래 검색
export const SEARCH_SHOP_REQUEST = 'SEARCH_SHOP_REQUEST';
export const SEARCH_SHOP_SUCCESS = 'SEARCH_SHOP_SUCCESS';
export const SEARCH_SHOP_FAILURE = 'SEARCH_SHOP_FAILURE';
// 중고 거래 상세 보기
export const LOAD_SHOP_DETAIL_REQUEST = 'LOAD_SHOP_DETAIL_REQUEST';
export const LOAD_SHOP_DETAIL_SUCCESS = 'LOAD_SHOP_DETAIL_SUCCESS';
export const LOAD_SHOP_DETAIL_FAILURE = 'LOAD_SHOP_DETAIL_FAILURE';
export const LOAD_SHOP_DETAIL_RESET = 'LOAD_SHOP_DETAIL_RESET';

const reducer = (state = initialStatement, action) =>
  produce(state, draft => {
    switch (action.type) {
      case CREATE_SHOP_REQUEST:
        draft.createShopLoading = true;
        draft.createShopDone = false;
        draft.createShopError = null;
        break;
      case CREATE_SHOP_SUCCESS:
        draft.createShopLoading = false;
        draft.createShopDone = true;
        draft.Shops = {};
        break;
      case CREATE_SHOP_FAILURE:
        draft.createShopDone = false;
        draft.createShopError = action.error;
        break;
      case UPDATE_SHOP_REQUEST:
        draft.updateShopLoading = true;
        draft.updateShopDone = false;
        draft.updateShopError = null;
        break;
      case UPDATE_SHOP_SUCCESS:
        draft.updateShopLoading = false;
        draft.updateShopDone = true;
        draft.Shops = {};
        break;
      case UPDATE_SHOP_FAILURE:
        draft.updateShopDone = false;
        draft.updateShopError = action.error;
        break;
      case DELETE_SHOP_REQUEST:
        draft.deleteShopLoading = true;
        draft.deleteShopDone = false;
        draft.deleteShopError = null;
        break;
      case DELETE_SHOP_SUCCESS:
        draft.deleteShopLoading = false;
        draft.deleteShopDone = true;
        draft.Shops = {};
        break;
      case DELETE_SHOP_FAILURE:
        draft.deleteShopDone = false;
        draft.deleteShopError = action.error;
        break;
      case LOAD_SHOP_REQUEST:
        draft.loadShopLoading = true;
        draft.loadShopDone = false;
        draft.loadShopError = null;
        break;
      case LOAD_SHOP_SUCCESS:
        draft.loadShopLoading = false;
        draft.loadShopDone = true;
        draft.Shops = action.data[0];
        draft.thumbNail = action.data[1];
        break;
      case LOAD_SHOP_FAILURE:
        draft.loadShopDone = false;
        draft.loadShopError = action.error;
        break;
      case SEARCH_SHOP_REQUEST:
        draft.searchShopLoading = true;
        draft.searchShopDone = false;
        draft.searchShopError = null;
        break;
      case SEARCH_SHOP_SUCCESS:
        draft.searchShopLoading = false;
        draft.searchShopDone = true;
        draft.Shops = action.data;
        break;
      case SEARCH_SHOP_FAILURE:
        draft.searchShopDone = false;
        draft.searchShopError = action.error;
        break;
      case LOAD_SHOP_DETAIL_REQUEST:
        draft.loadShopDetailLoading = true;
        draft.loadShopDetailDone = false;
        draft.loadShopDetailError = null;
        break;
      case LOAD_SHOP_DETAIL_SUCCESS:
        draft.loadShopDetailLoading = false;
        draft.loadShopDetailDone = true;
        draft.ShopDetail = action.data;
        break;
      case LOAD_SHOP_DETAIL_FAILURE:
        draft.loadShopDetailDone = false;
        draft.loadShopDetailError = action.error;
        break;
      case LOAD_SHOP_FILTER_REQUEST:
        draft.loadShopFilterLoading = true;
        draft.loadShopFilterDone = false;
        draft.loadShopFilterError = null;
        break;
      case LOAD_SHOP_FILTER_SUCCESS:
        draft.loadShopFilterLoading = false;
        draft.loadShopFilterDone = true;
        draft.Shops = action.data;
        break;
      case LOAD_SHOP_FILTER_FAILURE:
        draft.loadShopFilterDone = false;
        draft.loadShopFilterError = action.error;
        break;
      case LOAD_SHOP_DETAIL_RESET:
        draft.ShopDetail = '';
      default:
        break;
    }
  });

export default reducer;
