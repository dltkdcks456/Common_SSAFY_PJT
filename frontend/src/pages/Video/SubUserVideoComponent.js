/* eslint-disable */
import React, { Component } from 'react';
import SubOpenViduVideoComponent from './SubOvVideo';
import styled from 'styled-components';

const StyledContainer = styled.div`
  position: absolute;
  margin-left: 24%;
  margin-top: 2rem;
`;

export default class SubUserVideoComponent extends Component {
  render() {
    return (
      <div>
        {this.props.streamManager !== undefined ? (
          <StyledContainer>
            <SubOpenViduVideoComponent
              streamManager={this.props.streamManager}
            />
          </StyledContainer>
        ) : null}
      </div>
    );
  }
}
