/* eslint-disable */
import produce from 'immer';

const initialStatement = {
  // 게시판 목록 불러오기
  loadFreeBoardLoading: false,
  loadFreeBoardDone: false,
  loadFreeBoardError: null,
  // loadFreeDetailLoading: false,
  // loadFreeDetailDone: false,
  // loadFreeDetailError: null,
  // 게시판 생성
  createBoardLoading: false,
  createBoardDone: false,
  createBoardError: null,
  // 게시판 수정
  updateBoardLoading: false,
  updateBoardDone: false,
  updateBoardError: null,
  // 게시판 검색
  searchBoardLoading: false,
  searchBoardDone: false,
  searchBoardError: null,
  // 게시판 좋아요 조회
  loadLikeLoading: false,
  loadLikeDone: false,
  loadLikeError: null,
  // 게시판 좋아요 클릭
  setLikeLoading: false,
  setLikeDone: false,
  setLikeError: null,
  // 댓글 생성
  createCommentLoading: false,
  createCommentDone: false,
  createCommentError: null,
  // 댓글 삭제
  deleteCommentLoading: false,
  deleteCommentDone: false,
  deleteCommentError: null,
  // 댓글 수정
  updateCommentLoading: false,
  updateCommentDone: false,
  updateCommentError: null,
  page: 0,
  // 게시판 목록 집합
  freeBoards: {},
  // freeDetail: {},
  // 좋아요 조회
  like: false,
};

// 커뮤니티 목록
export const LOAD_FREE_BOARD_REQUEST = 'LOAD_FREE_BOARD_REQUEST';
export const LOAD_FREE_BOARD_SUCCESS = 'LOAD_FREE_BOARD_SUCCESS';
export const LOAD_FREE_BOARD_FAILURE = 'LOAD_FREE_BOARD_FAILURE';
// 커뮤니티 디테일
// export const LOAD_FREE_DETAIL_REQUEST = 'LOAD_FREE_DETAIL_REQUEST';
// export const LOAD_FREE_DETAIL_SUCCESS = 'LOAD_FREE_DETAIL_SUCCESS';
// export const LOAD_FREE_DETAIL_FAILURE = 'LOAD_FREE_DETAIL_FAILURE';
// 게시글 작성
export const CREATE_BOARD_REQUEST = 'CREATE_BOARD_REQUEST';
export const CREATE_BOARD_SUCCESS = 'CREATE_BOARD_SUCCESS';
export const CREATE_BOARD_FAILURE = 'CREATE_BOARD_FAILURE';
// 게시글 검색
export const SEARCH_BOARD_REQUEST = 'SEARCH_BOARD_REQUEST';
export const SEARCH_BOARD_SUCCESS = 'SEARCH_BOARD_SUCCESS';
export const SEARCH_BOARD_FAILURE = 'SEARCH_BOARD_FAILURE';
// 게시글 수정
export const UPDATE_BOARD_REQUEST = 'UPDATE_BOARD_REQUEST';
export const UPDATE_BOARD_SUCCESS = 'UPDATE_BOARD_SUCCESS';
export const UPDATE_BOARD_FAILURE = 'UPDATE_BOARD_FAILURE';
// 게시글 좋아요 조회
export const LIKE_BOARD_REQUEST = 'LIKE_BOARD_REQUEST';
export const LIKE_BOARD_SUCCESS = 'LIKE_BOARD_SUCCESS';
export const LIKE_BOARD_FAILURE = 'LIKE_BOARD_FAILURE';
// 게시글 좋아요 클릭
export const LIKE_CLICK_BOARD_REQUEST = 'LIKE_CLICK_BOARD_REQUEST';
export const LIKE_CLICK_BOARD_SUCCESS = 'LIKE_CLICK_BOARD_SUCCESS';
export const LIKE_CLICK_BOARD_FAILURE = 'LIKE_CLICK_BOARD_FAILURE';
// 댓글 작성
export const CREATE_COMMENT_REQUEST = 'CREATE_COMMENT_REQUEST';
export const CREATE_COMMENT_SUCCESS = 'CREATE_COMMENT_SUCCESS';
export const CREATE_COMMENT_FAILURE = 'CREATE_COMMENT_FAILURE';
// 댓글 삭제
export const DELETE_COMMENT_REQUEST = 'DELETE_COMMENT_REQUEST';
export const DELETE_COMMENT_SUCCESS = 'DELETE_COMMENT_SUCCESS';
export const DELETE_COMMENT_FAILURE = 'DELETE_COMMENT_FAILURE';
// 댓글 수정
export const UPDATE_COMMENT_REQUEST = 'UPDATE_COMMENT_REQUEST';
export const UPDATE_COMMENT_SUCCESS = 'UPDATE_COMMENT_SUCCESS';
export const UPDATE_COMMENT_FAILURE = 'UPDATE_COMMENT_FAILURE';

