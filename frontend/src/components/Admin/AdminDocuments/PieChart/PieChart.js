import React, { useState, useEffect } from "react";
import Chart from "react-google-charts";


const PieChart = props => {

  /**
   * State for PieChart, only contains a title used for the pie chart and 
   * the plot points for the pie chart
   */
  const [data, setData] = useState({
    data: props.initialPoints,
    title:"Frequencies of each query",
  });

  /**
   * Once the component is being rendered we need to populate the graph
   * with something, initial points is used to pre-populate the data
   */
 useEffect(()=>{
  setData({title:"Frequencies of each query", data:props.initialPoints});
 },[props.initialPoints]);

const pieOptions = {
  title: data.title,
  sliceVisibilityThreshold: 0.08, // 20%,
  pieHole: 0.6,
  legend: {
    position: "bottom",
    alignment: "center",
    textStyle: {
      color: "233238",
      fontSize: 14
    }
  },
  tooltip: {
    showColorCode: true
  },
  chartArea: {
    left: 0,
    top: 0,
    width: "100%",
    height: "80%"
  },
  fontName: "Roboto"
};
    return (
      <div className="App">
        <Chart
  width={'800px'}
  height={'400px'}
  chartType="PieChart"
  loader={<div>Loading Chart</div>}
  data={data.data}
  options={pieOptions}
  rootProps={{ 'data-testid': '7' }}
  legend_toggle
/>
      </div>
    );
}

export default PieChart;
