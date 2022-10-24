import * as React from "react";
import { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";

export default function GrossTable() {
  const [keyword, setKeyword] = useState("bi-sales");
  const [data, setData] = useState([]);
  const fetchData = () => {
    fetch(`http://localhost:8790/bi-sales/gross-profit`)
      .then((response) => response.json())
      .then((data) => setData(data.productIntel))
      .catch((err) => console.error(err));
  };
  
  useEffect(() => {
    setInterval(() => {
      fetchData();
    }, 1000);
  }, []);

  const columns = [
    { field: "productName", headerName: "Product Name", width: 125, minWidth: 150, maxWidth: 200 },
    { field: "total", headerName: "total", width: 125, minWidth: 150, maxWidth: 200 },
  ];

  

  const rows = [];
  function createData(productName, total) {
    return {productName, total};
  }

  

  data.map((product) =>
    rows.push(
      createData(product.productName, product.total)
    )
  );

  return (
    <div style={{ height: 400, width: "100%" }}>
      {/* <Title>Suppliers</Title> */}
      <DataGrid
        rows={rows}
        columns={columns}
        pageSize={5}
        rowsPerPageOptions={[5]}
        getRowId={(rows) => rows.productName}
      />
      
    </div>
  );
}
