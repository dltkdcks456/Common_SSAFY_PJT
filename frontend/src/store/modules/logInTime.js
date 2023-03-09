/* eslint-disable */
import produce from 'immer';

const initialState = {
  logInTime: 0,
};

// 로그인 시간 저장
export const TIME_REQUEST = 'TIME_REQUEST';

const reducer = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case TIME_REQUEST:
        draft.logInTime = action.data.now;
      default:
        break;
    }
  });

export default reducer;
