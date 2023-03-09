import { all, fork } from 'redux-saga/effects';
import communitySaga from './communitySaga';
import userSaga from './userSaga';
import myPageSaga from './myPageSaga';
import courseSaga from './courseSaga';
import meetingSaga from './meetingSaga';
import shopSaga from './shopSaga';
import chatSaga from './chatSaga';

export default function* rootSaga() {
  yield all([
    fork(userSaga),
    fork(communitySaga),
    fork(myPageSaga),
    fork(courseSaga),
    fork(meetingSaga),
    fork(shopSaga),
    fork(chatSaga),
  ]);
}
