/* eslint-disable */
import React, { useEffect, useState } from 'react';
import { getAchievementList } from '../../store/apis/myPageApi';

const UserAchievement = ({ user }) => {
  const [badgeList, setBadgeList] = useState([]);

  useEffect(() => {
    getAchievementList({ nickname: user.nickname })
      .then(res => setBadgeList(res.data))
      .catch(err => console.log(err));
  }, []);

  // console.log('획득 배지 목록', badgeList)

  return (
    <>
      <div>
        {badgeList.length > 0 && (
          <div>
            획득 배지 목록 만들어야됨!!
          </div>
        )}
      </div>
    </>
  )

};

export default UserAchievement;
