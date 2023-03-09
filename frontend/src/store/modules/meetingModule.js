/* eslint-disable */
import produce from 'immer';

const initialState = {
  // 모임페이지
  getListRequest: false,
  getListDone: false,
  getListError: null,
  meetingList: [],

  // 모임방 등록
  meetingCreateRequest: false,
  meetingCreateDone: false,
  meetingCreateError: false,

  // 모임방 수정
  meetingUpdateRequest: false,
  meetingUpdateDone: false,
  meetingUpdateError: false,

  // 모임방 참여
  participationRequest: false,
  participationDone: false,
  participationError: false,

  // 모임방 나가기
  meetingExitRequest: false,
  meetingExitDone: false,
  meetingExitError: false,

  // 모임방 삭제
  deleteMeetingRequest: false,
  deleteMeetingDone: false,
  deleteMeetingError: false,

  // 모임방 검색
  searchListRequest: false,
  searchListDone: false,
  searchListError: false,
};

// 모임방 정보 불러오기
export const GET_LIST_REQUEST = 'GET_LIST_REQUEST';
export const GET_LIST_SUCCESS = 'GET_LIST_SUCCESS';
export const GET_LIST_FAILURE = 'GET_LIST_FAILURE';

// 모임방 생성
export const MEETING_CREATE_REQUEST = 'MEETING_CREATE_REQUEST';
export const MEETING_CREATE_SUCCESS = 'MEETING_CREATE_SUCCESS';
export const MEETING_CREATE_FAILURE = 'MEETING_CREATE_FAILURE';

// 모임방 수정
export const MEETING_UPDATE_REQUEST = 'MEETING_UPDATE_REQUEST';
export const MEETING_UPDATE_SUCCESS = 'MEETING_UPDATE_SUCCESS';
export const MEETING_UPDATE_FAILURE = 'MEETING_UPDATE_FAILURE';

// 모임방 참여
export const PARTICIPATION_REQUEST = 'PARTICIPATION_REQUEST';
export const PARTICIPATION_SUCCESS = 'PARTICIPATION_SUCCESS';
export const PARTICIPATION_FAILURE = 'PARTICIPATION_FAILURE';

// 모임방 나가기
export const MEETING_EXIT_REQUEST = 'MEETING_EXIT_REQUEST';
export const MEETING_EXIT_SUCCESS = 'MEETING_EXIT_SUCCESS';
export const MEETING_EXIT_FAILURE = 'MEETING_EXIT_FAILURE';

// 모임방 삭제
export const DELETE_MEETING_REQUEST = 'DELETE_MEETING_REQUEST';
export const DELETE_MEETING_SUCCESS = 'DELETE_MEETING_SUCCESS';
export const DELETE_MEETING_FAILURE = 'DELETE_MEETING_FAILURE';

// 모임방 검색
export const SEARCH_LIST_REQUEST = 'SEARCH_LIST_REQUEST';
export const SEARCH_LIST_SUCCESS = 'SEARCH_LIST_SUCCESS';
export const SEARCH_LIST_FAILURE = 'SEARCH_LIST_FAILURE';

const reducer = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      // 모임방 불러오기
      case GET_LIST_REQUEST:
        draft.getListRequest = true;
        draft.getListDone = false;
        draft.getListError = null;
        break;
      case GET_LIST_SUCCESS:
        draft.getListRequest = false;
        draft.getListDone = true;
        draft.meetingList = action.data.content;
        // console.log(draft.user);
        break;
      case GET_LIST_FAILURE:
        draft.getListRequest = false;
        draft.getListError = action.error;
        break;

      // 모임방 생성
      case MEETING_CREATE_REQUEST:
        draft.meetingCreateRequest = true;
        draft.meetingCreateDone = false;
        draft.meetingCreateError = null;
        break;
      case MEETING_CREATE_SUCCESS:
        draft.meetingCreateRequest = false;
        draft.meetingCreateDone = true;
        break;
      case MEETING_CREATE_FAILURE:
        draft.meetingCreateRequest = false;
        draft.meetingCreateError = action.error;
        break;

      // 모임방 수정
      case MEETING_UPDATE_REQUEST:
        draft.meetingUpdateRequest = true;
        draft.meetingUpdateDone = false;
        draft.meetingUpdateError = null;
        break;
      case MEETING_UPDATE_SUCCESS:
        draft.meetingUpdateRequest = false;
        draft.meetingUpdateDone = true;
        break;
      case MEETING_UPDATE_FAILURE:
        draft.meetingUpdateRequest = false;
        draft.meetingUpdateError = action.error;
        break;

      // 모임방 참여
      case PARTICIPATION_REQUEST:
        draft.participationRequest = true;
        draft.participationDone = false;
        draft.participationError = null;
        break;
      case PARTICIPATION_SUCCESS:
        draft.participationRequest = false;
        draft.participationDone = true;
        break;
      case PARTICIPATION_FAILURE:
        draft.participationRequest = false;
        draft.participationError = action.error;
        break;

      // 모임방 나가기
      case MEETING_EXIT_REQUEST:
        draft.meetingExitRequest = true;
        draft.meetingExitDone = false;
        draft.meetingExitError = null;
        break;
      case MEETING_EXIT_SUCCESS:
        draft.meetingExitRequest = false;
        draft.meetingExitDone = true;
        break;
      case MEETING_EXIT_FAILURE:
        draft.meetingExitRequest = false;
        draft.meetingExitError = action.error;
        break;

      // 모임방 삭제
      case DELETE_MEETING_REQUEST:
        draft.deleteMeetingRequest = true;
        draft.deleteMeetingDone = false;
        draft.deleteMeetingError = null;
        break;
      case DELETE_MEETING_SUCCESS:
        draft.deleteMeetingRequest = false;
        draft.deleteMeetingDone = true;
        break;
      case DELETE_MEETING_FAILURE:
        draft.deleteMeetingRequest = false;
        draft.deleteMeetingError = action.error;
        break;

      // 모임방 검색
      case SEARCH_LIST_REQUEST:
        draft.searchListRequest = true;
        draft.searchListDone = false;
        draft.searchListError = null;
        break;
      case SEARCH_LIST_SUCCESS:
        draft.searchListRequest = false;
        draft.searchListDone = true;
        draft.meetingList = action.data.content;
        break;
      case SEARCH_LIST_FAILURE:
        draft.searchListRequest = false;
        draft.searchListError = action.error;
        break;
      default:
        break;
    }
  });

export default reducer;
