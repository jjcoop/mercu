import * as React from "react";
import { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";

export default function StoreSalesTable() {
  const [keyword, setKeyword] = useState("sales");
  const [data, setData] = useState([]);
  const fetchData = () => {
    fetch(`http://localhost:8789/${keyword}/store`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.storeList))
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    fetchData();
  }, []);

  const columns = [
    { field: "id", headerName: "Store ID", width: 100, minWidth: 75, maxWidth: 200 },
    { field: "sale_id", headerName: "Sale ID", width: 100, minWidth: 75, maxWidth: 200 },
    { field: "productName", headerName: "Product Name", width: 250, minWidth: 200, maxWidth: 400 },
    { field: "quantity", headerName: "Quantity", width: 100, minWidth: 50, maxWidth: 200 },
    { field: "date", headerName: "Date", width: 100, minWidth: 75, maxWidth: 200 },
    { field: "orderStatus", headerName: "Order Status", width: 200, minWidth: 150, maxWidth: 200 },
  ];

  const rows = [];
  function createData(id, sale_id, productName, orderStatus, quantity, date) {
    return { id, sale_id, productName, orderStatus, quantity, date};
  }

  data.map((store) =>
    store.sales.map((c) =>

      rows.push(
        createData(store.id, c.id, c.productName, c.orderStatus, c.quantity, c.dateTime.substring(0, 10))
      )
    )
  );

  return (
    <div style={{ height: 400, width: "100%" }}>
      {/* <Title>Contacts</Title> */}
      <DataGrid
        sx={{
          "@media print": {
            ".MuiDataGrid-main": { color: "rgba(0, 0, 0, 0.87)" },
          },
        }}
        rows={rows}
        columns={columns}
        pageSize={5}
        rowsPerPageOptions={[5]}
        // checkboxSelection
      />
    </div>
  );
}
