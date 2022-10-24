import * as React from 'react';
import { useTheme } from '@mui/material/styles';
import { LineChart, Line, XAxis, YAxis, Label, ResponsiveContainer } from 'recharts';
import Title from '../Title';
import { useEffect, useState } from "react";



// const data = [
//   createData('00:00', 0),
//   createData('03:00', 300),
//   createData('06:00', 600),
//   createData('09:00', 800),
//   createData('12:00', 1500),
//   createData('15:00', 2000),
//   createData('18:00', 2400),
//   createData('21:00', 2400),
//   createData('24:00', undefined),
// ];

export default function Chart() {
  const theme = useTheme();
  const [data, setData] = useState([]);

  useEffect(() => {
    setData(arr => [...arr, { time: new Date().getSeconds(), amount:0 }]);
    setInterval(() => {
      fetchData();
    }, 1500);
  }, []);

  const fetchData = () => {
    fetch(`http://localhost:8790/bi-sales/gross-profit`)
      .then((response) => response.json())
      .then((responseData) => {
        setData(arr => [...arr, { time: new Date().getSeconds(), amount:parseInt(responseData.totalRevenue) }]);
        return parseInt(responseData.totalRevenue);
      })
      .catch(error => console.warn(error));

  };
  return (
    <React.Fragment>
      <Title>Today</Title>
      <ResponsiveContainer>
        <LineChart
          data={data}
          margin={{
            top: 16,
            right: 16,
            bottom: 0,
            left: 24,
          }}
        >
          <YAxis
            stroke={theme.palette.text.secondary}
            style={theme.typography.body2}
          >
            <Label
              angle={270}
              position="left"
              style={{
                textAnchor: 'middle',
                fill: theme.palette.text.primary,
                ...theme.typography.body1,
              }}
            >
              Sales ($)
            </Label>
          </YAxis>
          <Line
            isAnimationActive={false}
            type="monotone"
            dataKey="amount"
            stroke={theme.palette.primary.main}
            dot={false}
          />
        </LineChart>
      </ResponsiveContainer>
    </React.Fragment>
  );
}