const reducer = (state = initialStatement, action) =>
  produce(state, draft => {
    switch (action.type) {
      case LOAD_FREE_BOARD_REQUEST:
        draft.loadFreeBoardLoading = true;
        draft.loadFreeBoardDone = false;
        draft.loadFreeBoardError = null;
        break;
      case LOAD_FREE_BOARD_SUCCESS:
        draft.loadFreeBoardLoading = false;
        draft.loadFreeBoardDone = true;
        draft.freeBoards = action.data;
        break;
      case LOAD_FREE_BOARD_FAILURE:
        draft.loadFreeBoardDone = false;
        draft.loadFreeBoardError = action.error;
        break;
      // case LOAD_FREE_DETAIL_REQUEST:
      //   draft.loadFreeDetailLoading = true;
      //   draft.loadFreeDetailDone = false;
      //   draft.loadFreeDetailError = null;
      //   break;
      // case LOAD_FREE_DETAIL_SUCCESS:
      //   draft.loadFreeDetailLoading = false;
      //   draft.loadFreeDetailDone = true;
      //   draft.freeDetail = action.data;

      //   break;
      // case LOAD_FREE_DETAIL_FAILURE:
      //   draft.loadFreeDetailDone = false;
      //   draft.loadFreeDetailError = action.error;
      //   break;
      case CREATE_BOARD_REQUEST:
        draft.createBoardLoading = true;
        draft.createBoardDone = false;
        draft.createBoardError = null;
        break;
      case CREATE_BOARD_SUCCESS:
        draft.createBoardLoading = false;
        draft.createBoardDone = true;
        break;
      case CREATE_BOARD_FAILURE:
        draft.createBoardDone = false;
        draft.createBoardError = action.error;
        break;
      case UPDATE_BOARD_REQUEST:
        draft.updateBoardLoading = true;
        draft.updateBoardDone = false;
        draft.updateBoardError = null;
        break;
      case UPDATE_BOARD_SUCCESS:
        draft.updateBoardLoading = false;
        draft.updateBoardDone = true;
        break;
      case UPDATE_BOARD_FAILURE:
        draft.updateBoardDone = false;
        draft.updateBoardError = action.error;
        break;
      case SEARCH_BOARD_REQUEST:
        draft.searchBoardLoading = true;
        draft.searchBoardDone = false;
        draft.searchBoardError = null;
      case SEARCH_BOARD_SUCCESS:
        draft.searchBoardLoading = false;
        draft.searchBoardDone = true;
        draft.freeBoards = action.data;
      case SEARCH_BOARD_FAILURE:
        draft.searchBoardLoading = false;
        draft.searchBoardError = action.error;
      case LIKE_BOARD_REQUEST:
        draft.loadLikeLoading = true;
        draft.loadLikeDone = false;
        draft.loadLikeError = null;
        break;
      case LIKE_BOARD_SUCCESS:
        draft.loadLikeLoading = false;
        draft.loadLikeDone = true;
        draft.like = action.data;
        break;
      case LIKE_BOARD_FAILURE:
        draft.loadLikeDone = false;
        draft.loadLikeError = action.error;
        break;
      case LIKE_CLICK_BOARD_REQUEST:
        draft.setLikeLoading = true;
        draft.setLikeDone = false;
        draft.setLikeError = null;
        break;
      case LIKE_CLICK_BOARD_SUCCESS:
        draft.setLikeLoading = false;
        draft.setLikeDone = true;
        break;
      case LIKE_CLICK_BOARD_FAILURE:
        draft.setLikeDone = false;
        draft.setLikeError = action.error;
        break;
      case CREATE_COMMENT_REQUEST:
        draft.createCommentLoading = true;
        draft.createCommentDone = false;
        draft.createCommentError = null;
        break;
      case CREATE_COMMENT_SUCCESS:
        draft.createCommentLoading = false;
        draft.createCommentDone = true;
        break;
      case CREATE_COMMENT_FAILURE:
        draft.createCommentDone = false;
        draft.createCommentError = action.error;
        break;
      case DELETE_COMMENT_REQUEST:
        draft.deleteCommentLoading = true;
        draft.deleteCommentDone = false;
        draft.deleteCommentError = null;
        break;
      case DELETE_COMMENT_SUCCESS:
        draft.deleteCommentLoading = false;
        draft.deleteCommentDone = true;
        break;
      case DELETE_COMMENT_FAILURE:
        draft.deleteCommentDone = false;
        draft.deleteCommentError = action.error;
        break;
      case UPDATE_COMMENT_REQUEST:
        draft.updateCommentLoading = true;
        draft.updateCommentDone = false;
        draft.updateCommentError = null;
        break;
      case UPDATE_COMMENT_SUCCESS:
        draft.updateCommentLoading = false;
        draft.updateCommentDone = true;
        break;
      case UPDATE_COMMENT_FAILURE:
        draft.updateCommentDone = false;
        draft.updateCommentError = action.error;
        break;
      default:
        break;
    }
  });
export default reducer;
