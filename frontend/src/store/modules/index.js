import { combineReducers } from 'redux';
import user from './userModule';
import community from './communityModule';
import myPage from './myPageModule';
import course from './courseModule';
import theme from './themeModule';
import chat from './chatModule';
import meeting from './meetingModule';
import shop from './shopModule';
import logInTime from './logInTime';

const rootReducer = combineReducers({
  user,
  community,
  myPage,
  course,
  theme,
  chat,
  meeting,
  shop,
  logInTime,
});
export default rootReducer;
