import React, { Component } from 'react';
import PropTypes from 'prop-types';//From node_modules
import {connect} from 'react-redux';
import {createProject,getProject} from '../../actions/projectActions';
import classnames from 'classnames';    //Package for adding CSS classes according to conditions in our JSX elements like our input element
class UpdateProject extends Component {
    constructor(){
        super();
        this.state = {
            projectName: "",
            projectIdentifier: "",
            description: "",
            start_date: "",
            end_date: "",
            id:"",
            errors:{}
        }
        this.onChange = this.onChange.bind(this); 
        this.onSubmit = this.onSubmit.bind(this);       
    }

    onChange(e){
        this.setState({[e.target.name]:e.target.value})
    }

    onSubmit(e){
        e.preventDefault();
        const updateProject = {
            projectName: this.state.projectName,
            projectIdentifier: this.state.projectIdentifier,
            description: this.state.description,
            start_date: this.state.start_date,
            end_date: this.state.end_date,
            id:this.state.id
        }
        this.props.createProject(updateProject,this.props.history);
    }

    componentWillReceiveProps(nextProps){
        if(nextProps.errors){
            this.setState({errors:nextProps.errors});
        }
        const {
            projectName,
            projectIdentifier,
            description,
            start_date,
            end_date,            
            id
        } = nextProps.project;

        this.setState({   //Shortcut trick to set the state, just remember that variable name must be exactly as the state attr name
            projectName,
            projectIdentifier,
            description,
            start_date,
            end_date,            
            id
        })
    }

    componentDidMount(){
        this.props.getProject(this.props.match.params.id,this.props.history);
    }

    render() {
        const {errors} = this.state;    //this.props will also work here..I have no idea why this.state is working correctly here
        
        return (
        <div>
            <div className="project">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h5 className="display-4 text-center">Update Project form</h5>
                            <hr />
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input type="text" className={classnames("form-control form-control-lg ",{
                                        "is-invalid" : errors.projectName
                                    })} placeholder="Project Name" 
                                    name="projectName"
                                    value={this.state.projectName}
                                    onChange={this.onChange/*.bind(this)*/}  />
                                    {errors.projectName && (
                                        <div className="invalid-feedback">{errors.projectName}</div>
                                    )}
                                </div>
                                <div className="form-group">
                                    <input type="text" className={classnames("form-control form-control-lg",{
                                        "is-invalid" : errors.projectIdentifier
                                    })} placeholder="Unique Project ID"
                                    disabled
                                    name="projectIdentifier" value={this.state.projectIdentifier}  
                                    onChange={this.onChange}  />
                                    {errors.projectIdentifier && 
                                        (
                                            <div className="invalid-feedback">
                                                {errors.projectIdentifier}
                                            </div>
                                        )
                                    }
                                </div>
                                <div className="form-group">
                                    <textarea className={classnames("form-control form-control-lg",{
                                        "is-invalid" : errors.description
                                    })} placeholder="Project Description" name="description" value={this.state.description}
                                    onChange={this.onChange}></textarea>
                                    {errors.description && 
                                        (
                                            <div className="invalid-feedback">
                                                {errors.description}
                                            </div>
                                        )
                                    }
                                </div>
                                <h6>Start Date</h6>
                                <div className="form-group">
                                    <input type="date" className="form-control form-control-lg" name="start_date" value={this.state.start_date}
                                    onChange={this.onChange}/>
                                </div>
                                <h6>Estimated End Date</h6>
                                <div className="form-group">
                                    <input type="date" className="form-control form-control-lg" name="end_date" value={this.state.end_date}
                                    onChange={this.onChange}/>
                                </div>

                                <input type="submit" className="btn btn-primary btn-block mt-4" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
         </div>    
        )
    }
}

UpdateProject.propTypes = {    //Notice we imported PropTypes with a cap P but we use it as propTypes with a small p..this basically provides a constraint on something

    createProject : PropTypes.func.isRequired, //This is telling React that createProject is a required function and our code will not run properly without it
    errors : PropTypes.object.isRequired,
    getProject : PropTypes.func.isRequired,
    project : PropTypes.object.isRequired
}

const mapStateToProps = state => ({
  errors : state.errors,
  project : state.project.project
})

// const mapDispatchToProps = dispatch => ({
//     createProject : (project,history) => createProject(project,history),
//     getProject : (projectIdentifier) => getProject(projectIdentifier)
// })

export default connect(mapStateToProps,{getProject,createProject})(UpdateProject);