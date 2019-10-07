'use strict';

class Wallboard extends React.Component {

    state = {
        date: this.props.date,
        lookBackDays: 6
    }

    constructor(props) {
        super(props)
        this.changeDay = this.changeDay.bind(this)
        this.changeRange = this.changeRange.bind(this)
    }

    changeDay(byDays) {
        this.setState((state) => {
            const oldDate = new Date(state.date)
            const newDate = new Date(oldDate.getTime() + (byDays * 24 * 3600 * 1000))
            return {
                date: normalizedDate(newDate)
            }
        })
    }

    changeRange(byDays) {
        this.setState((state) => {
            return {
                lookBackDays: state.lookBackDays + byDays
            }
        })
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col py-5 text-center">
                        <h1 className="display-4 text-center">System availability on {this.state.date}</h1>
                        <div className="btn-group" role="group" aria-label="Date navigation">
                            <button type="button" onClick={this.changeDay.bind(this, 1)} className="btn btn-primary">Day +</button>
                            <button type="button" onClick={this.changeDay.bind(this, -1)} className="btn btn-primary">Day -</button>
                            <button type="button" onClick={this.changeRange.bind(this, 1)} className="btn btn-secondary">Range +</button>
                            <button type="button" onClick={this.changeRange.bind(this, -1)} className="btn btn-secondary">Range -</button>
                        </div>
                    </div>
                </div>
                <div className="row">
                    <div className="col-lg">
                        <SingleDayChart key={this.state.date} date={this.state.date} />
                    </div>
                    <div className="col-lg">
                        <DayByDayChart date={this.state.date} lookBackDays={this.state.lookBackDays} />
                    </div>
                </div>
            </div>
        )
    }
}

ReactDOM.render(<Wallboard date="2019-05-07" />, document.querySelector('#wallboard'))