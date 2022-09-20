import * as React from "react";
import { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import Title from "./Title";

export default function StoresTable() {
  const [keyword, setKeyword] = useState("sales");
  const [data, setData] = useState([]);
  const fetchData = () => {
    fetch(`http://localhost:8789/${keyword}/online`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.onlineSaleList))
      .catch((err) => console.error(err));
  };
  

  useEffect(() => {
    fetchData();
  }, []);

  const columns = [
    { field: "id", headerName: "ID", width: 100, minWidth: 75, maxWidth: 200 },
    { field: "customerName", headerName: "Customer Name", width: 125, minWidth: 150, maxWidth: 200 },
    { field: "customerAddress", headerName: "Customer Address", width: 250, minWidth: 200, maxWidth: 600 },

    { field: "productName", headerName: "Product Name", width: 125, minWidth: 150, maxWidth: 200 },
    { field: "quantity", headerName: "Quantity", width: 100, minWidth: 50, maxWidth: 200},
    { field: "orderStatus", headerName: "Order Status", width: 200, minWidth: 150, maxWidth: 200 }
    
  ];

  const rows = [];
  function createData(id, customerName, customerAddress, productName, orderStatus, quantity) {
    return {id, customerName, customerAddress, productName, orderStatus, quantity};
  }

  data.map((sale) =>
    rows.push(
      createData(sale.id, sale.customerName, sale.address, sale.productName, sale.orderStatus, sale.quantity)
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
        // checkboxSelection
      />
    </div>
  );
}