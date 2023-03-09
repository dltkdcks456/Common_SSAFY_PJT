/* eslint-disable react/destructuring-assignment */
import React from 'react';
// import { imageGetAPI } from '../../store/apis/userApi';
import { BASE_URL } from '../../utils/urls';

const UserImage = ({ imagePath }) => {
  const url = `${BASE_URL}user/imageDownloadBy/${imagePath}`;
  return (
    <div>
      <img src={url} alt="프로필 사진" />
    </div>
  );
};

export default UserImage;
