'use strict';

class Wallboard extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-lg">
                        <SingleDayChart date={this.props.date} />
                    </div>
                    <div className="col-lg">
                        <DayByDayChart beginning="2019-05-01" end="2019-05-07" />
                    </div>
                </div>
            </div>
        )
    }
}

ReactDOM.render(<Wallboard date="2019-05-03" />, document.querySelector('#wallboard'))