'use strict';

const header = ["Name", "Region", "Area", "Population", "Flag"];

class DataTable extends React.Component {
    render() {
        return (
            <div id="table_section">
                <h3>Countries Sharing Borders With China</h3>
                <table class="hover">
                    <thead>
                    <tr>{header.map((h, i) => <th key={i}>{h}</th>)}</tr>
                    </thead>
                    <tbody>
                    {Object.keys(dashboard.countriesWithChinaBorder).map((k, i) => {
                        let country = dashboard.countriesWithChinaBorder[k];
                        return (
                            <tr key={i}>
                                <td>{country.name}</td>
                                <td>{country.region}</td>
                                <td>{country.area}</td>
                                <td>{country.population}</td>
                                <td><img crossOrigin="anonymous" src={country.flag} width="50" height="50"/></td>
                            </tr>
                        );
                    })}
                    </tbody>
                </table>

            </div>
        );
    }
}

ReactDOM.render(<DataTable/>, document.querySelector('#table_container')
);

