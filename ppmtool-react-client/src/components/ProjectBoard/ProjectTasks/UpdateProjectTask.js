import React, { Component } from 'react'
import { Link } from 'react-router-dom';
import {connect} from 'react-redux';
import classnames from 'classnames';
import {updateProjectTask,getProjectTask} from '../../../actions/backlogActions';
import PropTypes from 'prop-types';

class UpdateProjectTask extends Component {

    constructor(props){
        super(props);
        const {id} = this.props.match.params;
        this.state = {
            summary : "",
            acceptanceCriteria : "",
            status : "",
            priority : 0,
            dueDate : "",
            id : "",
            projectIdentifier : id,
            errors : {},
            projectSequence : "",
            create_At : ""
        };
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onChange(e){
        this.setState({[e.target.name]:e.target.value});
    }

    onSubmit(e){
        e.preventDefault();

        const newTask = {
            summary : this.state.summary,
            acceptanceCriteria : this.state.acceptanceCriteria,
            status : this.state.status,
            priority : this.state.priority,
            dueDate : this.state.dueDate,
            id : this.state.id,
            projectSequence : this.state.projectSequence,
            projectIdentifier : this.state.projectIdentifier,
            create_At : this.state.create_At
        }

        this.props.updateProjectTask(this.state.projectIdentifier,this.state.projectSequence,newTask,this.props.history);  //remember we get history here because we come to this page using 'Link' of react-router-dom which passes the history as a prop
    }

    componentWillReceiveProps(nextProps){
        if(nextProps.errors){
            this.setState({errors:nextProps.errors});
        }
        const {
            summary,
            acceptanceCriteria,
            status,
            priority,
            dueDate,
            id,
            projectSequence,
            create_At
        } = nextProps.project_task;

        this.setState({   
            summary,
            acceptanceCriteria,
            status,
            priority,
            dueDate,
            id,
            projectSequence,
            create_At
        })
    }

    componentDidMount(){
        this.props.getProjectTask(this.props.match.params.id,this.props.match.params.ptsequence,this.props.history);
    }

    render() {
        const {id} = this.props.match.params;
        const {errors} = this.state;
        return (
            <div className="add-PBI">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <Link to={`/projectBoard/${id}`} className="btn btn-light">
                                Back to Project Board
                            </Link>
                            <h4 className="display-4 text-center">Add /Update Project Task</h4>
                            <p className="lead text-center">Project Name + Project Code</p>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input type="text" className={classnames("form-control form-control-lg",{
                                        "is-invalid" : errors.summary
                                    })} name="summary" placeholder="Project Task summary" value={this.state.summary} onChange={this.onChange}/>
                                    {
                                        errors.summary && (
                                            <div className="invalid-feedback">
                                                {errors.summary}
                                            </div>
                                        )
                                    }
                                </div>
                                <div className="form-group">
                                    <textarea className="form-control form-control-lg" placeholder="Acceptance Criteria" name="acceptanceCriteria" value={this.state.acceptanceCriteria} onChange={this.onChange}></textarea>
                                </div>
                                <h6>Due Date</h6>
                                <div className="form-group">
                                    <input type="date" className="form-control form-control-lg" name="dueDate" value={this.state.dueDate} onChange={this.onChange} />
                                </div>
                                <div className="form-group">
                                    <select className="form-control form-control-lg" name="priority" value={this.state.priority} onChange={this.onChange}>
                                        <option value={0}>Select Priority</option>
                                        <option value={1}>High</option>
                                        <option value={2}>Medium</option>
                                        <option value={3}>Low</option>
                                    </select>
                                </div>

                                <div className="form-group">
                                    <select className="form-control form-control-lg" name="status" value={this.state.status} onChange={this.onChange}>
                                        <option value="">Select Status</option>
                                        <option value="TO_DO">TO DO</option>
                                        <option value="IN_PROGRESS">IN PROGRESS</option>
                                        <option value="DONE">DONE</option>
                                    </select>
                                </div>

                                <input type="submit" className="btn btn-primary btn-block mt-4" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

UpdateProjectTask.propTypes = {
    updateProjectTask:PropTypes.func.isRequired,
    errors:PropTypes.object.isRequired,
    getProjectTask : PropTypes.func.isRequired,
    project_task : PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    errors : state.errors,
    project_task : state.backlog.project_task
})

export default connect(mapStateToProps,{updateProjectTask,getProjectTask})(UpdateProjectTask);