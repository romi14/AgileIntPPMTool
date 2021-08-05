import React, { Component } from 'react'
import ProjectTask from './ProjectTasks/ProjectTask';

class Backlog extends Component {
    render() {
        const {project_tasks} = this.props;
        const tasks = project_tasks.map(project_task => {
            return <ProjectTask key={project_task.id} projectSequence = {project_task.projectSequence} priority={project_task.priority} acceptanceCriteria={project_task.acceptanceCriteria} summary={project_task.summary} status={project_task.status} id={project_task.projectIdentifier}/>
        })
        return (
                <div className="container">
                <div className="row">
                    <div className="col-md-4">
                        <div className="card text-center mb-2">
                            <div className="card-header bg-secondary text-white">
                                <h3>TO DO</h3>
                            </div>
                        </div>

                    {tasks.filter(task => task.props.status === "TO_DO")}

                    </div>
                    <div className="col-md-4">
                        <div className="card text-center mb-2">
                            <div className="card-header bg-primary text-white">
                                <h3>In Progress</h3>
                            </div>
                        </div>

                    {tasks.filter(task => task.props.status === "IN_PROGRESS")}

                    </div>
                    <div className="col-md-4">
                        <div className="card text-center mb-2">
                            <div className="card-header bg-success text-white">
                                <h3>Done</h3>
                            </div>
                        </div>

                    {tasks.filter(task => task.props.status === "DONE")}

                    </div>
                </div>
            </div>

        )
    }
}

export default Backlog;