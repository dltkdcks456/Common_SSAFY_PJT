/* eslint-disable */
import { OpenVidu } from 'openvidu-browser';
import axios from 'axios';
import styled from 'styled-components';
import React, { Component } from 'react';
import UserVideoComponent from './UserVideoComponent';
import { useLocation } from 'react-router';
import { connect, useSelector } from 'react-redux';
import { BASE_URL, CONTEXT_URL } from '../../utils/urls';
import SubUserVideoComponent from './SubUserVideoComponent';
import mute from '../../assets/images/mute.png';
import unmute from '../../assets/images/unmute.png';
import videoOff from '../../assets/images/videoOff.png';
import videoOn from '../../assets/images/videoOn.png';
import exit from '../../assets/images/exit.png';

const StyledContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const APPLICATION_SERVER_URL = '';
const OPENVIDU_SERVER_SECRET = '';
class Video extends Component {
  constructor(props) {
    super(props);
    // console.log('프랍스', props);
    // console.log('샵샵', props.storeDetail.dealId);
    // console.log('유저유저', props.storeUser.user.nickname);
    // console.log('유저유저', props.storeMypage.userInfo.nickname);
    let inputSession;
    const inputMyUserName = props.storeUser.user.nickname;

    if (!props.storeDetail.dealId) {
      inputSession = 'user' + props.storeMypage.userInfo.user_id;
    } else {
      inputSession = props.storeDetail.dealId;
    }

    // These properties are in the state's component in order to re-render the HTML whenever their values change
    this.state = {
      mySessionId: 'Session' + inputSession,
      myUserName: inputMyUserName,
      session: undefined,
      mainStreamManager: undefined, // Main video of the page. Will be the 'publisher' or one of the 'subscribers'
      publisher: undefined,
      subscribers: [],
      videoOn: true,
      audioOn: true,
    };

    // console.log('1', props.storeDetail.dealId);
    // console.log('2', props.storeDetail.userNickname);
    // console.log('3', this.state.mySessionId);
    // console.log('4', this.state.myUserName);
    this.joinSession = this.joinSession.bind(this);
    this.leaveSession = this.leaveSession.bind(this);
    this.onOffVideo = this.onOffVideo.bind(this);
    this.onMute = this.onMute.bind(this);
    // this.switchCamera = this.switchCamera.bind(this);
    this.handleMainVideoStream = this.handleMainVideoStream.bind(this);
    this.onbeforeunload = this.onbeforeunload.bind(this);
  }

  componentDidMount() {
    window.addEventListener('beforeunload', this.onbeforeunload);
    // console.log('5', this.state.mySessionId);
    // console.log('6', this.state.myUserName);
    this.joinSession();
  }

  componentWillUnmount() {
    window.removeEventListener('beforeunload', this.onbeforeunload);
  }

  onbeforeunload(event) {
    this.leaveSession();
  }

  handleMainVideoStream(stream) {
    if (this.state.mainStreamManager !== stream) {
      this.setState({
        mainStreamManager: stream,
      });
    }
  }

  deleteSubscriber(streamManager) {
    let subscribers = this.state.subscribers;
    let index = subscribers.indexOf(streamManager, 0);
    if (index > -1) {
      subscribers.splice(index, 1);
      this.setState({
        subscribers: subscribers,
      });
    }
  }

