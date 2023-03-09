/* eslint-disable */
import axios from 'axios';
import { BASE_URL } from '../../utils/urls';

// 자유게시판 전체 조회
export async function FreeBoardAPI({ page, boardCode }) {
  const result = await axios.get(`${BASE_URL}board/${boardCode}?page=${page}`);
  // console.log(result.data);
  return result.data;
}

// 게시판 상세 조회
export async function FreeDetailAPI({ boardId, boardCode, user }) {
  const result = await axios.get(
    `${BASE_URL}board/${boardCode}/?boardId=${boardId}`,
    { headers: { userId: user.user_id } },
  );
  // console.log(result.data);
  return result.data;
}

// 게시판 검색
export async function searchBoardAPI({ keyword, page, boardCode }) {
  // console.log(keyword, page, boardCode);
  const result = await axios.get(
    `${BASE_URL}board/search?keyword=${keyword}&page=${page}&boardCode=${boardCode}`,
  );

  return result.data;
}

// 게시판 생성
export async function createBoardAPI({ title, content, board_code, token }) {
  const result = await axios.post(`${BASE_URL}board/`, {
    board_code,
    token,
    content,
    title,
  });
  // console.log('더미 완성', result);
  return result.data;
}

// 게시판 수정
export async function updateBoardAPI({ board_id, title, content, token }) {
  const result = await axios.put(`${BASE_URL}board/`, {
    board_id,
    title,
    content,
    token,
  });
  return result.data;
}

// 게시판 삭제
export async function onDeleteArticleAPI({ boardId }) {
  await axios.delete(`${BASE_URL}board/${boardId}`);
}

// 게시판 좋아요 조회
export async function likeBoardAPI({ boardId, userId }) {
  // console.log('API', boardId, userId);
  // console.log(typeof boardId);
  // console.log(typeof userId);
  const result = await axios.get(`${BASE_URL}board/like/`, {
    params: { boardId, userId },
  });
  // console.log(result);
  return result.data;
}

// 게시판 좋아요 클릭
export async function likeClickBoardAPI({ board_id, user_id }) {
  // console.log('좋아요오', board_id, user_id);
  const result = await axios.post(`${BASE_URL}board/like/`, {
    board_id,
    user_id,
  });
  return result.data;
}

// 댓글 생성
export async function createCommentAPI({ board_id, user_id, content }) {
  const result = await axios.post(`${BASE_URL}board/comment/`, {
    board_id,
    user_id,
    content,
  });
  return result.data;
}

// 댓글 삭제
export async function deleteCommentAPI({ commentId }) {
  // console.log(commentId);
  const result = await axios.delete(`${BASE_URL}board/comment/${commentId}`);
  return result.data;
}

// 댓글 수정
export async function updateCommentAPI({ comment_id, content }) {
  const result = await axios.put(`${BASE_URL}board/comment/`, {
    comment_id,
    content,
  });
  return result.data;
}
