import * as React from "react";
import { useEffect, useState } from "react";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import Typography from "@mui/material/Typography";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import Title from "../Title";
import { DataGrid } from "@mui/x-data-grid";
import { useTheme } from "@mui/material/styles";
import Container from "@mui/material/Container";

import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Label,
  ResponsiveContainer,
} from "recharts";

function encodeThatProduct(productString) {
  const returnField = encodeURIComponent(productString);
  return returnField;
}

export default function LookupSale() {
  const [inputValue, setInputValue] = React.useState("");
  const [productList, setProductList] = useState([]);
  const [date, setDate] = useState("");
  const [productName, setProductName] = useState("Product Realtime Revenue");
  const [salesAmount, setSalesAmount] = useState();
  const [product, setProduct] = useState("");
  const [check, setCheck] = useState(true);

  const [tableData, setTableData] = useState([]);

  const theme = useTheme();
  const [data, setData] = useState([]);

  const fetchTableData = () => {
    fetch(`http://${process.env.NEXT_PUBLIC_DB_HOST}:8790/bi-sales/gross-profit`)
      .then((response) => response.json())
      .then((responseData) => {
        setTableData(responseData.productIntel);
      })
      .catch((error) => console.warn(error));
  };

  const fetchGraphData = () => {
    fetch(
      `http://${process.env.NEXT_PUBLIC_DB_HOST}:8790/bi-sales/product/?productName=${encodeThatProduct(
        inputValue
      )}`
    )
      .then((response) => response.json())
      .then((responseData) => {
        const d = parseInt(responseData);
        if (d > 0) {
          setData((arr) => [
            ...arr,
            { time: new Date().getSeconds(), amount: d },
          ]);
        }
      })
      .catch((error) => console.warn(error));
  };
  const columns = [
    {
      field: "productName",
      headerName: "Product Name",
      width: 125,
      minWidth: 150,
      maxWidth: 200,
    },
    {
      field: "total",
      headerName: "total",
      width: 125,
      minWidth: 150,
      maxWidth: 200,
    },
  ];

  const rows = [];
  function createData(productName, total) {
    return { productName, total };
  }

  tableData.map((product) =>
    rows.push(createData(product.productName, product.total))
  );

  function currencyFormat(num) {
    const new_num = Number(num);
    return "$" + new_num.toFixed(2).replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
  }
  const fetchProductNames = () => {
    fetch(`http://${process.env.NEXT_PUBLIC_DB_HOST}:8788/productInventory`)
      .then((response) => response.json())
      .then((data) => {
        setProductList(data._embedded.productList);
      })
      .catch((error) => console.warn(error));
  };

  const fetchSalesData = () => {
    if (check) {
      fetch(
        `http://${process.env.NEXT_PUBLIC_DB_HOST}:8790/bi-sales/product/?productName=${encodeThatProduct(
          inputValue
        )}`
      )
        .then((response) => response.json())
        .then((responseData) => {
          setSalesAmount(responseData);
          setCheck(false);
          setData([]);
        })
        .catch((error) => console.warn(error));
    } else {
      fetchGraphData();
    }
  };

  useEffect(() => {
    fetchProductNames();
    setInterval(() => {
      fetchTableData();
    }, 1000);
  }, []);

  return (
    <div>
      <Container maxWidth={false} sx={{ mt: 4, mb: 4 }}>
        <Grid container spacing={3}>
          <Grid item xs={12} md={7} lg={7}> 
            <Paper
              sx={{ p: 2, display: "flex", flexDirection: "column", height: 150 }}
            >
              <Title>Look Up Sales By Product</Title>

              <form>
                <Autocomplete
                  disableClearable
                  inputValue={product}
                  onChange={(e, v) => setProduct(`${v.name}: ${v.id}`)}
                  getOptionLabel={(option) => `${option.name}: ${option.id}`}
                  isOptionEqualToValue={(option, value) =>
                    option.name === value.name
                  }
                  onInputChange={(event, newInputValue) => {
                    setInputValue(
                      newInputValue.substring(0, newInputValue.indexOf(":"))
                    );
                    setDate(new Date().toLocaleString());
                    setProductName(newInputValue);
                    if (!check) {
                      setData((arr) => []);
                      setCheck(true);
                    }
                    console.log(data);
                  }}
                  onClick={fetchSalesData()}
                  disablePortal
                  id="combo-box-demo"
                  options={productList}
                  fullWidth
                  renderInput={(params) => (
                    <div>
                      <TextField {...params} label="Product" />
                      <br />
                    </div>
                  )}
                />
              </form>
            </Paper>
          </Grid>
          <Grid item xs={12} md={5} lg={5} sx={{p: 0}}> 
            <Paper
              sx={{ p: 2, display: "flex", flexDirection: "column", height: 150 }}
            >
              <Title>{productName}</Title>
              <Typography component="p" variant="h4">
                {currencyFormat(salesAmount)}
              </Typography>
              <Typography id="d" color="text.secondary" sx={{ flex: 1 }}>
                {date}
              </Typography>
            </Paper>
          </Grid>
          <Grid item xs={12} md={5} lg={5}>
            <Paper sx={{ p: 2, display: "flex", flexDirection: "column" }}>
              <Title>Products Revenue</Title>
              <div style={{ height: 400, width: "100%" }}>
                <DataGrid
                  rows={rows}
                  columns={columns}
                  pageSize={5}
                  rowsPerPageOptions={[5]}
                  getRowId={(rows) => rows.productName}
                />
              </div>
            </Paper>
          </Grid>
          <Grid item xs={12} md={7} lg={7}>
            <Paper
              sx={{
                p: 2,
                display: "flex",
                flexDirection: "column",
                height: 470,
              }}
            >
              <Title>{productName} Graph</Title>
              <ResponsiveContainer>
                <LineChart
                  data={data}
                  margin={{
                    top: 16,
                    right: 16,
                    bottom: 30,
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
                        textAnchor: "middle",
                        fill: theme.palette.text.primary,
                        ...theme.typography.body1,
                      }}
                    >
                      Product Sales ($)
                    </Label>
                  </YAxis>
                  <Line
                    isAnimationActive={true}
                    type="monotone"
                    dataKey="amount"
                    stroke={theme.palette.primary.main}
                    dot={true}
                  />
                </LineChart>
              </ResponsiveContainer>
            </Paper>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
}
