window.onload = function () {

    fetch('http://localhost:8080/presentation/dayByDay', {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            beginning: "2019-05-01",
            end: "2019-05-07"
        })
    })
    .then(response => response.json())
    .then(data => {
        var chart = new CanvasJS.Chart("day_by_day_chart", {
            animationEnabled: true,
            theme: "light2",
            title:{
                text: "Last 6 Days"
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
            <h2 class="text-center">Last 6 Days</h2>
            <h6 class="text-center">You are not allowed to see this chart</h6>
        `;
    })
    
}