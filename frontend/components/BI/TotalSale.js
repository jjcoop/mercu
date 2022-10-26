import * as React from "react";
import Typography from "@mui/material/Typography";
import Title from "../Title";
import { useEffect, useState } from "react";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";

function currencyFormat(num) {
  const new_num = Number(num);
  return "$" + new_num.toFixed(2).replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
}

export default function TotalSale() {
  const [grossText, setGrossText] = "";
  const [data, setData] = useState([]);
  const [date, setDate] = useState("");
  const fetchData = () => {
    fetch(
      `http://${process.env.NEXT_PUBLIC_DB_HOST}:8790/bi-sales/gross-profit`
    )
      .then((response) => response.json())
      .then((data) => setData(data.totalRevenue))
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    setInterval(() => {
      fetchData();
      setDate(new Date().toLocaleString());
    }, 1000);
  }, []);

  return (
    <div>
      <React.Fragment>
        <Title>Gross Sales</Title>
        <Typography component="p" variant="h4">
          {currencyFormat(data)}
        </Typography>
        <Typography id="d" color="text.secondary" sx={{ flex: 1 }}>
          {date}
        </Typography>
      </React.Fragment>
    </div>
  );
}