  joinSession() {
    // --- 1) Get an OpenVidu object ---

    this.OV = new OpenVidu();

    // --- 2) Init a session ---

    this.setState(
      {
        session: this.OV.initSession(),
      },
      () => {
        var mySession = this.state.session;
        // console.log('마이 세션!!!', mySession)

        // --- 3) Specify the actions when events take place in the session ---

        // On every new Stream received...
        mySession.on('streamCreated', event => {
          // Subscribe to the Stream to receive it. Second parameter is undefined
          // so OpenVidu doesn't create an HTML video by its own
          var subscriber = mySession.subscribe(event.stream, undefined);
          var subscribers = this.state.subscribers;
          subscribers.push(subscriber);

          // Update the state with the new subscribers
          this.setState({
            subscribers: subscribers,
          });
        });

        // On every Stream destroyed...
        mySession.on('streamDestroyed', event => {
          // Remove the stream from 'subscribers' array
          this.deleteSubscriber(event.stream.streamManager);
        });

        // On every asynchronous exception...
        mySession.on('exception', exception => {
          console.warn(exception);
        });

        // --- 4) Connect to the session with a valid user token ---

        // Get a token from the OpenVidu deployment
        this.getToken().then(token => {
          // First param is the token got from the OpenVidu deployment. Second param can be retrieved by every user on event
          // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
          // console.log('유저이름', this.state.myUserName)
          mySession
            .connect(token, { clientData: this.state.myUserName })
            .then(async () => {
              // --- 5) Get your own camera stream ---

              // Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
              // element: we will manage it on our own) and with the desired properties
              let publisher = await this.OV.initPublisherAsync(undefined, {
                audioSource: undefined, // The source of audio. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
                publishVideo: true, // Whether you want to start publishing with your video enabled or not
                resolution: '640x480', // The resolution of your video
                frameRate: 30, // The frame rate of your video
                insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
                mirror: false, // Whether to mirror your local video or not
              });

              // --- 6) Publish your stream ---

              mySession.publish(publisher);

              // Obtain the current video device in use
              var devices = await this.OV.getDevices();
              var videoDevices = devices.filter(
                device => device.kind === 'videoinput',
              );
              var currentVideoDeviceId = publisher.stream
                .getMediaStream()
                .getVideoTracks()[0]
                .getSettings().deviceId;
              var currentVideoDevice = videoDevices.find(
                device => device.deviceId === currentVideoDeviceId,
              );

              // Set the main video in the page to display our webcam and store our Publisher
              this.setState({
                currentVideoDevice: currentVideoDevice,
                mainStreamManager: publisher,
                publisher: publisher,
              });
            })
            .catch(error => {
              console.log(
                'There was an error connecting to the session:',
                error.code,
                error.message,
              );
            });
        });
      },
      // console.log('캬하하핫', this.state.subscribers !== []),
    );
  }

  leaveSession() {
    // --- 7) Leave the session by calling 'disconnect' method over the Session object ---

    const mySession = this.state.session;

    if (mySession) {
      mySession.disconnect();
    }

    // Empty all properties...
    this.OV = null;
    this.setState({
      session: undefined,
      subscribers: [],
      mySessionId: '',
      myUserName: '',
      mainStreamManager: undefined,
      publisher: undefined,
    });
    window.location.replace(`${CONTEXT_URL}shop`);
  }

  onOffVideo(publisher) {
    if (!this.state.videoOn) {
      this.setState({
        videoOn: true,
      });
      this.state.publisher.publishVideo(true); // true to enable the video track, false to disable it
    } else {
      this.setState({
        videoOn: false,
      });
      this.state.publisher.publishVideo(false); // true to enable the video track, false to disable it
    }
  }

  onMute(publisher) {
    if (!this.state.audioOn) {
      this.setState({
        audioOn: true,
      });
      this.state.publisher.publishAudio(true); // true to enable the video track, false to disable it
    } else {
      this.setState({
        audioOn: false,
      });
      this.state.publisher.publishAudio(false); // true to enable the video track, false to disable it
    }
  }

  // async switchCamera() {
  //   try {
  //     const devices = await this.OV.getDevices();
  //     var videoDevices = devices.filter(device => device.kind === 'videoinput');

  //     if (videoDevices && videoDevices.length > 1) {
  //       var newVideoDevice = videoDevices.filter(
  //         device => device.deviceId !== this.state.currentVideoDevice.deviceId,
  //       );

  //       if (newVideoDevice.length > 0) {
  //         // Creating a new publisher with specific videoSource
  //         // In mobile devices the default and first camera is the front one
  //         var newPublisher = this.OV.initPublisher(undefined, {
  //           videoSource: newVideoDevice[0].deviceId,
  //           publishAudio: true,
  //           publishVideo: true,
  //           mirror: true,
  //         });

  //         //newPublisher.once("accessAllowed", () => {
  //         await this.state.session.unpublish(this.state.mainStreamManager);

