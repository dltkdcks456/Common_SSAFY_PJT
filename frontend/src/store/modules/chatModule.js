/* eslint-disable consistent-return */
/* eslint-disable default-param-last */

import produce from 'immer';

const OPEN_CHAT = 'CHAT/OPEN_CHAT';
const CLOSE_CHAT = 'CHAT/CLOSE_CHAT';
const OPEN_CHAT_LIST = 'CHAT/OPEN_CHAT_LIST';
// 채팅방 목록
export const CHAT_LIST_REQUEST = 'CHAT_LIST_REQUEST';
export const CHAT_LIST_SUCCESS = 'CHAT_LIST_SUCCESS';
export const CHAT_LIST_FAILURE = 'CHAT_LIST_FAILURE';

const initialState = {
  isOpenChat: false,
  roomId: null,
  users: null,
  memberId: null,
  // 채팅방 목록
  chatListRequest: false,
  chatListDone: false,
  chatListError: false,
  chatList: [],
};

export const openChat = (roomId, users, memberId) => ({
  type: OPEN_CHAT,
  roomId,
  users,
  memberId,
});

export const closeChat = () => ({
  type: CLOSE_CHAT,
});

export const openChatList = () => ({
  type: OPEN_CHAT_LIST,
});

const chat = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case OPEN_CHAT:
        draft.isOpenChat = true;
        draft.roomId = action.roomId;
        draft.users = action.users;
        draft.memberId = action.memberId;
        break;
      case CLOSE_CHAT:
        draft.isOpenChat = false;
        draft.roomId = null;
        draft.users = null;
        draft.memberId = null;
        break;
      case OPEN_CHAT_LIST:
        draft.isOpenChat = true;
        draft.roomId = null;
        draft.users = null;
        draft.memberId = null;
        break;
      case CHAT_LIST_REQUEST:
        draft.chatListRequest = true;
        draft.chatListDone = false;
        draft.chatListError = null;
        break;
      case CHAT_LIST_SUCCESS:
        draft.chatListRequest = false;
        draft.chatListDone = true;
        draft.chatList = action.data;
        break;
      case CHAT_LIST_FAILURE:
        draft.chatListRequest = false;
        draft.chatListError = action.error;
        break;

      default:
        break;
    }
  });

export default chat;
