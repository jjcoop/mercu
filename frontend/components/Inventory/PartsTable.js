import * as React from "react";
import { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";

export default function PartsTable() {
  const [keyword, setKeyword] = useState("productInventory");
  const [data, setData] = useState([]);
  const [sortModel, setSortModel] = React.useState([
    {
      field: 'id',
      sort: 'desc',
    },
  ]);
  const fetchData = () => {
    fetch(`http://localhost:8788/${keyword}`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.productList))
      .catch((err) => console.error(err));
  };
  
  useEffect(() => {
    setInterval(() => {
      fetchData();
    }, 1000);
  }, []);

  const columns = [
    { field: "partID", headerName: "Product ID", width: 125, minWidth: 150, maxWidth: 200 },
    { field: "id", headerName: "Part ID", width: 125, minWidth: 150, maxWidth: 200 },
    { field: "partName", headerName: "Part Name", width: 250, minWidth: 150, maxWidth: 250},
    { field: "partDescription", headerName: "Part Description", width: 500, minWidth: 150, maxWidth: 500},
    { field: "manufacturer", headerName: "Manufacturer", width: 300, minWidth: 150, maxWidth: 300},
    { field: "quantity", headerName: "Quantity", width: 125, minWidth: 150, maxWidth: 200},
  ];

  const rows = [];
  function createData(partID, id, partName, partDescription, manufacturer, quantity) {
    return {partID, id, partName, partDescription, manufacturer, quantity};
  }

  data.map((product) =>
      product.parts.map((c) =>
        rows.push(
          createData(product.id, c.id, c.partName, c.partDescription, c.manufacturer, c.quantity)
        )
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
        sortModel={sortModel}
        onSortModelChange={(model) => setSortModel(model)}
      />
    </div>
  );
}