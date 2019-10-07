class DayByDayChart extends React.Component {
    constructor(props) {
        super(props)
    }

    componentDidMount() {

        const { beginning, end } = this.props;

        fetch('http://localhost:8080/presentation/dayByDay', {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ beginning, end })
        })
        .then(response => response.json())
        .then(data => {
            var chart = new CanvasJS.Chart("day_by_day_chart", {
                animationEnabled: true,
                theme: "light2",
                title:{
                    text: `Last ${this.getNumberOfDays()} Days`
                },
                axisY:{
                    includeZero: false
                },
                data: [{        
                    type: "line",       
                    dataPoints: data.dayByDayAvailabilities.map(a => {
                        return {y: a.value}
                    })
                }]
            });
            chart.render();
        })
        .catch(() => {
            this.document.querySelector('#day_by_day_chart').innerHTML = `
                <h2 class="text-center">Last ${this.getNumberOfDays()} Days</h2>
                <h6 class="text-center">You are not allowed to see this chart</h6>
            `;
        })
    }

    getNumberOfDays() {
        const end = new Date(this.props.end)
        const beginning = new Date(this.props.beginning)
        return (end - beginning) / 86400000
    }

    render() {
        return (
            <div id="day_by_day_chart" style={{height: '370px', width: '100%'}}></div>
        )
    }
}