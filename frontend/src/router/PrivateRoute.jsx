import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { LOG_OUT_REQUEST } from '../store/modules/userModule';

const PrivateRoute = ({ children }) => {
  const dispatch = useDispatch();
  const { myPageDone } = useSelector(state => state.myPage);
  const navigate = useNavigate();
  const userToken = sessionStorage.getItem('userToken');
  const { logInTime } = useSelector(state => state.logInTime);
  useEffect(() => {
    const now = new Date();
    if (!myPageDone) {
      navigate('/user/login');
    }
    if (logInTime <= now || !userToken) {
      dispatch({
        type: LOG_OUT_REQUEST,
        data: { navigate },
      });
      navigate('/user/login');
    }
  }, [userToken]);

  return myPageDone ? children : <div>Loading...</div>;
};

export default PrivateRoute;
