import {GET_ERRORS} from '../actions/types';

const initialState = {
    
};

export default function(state = initialState, action){
    switch(action.type){ 

        case GET_ERRORS:
            return action.payload;  //Since errors will always get updated or will be new..just passing the payload..no requirement of the state
        default :
            return state;
    }
}