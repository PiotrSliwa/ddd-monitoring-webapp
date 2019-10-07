'use strict';

class Wallboard extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col py-5">
                        <h1 className="display-4 text-center">System availability on {this.props.date}</h1>
                    </div>
                </div>
                <div className="row">
                    <div className="col-lg">
                        <SingleDayChart date={this.props.date} />
                    </div>
                    <div className="col-lg">
                        <DayByDayChart date={this.props.date} lookBackDays="6" />
                    </div>
                </div>
            </div>
        )
    }
}

ReactDOM.render(<Wallboard date="2019-05-07" />, document.querySelector('#wallboard'))