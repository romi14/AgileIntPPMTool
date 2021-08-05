import React, { Component } from 'react';
import {Link} from 'react-router-dom';
import Backlog from './Backlog';
import {connect} from 'react-redux';
import PropTypes from 'prop-types';
import {getBacklog} from '../../actions/backlogActions';

class ProjectBoard extends Component {
    constructor(){
        super();
        this.state = {
            errors : {}
        }
    }

    componentDidMount(){
        this.props.getBacklog(this.props.match.params.id);
    }

    componentWillReceiveProps(nextProps){
        if(nextProps.errors){
            this.setState({errors:nextProps.errors});
        }
    }

    render() {
        const {id} = this.props.match.params;
        const {project_tasks} = this.props.backlog;
        const {errors} = this.state;
        let boardContent;
        let createProjectButton;
        const boardAlgo = (errors,project_tasks) => {
            if(project_tasks.length < 1){
                if(errors.projectIdentifier){
                    return(
                        <div className="alert alert-danger text-center" role="alert">
                            {errors.projectIdentifier}
                        </div>
                    )
                }
                else{
                    return(
                        <div className="alert alert-info text-center" role="alert">
                        {errors.projectNotFound}
                        </div>
                    )
                }
            }
            else{
                return  <Backlog project_tasks = {project_tasks}/>;
            }
        }

        boardContent = boardAlgo(errors,project_tasks);

        if(errors == null || !errors.projectIdentifier){
          createProjectButton = (
              <Link to={`/addProjectTask/${id}`} className="btn btn-primary mb-3">
                <i className="fas fa-plus-circle"> Create Project Task</i>
              </Link>
            )  
        }

        return (
            <div className="container">
                {createProjectButton}
                <br />
                <hr />
                {boardContent}
            </div>
        )
    }
}

ProjectBoard.propTypes = {
    backlog : PropTypes.object.isRequired,
    getBacklog : PropTypes.func.isRequired,
    errors : PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    backlog : state.backlog,
    errors : state.errors
})

export default connect(mapStateToProps,{getBacklog})(ProjectBoard);