/* eslint-disable */
import React, { Suspense, lazy } from 'react';
import { Route, Routes } from 'react-router-dom';
import ErrorPage from '../components/Error/ErrorPage';
import PrivateRoute from './PrivateRoute';
import LoadingSpin from 'react-loading-spin';

const MainPage = lazy(() => import('../pages/Main/Main'));
const LoginPage = lazy(() => import('../pages/Login/Login'));
const SignupPage = lazy(() => import('../pages/Signup/Signup'));
const MyPage = lazy(() => import('../pages/MyPage/MyPage'));
const IDSearchPage = lazy(() => import('../pages/Login/IDSearch'));
const PwdSearchPage = lazy(() => import('../pages/Login/PwdSearch'));
const PwdEditPage = lazy(() => import('../pages/Login/PwdEdit'));
const MapPage = lazy(() => import('../pages/Map/Map'));
const CreateArticlePage = lazy(() =>
  import('../pages/Community/CreateArticle'),
);
const UpdateArticlePage = lazy(() =>
  import('../pages/Community/UpdateArticle'),
);
const FreePage = lazy(() => import('../pages/Community/Free'));
const DetailArticlePage = lazy(() =>
  import('../pages/Community/DetailArticle'),
);
const EditUser = lazy(() => import('../pages/EditUser/EditUser'));
const UserDelete = lazy(() => import('../pages/Login/UserDelete'));
const UserSearch = lazy(() => import('../pages/UserSearch/UserSearch'));
const UserInfo = lazy(() => import('../pages/UserInfo/UserInfo'));
const ArticleListPage = lazy(() => import('../pages/Community/ArticleList'));
const ShopListPage = lazy(() => import('../pages/Shop/ShopList'));
const CreateShopPage = lazy(() => import('../pages/Shop/CreateShop'));
const CourseListPage = lazy(() => import('../pages/Course/CourseMain'));
const CourseDetailPage = lazy(() => import('../pages/Course/CourseDetail'));
const CreateCoursePage = lazy(() => import('../pages/Course/CourseCreate'));
const UpdateCoursePage = lazy(() => import('../pages/Course/CourseUpdate'));
const MeetingPage = lazy(() => import('../pages/MeetingPage/MeetingPage'));
const DetailShopPage = lazy(() => import('../pages/Shop/DetailShop'));
const UpdateShopPage = lazy(() => import('../pages/Shop/UpdateShop'));
const VideoPage = lazy(() => import('../pages/Video/Video'));

const Router = () => (
  <Suspense
    fallback={
      <div
        style={{
          display: 'flex',
          marginTop: '48vh',
          justifyContent: 'center',
          alignItems: 'center',
        }}
      >
        <LoadingSpin size="50px" primaryColor="#def1ff" />
      </div>
    }
  >
    <Routes>
      <Route path="/" element={<MainPage />} />
      <Route path="/user/login" element={<LoginPage />} />
      <Route path="/user/signup" element={<SignupPage />} />
      <Route path="/video" element={<VideoPage />} />
      <Route
        path="/user/edituser"
        element={
          <PrivateRoute>
            <EditUser />
          </PrivateRoute>
        }
      />
      <Route
        path="/user/mypage"
        element={
          <PrivateRoute>
            <MyPage />
          </PrivateRoute>
        }
      />
      <Route path="/user/findId" element={<IDSearchPage />} />
      <Route path="/user/findPwd" element={<PwdSearchPage />} />
      <Route
        path="/user/editPwd"
        element={
          <PrivateRoute>
            <PwdEditPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/user/search-user"
        element={
          <PrivateRoute>
            <UserSearch />
          </PrivateRoute>
        }
      />
      <Route
        path="/user/userinfo"
        element={
          <PrivateRoute>
            <UserInfo />
          </PrivateRoute>
        }
      />
      <Route
        path="/user/delete"
        element={
          <PrivateRoute>
            <UserDelete />
          </PrivateRoute>
        }
      />
      <Route
        path="/community/create"
        element={
          <PrivateRoute>
            <CreateArticlePage />
          </PrivateRoute>
        }
      />
      <Route
        path="/community/update"
        element={
          <PrivateRoute>
            <UpdateArticlePage />
          </PrivateRoute>
        }
      />
      <Route
        path="/map"
        element={
          <PrivateRoute>
            <MapPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/community/free/"
        element={
          <PrivateRoute>
            <FreePage />
          </PrivateRoute>
        }
      >
        <Route
          path=":boardCode"
          element={
            <PrivateRoute>
              <ArticleListPage />
            </PrivateRoute>
          }
        />
      </Route>
      <Route
        path="/community/free/detail"
        element={
          <PrivateRoute>
            <DetailArticlePage />
          </PrivateRoute>
        }
      />
      <Route
        path="/shop"
        element={
          <PrivateRoute>
            <ShopListPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/shop/detail"
        element={
          <PrivateRoute>
            <DetailShopPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/shop/update"
        element={
          <PrivateRoute>
            <UpdateShopPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/shop/create"
        element={
          <PrivateRoute>
            <CreateShopPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/course"
        element={
          <PrivateRoute>
            <CourseListPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/course/detail"
        element={
          <PrivateRoute>
            <CourseDetailPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/course/create"
        element={
          <PrivateRoute>
            <CreateCoursePage />
          </PrivateRoute>
        }
      />
      <Route
        path="/course/update"
        element={
          <PrivateRoute>
            <UpdateCoursePage />
          </PrivateRoute>
        }
      />
      <Route
        path="/meeting"
        element={
          <PrivateRoute>
            <MeetingPage />
          </PrivateRoute>
        }
      />
      <Route path="/*" element={<ErrorPage />} />
    </Routes>
  </Suspense>
);

export default Router;
