/* eslint-disable */
import {
  all,
  call,
  fork,
  getContext,
  put,
  takeLatest,
} from '@redux-saga/core/effects';
import Swal from 'sweetalert2';
import { CONTEXT_URL } from '../../utils/urls';
import {
  createBoardAPI,
  createCommentAPI,
  deleteCommentAPI,
  FreeBoardAPI,
  FreeDetailAPI,
  likeBoardAPI,
  likeClickBoardAPI,
  searchBoardAPI,
  updateBoardAPI,
  updateCommentAPI,
} from '../apis/communityApi';
import {
  CREATE_BOARD_FAILURE,
  CREATE_BOARD_REQUEST,
  CREATE_BOARD_SUCCESS,
  CREATE_COMMENT_FAILURE,
  CREATE_COMMENT_REQUEST,
  CREATE_COMMENT_SUCCESS,
  DELETE_COMMENT_FAILURE,
  DELETE_COMMENT_REQUEST,
  DELETE_COMMENT_SUCCESS,
  LIKE_BOARD_FAILURE,
  LIKE_BOARD_REQUEST,
  LIKE_BOARD_SUCCESS,
  LIKE_CLICK_BOARD_FAILURE,
  LIKE_CLICK_BOARD_REQUEST,
  LIKE_CLICK_BOARD_SUCCESS,
  LOAD_FREE_BOARD_FAILURE,
  LOAD_FREE_BOARD_REQUEST,
  LOAD_FREE_BOARD_SUCCESS,
  // LOAD_FREE_DETAIL_FAILURE,
  // LOAD_FREE_DETAIL_REQUEST,
  // LOAD_FREE_DETAIL_SUCCESS,
  SEARCH_BOARD_FAILURE,
  SEARCH_BOARD_REQUEST,
  SEARCH_BOARD_SUCCESS,
  UPDATE_BOARD_FAILURE,
  UPDATE_BOARD_REQUEST,
  UPDATE_BOARD_SUCCESS,
  UPDATE_COMMENT_FAILURE,
  UPDATE_COMMENT_REQUEST,
  UPDATE_COMMENT_SUCCESS,
} from '../modules/communityModule';

// 자유게시판 목록
function* getFreeBoardList(action) {
  try {
    const result = yield call(FreeBoardAPI, action.data);
    yield put({
      type: LOAD_FREE_BOARD_SUCCESS,
      data: result,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: LOAD_FREE_BOARD_FAILURE,
    });
  }
}

function* watchFreeBoardList() {
  yield takeLatest(LOAD_FREE_BOARD_REQUEST, getFreeBoardList);
}
// 자유게시판 상세
// function* getFreeBoardDetail(action) {
//   try {
//     const result = yield call(FreeDetailAPI, action.data);
//     yield put({
//       type: LOAD_FREE_DETAIL_SUCCESS,
//       data: result,
//     });
//   } catch (error) {
//     console.log(error);
//     yield put({
//       type: LOAD_FREE_DETAIL_FAILURE,
//     });
//   }
// }

// function* watchFreeBoardDetail() {
//   yield takeLatest(LOAD_FREE_DETAIL_REQUEST, getFreeBoardDetail);
// }

// 게시글 작성
function* createBoard(action) {
  try {
    yield call(createBoardAPI, action.data);
    yield put({
      type: CREATE_BOARD_SUCCESS,
    });
    const navigate = action.data.navigate;
    navigate(`/community/free/${action.data.board_code}`);
  } catch (error) {
    // console.log(error);
    yield put({
      type: CREATE_BOARD_FAILURE,
    });
  }
}

function* watchCreateBoard() {
  yield takeLatest(CREATE_BOARD_REQUEST, createBoard);
}

// 게시글 수정
function* updateBoard(action) {
  try {
    yield call(updateBoardAPI, action.data);
    yield put({
      type: UPDATE_BOARD_SUCCESS,
    });
    const navigate = action.data.navigate;
    navigate(`/community/free/detail`, {
      state: {
        boardId: action.data.board_id,
        boardCode: action.data.boardCode,
      },
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: UPDATE_BOARD_FAILURE,
    });
  }
}

function* watchUpdateBoard() {
  yield takeLatest(UPDATE_BOARD_REQUEST, updateBoard);
}

// 게시글 검색
function* searchBoard(action) {
  try {
    const result = yield call(searchBoardAPI, action.data);
    yield put({
      type: SEARCH_BOARD_SUCCESS,
      data: result,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: SEARCH_BOARD_FAILURE,
    });
  }
}

function* watchSearchBoard() {
  yield takeLatest(SEARCH_BOARD_REQUEST, searchBoard);
}

// 게시글 좋아요 조회
function* likeBoard(action) {
  try {
    const result = yield call(likeBoardAPI, action.data);
    yield put({
      type: LIKE_BOARD_SUCCESS,
      data: result,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: LIKE_BOARD_FAILURE,
    });
  }
}

function* watchLikeBoard() {
  yield takeLatest(LIKE_BOARD_REQUEST, likeBoard);
}

// 게시글 좋아요 클릭
function* likeClickBoard(action) {
  try {
    yield call(likeClickBoardAPI, action.data);
    yield put({
      type: LIKE_CLICK_BOARD_SUCCESS,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: LIKE_CLICK_BOARD_FAILURE,
    });
  }
}

function* watchLikeClickBoard() {
  yield takeLatest(LIKE_CLICK_BOARD_REQUEST, likeClickBoard);
}

// 댓글 작성
function* createComment(action) {
  try {
    yield call(createCommentAPI, action.data);
    yield put({
      type: CREATE_COMMENT_SUCCESS,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: CREATE_COMMENT_FAILURE,
    });
  }
}

function* watchCreateComment() {
  yield takeLatest(CREATE_COMMENT_REQUEST, createComment);
}

// 댓글 삭제
function* deleteComment(action) {
  try {
    yield call(deleteCommentAPI, action.data);
    yield put({
      type: DELETE_COMMENT_SUCCESS,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: DELETE_COMMENT_FAILURE,
    });
  }
}

function* watchDeleteComment() {
  yield takeLatest(DELETE_COMMENT_REQUEST, deleteComment);
}

// 댓글 수정
function* updateComment(action) {
  try {
    yield call(updateCommentAPI, action.data);
    yield put({
      type: UPDATE_COMMENT_SUCCESS,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: UPDATE_COMMENT_FAILURE,
    });
  }
}

function* watchUpdateComment() {
  yield takeLatest(UPDATE_COMMENT_REQUEST, updateComment);
}

export default function* communitySaga() {
  yield all([
    fork(watchFreeBoardList),
    // fork(watchFreeBoardDetail),
    fork(watchCreateBoard),
    fork(watchSearchBoard),
    fork(watchUpdateBoard),
    fork(watchLikeBoard),
    fork(watchLikeClickBoard),
    fork(watchCreateComment),
    fork(watchDeleteComment),
    fork(watchUpdateComment),
  ]);
}
