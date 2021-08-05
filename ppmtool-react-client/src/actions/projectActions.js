import axios from 'axios';
import { bindActionCreators } from 'redux';
import {DELETE_PROJECT, GET_ERRORS, GET_PROJECT, GET_PROJECTS} from './types';


export const createProject = (project,history) => async dispatch => {
    try {
        await axios.post("/api/project",project);
        history.push("/dashboard");
        dispatch({
            type : GET_ERRORS,
            payload : {}
        })
    } catch (error) {
        dispatch({
            type : GET_ERRORS,
            payload : error.response.data
        })
    }
}

export const getProjects = () => async dispatch => {
    try{
        const res = await axios.get("/api/project/all");
        dispatch({
            type : GET_PROJECTS,
            payload : res.data
        })
    }
    catch(error){
        dispatch({
            type : GET_ERRORS,
            payload : error.response
        })
    }
}

export const getProject = (id,history) => async dispatch => {
    try{
        const res = await axios.get(`/api/project/${id}`);
        dispatch({
            type:GET_PROJECT,
            payload:res.data
        })
    }
    catch(error){
        history.push("/dashboard"); //We are handling at backend what to do when user wants to update a project whose projectId does not exists..but it would not work for frontend and an empty form section will get opened when user tries to access update URL directly with wrong projectID..this is to prevent such a case
    }
}

export const deleteProject = (id) => async dispatch => {
    if(window.confirm("Are you sure? This will delete the project and all the data related to it")){
        await axios.delete(`/api/project/${id}`);
        dispatch({
            type:DELETE_PROJECT,
            payload:id
        })
    }
}