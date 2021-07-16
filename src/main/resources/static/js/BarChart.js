'use strict';

const e = React.createElement;

class BarChart extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const data = {
            labels: dashboard.topCountries.names,
            datasets: [{
                label: 'Top ' + dashboard.topCountries.maxSize + ' countries with the most borders',
                backgroundColor: 'rgb(255, 99, 132)',
                borderColor: 'rgb(255, 99, 132)',
                data: dashboard.topCountries.borderCounts,
            }]
        };
        const config = {
            type: 'bar',
            data,
            options: {}
        };

        new Chart(
            document.getElementById('barChart'),
            config
        );
        return "";
    }
}

ReactDOM.render(e(BarChart), document.querySelector('#barChart'));