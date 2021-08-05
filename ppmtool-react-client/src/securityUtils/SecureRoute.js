import React from 'react'
import {Route,Redirect} from 'react-router-dom';
import {connect} from 'react-redux';
import PropTypes from 'prop-types';
import { Component } from 'react';

//here we are passing our specific Components and deciding whether they should be rendered or not on the basis of 'validToken' of 'security'
//Note - security is passed here through props using 'connect'..don't get confused just because it's a function based component not a class based one
const SecureRoute = ({component : Component, security, ...otherProps}) => ( //normal otherProps without '...' is also working here when I checked
    <Route {...otherProps} render = {props => security.validToken === true?(<Component {...props}/>) : (<Redirect to = "login"/>) 
    }/>

)

SecureRoute.propTypes = {
    security : PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    security : state.security
})

export default connect(mapStateToProps)(SecureRoute);