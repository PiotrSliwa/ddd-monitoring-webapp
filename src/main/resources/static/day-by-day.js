class DayByDayChart extends React.Component {
    constructor(props) {
        super(props)
        this.getLookBackDate = this.getLookBackDate.bind(this)
        this.container = React.createRef()
    }

    componentDidMount() {

        const that = this

        fetch('http://localhost:8080/presentation/dayByDay', {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ 
                beginning: that.getLookBackDate(),
                end: that.props.date
            })
        })
        .then(response => response.json())
        .then(data => {
            var chart = new CanvasJS.Chart("day_by_day_chart", {
                animationEnabled: true,
                theme: "light2",
                title:{
                    text: `Last ${this.props.lookBackDays} Days`
                },
                axisY:{
                    includeZero: false
                },
                data: [{        
                    type: "line",       
                    dataPoints: data.dayByDayAvailabilities.map(a => {
                        return {y: a ? a.value : null}
                    })
                }]
            });
            chart.render()
        })
        .catch(() => {
            that.container.current.innerHTML = `
                <h2 class="text-center">Last ${this.props.lookBackDays} Days</h2>
                <h6 class="text-center">You are not allowed to see this chart</h6>
            `;
        })
    }

    getLookBackDate() {
        const withLeadingZero = num => `${num < 10 ? '0' : ''}${num}`
        const end = new Date(this.props.date)
        const lookBackDate = new Date(end - this.props.lookBackDays * 24 * 3600 * 1000)
        return `${lookBackDate.getFullYear()}-${withLeadingZero(lookBackDate.getMonth() + 1)}-${withLeadingZero(lookBackDate.getDate())}`
    }

    render() {
        return (
            <div id="day_by_day_chart" ref={this.container} style={{height: '370px', width: '100%'}}></div>
        )
    }
}