import {createStore, applyMiddleware,compose} from 'redux';
import thunk from 'redux-thunk';
import rootReducer from './reducers';

const initialState = {

}

const middleware = [thunk];

const ReactReduxDevTools = window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__();//We have to add these only for dev. env., for production(also for some browsers even in dev. env.) they don't even exist and will throw errors...4 famous browsers that can run these extensions are chrome, mozilla, safari, microsoft edge

let store;

if(window.navigator.userAgent.includes("Chrome")&&ReactReduxDevTools){
    store = createStore(rootReducer,initialState,compose(applyMiddleware(...middleware),ReactReduxDevTools/*, window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__() */));//DEVTOOLS_EXTENSION might burst in any other browser
}
else{
    store = createStore(rootReducer,initialState,compose(applyMiddleware(...middleware)));
}

export default store;