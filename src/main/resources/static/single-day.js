'use strict';

function LastMonthChangePolygon(props) {
    if (props.change > 0) {
        return <polygon points="100,0 200,200 0,200" transform="translate(200,100) scale(0.1)" className="triangle-up" />
    }
    else if (props.change < 0) {
        return <polygon points="100,0 200,200 0,200" transform="translate(200,100) scale(0.1) rotate(180,100,100)" className="triangle-down" />
    }
    return <polygon points="0,0 200,0 200,200 0,200" transform="translate(200,100) scale(0.1) rotate(180,100,100)" className="rectangle" />
}

function RoundChart(props) {
    const dashoffset = 350 * (100 - props.percent) / 100;
    const roomStatistics = props.roomStatistics ? `${props.roomStatistics.qtyRoomsIssues} issues in ${props.roomStatistics.qtyRooms} rooms` : ''
    return (
        <svg xmlns="http://www.w3.org/2000/svg" className="single-day-chart" width="400" height="200" viewBox="0 0 400 200">
            <path className="bg" stroke="#ccc" d="M41 149.5a77 77 0 1 1 117.93 0"  fill="none"/>
            <path className="meter" stroke={props.color} d="M41 149.5a77 77 0 1 1 117.93 0" fill="none" strokeDasharray="350" strokeDashoffset={dashoffset} />
            <text x="50%" y="45%" dominantBaseline="middle" textAnchor="middle" className="percentage" transform="translate(-100,0)">{props.percent}%</text>
            <text x="50%" y="60%" dominantBaseline="middle" textAnchor="middle" className="description" transform="translate(-100,0)">Availability</text>
            <LastMonthChangePolygon change={props.lastMonthChange} />
            <text x="56%" y="58%" className="last-month-change">{props.lastMonthChange}% Last Month</text>
            <text x="50%" y="68%" className="details">{roomStatistics}</text>
        </svg>
    )
}

class SingleDayChart extends React.Component {

    state = {
        show: false,
        total: {
            percent: 0,
            lastMonthChange: 0
        },
        locations: []
    }

    constructor(props) {
        super(props)
        this.getLocationCharts = this.getLocationCharts.bind(this)
    }

    componentDidMount() {
        this.fetchData()
    }

    fetchData() {
        const date = this.props.date;
        const that = this;
        fetch('/presentation/singleDay', {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({date})
        })
        .then(response => response.json())
        .then(data => {
            that.setState({
                show: true,
                total: {
                    percent: data.total.index.value,
                    lastMonthChange: data.total.lastMonthDifference.difference.value
                },
                locations: Object.entries(data.locationAvailabilities).map(e => {
                    return {
                        name: e[0],
                        percent: e[1].index.value,
                        lastMonthChange: e[1].lastMonthDifference.difference.value,
                        roomStatistics: {
                            qtyRooms: e[1].roomStatistics.quantityOfRooms,
                            qtyRoomsIssues: e[1].roomStatistics.quantityOfRoomIssues
                        }
                    }
                })
            })
        })
    }

    getLocationCharts() {
        return this.state.locations.map((location) => 
            <div key={location.name} className={`row align-items-center`}>
                <div className="col">
                    <h6 className="text-right">{location.name}</h6>
                </div>
                <div className="col">
                    <RoundChart percent={location.percent} lastMonthChange={location.lastMonthChange} roomStatistics={location.roomStatistics} color="#0dd" />
                </div>
            </div>
        )
    }

    render() {
        if (!this.state.show) {
            return (
                <div className={`single-day-container`}>
                    <h2 className="text-center">Today</h2>
                    <h6 className="text-center">You are not allowed to see this chart</h6>
                </div>
            )
        }
        return (
            <div className={`single-day-container`}>
                <h2 className="text-center">Today</h2>
                <div className={`row align-items-center`}>
                    <div className="col">
                        <RoundChart percent={this.state.total.percent} lastMonthChange={this.state.total.lastMonthChange} color="#09c" />
                    </div>
                </div>
                {this.getLocationCharts()}
            </div>
        )
    }
}