  //         await this.state.session.publish(newPublisher);
  //         this.setState({
  //           currentVideoDevice: newVideoDevice[0],
  //           mainStreamManager: newPublisher,
  //           publisher: newPublisher,
  //         });
  //       }
  //     }
  //   } catch (e) {
  //     console.log('카메라 없지롱');
  //     console.error(e);
  //   }
  // }
  render() {
    // const { storeDetail, storeUser } = this.props;
    // const { mySessionId, myUserName } = this.state;
    // // handleChangeSessionId(storeDetail.dealId);
    // // handleChangeUserName(storeUser.user.nickname);
    // console.log('으악', this.state.mySessionId);
    // console.log('');
    // console.log('세션', this.state.session);
    // console.log('구독', this.state.subscribers.length == 0);
    // console.log('퍼브리셔', this.state.publisher);
    return (
      <div>
        {this.state.session === undefined ? <div>Loading...</div> : null}
        {this.state.session !== undefined &&
        this.state.subscribers.length == 0 ? (
          <div>
            <div style={{ position: 'relative' }}>
              {this.state.publisher !== undefined ? (
                <UserVideoComponent
                  streamManager={this.state.publisher}
                  myUserName={this.state.myUserName}
                />
              ) : null}
            </div>
          </div>
        ) : null}

        {this.state.session !== undefined &&
        this.state.subscribers.length > 0 ? (
          <div>
            <div style={{ position: 'relative' }}>
              {this.state.subscribers.map((sub, i) => (
                <UserVideoComponent streamManager={sub} />
              ))}
              {this.state.publisher !== undefined ? (
                <SubUserVideoComponent
                  streamManager={this.state.publisher}
                  myUserName={this.state.myUserName}
                />
              ) : null}
            </div>
          </div>
        ) : null}

        <div>
          <div style={{ height: '770px' }}></div>
          <div
            style={{
              display: 'flex',
              // justifyContent: 'center',
              marginLeft: '41%',
            }}
          >
            <div>
              {this.state.videoOn ? (
                <img
                  style={{
                    width: '40px',
                    marginLeft: '3rem',
                    cursor: 'pointer',
                  }}
                  src={videoOn}
                  onClick={() => this.onOffVideo()}
                ></img>
              ) : (
                <img
                  style={{
                    width: '40px',
                    marginLeft: '3rem',
                    cursor: 'pointer',
                  }}
                  src={videoOff}
                  onClick={() => this.onOffVideo()}
                ></img>
              )}
            </div>
            <div>
              {this.state.audioOn ? (
                <img
                  style={{
                    width: '40px',
                    marginLeft: '3rem',
                    cursor: 'pointer',
                  }}
                  src={unmute}
                  onClick={() => this.onMute()}
                ></img>
              ) : (
                <img
                  style={{
                    width: '40px',
                    marginLeft: '3rem',
                    cursor: 'pointer',
                  }}
                  src={mute}
                  onClick={() => this.onMute()}
                ></img>
              )}
            </div>
            <div>
              <img
                style={{ width: '40px', marginLeft: '3rem', cursor: 'pointer' }}
                src={exit}
                onClick={this.leaveSession}
              />
            </div>
          </div>
        </div>
      </div>
    );
  }

  /**
   * --------------------------------------------
   * GETTING A TOKEN FROM YOUR APPLICATION SERVER
   * --------------------------------------------
   * The methods below request the creation of a Session and a Token to
   * your application server. This keeps your OpenVidu deployment secure.
   *
   * In this sample code, there is no user control at all. Anybody could
   * access your application server endpoints! In a real production
   * environment, your application server must identify the user to allow
   * access to the endpoints.
   *
   * Visit https://docs.openvidu.io/en/stable/application-server to learn
   * more about the integration of OpenVidu in your application server.
   */
  async getToken() {
    // console.log(this.state.mySessionId);
    const sessionId = await this.createSession(this.state.mySessionId);

    return await this.createToken(sessionId);
  }

  async createSession(sessionId) {
    try {
      const response = await axios.post(
        APPLICATION_SERVER_URL + 'openvidu/api/sessions',
        { customSessionId: sessionId },
        {
          headers: {
            Authorization:
              'Basic ' + btoa('OPENVIDUAPP:' + OPENVIDU_SERVER_SECRET),
            'Content-Type': 'application/json',
          },
        },
      );
      return response.data.sessionId;
    } catch (err) {
      return sessionId;
    }

    // return response.data.sessionId; // The sessionId
  }

  async createToken(sessionId) {
    const response = await axios.post(
      APPLICATION_SERVER_URL +
        'openvidu/api/sessions/' +
        sessionId +
        '/connection',
      {},
      {
        headers: {
          Authorization:
            'Basic ' + btoa('OPENVIDUAPP:' + OPENVIDU_SERVER_SECRET),
          'Content-Type': 'application/json',
        },
      },
    );

    return response.data.token; // The token
  }
}

const mapStateToProps = state => ({
  storeMypage: state.user,
  storeDetail: state.shop.ShopDetail,
  storeUser: state.myPage,
});

export default connect(mapStateToProps)(Video);
